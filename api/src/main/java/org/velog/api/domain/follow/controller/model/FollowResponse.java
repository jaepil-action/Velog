package org.velog.api.domain.follow.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FollowResponse {
    private String loginId;
    private String name;
    private String profileImage;
}
