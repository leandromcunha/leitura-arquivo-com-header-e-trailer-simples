package br.com.cs.rfi.rules;

import br.com.cs.rfi.interfaces.IRules;

public class RuleNotIsEmpty implements IRules<String>{
	public Boolean isValidate(String value ) {
		return ( value != null && !value.trim().isEmpty() );
	}

}