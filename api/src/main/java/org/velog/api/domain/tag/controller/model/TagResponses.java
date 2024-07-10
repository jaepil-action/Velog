package org.velog.api.domain.tag.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class TagResponses {

    private List<TagResponse> tagResponseList;
}
