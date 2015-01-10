package br.com.cs.rfi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Trailer {
	String identification();
	int initPos();
	int endPos();
	int size();
	boolean allFailure() default false;
}