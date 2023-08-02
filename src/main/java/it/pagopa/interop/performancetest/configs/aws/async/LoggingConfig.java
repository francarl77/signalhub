package it.pagopa.interop.performancetest.configs.aws.async;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class LoggingConfig {

    @Pointcut("execution(* it.pagopa.interop.performancetest.rest.SignalController.*(..))")
    public void server(){}

    @Pointcut("execution(* it.pagopa.interop.performancetest.service.impl.SignalRelationalService.*(..))")
    public void service(){}


    @Before("server()")
    public void beforeApiCall(JoinPoint point){
        log.info("START CONTROLLER {}", point.getSignature().getName());
    }

    @AfterReturning(pointcut = "server()")
    public void afterApiCall(JoinPoint point){
        log.info("END CONTROLLER {}", point.getSignature().getName());
    }

    @Before("server()")
    public void beforeServiceCall(JoinPoint point){
        log.info("START SERVICE {}", point.getSignature().getName());
    }

    @AfterReturning(pointcut = "server()")
    public void afterServiceCall(JoinPoint point){
        log.info("END SERVICE {}", point.getSignature().getName());
    }
}
