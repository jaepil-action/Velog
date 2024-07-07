package org.velog.api.domain.series.business;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.blog.service.BlogService;
import org.velog.api.domain.series.controller.model.SeriesRequest;
import org.velog.api.domain.series.controller.model.SeriesResponse;
import org.velog.api.domain.series.converter.SeriesConverter;
import org.velog.api.domain.series.service.SeriesService;
import org.velog.api.domain.session.ifs.CookieServiceIfs;
import org.velog.db.blog.BlogEntity;
import org.velog.db.series.SeriesEntity;

import java.util.List;
import java.util.Optional;

@Business
@Slf4j
@RequiredArgsConstructor
public class SeriesBusiness {

    private final SeriesService seriesService;
    private final SeriesConverter seriesConverter;
    private final CookieServiceIfs cookieService;
    private final BlogService blogService;

    public SeriesResponse register(
            SeriesRequest seriesRequest,
            HttpServletRequest request
    ){
        BlogEntity blogEntity = getBlogEntityByRequest(request);
        SeriesEntity seriesEntity = seriesConverter.toEntity(blogEntity, seriesRequest);

        return Optional.ofNullable(seriesEntity)
                .map(seriesService::register)
                .map(seriesConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public List<SeriesResponse> retrieveAllTag(Long blogId) {

        BlogEntity blogEntity = blogService.getBlogByIdWithThrow(blogId);
        List<SeriesEntity> seriesEntityList = blogEntity.getSeriesEntityList();

        return seriesEntityList.stream()
                .map(seriesConverter::toResponse)
                .toList();
    }

    public void edit(
            HttpServletRequest request,
            Long seriesId,
            SeriesRequest seriesRequest
    ){
        BlogEntity blogEntity = getBlogEntityByRequest(request);
        seriesService.edit(blogEntity, seriesId, seriesRequest);
    }

    public void delete(
            HttpServletRequest request,
            Long seriesId
    ){
        BlogEntity blogEntity = getBlogEntityByRequest(request);
        seriesService.delete(blogEntity, seriesId);
    }

    private BlogEntity getBlogEntityByRequest(HttpServletRequest request) {
        Long userId = cookieService.validateRoleUserGetId(request);
        return blogService.getBlogByUserIdWithThrow(userId);
    }
}
