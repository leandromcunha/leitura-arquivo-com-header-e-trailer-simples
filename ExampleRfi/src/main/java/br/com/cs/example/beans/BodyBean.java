package br.com.cs.example.beans;

import br.com.cs.rfi.annotations.Body;
import br.com.cs.rfi.annotations.Rule;
import br.com.cs.rfi.annotations.Values;
import br.com.cs.rfi.formatters.FormatterInteger;
import br.com.cs.rfi.interfaces.IBean;
import br.com.cs.rfi.rules.RuleNotIsEmpty;

@Body(size=33)
public class BodyBean implements IBean {
	private static final long serialVersionUID = 1L;

	@Values(formatted=FormatterInteger.class, position=1,size=3)
	@Rule(message="ID is empty", rule=RuleNotIsEmpty.class)
	private Integer id;

	@Values(position=4,size=30)
	@Rule(message="Name is empty", rule=RuleNotIsEmpty.class)
	private String name;

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
}
