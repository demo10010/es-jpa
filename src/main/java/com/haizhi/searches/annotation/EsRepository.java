package com.haizhi.searches.annotation;


import java.lang.annotation.*;

/**
 * 目前仅仅支持查询 TODO
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EsRepository {
    boolean value() default false;
}
