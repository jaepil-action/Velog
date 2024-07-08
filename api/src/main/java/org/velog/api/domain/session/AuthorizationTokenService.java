package org.velog.api.domain.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.error.TokenErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.session.ifs.AuthorizationServiceIfs;
import org.velog.api.domain.token.business.TokenBusiness;
import org.velog.api.domain.token.controller.model.TokenResponse;
import org.velog.db.user.UserEntity;

/***
 * 1. 쿠키 도입
 * 2. 토큰 도입
 * 3. 오디트 도입
 * 4. 헤테오스 링크 도입
 * 5. 테스트 코드 도입
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorizationTokenService implements AuthorizationServiceIfs {

    private final  TokenBusiness tokenBusiness;
    public static final String USER_TOKEN = "userToken";
    public static final String ADMIN_TOKEN = "adminToken";

    public void createUserCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            UserEntity loginUser
    ){
        TokenResponse token = tokenBusiness.issueToken(loginUser);
        createCookie(response, token, USER_TOKEN);
    }

    public void createAdminCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            UserEntity loginUser
    ){
        TokenResponse token = tokenBusiness.issueToken(loginUser);
        createCookie(response, token, ADMIN_TOKEN);
    }

    private static void createCookie(HttpServletResponse response, TokenResponse token, String tokenKey) {
        Cookie cookie = new Cookie(tokenKey, token.getAccessToken());
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(60 * 60);
        response.addCookie(cookie);
    }

    public void deleteSession(HttpServletRequest request){

        HttpSession session = request.getSession(false);

        if(session != null){
            session.invalidate();
        }else{
            throw new ApiException(ErrorCode.NULL_POINT);
        }
    }

    public void deleteCookie(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(USER_TOKEN.equals(cookie.getName()) || ADMIN_TOKEN.equals(cookie.getName())){
                    cookie.setPath("/");
                    cookie.setMaxAge(0); // 유효 시간 0으로 설정 (즉시 만료)
                    cookie.setValue(""); // 값을 빈 문자열로 설정
                    response.addCookie(cookie); // 수정된 쿠키를 응답에 추가
                }
            }
        }
    }

    public void expiredCookie(
            HttpServletRequest request,
            HttpServletResponse response
    ){
        deleteCookie(request, response);
    }


    public void validateRoleAdmin(HttpServletRequest request) {

        String accessToken = getAccessToken(request, ADMIN_TOKEN);
        tokenBusiness.validationToken(accessToken);
    }

    public Long validateRoleUserGetId(HttpServletRequest request) {

        String accessToken = getAccessToken(request, USER_TOKEN);
        log.info("===========token={}", accessToken);
        return tokenBusiness.validationToken(accessToken);
    }

    private String getAccessToken(HttpServletRequest request, String tokenKey){
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : cookies) {
                if(tokenKey.equals(cookie.getName())){
                    return cookie.getValue();
                }
            }
        }
        throw new ApiException(TokenErrorCode.INVALID_TOKEN);
    }
}
