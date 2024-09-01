package com.github.ngodat0103.yamp.authsvc;


import org.junit.jupiter.api.Disabled;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@Disabled
@SelectPackages({"com.github.ngodat0103.yamp.authsvc.repository",
        "com.github.ngodat0103.yamp.authsvc.service","com.github.ngodat0103.yamp.authsvc.controller"})
public class UnitTest {
}
