package com.swarnava.ipl;

import java.io.File;
import java.sql.*;
import java.util.*;

public class Main {
    private static final String PATH_MATCHES = "csv/matches.csv", PATH_DELIVERY = "csv/deliveries.csv";
    private static final int ID=0,SEASON=1,CITY=2,DATE=3,TEAM1=4,TEAM2=5,TOSS_WINNER=6,TOSS_DECISION=7,RESULT=8,
            DL_APPLIED=9,WINNER=10,WIN_BY_RUNS=11,WIN_BY_WICKETS=12,PLAYER_OF_MATCH=13,VENUE=14,
            UMPIRE1=15,UMPIRE2=16,UMPIRE3=17;
    private static final int MATCH_ID=0,INNING=1,BATTING_TEAM=2,BOWLING_TEAM=3,OVER=4,BALL=5,BATSMAN=6,NON_STRIKER=7,
            BOWLER=8,IS_SUPER_OVER=9,WIDE_RUNS=10,BYE_RUNS=11,LEGBYE_RUNS=12,NOBALL_RUNS=13,PENALTY_RUNS=14,
            BATSMAN_RUNS=15,EXTRA_RUNS=16,TOTAL_RUNS=17,PLAYER_DISMISSED=18,DISMISSAL_KIND=19,FIELDER=20;
    static Connection connection = null;
    static final String DATABASE_NAME = "new_schema1";
    static final String URL = "jdbc:mysql://localhost:3306/"+ DATABASE_NAME;
    static final String TABLE_NAME = "matches";
    static final String USER_NAME = "swarnava";
    static final String PASSWORD = "12345";
    static final int COL1 = 1;

    public static void main(String[] args)   {

        if(setConnection()){
            //List<Match> matches = matchesData(PATH_MATCHES);
            //List<Delivery> deliveries = deliveriesData(PATH_DELIVERY);
            printNumberOfMatchesPlayedPerYear();
            printNumberOfMatchesWonOfAllTeam();
//            printTheExtraRunsConcededPerTeamForParticularYear(matches, deliveries, 2016);
//            printTheTopEconomicalBowlersForParticularYear(matches, deliveries, 2015);
//            printTheWinnersWhoWinInAParticularCityLeastOneTime(matches, "Kolkata");

        }
    }

    static boolean setConnection(){
        try {
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void printTheTopEconomicalBowlersForParticularYear(List<Match> matches, List<Delivery> deliveries, int targetYear){
        Set<Integer> IdList = new HashSet<>();
        Map<String, List<Float>> bowlersOverAndRun = new HashMap<String, List<Float>>();
        Map<String, Float> bowlersEconomy = new TreeMap<>();
        String bowler = "";
        float over, run;
        int year, id;
        for(Match match : matches) {
            year = match.getSeason();
            if (year == targetYear) {
                IdList.add(match.getId());
            }
        }
        for(Delivery delivery : deliveries) {
            id = delivery.getMatchId();
            if( IdList.contains(id) ) {
                bowler = delivery.getBowler();
                over = delivery.getOver();
                run = delivery.getTotalRuns();
                if(bowlersOverAndRun.containsKey(bowler)){
                    over += bowlersOverAndRun.get(bowler).get(0);
                    run += bowlersOverAndRun.get(bowler).get(1);
                    ArrayList<Float> row = new ArrayList<Float>();
                    row.add(0, over);
                    row.add(1, run);
                    bowlersOverAndRun.put(bowler, row);
                }else{
                    List<Float> row = new ArrayList<Float>();
                    row.add(0, over);
                    row.add(1, run);
                    bowlersOverAndRun.put(bowler, row);
                }
            }
        }
        for(String key : bowlersOverAndRun.keySet() ) {
            bowlersEconomy.put( key, (bowlersOverAndRun.get(key).get(0) / bowlersOverAndRun.get(key).get(1)) );
        }
        System.out.print("\n4.) For the year 2015 get the top economical bowlers. :\n"+bowlersEconomy+"\nSize="+bowlersEconomy.size());
    }

    private static void printNumberOfMatchesPlayedPerYear(){
        Map<Integer, Integer> countNoOfMatchPerYear = new TreeMap<>();
        int season = 0;
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT season FROM matches;";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                season = Integer.parseInt(resultSet.getString(COL1));
                if (countNoOfMatchPerYear.containsKey(season)) {
                    countNoOfMatchPerYear.put(season, countNoOfMatchPerYear.get(season)+1);
                } else {
                    countNoOfMatchPerYear.put(season, 1);
                }
            }
        }catch (Exception e){
            System.out.println("Error");
            e.printStackTrace();
        }
        System.out.println(" \n\n1.) After Collect data from matches 1st time : \n"+countNoOfMatchPerYear);
    }

    private static void printNumberOfMatchesWonOfAllTeam(){
        HashMap<String, Integer> trackNoOfMatchesWinByTeam = new HashMap<String, Integer>();
        String winner = "";
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT winner FROM "+TABLE_NAME);
            while (resultSet.next()) {
                winner = resultSet.getString(COL1);
                if (trackNoOfMatchesWinByTeam.containsKey(winner)){
                    trackNoOfMatchesWinByTeam.put(winner, trackNoOfMatchesWinByTeam.get(winner)+1);
                } else{
                    trackNoOfMatchesWinByTeam.put(winner, 1);
                }
            }
        }catch (Exception e){

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
        System.out.println("\n\n5.) Winners who win in the city: "+targetCity);
        for (Match match : matches) {
            if (match.getCity().equals(targetCity)){
                winners.add(match.getWinner());
            }
        }
        System.out.print(winners);
    }

    static List<Match> matchesData(String PATH_MATCHES) {
        List<Match> matchList = new ArrayList<Match>();
        try {
            Match match;
            String line;
            Scanner sc = new Scanner(new File(PATH_MATCHES));
            sc.useDelimiter("\n");
            sc.next(); // to eliminate heading text, ignore NumberFormatException
            while (sc.hasNext()) {
                line = sc.next();
                String values[] = line.split(",");
                match = null;
                match = new Match();
                match.setId(Integer.parseInt(values[ID]));
                match.setSeason(Integer.parseInt(values[SEASON]));
                match.setCity(values[CITY]);
                match.setDate(values[DATE]);
                match.setTeam1(values[TEAM1]);
                match.setTeam2(values[TEAM2]);
                match.setTossWinner(values[TOSS_WINNER]);
                match.setTossDecision(values[TOSS_DECISION]);
                match.setResult(values[RESULT]);
                match.setDlApplied(Integer.parseInt(values[DL_APPLIED]));
                match.setWinner(values[WINNER]);
                match.setWinByRuns(Integer.parseInt(values[WIN_BY_RUNS]));
                match.setWinByWickets(Integer.parseInt(values[WIN_BY_WICKETS]));
                match.setPlayerOfMatch(values[PLAYER_OF_MATCH]);
                match.setVenue(values[VENUE]);
                match.setUmpire1(values[UMPIRE1]);
                match.setUmpire2(values[UMPIRE2]);
                match.setUmpire3(values[UMPIRE3]);
                matchList.add(match);
            }
            sc.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return matchList;
    }

    static List<Delivery> deliveriesData(String PATH_DELIVERY) {
        List<Delivery> deliveryList = new ArrayList<Delivery>();
        try{
            Delivery delivery;
            String line;
            Scanner sc = new Scanner(new File(PATH_DELIVERY));
            sc.useDelimiter("\n");
            sc.next(); // to eliminate head txt, otherwise NumberFormatException happen
            while (sc.hasNext()) {
                line = sc.next();
                String values[] = line.split(",");
                delivery = null;
                delivery = new Delivery();
                delivery.setMatchId(Integer.parseInt(values[MATCH_ID]));
                delivery.setInning(Integer.parseInt(values[INNING]));
                delivery.setBattingTeam(values[BATTING_TEAM]);
                delivery.setBowlingTeam(values[BOWLING_TEAM]);
                delivery.setOver(Integer.parseInt(values[OVER]));
                delivery.setBall(Integer.parseInt(values[BALL]));
                delivery.setBatsMan(values[BATSMAN]);
                delivery.setNonStriker(values[NON_STRIKER]);
                delivery.setBowler(values[BOWLER]);
                delivery.setIsSuperOver(Integer.parseInt(values[IS_SUPER_OVER]));
                delivery.setWideRuns(Integer.parseInt(values[WIDE_RUNS]));
                delivery.setByeRuns(Integer.parseInt(values[BYE_RUNS]));
                delivery.setLegByeRuns(Integer.parseInt(values[LEGBYE_RUNS]));
                delivery.setNoBallRuns(Integer.parseInt(values[NOBALL_RUNS]));
                delivery.setPenaltyRuns(Integer.parseInt(values[PENALTY_RUNS]));
                delivery.setBatsmanRuns(Integer.parseInt(values[BATSMAN_RUNS]));
                delivery.setExtraRuns(Integer.parseInt(values[EXTRA_RUNS]));
                delivery.setTotalRuns(Integer.parseInt(values[TOTAL_RUNS]));
                delivery.setPlayerDismissed(values[PLAYER_DISMISSED]);
                delivery.setDismissalKind(values[DISMISSAL_KIND]);
                delivery.setFielder(values[FIELDER]);
                deliveryList.add(delivery);
            }
            sc.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return deliveryList;
    }
}

