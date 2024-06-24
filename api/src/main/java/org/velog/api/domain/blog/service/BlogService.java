package org.velog.api.domain.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.blog.controller.model.BlogRegisterRequest;
import org.velog.db.blog.BlogEntity;
import org.velog.db.blog.BlogEntityRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogEntityRepository blogEntityRepository;

    public BlogEntity register(
            BlogEntity request
    ){
        return Optional.ofNullable(request)
                .map(it -> {
                    request.setBlogTitle(request.getBlogTitle());
                    request.setRegistrationDate();
                    return blogEntityRepository.save(request);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public List<BlogEntity> getBlogByLoginIdWithThrow(
            String loginId
    ){
        return blogEntityRepository.findAllByUserEntity_LoginId(loginId);
    }
}
