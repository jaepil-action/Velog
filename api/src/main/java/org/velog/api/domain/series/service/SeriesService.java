package org.velog.api.domain.series.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.ObjectError;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.series.controller.model.SeriesRequest;
import org.velog.db.blog.BlogEntity;
import org.velog.db.series.SeriesEntity;
import org.velog.db.series.SeriesRepository;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeriesService {

    private final SeriesRepository seriesRepository;

    public SeriesEntity register(
            SeriesEntity seriesEntity
    ){
        return Optional.ofNullable(seriesEntity)
                .map(it -> {
                    it.setRegistrationDate();
                    return seriesRepository.save(it);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public SeriesEntity getSeriesWithThrow(
            Long seriesId
    ){
        return seriesRepository.findById(seriesId)
                .orElseThrow(() -> new ApiException(ErrorCode.NOT_FOUND));
    }

    public void edit(
            BlogEntity blogEntity,
            Long seriesId,
            SeriesRequest seriesRequest
    ){
        SeriesEntity seriesEntity = checkMySeriesByBlog(blogEntity, seriesId);

        seriesEntity.changeTitle(seriesRequest.getTitle());
    }

    public void delete(
            BlogEntity blogEntity,
            Long seriesId
    ){
        checkMySeriesByBlog(blogEntity, seriesId);
        seriesRepository.deleteById(seriesId);
    }

    private SeriesEntity checkMySeriesByBlog(BlogEntity blogEntity, Long seriesId) {
        SeriesEntity seriesEntity = getSeriesWithThrow(seriesId);
        if(!Objects.equals(blogEntity.getId(), seriesEntity.getBlogEntity().getId())){
            throw new ApiException(ErrorCode.BAD_REQUEST, "자신의 시리즈가 아닙니다");
        }
        return seriesEntity;
    }
}
