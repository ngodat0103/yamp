package com.example.yamp.usersvc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class ServiceUtil {
    private final int port;
    private String serviceAddress = null;

    @Autowired
    public ServiceUtil(@Value("${server.port}") int port){
    this.port = port;
    }
    public String getServiceAddress(){
        if(serviceAddress == null){
            serviceAddress = findMyHostname()+"/"+findMyAddress()+":" + port ;
        }
        return serviceAddress;
    }

    public String findMyHostname(){
        try{
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException ex){
            return "Unknown host";
        }
    }
    public String findMyAddress(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException ex){
            return "Unknown ip address";
        }
    }
}
