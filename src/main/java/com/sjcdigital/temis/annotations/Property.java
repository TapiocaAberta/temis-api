package com.sjcdigital.temis.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.inject.Qualifier;

/**
 * @author pedro-hos
 * Borrowed: https://dzone.com/articles/how-to-inject-property-file-properties-with-cdi
 */

@Qualifier
@Retention(RUNTIME)
@Target({ TYPE, FIELD, METHOD, PARAMETER })
public @interface Property {
	
	@Nonbinding String value() default "";
    @Nonbinding boolean required() default true;

}
