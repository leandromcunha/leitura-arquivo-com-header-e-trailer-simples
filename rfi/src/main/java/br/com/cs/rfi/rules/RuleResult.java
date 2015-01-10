package br.com.cs.rfi.rules;

import java.io.Serializable;

public class RuleResult implements Serializable{
	private static final long serialVersionUID = 1L;

	private String  message;
	private Integer line;
	private String  field;
	private String  objectName;
	private Integer position;
	private Integer size;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getLine() {
		return line;
	}
	public void setLine(Integer line) {
		this.line = line;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}

}
