package org.velog.api.domain.series.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.series.controller.model.SeriesRegisterRequest;
import org.velog.db.series.SeriesEntity;
import org.velog.db.series.SeriesRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
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
}
