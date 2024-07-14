package org.velog.view.open;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.user.business.UserBusiness;
import org.velog.api.domain.user.controller.model.UserLoginRequest;
import org.velog.api.domain.user.controller.model.UserResponse;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserBusiness userBusiness;

    @GetMapping("/login")
    public String loginForm(
            @ModelAttribute("loginForm") UserLoginRequest loginForm
    ){
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(
            @Validated @ModelAttribute("loginForm") UserLoginRequest loginForm,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ){
        if(bindingResult.hasErrors()){
            log.info("login Errors={}", bindingResult);
            return "login/loginForm";
        }

        try{
            String loginId = userBusiness.login(loginForm, request, response).getLoginId();
            redirectAttributes.addAttribute("loginId", loginId);
            return "redirect:/@{loginId}";
        }catch (ApiException e){
            bindingResult.reject("LoginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
    }

    @PostMapping("/logout")
    public String logout(
            HttpServletRequest request
    ){
        HttpSession session = request.getSession();
        if(session != null){
            session.invalidate();
        }

        return "redirect:/";
    }
}
