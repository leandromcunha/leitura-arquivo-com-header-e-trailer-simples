package br.com.cs.rfi.interfaces;

public interface IRules<T extends Object> {
	public Boolean isValidate( T value );
}