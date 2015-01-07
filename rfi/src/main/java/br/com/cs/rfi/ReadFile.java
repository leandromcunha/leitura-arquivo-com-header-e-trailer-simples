package br.com.cs.rfi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class ReadFile {
	private String fileName;
	private Scanner read;
	private InputStream fr;
	
	public static ReadFile newInstance( String fileName ) throws Exception{
		ReadFile r = new ReadFile(fileName);
		r.init();
		return r;
	}
	private ReadFile( String fileName ){
		this.fileName = fileName;
	}
	private void init() throws Exception {
		this.fr = new FileInputStream( fileName );
		this.read = new Scanner( fr );		
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