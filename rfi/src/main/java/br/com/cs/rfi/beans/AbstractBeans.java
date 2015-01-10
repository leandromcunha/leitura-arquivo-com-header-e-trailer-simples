package br.com.cs.rfi.beans;

import java.util.LinkedList;
import java.util.List;

import br.com.cs.rfi.interfaces.IBean;
import br.com.cs.rfi.rules.RuleResult;

public class AbstractBeans implements IBean {

	private static final long serialVersionUID = 1L;
	
	private List<RuleResult>  ruleResult;
	
	public void addRuleResult(RuleResult ruleResult) {
		if( this.ruleResult == null ){
			this.ruleResult = new LinkedList<RuleResult>();
		}
		this.ruleResult.add( ruleResult );
	}
	
	@Override
	public List<RuleResult> ruleResult() {
		return this.ruleResult;
	}
}
