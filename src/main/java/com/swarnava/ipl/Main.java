package com.swarnava.ipl;
import java.sql.*;
import java.util.*;

public class Main {
    static Connection connection = null;
    static final String DATABASE_NAME = "new_schema1";
    static final String URL = "jdbc:mysql://localhost:3306/"+ DATABASE_NAME;
    static final String TABLE_MATCH = "matches";
    static final String TABLE_DELIVERY = "deliveries2";
    static final String USER_NAME = "swarnava";
    static final String PASSWORD = "12345";
    static final int COL1 = 1, COL2 = 2, COL3 = 3, COL4 = 4;

    public static void main(String[] args)   {
        if(setConnection()){
            printNumberOfMatchesPlayedPerYear();
            printNumberOfMatchesWonOfAllTeam();
            printTheExtraRunsConcededPerTeamForParticularYear(2016);
            printTheTopEconomicalBowlersForParticularYear(2015);
            printTheWinnersWhoWinInAParticularCityLeastOneTime("Kolkata");
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

    private static void printTheTopEconomicalBowlersForParticularYear(int targetYear){
        Set<Integer> IdList = new HashSet<>();
        Map<String, List<Float>> bowlersOverAndRun = new HashMap<String, List<Float>>();
        Map<String, Float> bowlersEconomy = new TreeMap<>();
        String bowler = "", query;
        float over, run;
        int year, id;
        Statement statement;
        ResultSet resultSet;
        try {
            statement = connection.createStatement();
            query = "Select id, season from "+TABLE_MATCH;
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                year = Integer.parseInt(resultSet.getString(COL2));
                if (year == targetYear) {
                    IdList.add(Integer.parseInt(resultSet.getString(COL1)));
                }
            }
            query = "Select match_id, bowler, over, total_runs from "+TABLE_DELIVERY;//MatchId(del), Bowler, over, total_runs
            resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                id = Integer.parseInt(resultSet.getString(COL1));
                if (IdList.contains(id)) {
                    bowler = resultSet.getString(COL2);
                    over = Float.parseFloat(resultSet.getString(COL3));
                    run = Float.parseFloat(resultSet.getString(COL4));
                    if (bowlersOverAndRun.containsKey(bowler)) {
                        over += bowlersOverAndRun.get(bowler).get(0);
                        run += bowlersOverAndRun.get(bowler).get(1);
                        ArrayList<Float> row = new ArrayList<Float>();
                        row.add(0, over);
                        row.add(1, run);
                        bowlersOverAndRun.put(bowler, row);
                    } else {
                        List<Float> row = new ArrayList<Float>();
                        row.add(0, over);
                        row.add(1, run);
                        bowlersOverAndRun.put(bowler, row);
                    }
                }
            }
            for (String key : bowlersOverAndRun.keySet()) {
                bowlersEconomy.put(key, (bowlersOverAndRun.get(key).get(0) / bowlersOverAndRun.get(key).get(1)));
            }
        }catch (Exception e){
            e.printStackTrace();
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
            ResultSet resultSet = statement.executeQuery("SELECT winner FROM "+ TABLE_MATCH);
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

    private static void printTheExtraRunsConcededPerTeamForParticularYear(int targetYear){
        Map<Integer, String> listOfIdAndWinner = new HashMap<Integer, String>();
        Map<String, Integer> trackExtraRun = new HashMap<String, Integer>();
        String winner = "";
        int countExtraRun = 0, extraRun = 0, matchId, season;
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id,season,winner FROM "+ TABLE_MATCH);
            while (resultSet.next()) {
                season = Integer.parseInt(resultSet.getString(COL2));
                if (season == targetYear){
                    listOfIdAndWinner.put (Integer.parseInt(resultSet.getString(COL1)), resultSet.getString(COL3));
                }
            }
            resultSet = statement.executeQuery("SELECT match_id, extra_runs FROM "+ TABLE_DELIVERY);
            boolean v = resultSet.next();
            while (v) {
                matchId = Integer.parseInt(resultSet.getString(COL1));
                if ( listOfIdAndWinner.containsKey(matchId) ) {
                    winner = listOfIdAndWinner.get(matchId);
                    extraRun = Integer.parseInt(resultSet.getString(COL2));
                    if(trackExtraRun.containsKey(winner)){
                        trackExtraRun.put(winner, trackExtraRun.get(winner)+extraRun);
                    }else{
                        trackExtraRun.put(winner, extraRun);
                    }
                    countExtraRun += extraRun;
                }
                v=resultSet.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("\n3.) For the year "+targetYear+" get the extra runs conceded per team. : \n"+trackExtraRun
                +"\n Total extra run make by all team for the year "+targetYear+" : "+ countExtraRun);
    }

    private static void printTheWinnersWhoWinInAParticularCityLeastOneTime(String targetCity){
        Set<String> winners = new HashSet<String>() ;
        System.out.println("\n\n5.) Winners who win in the city: "+targetCity);
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT city, winner from "+ TABLE_MATCH);
            while (resultSet.next()) {
                if (targetCity.equals(resultSet.getString(COL1))) {
                    winners.add(resultSet.getString(COL2));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.print(winners);
    }
}

