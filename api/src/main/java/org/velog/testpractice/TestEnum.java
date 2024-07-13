package org.velog.testpractice;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TestEnum {

    CODE("코드"),
    TEST("테스트"),
    ;
    private String description;
}
