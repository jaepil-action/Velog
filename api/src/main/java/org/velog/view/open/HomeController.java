package org.velog.view.open;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.velog.api.common.annotation.Login;
import org.velog.api.domain.blog.business.BlogBusiness;
import org.velog.api.domain.post.business.PostBusiness;
import org.velog.api.domain.post.controller.model.PostsDetailPageResponse;
import org.velog.api.domain.user.model.UserDto;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PostBusiness postBusiness;
    private final BlogBusiness blogBusiness;


    @GetMapping("/")
    public String home(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "latest") String sortCond
    ){
        PostsDetailPageResponse detailPosts = postBusiness.getDetailPosts(page, size, sortCond);
        model.addAttribute("posts", detailPosts);
        return "home";
    }

    @GetMapping("/{loginId}")
    public String loginHome(
            Model model,
            //@PathVariable String loginId,
            @Login UserDto user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "latest") String sortCond
    ){
        PostsDetailPageResponse detailPosts = postBusiness.getDetailPosts(page, size, sortCond);
        model.addAttribute("posts", detailPosts);
        model.addAttribute("user", user);
        model.addAttribute("myUri", "/users/" + user.getLoginId() + "/myPage");

        if(Objects.equals(user.getBlogId(), null)){
            return "unCreatedVelogHome";
        }else{
            return "loginHome";
        }
    }
}
