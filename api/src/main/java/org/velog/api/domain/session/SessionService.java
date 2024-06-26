package org.velog.api.domain.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.db.user.UserEntity;

@Service
public class SessionService {

    public static final String LOGIN_USER = "loginUser";
    public static final String LOGIN_USER_ID = "loginUserId";
    public static final String LOGIN_ADMIN = "adminUser";
    public static final String LOGIN_ADMIN_ID = "loginAdminId";

    public void createUserSession(HttpServletRequest request, UserEntity loginUser){
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, loginUser);
        session.setAttribute(LOGIN_USER_ID, loginUser.getId());
    }

    public void createAdminSession(HttpServletRequest request, UserEntity loginUser){
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_ADMIN, loginUser);
        session.setAttribute(LOGIN_USER, loginUser);
        session.setAttribute(LOGIN_ADMIN_ID, loginUser.getId());
    }

    public void deleteSession(HttpSession session){
        if(session != null){
            session.invalidate();
        }else{
            throw new ApiException(ErrorCode.NULL_POINT);
        }
    }

    public void validateRoleAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session.getAttribute(LOGIN_ADMIN) == null){
            throw new ApiException(ErrorCode.BAD_REQUEST, "관리자 권한이 없습니다");
        }
    }

    public UserEntity validateRoleUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(LOGIN_USER) == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "일반 사용자 권한이 없습니다");
        }
        return (UserEntity) session.getAttribute(LOGIN_USER);
    }

    public Long validateRoleUserId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(LOGIN_USER_ID) == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "일반 사용자 권한이 없습니다");
        }
        return (Long) session.getAttribute(LOGIN_USER_ID);
    }

    public String validateRoleUserLoginId(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session.getAttribute(LOGIN_USER) == null) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "일반 사용자 권한이 없습니다");
        }
        UserEntity userEntity = (UserEntity) session.getAttribute(LOGIN_USER);
        return userEntity.getLoginId();
    }
}
