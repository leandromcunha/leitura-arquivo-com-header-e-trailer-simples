package br.com.cs.rfi.formatters;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import br.com.cs.rfi.interfaces.IFormatterValues;

public class FormatterTimestamp implements IFormatterValues<Timestamp>{
	public Timestamp getValue( String pattern, String value) throws Exception {
		if( value == null || value.isEmpty() ){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat( pattern );
		return new Timestamp( sdf.parse( value ).getTime() );
	}
}