package br.com.cs.example.beans;

import br.com.cs.rfi.annotations.Trailer;
import br.com.cs.rfi.annotations.Values;
import br.com.cs.rfi.formatters.FormatterInteger;
import br.com.cs.rfi.interfaces.IBean;

@Trailer(identification="TRL",initPos=1,endPos=3,size=8 )
public class TrailerBean implements IBean {
	private static final long serialVersionUID = 1L;

	@Values(position=1,size=3)
	private String identification;
	
	@Values(position=4,size=5,formatted=FormatterInteger.class)
	private Integer registers;
	
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public Integer getRegisters() {
		return registers;
	}

	public void setRegisters(Integer registers) {
		this.registers = registers;
	}
}
