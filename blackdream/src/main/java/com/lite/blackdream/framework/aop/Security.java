package com.lite.blackdream.framework.aop;

import com.lite.blackdream.framework.model.Role;
import java.lang.annotation.*;

/**
 * Security
 *
 * @author LaineyC
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Security {

    /**
     * 对外开放可以任意访问
     */
    boolean open() default false;

    /**
     * 需要相应角色才能访问
     */
    Role role() default Role.USER;

}