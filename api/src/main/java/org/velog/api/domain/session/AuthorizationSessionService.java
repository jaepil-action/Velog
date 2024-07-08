package org.velog.api.domain.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.session.ifs.AuthorizationServiceIfs;
import org.velog.db.user.UserEntity;

/***
 * 1. 쿠키 도입
 * 2. 토큰 도입
 * 3. 오디트 도입
 * 4. 헤테오스 링크 도입
 * 5. 테스트 코드 도입
 */

//@Service
public class AuthorizationSessionService implements AuthorizationServiceIfs {

    public static final String LOGIN_USER_ID = "loginUserId";
    public static final String LOGIN_ADMIN_ID = "loginAdminId";
    public static final String USER_COOKIE = "userSession";
    public static final String ADMIN_COOKIE = "adminSession";

    public void createUserCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            UserEntity loginUser
    ){
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER_ID, loginUser.getId());

        createCookie(response, session, USER_COOKIE);
    }

    public void createAdminCookie(
            HttpServletRequest request,
            HttpServletResponse response,
            UserEntity loginUser
    ){
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_ADMIN_ID, loginUser.getId());

        createCookie(response, session, ADMIN_COOKIE);
    }

    private static void createCookie(HttpServletResponse response, HttpSession session, String resource) {
        Cookie cookie = new Cookie(resource, session.getId());
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
                if(USER_COOKIE.equals(cookie.getName()) || ADMIN_COOKIE.equals(cookie.getName())){
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
        deleteSession(request);
        deleteCookie(request, response);
    }


    public void validateRoleAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(ADMIN_COOKIE) == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "관리자 권한이 없습니다");
        }
    }

    public Long validateRoleUserGetId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(USER_COOKIE) == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "일반 사용자 권한이 없습니다");
        }
        return (Long) session.getAttribute(LOGIN_USER_ID);
    }
}
