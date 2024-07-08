package org.velog.api.domain.session.ifs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.velog.db.user.UserEntity;

public interface AuthorizationServiceIfs {

    void createUserCookie(HttpServletRequest request, HttpServletResponse response, UserEntity loginUser);

    void createAdminCookie(HttpServletRequest request, HttpServletResponse response, UserEntity loginUser);

    void deleteSession(HttpServletRequest request);

    void deleteCookie(HttpServletRequest request, HttpServletResponse response);
    void expiredCookie(HttpServletRequest request, HttpServletResponse response);
    void validateRoleAdmin(HttpServletRequest request);
    Long validateRoleUserGetId(HttpServletRequest request);
}
