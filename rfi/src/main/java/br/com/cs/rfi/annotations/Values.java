package br.com.cs.rfi.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.cs.rfi.formatters.FormatterString;
import br.com.cs.rfi.interfaces.IFormatterValues;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Values {
	int size();
	int position();
	String pattern()   default "";
	Class< ? extends IFormatterValues<? extends Object>> formatted() default FormatterString.class; 
}