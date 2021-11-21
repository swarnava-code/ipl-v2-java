package com.swarnava.ipl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static final String PATH_MATCHES = "csv/matches.csv", PATH_DELIVERY = "csv/deliveries.csv";
    static String next="";
    static int id;

    private static final HashMap<String, Integer> runHm = new HashMap<String, Integer>();
    private static final HashMap<String, Float> overHm = new HashMap<String, Float>();

    public static void main(String[] args)   {

        List<Match> matches = null;
        try {
            matches = getMatchesData(PATH_MATCHES);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Delivery> deliveries = null;
        try {
            deliveries = getDeliveriesData(PATH_DELIVERY);
        } catch (Exception e) {
            e.printStackTrace();
        }


        printNumberOfMatchesPlayedPerYear(matches);
        printNumberOfMatchesWonOfAllTeam(matches);
        printTheExtraRunsConcededPerTeamForParticularYear(matches, deliveries, 2016);
        printTheTopEconomicalBowlersForParticularYear(matches, deliveries, 2015);
        printTheWinnersWhoWinInAParticularCityLeastOneTime(matches, "Kolkata");

    }


    private static void printTheTopEconomicalBowlersForParticularYear(List<Match> matches, List<Delivery> deliveries, int targetYear){
        List<Integer> IdList = new LinkedList<Integer>();
        Map<String, List<Float>> bowlerByEconomy = new HashMap<String, List<Float>>();
        // List<Delivery> trackEconomicBowler = new LinkedList<>();
        String bowler="";
        float over, run;
        int year;

        //for (int i=0; i<matches.size(); i++) {
        for(Match match : matches) {
            year = match.getSeason();
            if (year == targetYear) {
                IdList.add(match.getId());
            }
        }

        for(int i=0; i<deliveries.size(); i++) {
            id = deliveries.get(i).getMatchId();
            if( IdList.contains(id) ) {
                bowler = deliveries.get(i).getBowler();
                over = deliveries.get(i).getOver();
                run = deliveries.get(i).getTotalRuns();

                if(bowlerByEconomy.containsKey(bowler)){
                    over += bowlerByEconomy.get(bowler).get(0);
                    run += bowlerByEconomy.get(bowler).get(1);
                    ArrayList<Float> row = new ArrayList<Float>();
                    row.add(0, over);
                    row.add(1, run);
                    bowlerByEconomy.put(bowler, row);
                }else{
                    List<Float> row = new ArrayList<Float>();
                    row.add(0, over);
                    row.add(1, run);
                    bowlerByEconomy.put(bowler, row);

                }
            }
        }

        float res=0;
        List<Float> bowlersEconomy = new ArrayList<Float>();
        for(String key : bowlerByEconomy.keySet() ) {
            bowlersEconomy.add(bowlerByEconomy.get(key).get(0) / bowlerByEconomy.get(key).get(1));
        }


        System.out.print("\n4.) For the year 2015 get the top economical bowlers. :\n"+bowlersEconomy);

    }
    private static void printNumberOfMatchesPlayedPerYear(List<Match> matches){
        Map<Integer, Integer> countNoOfMatchPerYear = new HashMap<Integer, Integer>();
        int year;
        for (Match match : matches) {
            year = match.getSeason();
            if (countNoOfMatchPerYear.containsKey(year)) {
                countNoOfMatchPerYear.put(year, countNoOfMatchPerYear.get(year)+1);
            } else {
                countNoOfMatchPerYear.put(year, 1);
            }
        }
        System.out.println(" \n\n1.) After Collect data from matches 1st time : \n"+countNoOfMatchPerYear);
    }
    private static void printNumberOfMatchesWonOfAllTeam(List<Match> matches){
        HashMap<String, Integer> trackNoOfMatchesWinByTeam = new HashMap<String, Integer>();
        String winner = "";
        for (Match match : matches){
            winner = match.getWinner();
            if (trackNoOfMatchesWinByTeam.containsKey(winner)){
                trackNoOfMatchesWinByTeam.put(winner, trackNoOfMatchesWinByTeam.get(winner)+1);
            } else{
                trackNoOfMatchesWinByTeam.put(winner, 1);
            }
        }
        trackNoOfMatchesWinByTeam.remove("");
        System.out.println(
                "\n2.) Number of matches won of all teams over all the years of IPL. : \n"
                        +trackNoOfMatchesWinByTeam);
    }
    private static void printTheExtraRunsConcededPerTeamForParticularYear(List<Match> matches, List<Delivery> deliveries, int targetYear){
        Map<Integer, String> listOfIdAndWinner = new HashMap<Integer, String>();
        Map<String, Integer> trackExtraRun = new HashMap<String, Integer>();    //for question 3
        String winner = "";
        int countExtraRun = 0, extraRun = 0, matchId;

        for (Match match : matches) {
            if (match.getSeason() == targetYear){
                listOfIdAndWinner.put (match.getId(), match.getWinner());
            }
        }

        for (Delivery delivery : deliveries) {
            matchId = delivery.getMatchId();
            if ( listOfIdAndWinner.containsKey(matchId) ) {
                winner = listOfIdAndWinner.get(matchId);
                extraRun = delivery.getExtraRuns();
                if(trackExtraRun.containsKey(winner)){
                    trackExtraRun.put(winner, trackExtraRun.get(winner)+extraRun);
                }else{
                    trackExtraRun.put(winner, extraRun);
                }
                countExtraRun += extraRun;
            }
        }

        System.out.println("\n3.) For the year "+targetYear+" get the extra runs conceded per team. : \n"+trackExtraRun
                +"\n Total extra run make by all team for the year "+targetYear+" : "+ countExtraRun);
    }
    private static void printTheWinnersWhoWinInAParticularCityLeastOneTime(List<Match> matches, String targetCity){
        Set<String> winners = new HashSet<String>() ;
        System.out.println("\n5.) Winners who win in the city: "+targetCity);
        for (Match match : matches) {
            if (match.getCity().equals(targetCity)){
                winners.add(match.getWinner());
            }
        }
        System.out.print(winners);
    }

    static List<Match> getMatchesData(String PATH_MATCHES) throws FileNotFoundException {
        List<Match> matchList = new ArrayList<Match>();
        Match object;
        Scanner sc = new Scanner(new File(PATH_MATCHES));
        sc.useDelimiter("\n");
        sc.next(); // to eliminate heading text, ignore NumberFormatException
        while (sc.hasNext()) {
            next = sc.next();
            String values[] = next.split(",");
            object = null;
            object = new Match();

            object.setId(Integer.parseInt(values[0]));
            object.setSeason(Integer.parseInt(values[1]));
            object.setCity(values[2]);
            object.setDate(values[3]);
            object.setTeam1(values[4]);
            object.setTeam2(values[5]);
            object.setTossWinner(values[6]);
            object.setTossDecision(values[7]);
            object.setResult(values[8]);
            object.setDlApplied(Integer.parseInt(values[9]));
            object.setWinner(values[10]);
            object.setWinByRuns(Integer.parseInt(values[11]));
            object.setWinByWickets(Integer.parseInt(values[12]));
            object.setPlayerOfMatch(values[13]);
            object.setVenue(values[14]);
            object.setUmpire1(values[15]);
            object.setUmpire2(values[16]);
            object.setUmpire3(values[17]);

            matchList.add(object);
        }
        sc.close();
        return matchList;
    }
    static List<Delivery> getDeliveriesData(String PATH_DELIVERY) throws FileNotFoundException {
        List<Delivery> deliveryList = new ArrayList<Delivery>();
        Delivery object;
        Scanner sc = new Scanner(new File(PATH_DELIVERY));
        sc.useDelimiter("\n");
        sc.next(); // to eliminate head txt, otherwise NumberFormatException happen
        while (sc.hasNext()) {
            next = sc.next();
            String values[] = next.split(",");
            object = null;
            object = new Delivery();

            object.setMatchId(Integer.parseInt(values[0]));
            object.setInning(Integer.parseInt(values[1]));
            object.setBattingTeam(values[2]);
            object.setBowlingTeam(values[3]);
            object.setOver(Integer.parseInt(values[4]));
            object.setBall(Integer.parseInt(values[5]));
            object.setBatsMan(values[6]);
            object.setNonStriker(values[7]);
            object.setBowler(values[8]);
            object.setIsSuperOver(Integer.parseInt(values[9]));
            object.setWideRuns(Integer.parseInt(values[10]));
            object.setByeRuns(Integer.parseInt(values[11]));
            object.setLegByeRuns(Integer.parseInt(values[12]));
            object.setNoBallRuns(Integer.parseInt(values[13]));
            object.setPenaltyRuns(Integer.parseInt(values[14]));
            object.setBatsmanRuns(Integer.parseInt(values[15]));
            object.setExtraRuns(Integer.parseInt(values[16]));
            object.setTotalRuns(Integer.parseInt(values[17]));
            object.setPlayerDismissed(values[18]);
            object.setDismissalKind(values[19]);
            object.setFielder(values[20]);

            deliveryList.add(object);
        }
        sc.close();
        return deliveryList;
    }

}

