package br.com.cs.example.main;

import br.com.cs.example.beans.BodyBean;
import br.com.cs.example.beans.HeaderBean;
import br.com.cs.example.beans.TrailerBean;
import br.com.cs.rfi.AbstractReadingFileIntegration;

public class ParseFileRFI extends AbstractReadingFileIntegration<HeaderBean, BodyBean, TrailerBean>{
	public ParseFileRFI(String fileName) throws Exception {
		super(fileName,"UTF-8");
	}
}