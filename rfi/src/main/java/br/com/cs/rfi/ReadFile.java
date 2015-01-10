package br.com.cs.rfi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ReadFile {
	private static final String CHAR_SET = "UTF-8"; 
	private String fileName;
	private Scanner read;
	private InputStream fr;

	private String charSet;

	public static ReadFile newInstance( String fileName , String charSet ) throws Exception{
		ReadFile r = new ReadFile(fileName, charSet );
		r.init();
		return r;		
	}

	public static ReadFile newInstance( String fileName ) throws Exception{
		return ReadFile.newInstance( fileName, CHAR_SET );
	}

	private ReadFile( String fileName , String charSet ){
		this.fileName = fileName;
		this.charSet = charSet;
	}
	private void init() throws Exception {
		this.fr   = new FileInputStream( fileName );
		this.read = new Scanner( fr , charSet );		
	}

	public String next(){
		if( read.hasNext() ) {
			String line = read.nextLine();
			return line;
		}
		return null;
	}

	public void close(){
		try {
			this.read.close();
			this.fr.close();
		} catch (IOException ignore) {
			System.out.println( ignore.getMessage() );
		}
	}
}