package com.github.ngodat0103.yamp.authsvc;

import com.github.ngodat0103.yamp.authsvc.exception.NotFoundException;
import org.slf4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.util.UUID;
import java.util.function.Supplier;

public final class Util {
    private final static String TEMPLATE_NOT_FOUND = "%s with %s: %s not found";
    private final static String TEMPLATE_CONFLICT = "%s with %s: %s already exists";

    public static void throwNotFoundException(Logger log, String entity, String attributeName, Object attributeValue) {
        String message = String.format(TEMPLATE_NOT_FOUND, entity,attributeName,attributeValue);
        NotFoundException notFoundException = new NotFoundException(message);
        logging(log,message,notFoundException);
        throw notFoundException;
    }
    public static Supplier<NotFoundException> notFoundExceptionSupplier(Logger log, String entity, String attributeName, Object attributeValue){
        return () -> {
            String message = String.format(TEMPLATE_NOT_FOUND, entity,attributeName,attributeValue);
            NotFoundException notFoundException = new NotFoundException(message);
            logging(log,message,notFoundException);
            return notFoundException;
        };
    }

    public static void throwConflictException(Logger log, String entity, String attributeName, Object attributeValues) {
        String message = String.format(TEMPLATE_CONFLICT, entity,attributeName,attributeValues);
        NotFoundException notFoundException = new NotFoundException(message);
        logging(log,message,notFoundException);
        throw notFoundException;
    }



    private static void  logging(Logger log, String message,NotFoundException notFoundException){
        if(log.isTraceEnabled()){
            log.debug(message,notFoundException);
        }
        else if(log.isDebugEnabled()){
            log.debug(message);
        }
    }

    public static UUID getAccountUuidFromAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.notNull(authentication,"Authentication object is required");
        try{
            return UUID.fromString(authentication.getName());
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid UUID format", e);
        }
    }



}
