package job.data.domain.user.service;

import jakarta.transaction.Transactional;
import job.data.domain.user.domain.User;
import job.data.domain.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public User postLogin(User user){
        User findByLoginId = userRepository.findByLoginId(user.getLoginId()).orElse(null);
        if(findByLoginId==null){
            user.setErrorMessage("아이디가 존재하지 않습니다.");
        }

        if (!passwordEncoder.matches(user.getPassword(), findByLoginId.getPassword())) {
            user.setErrorMessage("비밀번호가 존재하지 않습니다.");
        }

        if(user.getPassword().isEmpty()){
            user.setErrorMessage("성공");
        }
        return user;
    }

    public User postJoin(User user){
        User findByLoginId = userRepository.findByLoginId(user.getLoginId()).orElse(null);
        boolean flag = true;
        if(findByLoginId!=null){
            user.setErrorMessage("이미 아이디가 존재합니다.");
        flag=false;
        }

        if (!user.getPasswordCheck().equals(user.getPassword())) {
            user.setErrorMessage("비밀번호가 일치하지 않습니다.");
        flag=false;
        }

        if(flag){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setErrorMessage("성공");
            user.setUserId(UUID.randomUUID().toString());
            user.setAuthority(false);
            user.setType("기업");
            userRepository.save(user);
        }
        return user;
    }
}
