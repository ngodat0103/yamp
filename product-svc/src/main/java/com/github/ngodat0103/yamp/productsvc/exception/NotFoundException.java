package com.github.ngodat0103.yamp.productsvc.exception;


import org.slf4j.Logger;

import java.util.UUID;
import java.util.function.Supplier;

public class NotFoundException extends RuntimeException {
    private static final String TEMPLATE = "%s with uuid %s not found";
    public NotFoundException(String message) {
        super(message);
    }

    public static void throwNotFoundException(Logger log, String entity, UUID uuid) {
        String message = String.format(TEMPLATE, entity,uuid);
        NotFoundException notFoundException = new NotFoundException(message);
        logging(log,message,notFoundException);
        throw notFoundException;
    }
    public static Supplier<NotFoundException> notFoundExceptionSupplier(Logger log, String entity, UUID uuid){
        return () -> {
            String message = String.format(TEMPLATE, entity,uuid);
            NotFoundException notFoundException = new NotFoundException(message);
            logging(log,message,notFoundException);
            return notFoundException;
        };
    }

    private static void logging(Logger log, String message, NotFoundException notFoundException){
        if(log.isTraceEnabled()){
            log.debug(message,notFoundException);
        }
        else{
            log.debug(message);
        }
    }
}
