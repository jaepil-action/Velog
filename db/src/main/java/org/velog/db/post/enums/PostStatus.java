package org.velog.db.post.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
@AllArgsConstructor
public enum PostStatus {

    PUBLIC("공개"),
    PRIVATE("비공개"),
    TEMPORARY("임시"),
    ;

    private String description;

    @JsonCreator
    public static PostStatus from(String s) {
        for (PostStatus status : PostStatus.values()) {
            if (status.name().equalsIgnoreCase(s)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid PostStatus: " + s);
    }
}
