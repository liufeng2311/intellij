package com.beiming;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
        //使每次的测试数据都会回滚
class IntellijIdeaJavaApplicationTests {

    @Test
    void contextLoads() {
    }

}
