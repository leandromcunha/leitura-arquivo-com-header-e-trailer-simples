package br.com.cs.rfi.formatters;

import br.com.cs.rfi.interfaces.IFormatterValues;

public class FormatterDouble implements IFormatterValues<Double>{
	public Double getValue( String mask, String value) throws Exception{
		return Double.parseDouble(value);
	}
}
