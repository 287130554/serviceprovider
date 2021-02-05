package com.jim.learn.serviceprovider.domain;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

@Service
public class RetryManage {

    static int i = 0;

    @LoadBalanced
    @Retryable(value = {RemoteAccessException.class, Exception.class}, maxAttempts = 5, backoff = @Backoff(delay = 500l, multiplier = 1))
    public String retryAgain(String request) throws Exception {


        System.out.println("retry again");

        i = i + 1;
        if(0 ==i%2){
            throw new RemoteAccessException("not access");
        }else{
            throw new Exception("not access");
        }

    }

    @Recover
    public String recover(RemoteAccessException ex){

        System.out.println("hello world");
        return "hello world";
    }

    @Recover
    public String recoverException(Exception ex){

        System.out.println("hello exception");
        return "hello exception";
    }
}
