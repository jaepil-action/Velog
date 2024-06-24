package org.velog.api.domain.series.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.blog.service.BlogService;
import org.velog.api.domain.series.controller.model.SeriesRegisterRequest;
import org.velog.api.domain.series.controller.model.SeriesResponse;
import org.velog.api.domain.series.converter.SeriesConverter;
import org.velog.api.domain.series.service.SeriesService;
import org.velog.api.domain.session.SessionService;
import org.velog.db.blog.BlogEntity;
import org.velog.db.series.SeriesEntity;
import org.velog.db.user.UserEntity;

import java.util.Optional;

@Business
@Slf4j
@RequiredArgsConstructor
public class SeriesBusiness {

    private final SeriesService seriesService;
    private final SeriesConverter seriesConverter;
    private final SessionService sessionService;
    private final BlogService blogService;

    public SeriesResponse register(
            SeriesRegisterRequest seriesRegisterRequest,
            HttpServletRequest request
    ){
        Long userId = sessionService.validateRoleUserId(request);
        BlogEntity blogEntity = blogService.getBlogByUserIdWithThrow(userId);
        SeriesEntity seriesEntity = seriesConverter.toEntity(blogEntity, seriesRegisterRequest);

        //log.info("blogEntity series={}", blogEntity.getSeriesEntityList());

        return Optional.ofNullable(seriesEntity)
                .map(seriesService::register)
                .map(seriesConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }
}
