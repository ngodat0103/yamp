package com.github.ngodat0103.yamp.productsvc;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectPackages("com.github.ngodat0103.yamp.productsvc.repository")
class UnitTest {

    @Test
    void contextLoads() {
    }

}
