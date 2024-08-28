package com.github.ngodat0103.yamp.productsvc;


import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"com.github.ngodat0103.yamp.productsvc.service", "com.github.ngodat0103.yamp.productsvc.repository"})
public class UnitTest {
}
