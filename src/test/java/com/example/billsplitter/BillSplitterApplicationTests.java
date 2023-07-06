package com.example.billsplitter;

import com.example.billsplitter.config.PostgresContainerConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(value = PostgresContainerConfiguration.class)
class BillSplitterApplicationTests {

    @Test
    void contextLoads() {
    }

}
