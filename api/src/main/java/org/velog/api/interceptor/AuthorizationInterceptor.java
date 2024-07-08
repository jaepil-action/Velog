package org.velog.api.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.error.TokenErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.token.business.TokenBusiness;

import java.util.Objects;

import static org.velog.api.domain.session.AuthorizationTokenService.ADMIN_TOKEN;
import static org.velog.api.domain.session.AuthorizationTokenService.USER_TOKEN;
import static org.velog.api.domain.token.service.TokenService.USER_ID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness  tokenBusiness;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor url : {}",request.getRequestURI());

        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        if(handler instanceof ResourceHttpRequestHandler){
            return true;
        }


        Cookie[] cookies = request.getCookies();
        String accessToken = null;

        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(USER_TOKEN.equals(cookie.getName())){
                   accessToken = cookie.getValue();
                }
                if(ADMIN_TOKEN.equals(cookie.getName())){
                    accessToken = cookie.getValue();
                }
            }
            if(accessToken == null){
                throw new ApiException(TokenErrorCode.AUTHORIZATION_TOKEN_NOT_FOUND);
            }
        }

        Long userId = tokenBusiness.validationToken(accessToken);
        if(userId != null){
            RequestAttributes requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes());
            requestContext.setAttribute(USER_ID, userId, RequestAttributes.SCOPE_REQUEST);
            return true;
        }

        throw new ApiException(ErrorCode.BAD_REQUEST, "사용자 인증 실패");
    }
}
