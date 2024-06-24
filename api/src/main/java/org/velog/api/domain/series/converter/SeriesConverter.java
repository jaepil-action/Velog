package org.velog.api.domain.series.converter;

import org.velog.api.common.annotation.Converter;
import org.velog.api.domain.series.controller.model.SeriesRegisterRequest;
import org.velog.api.domain.series.controller.model.SeriesResponse;
import org.velog.db.blog.BlogEntity;
import org.velog.db.series.SeriesEntity;

@Converter
public class SeriesConverter {

    public SeriesEntity toEntity(
            BlogEntity blogEntity,
            SeriesRegisterRequest seriesRegisterRequest
    ){
        SeriesEntity seriesEntity = SeriesEntity.builder()
                .title(seriesRegisterRequest.getTitle())
                .build();
        seriesEntity.addBlogEntity(blogEntity);

        return seriesEntity;
    }

    public SeriesResponse toResponse(
            SeriesEntity seriesEntity
    ){
        return SeriesResponse.builder()
                .title(seriesEntity.getTitle())
                .blogId(seriesEntity.getBlogEntity().getId())
                .build();
    }
}
