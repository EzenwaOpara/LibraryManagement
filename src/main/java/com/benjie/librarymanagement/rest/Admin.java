package com.benjie.librarymanagement.rest;

/*
 * Created by OPARA BENJAMIN
 * On 1/10/2021 - 3:17 PM
 */

import javax.ws.rs.NameBinding;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Admin {
}
