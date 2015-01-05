package br.com.cs.rfi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.cs.rfi.interfaces.IRules;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Rule {
	String message();
	Class< ? extends IRules<? extends Object>> rule(); 
}