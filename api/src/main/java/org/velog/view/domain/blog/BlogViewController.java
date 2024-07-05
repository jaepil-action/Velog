package org.velog.view.domain.blog;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.velog.api.common.api.Api;
import org.velog.api.domain.blog.business.BlogBusiness;
import org.velog.api.domain.blog.controller.model.BlogDetailResponse;
import org.velog.api.domain.blog.controller.model.BlogRegisterRequest;
import org.velog.api.domain.blog.controller.model.BlogResponse;

@Controller
@Slf4j
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogBusiness blogBusiness;

    @Operation(summary = "블로그 생성 API", description = "로그인한 사용자 블로그 생성")
    @PostMapping("/create")
    public String createBlog(
            HttpServletRequest request,
            @Valid @ModelAttribute BlogRegisterRequest blogRegisterRequest,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            log.info("errors={}", bindingResult);
            return "/blogs/registerForm";
        }

        blogBusiness.register(blogRegisterRequest, request);
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
        blogBusiness.delete(request);
        return "redirect:/";
    }
}
