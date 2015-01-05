package br.com.cs.rfi.interfaces;

public interface IFormatterValues<T extends Object> {
	public T getValue( String mask, String value ) throws Exception ;
}