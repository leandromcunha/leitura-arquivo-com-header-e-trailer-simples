package br.com.cs.rfi.interfaces;

import java.io.Serializable;

public interface IFormatterValues<T extends Serializable> {
	public T getValue( String mask, String value ) throws Exception ;
}