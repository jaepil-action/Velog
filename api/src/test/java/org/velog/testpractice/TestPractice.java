package org.velog.testpractice;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class TestPractice {

    @Test
    void test1(){
        log.info("Test1");
    }

    @Test
    @Disabled
    void test2(){
        log.info("Test1");
    }

    @BeforeAll
    static void beforeAll(){
        log.info("beforeAll");
    }

    @AfterAll
    static void afterAll(){
        log.info("afterAll");

    }

    @BeforeEach
    void beforeEach(){
        log.info("beforeEach");
    }

    @AfterEach
    void afterEach(){
        log.info("afterEach");
    }
}