package br.com.cs.rfi;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import br.com.cs.rfi.annotations.Body;
import br.com.cs.rfi.annotations.Header;
import br.com.cs.rfi.annotations.Rule;
import br.com.cs.rfi.annotations.Trailer;
import br.com.cs.rfi.annotations.Values;
import br.com.cs.rfi.interfaces.IBean;
import br.com.cs.rfi.interfaces.IFormatterValues;
import br.com.cs.rfi.interfaces.IRules;
import br.com.cs.rfi.rules.RuleResult;

public abstract class AbstractReadingFileIntegration<H extends IBean, L extends IBean , T extends IBean> {

	private   String       fileName;
	private   List<L>      listResponse;
	protected List<RuleResult> faulires;
	private   Integer lineReading = 0;
	private   Header  aHeader;
	private   Body    aBody;
	private   Trailer aTrailer;

	private H header;
	private T trailer;

	public AbstractReadingFileIntegration ( String fileName ) throws Exception {
		this.fileName = fileName;
		this.init();
		this.process();
	}

	abstract protected L bodyBeanRfi();
	abstract protected H headerBeanRfi();
	abstract protected T trailerBeanRfi();

	protected L lines( String line ) throws Exception{
		return parserLine(bodyBeanRfi(), line);
	}
	protected H header( String line ) throws Exception{
		return parserLine(headerBeanRfi(), line);
	}
	protected T trailer( String line ) throws Exception{
		return parserLine(trailerBeanRfi(), line);
	}

	public H getHeader(){
		return header;
	}

	public T getTrailer(){
		return trailer;
	}

	private void init(){
		if( headerBeanRfi() != null ){
			this.aHeader  = headerBeanRfi().getClass().getAnnotation( Header.class );
		}
		if( bodyBeanRfi() != null ){
			this.aBody    = bodyBeanRfi().getClass().getAnnotation( Body.class );
		}
		if( trailerBeanRfi() != null ){
			this.aTrailer = trailerBeanRfi().getClass().getAnnotation( Trailer.class );
		}
	}

	private void process() throws Exception{
		this.listResponse = new LinkedList<L>();
		this.faulires     = new LinkedList<RuleResult>();

		ReadFile read = ReadFile.newInstance(fileName);
		String line = "";

		boolean body = true;

		while ( ( line = read.next() ) != null ){
			body = true;
			this.lineReading++;

			if( aHeader != null && aHeader.size() == line.length() && line.substring( aHeader.initPos()-1, aHeader.endPos() ).equals( aHeader.identification() ) ){
				body = false;
				this.header = header(line);				
			}

			if( aTrailer != null && aTrailer.size() == line.length() && line.substring( aTrailer.initPos()-1, aTrailer.endPos() ).equals( aTrailer.identification() ) ){
				body = false;
				this.trailer = trailer( line );
			}

			if( aBody != null && ( line.length() == 0 || line.length() == aBody.size() ) && body ){
				listResponse.add( lines( line ) );
			}

			if( body &&( aBody == null || line.length() != aBody.size() ) ){
				RuleResult result = new RuleResult();
				result.setField( "not field" );
				result.setLine( this.lineReading );
				result.setMessage( "No indefinite record" );
				result.setObjectName( this.getClass().getSimpleName() );
				this.faulires.add( result );
			}
		}
		read.close();
	}

	public List<L> getListBody() throws Exception {
		return listResponse;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private <M extends IBean> M parserLine( M model , String line ) throws Exception {
		Field[] fields = model.getClass().getDeclaredFields();
		for( Field f : fields ){
			if (java.lang.reflect.Modifier.isStatic(f.getModifiers())) {
				continue;
			}
			Values values = f.getAnnotation( Values.class );
			if( values == null ){
				continue;
			}
			String valueObject = line.substring( values.position()-1 , (values.position()-1 + values.size() ) );
			Rule rule = f.getAnnotation( Rule.class );
			if( rule != null ){
				IRules r = rule.rule().newInstance();
				if( !r.isValidate( valueObject ) ){
					RuleResult result = new RuleResult();
					result.setField( f.getName() );
					result.setLine( this.lineReading );
					result.setMessage( rule.message() );
					result.setObjectName( model.getClass().getSimpleName() );
					this.faulires.add( result );
					continue;
				}
			}

			try{
				IFormatterValues<?> formatted = values.formatted().newInstance();
				Object valueFormatted = formatted.getValue( values.pattern(), valueObject );
				boolean accessible = f.isAccessible();
				f.setAccessible( true );
				f.set(model, valueFormatted);
				f.setAccessible( accessible );
			}catch( Exception e ) {
				RuleResult result = new RuleResult();
				result.setField( f.getName() );
				result.setLine( this.lineReading );
				result.setMessage( e.getMessage() );
				result.setObjectName( model.getClass().getSimpleName() );
				this.faulires.add( result );
			}
		}		
		return model;
	}

	public List<RuleResult> faulires() {
		return this.faulires;
	}
}