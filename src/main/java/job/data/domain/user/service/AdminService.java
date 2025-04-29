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
public class AdminService {

    private final UserRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public User postLogin(User admin) {
        User findByLoginId = adminRepository.findByLoginId(admin.getLoginId()).orElse(null);
        if (findByLoginId == null) {
            admin.setErrorMessage("아이디가 존재하지 않습니다.");
        }

        if (!passwordEncoder.matches(admin.getPassword(), findByLoginId.getPassword())) {
            admin.setErrorMessage("비밀번호가 존재하지 않습니다.");
        }

        if (!findByLoginId.isAuthority()) {
            admin.setErrorMessage("권한이 부여되지 않았습니다.");
        }

        if (admin.getPassword().isEmpty()) {
            admin.setErrorMessage("성공");
        }
        return admin;
    }

    public User postJoin(User admin) {
        User findByLoginId = adminRepository.findByLoginId(admin.getLoginId()).orElse(null);
        boolean flag = true;
        if (findByLoginId != null) {
            admin.setErrorMessage("이미 아이디가 존재합니다.");
            flag = false;
        }

        if (!admin.getPasswordCheck().equals(admin.getPassword())) {
            admin.setErrorMessage("비밀번호가 일치하지 않습니다.");
            flag = false;
        }


        if (flag) {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setErrorMessage("성공");
            admin.setUserId(UUID.randomUUID().toString());
            admin.setAuthority(false);
            admin.setType("관리자");
            adminRepository.save(admin);
        }
        return admin;
    }


    public void updateAuthority(String adminId){
        User admin = adminRepository.findById(adminId).orElse(null);
        if(admin.isAuthority()){
            admin.setAuthority(false);
        }else{
            admin.setAuthority(true);
        }
    }
}
