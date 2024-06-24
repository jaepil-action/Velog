package org.velog.api.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.annotation.UserSession;
import org.velog.api.domain.user.model.User;
import org.velog.api.domain.user.service.UserService;
import org.velog.db.user.UserEntity;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static org.velog.api.domain.session.SessionService.LOGIN_USER;
import static org.velog.api.domain.session.SessionService.LOGIN_USER_ID;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크, 어노테이션 체크

        // 1. 어노테이션이 있는지 체크
        boolean annotation = parameter.hasParameterAnnotation(UserSession.class);
        boolean parameterType = parameter.getParameterType().equals(User.class);

        return (annotation && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // support parameter 에서 ture 반환시 여기 실행

        // request context holder 에서 찾아오기
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession();
        Object userId = session.getAttribute(LOGIN_USER_ID);

        UserEntity userEntity = userService.getUserWithThrow(Long.parseLong(userId.toString()));


        // 사용자 정보 셋팅
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .loginId(userEntity.getLoginId())
                .registrationDate(userEntity.getRegistrationDate())
                .build()
                ;
    }
}
