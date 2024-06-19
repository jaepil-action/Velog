package org.velog.api.domain.user.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.error.UserErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.db.user.UserEntity;
import org.velog.db.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final String LOGIN_USER = "loginUser";
    private final UserRepository userRepository;

    public UserEntity register(UserEntity userEntity){
        return Optional.ofNullable(userEntity)
                .map(it -> {
                    userEntity.setRegistrationDate(LocalDateTime.now());
                    return userRepository.save(userEntity);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "User Entity null"));
    }

    public UserEntity login(
            String loginId,
            String password
    ){
        return getUserWithThrow(loginId, password);
    }

    public UserEntity getUserWithThrow(
            String loginId,
            String password
    ){
        return userRepository.findFirstByLoginIdAndPasswordOrderByIdDesc(
                loginId,
                password
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public UserEntity getUserWithThrow(
            Long userId
    ){
        return userRepository.findFirstByIdOrderByIdDesc(
                userId
        ).orElseThrow(()-> new ApiException(UserErrorCode.USER_NOT_FOUND));
    }

    public void createSession(HttpServletRequest request, UserEntity loginUser){
        HttpSession session = request.getSession();
        session.setAttribute(LOGIN_USER, loginUser);
    }
}
