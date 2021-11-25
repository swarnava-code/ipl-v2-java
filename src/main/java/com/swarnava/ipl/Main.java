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
    static final int VIEW_COL1 = 1, VIEW_COL2 = 2, VIEW_COL3 = 3, VIEW_COL4 = 4;

    public static void main(String[] args)   {
        if(setConnection()){
            printNumberOfMatchesPlayedPerYear();
            printNumberOfMatchesWonOfAllTeam();
            printTheExtraRunsConcededPerTeamForParticularYear(2016);
            printTheTopEconomicalBowlersForParticularYear(2015);
            printTheWinnersWhoWinInACityLeastOneTime("Kolkata");
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
        Set<Integer> idList = new HashSet<>();
        Map<String, List<Float>> bowlersOverAndRun = new HashMap<String, List<Float>>();
        Map<String, Float> bowlersEconomy = new TreeMap<>();
        String bowler = "", query;
        float over, run;
        int year, id;
        Statement statement;
        try {
            statement = connection.createStatement();
            query = "Select id from "+TABLE_MATCH+" Where season = '"+targetYear+"'";
            ResultSet idSet = statement.executeQuery(query);
            while (idSet.next()) {
                idList.add(Integer.parseInt(idSet.getString(VIEW_COL1)));
            }
            String idListStr = idList.toString();
            query = "Select match_id, bowler, over, total_runs from "+TABLE_DELIVERY+" where match_id in ("+(idListStr.substring(1,idListStr.length()-1))+") ";
            ResultSet idBowlerOverRun = statement.executeQuery(query);
            while (idBowlerOverRun.next()) {
                id = Integer.parseInt(idBowlerOverRun.getString(VIEW_COL1));
                if (idList.contains(id)) {
                    bowler = idBowlerOverRun.getString(VIEW_COL2);
                    over = Float.parseFloat(idBowlerOverRun.getString(VIEW_COL3));
                    run = Float.parseFloat(idBowlerOverRun.getString(VIEW_COL4));
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
        int season = 0, countNoOfMatch = 0;
        String query = "SELECT season, count(winner) FROM matches group by season;";;
        try(
                Statement statement = connection.createStatement();
                ResultSet countWinnerGroupBySeason = statement.executeQuery(query);
                ) {
            while (countWinnerGroupBySeason.next()) {
                season = Integer.parseInt(countWinnerGroupBySeason.getString(VIEW_COL1));
                countNoOfMatch = Integer.parseInt(countWinnerGroupBySeason.getString(VIEW_COL2));
                countNoOfMatchPerYear.put(season, countNoOfMatch);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(" \n\n1.) print Number Of Matches Played Per Year : \n"+countNoOfMatchPerYear);
    }

    private static void printNumberOfMatchesWonOfAllTeam(){
        HashMap<String, Integer> trackNoOfMatchesWinByTeam = new HashMap<String, Integer>();
        String winner = "";
        int count = 0;
        String query = "SELECT winner, count(winner) FROM matches group by winner ;";
        try(
                Statement statement = connection.createStatement();
                ResultSet winnerCountList = statement.executeQuery(query);
                ){
            while (winnerCountList.next()) {
                winner = winnerCountList.getString(VIEW_COL1);
                count = Integer.parseInt(winnerCountList.getString(VIEW_COL2));
                trackNoOfMatchesWinByTeam.put(winner, count);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        trackNoOfMatchesWinByTeam.remove("");
        System.out.println(
                "\n2.) Number of matches won of all teams over all the years of IPL. : \n"
                        +trackNoOfMatchesWinByTeam);
    }

    private static void printTheExtraRunsConcededPerTeamForParticularYear(int targetYear){
        Map<Integer, String> listOfIdAndWinner = new HashMap<Integer, String>();
        Map<String, Integer> trackExtraRun = new HashMap<String, Integer>();
        String winner = "", query = "SELECT id, winner FROM "+TABLE_MATCH+" where season = '"+targetYear+"';";
        int countExtraRun = 0, extraRun = 0, matchId, season;
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);//"SELECT id,season,winner FROM "+ TABLE_MATCH
            while (resultSet.next()) {
                season = Integer.parseInt(resultSet.getString(VIEW_COL1));
                winner = resultSet.getString(VIEW_COL2);
                listOfIdAndWinner.put (season, winner);
            }
            query = "SELECT match_id, extra_runs FROM "+ TABLE_DELIVERY;
            resultSet = statement.executeQuery(query);
            boolean v = resultSet.next();
            while (v) {
                matchId = Integer.parseInt(resultSet.getString(VIEW_COL1));
                if ( listOfIdAndWinner.containsKey(matchId) ) {
                    winner = listOfIdAndWinner.get(matchId);
                    extraRun = Integer.parseInt(resultSet.getString(VIEW_COL2));
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

    private static void printTheWinnersWhoWinInACityLeastOneTime(String targetCity){
        Set<String> winners = new HashSet<String>() ;
        System.out.println("\n\n5.) Winners who win in the city: "+targetCity);
        String query = "SELECT distinct winner from "+ TABLE_MATCH +" where city = '"+targetCity+"';";
        try {
            Statement statement = connection.createStatement();
            ResultSet winnerSet = statement.executeQuery(query);
            while (winnerSet.next()) {
                winners.add(winnerSet.getString(VIEW_COL1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.print(winners);
    }
}

