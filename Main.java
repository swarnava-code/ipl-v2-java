
import java.io.*;
import java.util.Scanner;
import java.util.*;//hashmap



public class Main {

/*
	static Short stringToShort(String string){

	}
	static Short shortToString(String string){

	}
	*/


	public static void main(String[] args)   {  
		final int col_path1 = 5; //18
		final String path1 = "csv/matches.csv";//"demo.csv";//archive/matches.csv
		String next="";
		int count, key, val;
		HashMap<Integer, Integer> noOfMatch = new HashMap<Integer, Integer>();
		//Short:Year, Byte:count

		try{
				Scanner sc = new Scanner(new File(path1));

				sc.useDelimiter("\n");   //sets the delimiter pattern  
				//count = 1;

				sc.next();//to eliminate heading, otherwise NumberFormatException happen
				while (sc.hasNext()) {



					next = sc.next();



					System.out.print(next+":");
					String values[] = next.split(",");

					

					key = Integer.parseInt(values[1]);


					
					System.out.print("\n\n:"+key+":\n\n");


					//counting no. of match in each year
					if(noOfMatch.containsKey(key)){
						val = noOfMatch.get(key);
						val++;
						noOfMatch.put(key, val);
					}else{
						noOfMatch.put(key, 1);
					}


				
				}   
				sc.close();


		}catch(FileNotFoundException e){
			System.out.println("File not found : "+path1+"\nDetails: "+e);
		}
		catch(NumberFormatException e){
			System.out.println("number format exception : "+"\nDetails: "+e);
		}
		finally{
			System.out.println(
				"Number of matches played per year for all the years in IPL: \n"
				+noOfMatch);
		}
	}  
}  







