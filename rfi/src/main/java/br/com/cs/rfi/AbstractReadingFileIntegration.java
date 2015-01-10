package br.com.cs.rfi;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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

public abstract class AbstractReadingFileIntegration<H extends IBean, B extends IBean , T extends IBean> {

	private static final String METHOD_WITH_SET = "set";
	private static final String CHAR_SET = "UTF-8";
	
	protected List<RuleResult>  faulires;
	private   Integer           lineReading = 0;
	private   Header            aHeader;
	private   Body              aBody;
	private   Trailer           aTrailer;
	private   Boolean           allFaulires;
	private   List<B>           bodys;
	private   String            fileName;
	private   H                 header;
	private   T                 trailer;
	private   String            charSet;   

	public AbstractReadingFileIntegration ( String fileName , String charSet ) throws Exception {
		this.charSet  = charSet;
		this.fileName = fileName;
		this.init();
		this.process();
	}
	public AbstractReadingFileIntegration ( String fileName ) throws Exception {
		this(fileName, CHAR_SET );
	}

	abstract protected B bodyBeanRfi();
	abstract protected H headerBeanRfi();
	abstract protected T trailerBeanRfi();

	protected B body( String line ) throws Exception{
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
		this.bodys = new LinkedList<B>();
		this.faulires     = new LinkedList<RuleResult>();

		ReadFile read = ReadFile.newInstance(fileName,charSet);
		String line = "";

		boolean body = true;

		while ( ( line = read.next() ) != null ){
			body = true;
			this.lineReading++;

			if( aHeader != null && aHeader.size() == line.length() && line.substring( aHeader.initPos()-1, aHeader.endPos() ).equals( aHeader.identification() ) ){
				body = false;
				this.allFaulires = aHeader.allFailure();
				this.header = header(line);				
			}

			if( aTrailer != null && aTrailer.size() == line.length() && line.substring( aTrailer.initPos()-1, aTrailer.endPos() ).equals( aTrailer.identification() ) ){
				body = false;
				this.allFaulires = aTrailer.allFailure();
				this.trailer = trailer( line );
			}

			if( aBody != null && ( aBody.size() == 0 || line.length() == aBody.size() ) && body ){
				this.allFaulires = aBody.allFailure();
				bodys.add( body( line ) );
			}

			if( body &&( aBody == null || ( aBody.size() > 0 && line.length() != aBody.size() ) ) ){
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

	public List<B> getListBody() throws Exception {
		return bodys;
	}

	private <M extends IBean> Method method( M model, String fieldName ) throws Exception {
		Method[] methods = model.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if( method.getName().startsWith( METHOD_WITH_SET ) && 
			    Modifier.isPublic(method.getModifiers())       &&  
			    method.getName().toUpperCase().contains( fieldName.toUpperCase() ) ){
				
				return method;
			}
		}
		throw new Exception( "Setter method for the field " + fieldName + " does not exist" );
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
					result.setPosition( values.position() );
					result.setSize( values.size() );
					model.addRuleResult( result );
					if( this.allFaulires ){
						faulires.add( result );
					}
					continue;
				}
			}

			try{
				IFormatterValues<?> formatted = values.formatted().newInstance();
				Object valueFormatted = formatted.getValue( values.pattern(), valueObject );
				Method method = method(model, f.getName() );
				method.invoke( model, new Object[]{ valueFormatted } );
			}catch( Exception e ) {
				RuleResult result = new RuleResult();
				result.setField( f.getName() );
				result.setLine( this.lineReading );
				result.setMessage( e.getMessage() );
				result.setObjectName( model.getClass().getSimpleName() );
				result.setPosition( values.position() );
				result.setSize( values.size() );
				model.addRuleResult( result );
			}
		}		
		return model;
	}

	public List<RuleResult> faulires() {
		return this.faulires;
	}
}