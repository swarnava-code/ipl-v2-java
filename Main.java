
import java.io.*;
import java.util.Scanner;
import java.util.*;//hashmap



public class Main {

	static final String path1 = "csv/matches.csv", path2 = "csv/deliveries.csv";
	static String next="", winner="";
	static int count, key, val, year, id, count_extra_run2016=0, extra_run;

	static HashMap<Integer, Integer> noOfMatch = new HashMap<Integer, Integer>();
	static HashMap<String, Integer> winning = new HashMap<String, Integer>();
	static HashMap<Integer, HashMap<String, Integer> > win = new HashMap<Integer, HashMap<String, Integer> >();
	static HashMap<Integer, String> id2016 = new HashMap<Integer, String>();
	static HashMap<String, Integer> countExtraRun = new HashMap<String, Integer>();
	//static Set<Integer> id2016 = new LinkedHashSet<Integer>(); //to collect id from matches.csv, will be use in deliveries.csv


	static boolean scanMatches() throws FileNotFoundException{
		Scanner sc = new Scanner(new File(path1));

		sc.useDelimiter("\n");   //sets the delimiter pattern  
		//count = 1;

		sc.next();//to eliminate heading, otherwise NumberFormatException happen
		while (sc.hasNext()) {
			next = sc.next();
			String values[] = next.split(",");

			id = Integer.parseInt(values[0]);
			year = Integer.parseInt(values[1]);//key
			winner = values[10];//key


			//counting no. of match in each year
			// 1. Number of matches played per year of all the years in IPL.
			if(noOfMatch.containsKey(year)){
				noOfMatch.put(year, noOfMatch.get(year)+1);
			}else{
				noOfMatch.put(year, 1);
			}

			// 3. collect id of 2016 ((1st part algo))
			if(year==2016){
				id2016.put(id, winner);
			}


			// 2. Number of matches won of all teams over all the years of IPL.
			if(winning.containsKey(winner)){
				winning.put(winner, winning.get(winner)+1);
			}else{
				winning.put(winner, 1);
			}

			/*
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
			*/

		}   
		sc.close();
		return true;
	}
	static boolean scanDeliveries() throws FileNotFoundException{

		Scanner sc = new Scanner(new File(path2));
		sc.useDelimiter("\n");   //sets the delimiter pattern  
		sc.next(); //to eliminate heading, otherwise NumberFormatException happen
		while (sc.hasNext()) {
			next = sc.next();
			String values[] = next.split(",");

			id = Integer.parseInt(values[0]); //foreign key
			extra_run = Integer.parseInt(values[16]);

			// 3. For the year 2016 get the extra runs conceded per team. (2nd part algo)
			if(id2016.containsKey(id)){
				winner = id2016.get(id);
				if(countExtraRun.containsKey(winner)){
					countExtraRun.put(winner, countExtraRun.get(winner)+extra_run);
				}else{
					countExtraRun.put(winner, extra_run);
				}
				count_extra_run2016 += extra_run;
			}

		}   
		sc.close();

		return true;
	}
	


	public static void main(String[] args)   {  
		
		
		// fetching 1st csv file and process required data : Matches
		try{
			scanMatches();
		}catch(FileNotFoundException e){
			System.out.println("File not found : "+path1+"\nDetails: "+e);
		}
		catch(Exception e){
			System.out.println("unknown exception handled : \nDetails: "+e);
		}


		// fetching 2nd csv file and process required data : Deliveries
		try{
			scanDeliveries();
		}catch(FileNotFoundException e){
			System.out.println("File2 not found : "+path2+"\nDetails: "+e);
		}
		catch(Exception e){
			System.out.println("unknown exception handled while process file2 : \nDetails: "+e);
		}

		//Outputs
		System.out.println(
			"1.) Number of matches played per year for all the years in IPL. : \n"
			+noOfMatch);

		winning.remove(""); //remove null/blank key element
		System.out.println(
			"\n2. Number of matches won of all teams over all the years of IPL. : \n"
			+winning);

		System.out.println("\n3. For the year 2016 get the extra runs conceded per team. : \n"+countExtraRun
			+"\n Total extra run make by all team for the year 2016 : "+count_extra_run2016);

		
	}  
}  







