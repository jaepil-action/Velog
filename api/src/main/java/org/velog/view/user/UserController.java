package org.velog.view.user;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.velog.api.common.annotation.Login;
import org.velog.api.common.api.Api;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.user.business.UserBusiness;
import org.velog.api.domain.user.controller.model.*;
import org.velog.api.domain.user.model.UserDto;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserBusiness userBusiness;

    @GetMapping("/register")
    public String registerForm(@ModelAttribute("user") UserRegisterRequest request){
        return "/users/registerForm";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("user")
            UserRegisterRequest request,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            log.info("form errors={}", bindingResult);
            return "/users/registerForm";
        }

        try{
            userBusiness.register(request);
        }catch(ApiException e){
            bindingResult.reject(e.getErrorCodeIfs().getDescription(), e.getErrorDescription());
            return "/users/registerForm";
        }

        return "/velog.io/velog";
    }

    @GetMapping("/@{loginId}/myPage")
    public String myPage(
            Model model,
            @Login UserDto userDto
    ){
       // UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "/users/myPage";
    }

    @GetMapping("/{id}/update")
    public String updateForm(
            @PathVariable Long id,
            @Login UserDto userDto,
            Model model
    ){
        model.addAttribute("user", userDto);
        return "/users/editForm";
    }

    @PostMapping("{userId}/update")
    public String editEmail(
            HttpServletRequest request,
            @ModelAttribute("user") UserDto user,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){

        if(bindingResult.hasErrors()){
            log.info("form errors={}", bindingResult);
            return "/users/editForm";
        }

        try{
            userBusiness.editUser(request, user.getEmail());
            redirectAttributes.addAttribute("loginId", user.getLoginId());
            return "redirect:/users/@{loginId}/myPage";
        }catch (ApiException e){
            bindingResult.reject(e.getErrorCodeIfs().getDescription(), e.getErrorDescription());
            redirectAttributes.addAttribute("id", user.getUserId());
            return "redirect:/users/{id}/update";
        }
    }

    @PostMapping("/{userId}/delete")
    public String delete(
            HttpServletRequest request,
            HttpServletResponse response,
            @Login UserDto user
    ){
        userBusiness.deleteUser(user.getPassword(), request, response);
        return "redirect:/";
    }
}
