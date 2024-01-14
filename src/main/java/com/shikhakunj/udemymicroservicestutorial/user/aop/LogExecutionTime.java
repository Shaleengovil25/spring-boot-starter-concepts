package com.shikhakunj.udemymicroservicestutorial.user.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LogExecutionTime {
    String additionalMessage() default "";
}


// https://shivaganesh01.medium.com/logging-using-spring-aop-4465c7349791
