package br.com.cs.example.beans;

import java.sql.Timestamp;

import br.com.cs.rfi.annotations.Header;
import br.com.cs.rfi.annotations.Values;
import br.com.cs.rfi.beans.AbstractBeans;
import br.com.cs.rfi.formatters.FormatterInteger;
import br.com.cs.rfi.formatters.FormatterTimestamp;

@Header(identification="HDL",initPos=1,endPos=3,size=16 ,allFailure=true)
public class HeaderBean extends AbstractBeans {
	private static final long serialVersionUID = 1L;

	@Values(position=1,size=3)
	private String identification;
	
	@Values(position=4,size=5,formatted=FormatterInteger.class)
	private Integer sequence;
	
	@Values(position=9,size=8,pattern="ddMMyyyy",formatted=FormatterTimestamp.class)
	private Timestamp dateGeneration;

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Timestamp getDateGeneration() {
		return dateGeneration;
	}

	public void setDateGeneration(Timestamp dateGeneration) {
		this.dateGeneration = dateGeneration;
	}
}
