package com.corona.documentmanager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SpringJUnitConfig
class DocumentManagerApplicationTest {

    @Test
    void contextLoads(ApplicationContext context) {
        assertNotNull(context, "Application context should not be null");
    }
}