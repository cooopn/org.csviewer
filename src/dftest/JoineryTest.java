package dftest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
//import java.util.function.Predicate;

//import org.apache.poi.hpsf.Date;

import joinery.DataFrame;
import joinery.DataFrame.Predicate;
//import joinery.Predicate;

public class JoineryTest {

	public static void main(String[] args) throws IOException {
		
		DataFrame<Object> df = DataFrame.readCsv("data/FounderCode.csv");	
		System.out.println(df);
		
		Iterator<Object> it;
		  it = df.columns().iterator();
		while (it.hasNext())
		System.out.print(it.next() + "\t");
		System.out.println();
		DataFrame<Object> df1 = df.select(new Predicate<Object>() {
		          @Override
		          public Boolean apply(List<Object> values) {
		        	  String matrilCode = values.get(1).toString();
		        	  int digits = Integer.parseInt(matrilCode.substring(1));
		        	  //System.out.println(matrilCode + "==>" + digits);
		              return digits > 1050;
		          }
		          
		      }).select(new Predicate<Object>() {
		          @Override
		          public Boolean apply(List<Object> values) {
		        	  String founderId = values.get(2).toString();
		        	  
		              return founderId.contains("2");
		          }
		          
		      }).sortBy("FounderId");
			
		  Iterator<List<Object>>  itrow = df1.iterrows();
		while (itrow.hasNext()) {
			for (Object o : itrow.next()) 
				System.out.print(o + "\t");
			System.out.println();			
		}
		/*
		//  DataFrame<Object> df = new DataFrame<>("name", "value");
		
		  //for (int i = 0; i < 10; i++)
		  //    df.append(Arrays.asList("name" + i, i));
			System.out.println(df.get(2, 1));
			*/
	}

}
