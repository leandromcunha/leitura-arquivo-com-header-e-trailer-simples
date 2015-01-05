package br.com.cs.rfi.formatters;

import br.com.cs.rfi.interfaces.IFormatterValues;

public class FormatterInteger implements IFormatterValues<Integer>{
	public Integer getValue( String mask, String value) throws Exception{
		return Integer.parseInt(value);
	}
}
