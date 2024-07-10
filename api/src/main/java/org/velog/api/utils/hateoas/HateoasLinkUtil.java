package org.velog.api.utils.hateoas;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.comment.controller.CommentApiController;
import org.velog.api.domain.follow.controller.FollowApiController;
import org.velog.api.domain.like.controller.LikeApiController;
import org.velog.api.domain.token.business.TokenBusiness;
import org.velog.api.domain.user.controller.UserOpenApiController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.velog.api.domain.session.AuthorizationTokenService.ADMIN_TOKEN;
import static org.velog.api.domain.session.AuthorizationTokenService.USER_TOKEN;

//@Component
@RequiredArgsConstructor
public class HateoasLinkUtil {

    private final TokenBusiness tokenBusiness;

    public void addCommonLinks(
        EntityModel<?> resource,
        HttpServletRequest request
    ){
        if(!isAuthenticated(request)){ // 비로그인 사용자 일경우

            Link loginLink = linkTo(methodOn(UserOpenApiController.class)
                    .login(null, null, null)).withRel("login");
            Link signupLink = linkTo(methodOn(UserOpenApiController.class)
                    .register(null)).withRel("signup");

            resource.add(loginLink, signupLink);
        }else{ // 로그인 사용자

            Link addLike = linkTo(methodOn(LikeApiController.class)
                    .addLike(null, null)).withRel("addLike");
            Link follow = linkTo(methodOn(FollowApiController.class)
                    .userFollow(null, null)).withRel("follow");
            Link addComment = linkTo(methodOn(CommentApiController.class)
                    .createComments(null, null, null)).withRel("addComment");

            resource.add(addLike, follow, addComment);
        }
    }

    private boolean isAuthenticated(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String accessToken = null;

        if(cookies != null){
            accessToken = getAccessToken(cookies);

            if(accessToken != null){
                return validationTokenNotThrow(accessToken);
            }
        }
        return false;
    }

    private static String getAccessToken(Cookie[] cookies) {
        String accessToken = null;

        for (Cookie cookie : cookies) {
            if(USER_TOKEN.equals(cookie.getName())){
                accessToken = cookie.getValue();
            }
            if(ADMIN_TOKEN.equals(cookie.getName())){
                accessToken = cookie.getValue();
            }
        }
        return accessToken;
    }

    private boolean validationTokenNotThrow(String accessToken){
        try {
            tokenBusiness.validationToken(accessToken);
            return true;
        }catch (ApiException e){ // 토큰인증실패 했거나, 만료됬을경우
            return  false;
        }
    }
}
