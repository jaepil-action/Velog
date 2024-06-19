package org.velog.db.role;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Admin {

    ROLE_USER("일반 사용자"),
    ROLE_ADMIN("관리자"),
    ;

    private final String description;
}
