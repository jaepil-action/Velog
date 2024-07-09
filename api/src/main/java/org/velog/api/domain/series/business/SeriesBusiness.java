package org.velog.api.domain.series.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.velog.api.common.annotation.Business;
import org.velog.api.common.error.ErrorCode;
import org.velog.api.common.exception.ApiException;
import org.velog.api.domain.blog.business.BlogBusiness;
import org.velog.api.domain.series.controller.model.SeriesRequest;
import org.velog.api.domain.series.controller.model.SeriesResponse;
import org.velog.api.domain.series.converter.SeriesConverter;
import org.velog.api.domain.series.service.SeriesService;
import org.velog.api.domain.user.model.UserDto;
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
    private final BlogBusiness blogBusiness;

    public SeriesResponse register(
            SeriesRequest seriesRequest,
            UserDto userDto
    ){
        BlogEntity blogEntity = blogBusiness.getBlogByIdWithThrow(userDto.getBlogId());
        SeriesEntity seriesEntity = seriesConverter.toEntity(blogEntity, seriesRequest);

        return Optional.ofNullable(seriesEntity)
                .map(seriesService::register)
                .map(seriesConverter::toResponse)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public List<SeriesResponse> retrieveAllTag(Long blogId) {

        BlogEntity blogEntity = blogBusiness.getBlogByIdWithThrow(blogId);
        List<SeriesEntity> seriesEntityList = blogEntity.getSeriesEntityList();

        return seriesEntityList.stream()
                .map(seriesConverter::toResponse)
                .toList();
    }

    public void edit(
            UserDto userDto,
            Long seriesId,
            SeriesRequest seriesRequest
    ){
        BlogEntity blogEntity = blogBusiness.getBlogByIdWithThrow(userDto.getBlogId());
        seriesService.edit(blogEntity, seriesId, seriesRequest);
    }

    public void delete(
            UserDto userDto,
            Long seriesId
    ){
        BlogEntity blogEntity = blogBusiness.getBlogByIdWithThrow(userDto.getBlogId());
        seriesService.delete(blogEntity, seriesId);
    }
}
