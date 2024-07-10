package org.velog.api.domain.follow.controller.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FollowResponses {

    private List<FollowResponse> followResponseList;
}
