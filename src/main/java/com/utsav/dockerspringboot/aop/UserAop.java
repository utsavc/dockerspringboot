package com.utsav.dockerspringboot.aop;

import org.apache.commons.logging.Log;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Aspect
public class UserAop {

    @Before(value = "execution(* com.utsav.dockerspringboot.controller.auth.UserController*(..))")
    public void beforeUserController(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Before method "+methodName+" is invoked on "+ LocalDateTime.now());
    }
}
