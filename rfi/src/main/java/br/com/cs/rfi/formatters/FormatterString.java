package br.com.cs.rfi.formatters;

import br.com.cs.rfi.interfaces.IFormatterValues;

public class FormatterString implements IFormatterValues<String>{
	public String getValue( String mask, String value) throws Exception{
		return value;
	}
}
