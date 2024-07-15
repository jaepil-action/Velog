package org.velog.view.domain.blog;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.annotation.Login;
import org.velog.api.domain.blog.business.BlogBusiness;
import org.velog.api.domain.blog.controller.model.BlogDetailResponse;
import org.velog.api.domain.blog.controller.model.BlogRegisterRequest;
import org.velog.api.domain.user.model.UserDto;

@Controller
@Slf4j
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogBusiness blogBusiness;

    @GetMapping("/create")
    public String writeForm(
            Model model,
            @Login UserDto user
    ){
        model.addAttribute("user", user);
        model.addAttribute("blog", new BlogRegisterRequest());
        return "/velog.io/createForm";
    }

    @PostMapping("/create")
    public String createBlog(
            @Login UserDto user,
            @Valid @ModelAttribute BlogRegisterRequest blogRegisterRequest,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "/blogs/registerForm";
        }

        blogBusiness.register(blogRegisterRequest, user);
        return "redirect:/";
    }

    @Operation(summary = "특정 블로그 조회 API", description = "LoginId 블로그 조회")
    @GetMapping("/{loginId}")
    public String retrieveBlogByLoginId(
            Model model,
            @PathVariable String loginId
    ){
        BlogDetailResponse response = blogBusiness.retrieveBlogByLoginId(loginId);
        model.addAttribute("blog", response);
        return "/blogs/details";
    }


    @Operation(summary = "블로그 삭제 API", description = "로그인한 사용자 블로그 삭제")
    @PostMapping("/delete")
    public String deleteBlog(
            HttpServletRequest request
    ){
       // blogBusiness.delete(request);
        return "redirect:/";
    }
}
