package job.data.domain.user.presentation;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import job.data.domain.user.domain.User;
import job.data.domain.user.domain.UserRepository;
import job.data.domain.user.service.AdminService;
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
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;

    @GetMapping("/login")
    public String getAdminLogin(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/login";
    }


    @GetMapping("/join")
    public String getAdminJoin(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "admin/join";
    }


    @PostMapping("/login")
    public String postLogin(@Validated @ModelAttribute User user,
                            BindingResult bindingResult,
                            HttpServletRequest request) {

        User findAdmin = adminService.postLogin(user);

        if (!findAdmin.getErrorMessage().equals("성공")) {
            bindingResult.reject("일치하지 않습니다.", findAdmin.getErrorMessage());
        }

        //로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, findAdmin);

        return "redirect:/";
    }


    @PostMapping("/join")
    public String postJoin(@Validated @ModelAttribute User user,
                           BindingResult bindingResult) {

        User findAdmin = adminService.postJoin(user);

        if (!findAdmin.getErrorMessage().equals("성공")) {
            bindingResult.reject("존재하지 않습니다.", findAdmin.getErrorMessage());
        }

        return "redirect:/admin/login";
    }


    @GetMapping("/list")
    public String getAdminList(Model model, @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)
    User loginUser) {

        if(loginUser.getType().equals("기업") || !loginUser.isAuthority()){
            return "redirect:/";
        }
        model.addAttribute("admin", userRepository.findAll());

        return "admin/list";
    }

    @PostMapping("/list/{id}")
    public String postAdminList(@PathVariable("id") String adminId){


        adminService.updateAuthority(adminId);
        return "redirect:/admin/list";
    }


}
