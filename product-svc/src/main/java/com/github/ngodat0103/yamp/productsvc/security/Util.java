package com.github.ngodat0103.yamp.productsvc.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.util.UUID;

public class Util {
    public static UUID getAccountUuidFromAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.notNull(authentication,"Authentication object is required");
        return UUID.fromString(authentication.getName());
    }
}
