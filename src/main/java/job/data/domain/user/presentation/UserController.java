package job.data.domain.user.presentation;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import job.data.domain.user.domain.User;
import job.data.domain.user.service.UserService;
import job.data.global.config.session.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String getLogin(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "user/login";
    }


    @GetMapping("/join")
    public String getJoin(Model model){
        User user =new User();
        model.addAttribute("user",user);
        return "user/join";
    }


    @PostMapping("/login")
    public String postLogin(@Validated @ModelAttribute User user,
                            BindingResult bindingResult,
                            @RequestParam(defaultValue = "/") String redirectURL,
                            HttpServletRequest request){

        User findUser = userService.postLogin(user);

        if(!findUser.getErrorMessage().equals("성공")){
            bindingResult.reject("일치하지 않습니다.", findUser.getErrorMessage());
        }

        //로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, findUser);

        return "redirect:" + redirectURL;
    }


    @PostMapping("/join")
    public String postJoin(@Validated @ModelAttribute User user,
                            BindingResult bindingResult){

        User findUser = userService.postJoin(user);

        if(!findUser.getErrorMessage().equals("성공")){
            bindingResult.reject("존재하지 않습니다.", findUser.getErrorMessage());
        }

        return "redirect:/user/login";
    }



}
