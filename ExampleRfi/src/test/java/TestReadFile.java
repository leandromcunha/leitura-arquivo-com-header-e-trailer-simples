import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.cs.example.beans.BodyBean;
import br.com.cs.example.beans.HeaderBean;
import br.com.cs.example.beans.TrailerBean;
import br.com.cs.example.main.ParseFileRFI;
import br.com.cs.rfi.rules.RuleResult;


public class TestReadFile {

	@Test
	public void test() {
		try {
			ParseFileRFI parse = new ParseFileRFI( "file/ExempleFile" );

			List<RuleResult> fail    = parse.faulires();
			
			System.out.println( fail.size() );
			
			for (RuleResult ruleResult : fail) {
				System.out.println( "Line: " + ruleResult.getLine() +  " Message: " + ruleResult.getMessage() );
			}

			HeaderBean       header  = parse.getHeader();
			System.out.println( header.getIdentification() );
			
			TrailerBean      trailer = parse.getTrailer();
			System.out.println( trailer.getRegisters() );
			
			List<BodyBean> bodys   = parse.getListBody();

			for (BodyBean bodyBean : bodys) {
				System.out.println( bodyBean.getName() );
			}
			
			Assert.assertEquals( fail.size(), 2 );

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
