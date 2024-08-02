package com.example.userservice.constant;

public class AuthServiceUri {
    public static final String AUTH_SVC_BASE = "http://gateway-service:8000/api/v1/auth";
    public final static String AUTH_SVC_ACC_URI = AUTH_SVC_BASE+"/account";
    public final static String AUTH_SVC_REG_URI = AUTH_SVC_ACC_URI+"/register";
    public final  static String AUTH_SVC_ROLE_URI = AUTH_SVC_ACC_URI+"/role";
    public final static  String DEFAULT_ROLE = "ROLE_CUSTOMER";
    public final static String ACCOUNT_UUID_HEADER = "X-Account-Uuid";
    public final static String ROLE_NAME_HEADER = "X-Role-Name";
}
