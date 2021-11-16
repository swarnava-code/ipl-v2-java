
import java.io.*;
import java.util.Scanner;
import java.util.*;//hashmap



public class Main {
	static HashMap<Integer, Integer> noOfMatch = new HashMap<Integer, Integer>();
	static HashMap<String, Integer> winning = new HashMap<String, Integer>();
	static HashMap<Integer, HashMap<String, Integer> > win = new HashMap<Integer, HashMap<String, Integer> >();



/*
	static Short stringToShort(String string){

	}
	static Short shortToString(String string){

	}
	*/


	public static void main(String[] args)   {  
		//final int col_path1 = 5; //18
		final String path1 = "csv/matches.csv";//"demo.csv";//archive/matches.csv
		String next="", winner="";
		int count, key, val, year;
		
		//Short:Year, Byte:count

		try{
				Scanner sc = new Scanner(new File(path1));

				sc.useDelimiter("\n");   //sets the delimiter pattern  
				//count = 1;

				sc.next();//to eliminate heading, otherwise NumberFormatException happen
				while (sc.hasNext()) {



					next = sc.next();



					//System.out.print(next+":");
					String values[] = next.split(",");


					//keys
					year = Integer.parseInt(values[1]);
					winner = values[10];


					// 2 
					if(winning.containsKey(winner)){
						winning.put(winner, winning.get(winner)+1);
					}else{
						winning.put(winner, 1);
					}



					// 2. Number of matches won of all teams over all the years of IPL.
					if(win.containsKey(year)){
						HashMap<String, Integer> inner = win.get(year);
						if(inner.containsKey(winner)){
							inner.put(winner, inner.get(winner)+1);
						}else{
							inner.put(winner, 1);
						}
						win.put(year, inner);
					} else{
						HashMap<String, Integer> inner = new HashMap<String, Integer>();
						inner.put(winner, 1);
						win.put(year, inner);
					}





					//((Map)win.get(year)).get(team);







					

					
					//System.out.print("\n\n:"+year+":\n\n");


					//counting no. of match in each year
					// 1. Number of matches played per year of all the years in IPL.
					if(noOfMatch.containsKey(year)){
						noOfMatch.put(year, noOfMatch.get(year)+1);
					}else{
						noOfMatch.put(year, 1);
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
				"1.) Number of matches played per year for all the years in IPL: \n"
				+noOfMatch);

			winning.remove("");
			System.out.println(
				"\n2. Number of matches won of all teams over all the years of IPL. \n"
				+winning);

		}
	}  
}  







