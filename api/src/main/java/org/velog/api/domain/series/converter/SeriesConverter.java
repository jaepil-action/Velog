package org.velog.api.domain.series.converter;

import org.velog.api.common.annotation.Converter;
import org.velog.api.domain.series.controller.model.SeriesRequest;
import org.velog.api.domain.series.controller.model.SeriesResponse;
import org.velog.api.domain.series.controller.model.SeriesResponses;
import org.velog.db.blog.BlogEntity;
import org.velog.db.series.SeriesEntity;

import java.util.List;

@Converter
public class SeriesConverter {

    public SeriesEntity toEntity(
            BlogEntity blogEntity,
            SeriesRequest seriesRequest
    ){
        SeriesEntity seriesEntity = SeriesEntity.builder()
                .title(seriesRequest.getTitle())
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

    public SeriesResponses toResponses(
            List<SeriesResponse> seriesResponseList
    ){
        return SeriesResponses.builder()
                .seriesResponseList(seriesResponseList)
                .build();
    }
}
