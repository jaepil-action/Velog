package org.velog.api.domain.series.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SeriesResponses {

    private List<SeriesResponse> seriesResponseList;
}
