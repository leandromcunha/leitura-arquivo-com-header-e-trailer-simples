package br.com.cs.example.beans;

import br.com.cs.rfi.annotations.Body;
import br.com.cs.rfi.annotations.Rule;
import br.com.cs.rfi.annotations.Values;
import br.com.cs.rfi.beans.AbstractBeans;
import br.com.cs.rfi.formatters.FormatterInteger;
import br.com.cs.rfi.rules.RuleNotIsEmpty;

@Body(size=33,allFailure=true)
public class BodyBean extends AbstractBeans {
	private static final long serialVersionUID = 1L;

	@Values(formatted=FormatterInteger.class, position=1,size=3)
	@Rule(message="ID is empty", rule=RuleNotIsEmpty.class)
	private Integer id;

	@Values(position=4,size=30)
	@Rule(message="Name is empty", rule=RuleNotIsEmpty.class)
	private String name;
	
	@Values(position=34,size=8)
	@Rule(message="CEP is empty", rule=RuleNotIsEmpty.class)
	private String cep;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
}
