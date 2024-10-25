package org.velog.api.domain.blog.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.BlogErrorCode;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.db.blog.BlogEntity;
import org.velog.db.blog.BlogEntityRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BlogService {

    private final BlogEntityRepository blogEntityRepository;

    public BlogEntity register(
            BlogEntity request
    ){
        return Optional.ofNullable(request)
                .map(it -> {
                    request.addBlogTitle(request.getBlogTitle());
                    return blogEntityRepository.save(request);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public BlogEntity getBlogByLoginId(
            String loginId
    ){
        return blogEntityRepository.findBlogByUserLoginId(loginId)
                .orElseThrow(() -> new ApiException(BlogErrorCode.BLOG_NOT_FOUND));
    }

    public BlogEntity getBlogByUserIdWithThrow(
            Long userId
    ){
        return blogEntityRepository.findByUserEntity_Id(userId)
                .orElseThrow(() -> new ApiException(BlogErrorCode.BLOG_NOT_FOUND));
    }

    public BlogEntity getBlogByIdWithThrow(
            Long blogId
    ){
        return blogEntityRepository.findById(blogId)
                .orElseThrow(() -> new ApiException(BlogErrorCode.BLOG_NOT_FOUND));
    }

    public void deleteBlogByUserId(
            Long userId
    ){
        blogEntityRepository.deleteByUserEntity_Id(userId);
    }
}
