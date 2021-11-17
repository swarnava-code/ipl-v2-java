package com.swarnava.ipl;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final String path1 = "csv/matches.csv", path2 = "csv/deliveries.csv", path3 = "csv/demo.csv";
    static String next="", winner="", bowler, name;
    static int count, key, val, year, id, count_extra_run2016=0, extra_run, run, salary;
    static float over, economic_rate;

    static HashMap<Integer, Integer> noOfMatch = new HashMap<Integer, Integer>();//for question 1
    static HashMap<String, Integer> winning = new HashMap<String, Integer>();//for question 2
    //static HashMap<Integer, HashMap<String, Integer> > win = new HashMap<Integer, HashMap<String, Integer> >();
    static HashMap<Integer, String> id2016 = new HashMap<Integer, String>();//for question 3 : to collect id from matches.csv
    static HashMap<String, Integer> countExtraRun = new HashMap<String, Integer>();//for question 3
    static List<Integer> id2015 = new LinkedList<Integer>();//for question 4 : to collect id from matches.csv
    static HashMap<String, Float> economicBowler = new HashMap<String, Float>();//for question 4
    static HashMap<String, Integer> myScenario = new HashMap<String, Integer>();
    static HashMap<String, Integer> runHm = new HashMap<String, Integer>();
    static HashMap<String, Float> overHm = new HashMap<String, Float>();




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

            // 4. collect id for 2015 ((1st part algo))
            if(year==2015){
                id2015.add(id);
            }

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
            bowler = values[8];
            run = Integer.parseInt(values[17]);
            over = Float.parseFloat(values[4]);


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


            //confirm 2015
            if(id2015.contains(id)){

                //count total run for a bowler
                if(runHm.containsKey(bowler)){
                    runHm.put(bowler, runHm.get(bowler)+run);
                }else{
                    runHm.put(bowler, run);
                }

                //count total over for a bowler
                if(overHm.containsKey(bowler)){
                    overHm.put(bowler, overHm.get(bowler)+over);
                }else{
                    overHm.put(bowler, over);
                }


            }






            // 4.) For the year 2015 get the top economical bowlers.

            for(String key : runHm.keySet()) {
                run = runHm.get(key);
                over = overHm.get(key);
                economic_rate = run / over;

                if(economicBowler.containsKey(key)){
                    economicBowler.put(key, economicBowler.get(key)+economic_rate);
                }else{
                    economicBowler.put(key, economic_rate);
                }
            }


        }
        sc.close();

        return true;
    }

    static boolean scanDemo() throws FileNotFoundException{
        Scanner sc = new Scanner(new File(path3));
        sc.useDelimiter("\n");   //sets the delimiter pattern
        sc.next(); //to eliminate heading, otherwise NumberFormatException happen
        while (sc.hasNext()) {
            next = sc.next();
            String values[] = next.split(",");

            name = values[0];
            salary = Integer.parseInt(values[3].trim()+"");

            // 5.) My scenario: list name whose salary more than 29k.
            if(salary>29000){
                if(myScenario.containsKey(name)){
                    myScenario.put(name, myScenario.get(name)+salary);
                }else{
                    myScenario.put(name, salary);
                }
            }

        }
        sc.close();
        return true;
    }



    public static void main(String[] args)   {


        // fetching 1st csv file and process with required data : Matches
        try{
            scanMatches();
        }catch(FileNotFoundException e){
            System.out.println("File not found : "+path1+"\nDetails: "+e);
        }
        catch(Exception e){
            System.out.println("UNKNOWN exception handled while process file-1 : \nDetails: "+e+"\n\n");
        }


        // fetching 2nd csv file and process with required data : Deliveries
        try{
            scanDeliveries();
        }catch(FileNotFoundException e){
            System.out.println("File2 not found : "+path2+"\nDetails: "+e);
        }
        catch(Exception e){
            System.out.println("UNKNOWN exception handled while process file-2 : \nDetails: "+e+"\n\n");
        }

        // fetching 3rd csv file and process with required data : (My scenario: demo.csv)
        try{
            scanDemo();
        }catch(FileNotFoundException e){
            System.out.println("File not found : "+path3+"\nDetails: "+e);
        }
        catch(Exception e){
            System.out.println("UNKNOWN exception handled while process file-3 : \nDetails: "+e+"\n\n");
        }

        //Outputs
        System.out.println(
                "1.) Number of matches played per year for all the years in IPL. : \n"
                        +noOfMatch);

        winning.remove(""); //remove null/blank key element
        System.out.println(
                "\n2.) Number of matches won of all teams over all the years of IPL. : \n"
                        +winning);

        System.out.println("\n3.) For the year 2016 get the extra runs conceded per team. : \n"+countExtraRun
                +"\n Total extra run make by all team for the year 2016 : "+count_extra_run2016);

        System.out.println("\n4.) For the year 2015 get the top economical bowlers. :\n"+economicBowler);


        System.out.println("\n5.) My scenario: list name whose salary more than 29k. :\n"+myScenario);


        System.out.println("\nrunHm: \n"+runHm);
        System.out.println("\noverHm: \n"+overHm);




    }
}

