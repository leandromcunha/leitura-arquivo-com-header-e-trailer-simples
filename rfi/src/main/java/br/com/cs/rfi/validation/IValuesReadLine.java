package br.com.cs.rfi.validation;

import br.com.cs.rfi.interfaces.IBean;

public interface IValuesReadLine {
	public <T extends IBean >String values( final T bean, final String line, final Integer posicion, final Integer size ) throws Exception;
}