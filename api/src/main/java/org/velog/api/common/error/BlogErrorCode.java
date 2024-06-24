package org.velog.api.common.error;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BlogErrorCode implements ErrorCodeIfs{

    BLOG_NOT_FOUND(404, 1501, "블로그를 찾을 수 없습니다.")
    ;

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
