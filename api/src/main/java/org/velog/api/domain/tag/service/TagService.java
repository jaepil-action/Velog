package org.velog.api.domain.tag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.db.tag.TagEntity;
import org.velog.db.tag.TagRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final TagRepository tagRepository;

    public TagEntity register(
            TagEntity tagEntity
    ){
        return Optional.ofNullable(tagEntity)
                .map(it ->{
                    it.setRegistrationDate();
                    it.setCountZero();
                    return tagRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "Tag Entity Null"));
    }

    public TagEntity getTagWithThrow(
            Long tagId
    ){
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
    }
}
