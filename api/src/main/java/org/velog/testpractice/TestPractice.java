package org.velog.testpractice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestPractice {

    private String name;
    private int age;
    private Long id;
    private TestEnum testEnum;
    private TestFiled testFiled;
    private LocalDateTime date;
}
