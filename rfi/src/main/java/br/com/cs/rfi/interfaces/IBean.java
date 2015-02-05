package br.com.cs.rfi.interfaces;

import java.io.Serializable;
import java.util.List;

import br.com.cs.rfi.rules.RuleResult;

public interface IBean extends Serializable{
	public List<RuleResult> ruleResult();
	public void addRuleResult(RuleResult ruleResult);
}