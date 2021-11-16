
import java.io.*;
import java.util.Scanner;


public class Main {


	public static void main(String[] args)   {  
		final int col_path1 = 5; //18
		final String path1 = "csv/demo.csv";//"demo.csv";//archive/matches.csv
		String next="";
		int count;

		try{
				Scanner sc = new Scanner(new File(path1));

				sc.useDelimiter("\n");   //sets the delimiter pattern  
				//count = 1;

				while (sc.hasNext()) {



					next = sc.next();



					System.out.print(next+":");

					String values[] = next.split(",");

					System.out.print("\n\n:"+values[1]+":\n\n");
					//System.out.println();

/*
					if(count>col_path1-1){
						count = 0;
						System.out.print("Hello");
					}
					if(next.equals("\n"))
						System.out.println("SWARNAVA CHAKRABORTY");
						*/

					//count++;
				}   
				sc.close();


		}catch(FileNotFoundException e){
			System.out.println("File not found : "+path1+"\nDetails: "+e);
		}
	}  
}  







