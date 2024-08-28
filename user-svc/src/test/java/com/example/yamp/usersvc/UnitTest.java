package com.example.yamp.usersvc;


import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages({"com.example.yamp.usersvc.service","com.example.yamp.usersvc.repository"})
public class UnitTest {
}
