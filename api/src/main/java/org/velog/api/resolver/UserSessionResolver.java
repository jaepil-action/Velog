package org.velog.api.resolver;

import lombok.RequiredArgsConstructor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.velog.api.common.annotation.Login;
import org.velog.api.domain.user.model.UserDto;
import org.velog.api.domain.user.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.velog.db.user.ProfileImage;
import org.velog.db.user.UserEntity;

import java.util.Optional;

import static org.velog.api.domain.token.service.TokenService.USER_ID;


@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크, 어노테이션 체크

        // 1. 어노테이션이 있는지 체크
        boolean annotation = parameter.hasParameterAnnotation(Login.class);
        boolean parameterType = parameter.getParameterType().equals(UserDto.class);

        return (annotation && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // support parameter 에서 ture 반환시 여기 실행

        // request context holder 에서 찾아오기
        RequestAttributes requestContext = RequestContextHolder.getRequestAttributes();
        Object userId = requestContext.getAttribute(USER_ID, RequestAttributes.SCOPE_REQUEST);

        UserEntity userEntity = userService.getUserWithThrow(Long.parseLong(userId.toString()));

        String profileImageUrl = Optional.ofNullable(userEntity.getProfileImage())
                .map(ProfileImage::getProfileImageUrl)
                .orElse(null);

        return UserDto.builder()
                .userId(userEntity.getId())
                .blogId(userEntity.getBlogEntity().getId())
                .loginId(userEntity.getName())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .profileImageUrl(profileImageUrl)
                .registrationDate(userEntity.getRegistrationDate())
                .lastModifiedDate(userEntity.getLastModifiedDate())
                .build();
    }
}
