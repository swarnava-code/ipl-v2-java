package com.swarnava.ipl;

import java.io.File;
import java.util.*;

public class Main {
    private static final int ID = 0;
    private static final int SEASON = 1;
    private static final int CITY = 2;
    private static final int DATE = 3;
    private static final int TEAM1 = 4;
    private static final int TEAM2 = 5;
    private static final int TOSS_WINNER = 6;
    private static final int TOSS_DECISION = 7;
    private static final int RESULT = 8;
    private static final int DL_APPLIED = 9;
    private static final int WINNER = 10;
    private static final int WIN_BY_RUNS = 11;
    private static final int WIN_BY_WICKETS = 12;
    private static final int PLAYER_OF_MATCH = 13;
    private static final int VENUE = 14;
    private static final int UMPIRE1 = 15;
    private static final int UMPIRE2 = 16;
    private static final int UMPIRE3 = 17;
    private static final int MATCH_ID = 0;
    private static final int INNING = 1;
    private static final int BATTING_TEAM = 2;
    private static final int BOWLING_TEAM = 3;
    private static final int OVER = 4;
    private static final int BALL = 5;
    private static final int BATSMAN = 6;
    private static final int NON_STRIKER = 7;
    private static final int BOWLER = 8;
    private static final int IS_SUPER_OVER = 9;
    private static final int WIDE_RUNS = 10;
    private static final int BYE_RUNS = 11;
    private static final int LEGBYE_RUNS = 12;
    private static final int NOBALL_RUNS = 13;
    private static final int PENALTY_RUNS = 14;
    private static final int BATSMAN_RUNS = 15;
    private static final int EXTRA_RUNS = 16;
    private static final int TOTAL_RUNS = 17;
    private static final int PLAYER_DISMISSED = 18;
    private static final int DISMISSAL_KIND = 19;
    private static final int FIELDER = 20;

    public static void main(String[] args) {
        List<Match> matches = matchesData("dataset/matches.csv");
        List<Delivery> deliveries = deliveriesData("dataset/deliveries.csv");
        printNumberOfMatchesPlayedPerYear(matches);
        printNumberOfMatchesWonOfAllTeam(matches);
        printTheExtraRunsConcededPerTeamForParticularYear(matches, deliveries, 2016);
        printTheTopEconomicalBowlersForParticularYear(matches, deliveries, 2015);
        printTheWinnersWhoWinInACityLeastOneTime(matches, "Kolkata");
    }

    protected static List<Match> matchesData(String PATH_MATCHES) {
        List<Match> matchList = new ArrayList<Match>();
        try {
            Match match;
            String line;
            Scanner sc = new Scanner(new File(PATH_MATCHES));
            sc.useDelimiter("\n");
            sc.next();
            while (sc.hasNext()) {
                line = sc.next();
                String values[] = line.split(",");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matchList;
    }

    protected static List<Delivery> deliveriesData(String PATH_DELIVERY) {
        List<Delivery> deliveryList = new ArrayList<Delivery>();
        try {
            Delivery delivery;
            String line;
            Scanner sc = new Scanner(new File(PATH_DELIVERY));
            sc.useDelimiter("\n");
            sc.next();
            while (sc.hasNext()) {
                line = sc.next();
                String values[] = line.split(",");
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
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deliveryList;
    }

    protected static Map<String, Float> printTheTopEconomicalBowlersForParticularYear(
            List<Match> matches, List<Delivery> deliveries, int targetYear) {
        Set<Integer> IdList = new HashSet<>();
        Map<String, List<Integer>> bowlersOverAndRun = new HashMap<String, List<Integer>>();
        Map<String, Float> bowlersEconomy = new TreeMap<>();
        String bowler = "";
        int countOver;
        int run;
        int year;
        int id;
        for (Match match : matches) {
            year = match.getSeason();
            if (year == targetYear) {
                IdList.add(match.getId());
            }
        }
        for (Delivery delivery : deliveries) {
            id = delivery.getMatchId();
            if (IdList.contains(id)) {
                bowler = delivery.getBowler();
                run = delivery.getTotalRuns();
                if (bowlersOverAndRun.containsKey(bowler)) {
                    countOver = bowlersOverAndRun.get(bowler).get(0) + 1;
                    run += bowlersOverAndRun.get(bowler).get(1);
                    ArrayList<Integer> row = new ArrayList<Integer>();
                    row.add(0, countOver);
                    row.add(1, run);
                    bowlersOverAndRun.put(bowler, row);
                } else {
                    List<Integer> row = new ArrayList<Integer>();
                    row.add(0, 1);
                    row.add(1, run);
                    bowlersOverAndRun.put(bowler, row);
                }
            }
        }
        for (String key : bowlersOverAndRun.keySet()) {
            float over = bowlersOverAndRun.get(key).get(0) / 6f;
            bowlersEconomy.put(key, (bowlersOverAndRun.get(key).get(1) / over));
        }
        System.out.print("\n4.) For the year 2015 get the top economical bowlers. :\n" +
                bowlersEconomy + "\nSize=" + bowlersEconomy.size());
        return bowlersEconomy;
    }

    protected static Map<Integer, Integer> printNumberOfMatchesPlayedPerYear(List<Match> matches) {
        Map<Integer, Integer> countNoOfMatchPerYear = new TreeMap<>();
        int year;
        for (Match match : matches) {
            year = match.getSeason();
            if (countNoOfMatchPerYear.containsKey(year)) {
                countNoOfMatchPerYear.put(year, countNoOfMatchPerYear.get(year) + 1);
            } else {
                countNoOfMatchPerYear.put(year, 1);
            }
        }
        System.out.println(" \n\n1.) print Number Of Matches Played Per Year : \n" + countNoOfMatchPerYear);
        return countNoOfMatchPerYear;
    }

    protected static Map<String, Integer> printNumberOfMatchesWonOfAllTeam(List<Match> matches) {
        HashMap<String, Integer> trackNoOfMatchesWinByTeam = new HashMap<String, Integer>();
        String winner = "";
        for (Match match : matches) {
            winner = match.getWinner();
            if (trackNoOfMatchesWinByTeam.containsKey(winner)) {
                trackNoOfMatchesWinByTeam.put(winner, trackNoOfMatchesWinByTeam.get(winner) + 1);
            } else {
                trackNoOfMatchesWinByTeam.put(winner, 1);
            }
        }
        trackNoOfMatchesWinByTeam.remove("");
        System.out.println(
                "\n2.) Number of matches won of all teams over all the years of IPL. : \n"
                        + trackNoOfMatchesWinByTeam);
        return trackNoOfMatchesWinByTeam;
    }

    protected static Map<String, Integer>
    printTheExtraRunsConcededPerTeamForParticularYear(List<Match> matches, List<Delivery> deliveries, int targetYear) {
        Map<Integer, String> listOfIdAndWinner = new HashMap<Integer, String>();
        Map<String, Integer> trackExtraRun = new HashMap<String, Integer>();
        String winner = "";
        int countExtraRun = 0;
        int extraRun = 0;
        int matchId;
        for (Match match : matches) {
            if (match.getSeason() == targetYear) {
                listOfIdAndWinner.put(match.getId(), match.getWinner());
            }
        }
        for (Delivery delivery : deliveries) {
            matchId = delivery.getMatchId();
            if (listOfIdAndWinner.containsKey(matchId)) {
                winner = listOfIdAndWinner.get(matchId);
                extraRun = delivery.getExtraRuns();
                if (trackExtraRun.containsKey(winner)) {
                    trackExtraRun.put(winner, trackExtraRun.get(winner) + extraRun);
                } else {
                    trackExtraRun.put(winner, extraRun);
                }
                countExtraRun += extraRun;
            }
        }
        System.out.println("\n3.) For the year " + targetYear +
                " get the extra runs conceded per team. : \n" + trackExtraRun
                + "\n Total extra run make by all team for the year " + targetYear + " : " + countExtraRun);
        return trackExtraRun;
    }

    protected static Set<String> printTheWinnersWhoWinInACityLeastOneTime(List<Match> matches, String targetCity) {
        Set<String> winners = new HashSet<String>();
        for (Match match : matches) {
            if (match.getCity().equals(targetCity)) {
                winners.add(match.getWinner());
            }
        }
        System.out.print("\n\n5.) Winners who win in the city: " + targetCity+"\n"+winners);
        return winners;
    }
}