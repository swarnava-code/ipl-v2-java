package com.swarnava.ipl;
import java.io.File;
import java.util.*;

public class Main {
    private static final String PATH_MATCHES = "csv/matches.csv", PATH_DELIVERY = "csv/deliveries.csv";
    public static final int MATCH_ID = 0, MATCH_SEASON = 1, MATCH_CITY = 2, MATCH_DATE = 3, MATCH_TEAM1 = 4,
            MATCH_TEAM2 = 5, MATCH_TOSS_WINNER = 6, MATCH_TOSS_DECISION = 7, MATCH_RESULT = 8, MATCH_DL_APPLIED = 9,
            MATCH_WINNER = 10, MATCH_WIN_BY_RUNS = 11, MATCH_WIN_BY_WICKETS = 12, MATCH_PLAYER_OF_MATCH = 13,
            MATCH_VENUE = 14, MATCH_UMPIRE1 = 15, MATCH_UMPIRE2 = 16, MATCH_UMPIRE3 = 17,
            DELIVERY_MATCH_ID = 0, DELIVERY_INNING = 1, DELIVERY_BATTING_TEAM = 2, DELIVERY_BOWLING_TEAM = 3,
            DELIVERY_OVER = 4, DELIVERY_BALL = 5, DELIVERY_BATSMAN = 6, DELIVERY_NON_STRIKER = 7, DELIVERY_BOWLER = 8,
            DELIVERY_IS_SUPER_OVER = 9, DELIVERY_WIDE_RUNS = 10, DELIVERY_BYE_RUNS = 11, DELIVERY_LEG_BYE_RUNS = 12,
            DELIVERY_NO_BALL_RUNS = 13, DELIVERY_PENALTY_RUNS = 14, DELIVERY_BATSMAN_RUNS = 15, DELIVERY_EXTRA_RUNS = 16,
            DELIVERY_TOTAL_RUNS = 17, DELIVERY_PLAYER_DISMISSED = 18, DELIVERY_DISMISSAL_KIND = 19, DELIVERY_FIELDER = 20;

    public static void main(String[] args) {
        List<Match> matches = getMatchesData(PATH_MATCHES);
        List<Delivery> deliveries = getDeliveriesData(PATH_DELIVERY);
        printNumberOfMatchesPlayedPerYear(matches);
        printNumberOfMatchesWonOfAllTeam(matches);
        printTheExtraRunsConcededPerTeamForParticularYear(matches, deliveries, 2016);
        printTheTopEconomicalBowlersForParticularYear(matches, deliveries, 2015);
        printTheWinnersWhoWinInAParticularCityLeastOneTime(matches, "Kolkata");
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

    private static void printNumberOfMatchesPlayedPerYear(List<Match> matches){
        Map<Integer, Integer> countNoOfMatchPerYear = new TreeMap<>();
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
        System.out.println("\n\n5.) Winners who win in the city: "+targetCity);
        for (Match match : matches) {
            if (match.getCity().equals(targetCity)){
                winners.add(match.getWinner());
            }
        }
        System.out.print(winners);
    }

    static List<Match> getMatchesData(String PATH_MATCHES) {
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
                match.setId(Integer.parseInt(values[MATCH_ID]));
                match.setSeason(Integer.parseInt(values[MATCH_SEASON]));
                match.setCity(values[MATCH_CITY]);
                match.setDate(values[MATCH_DATE]);
                match.setTeam1(values[MATCH_TEAM1]);
                match.setTeam2(values[MATCH_TEAM2]);
                match.setTossWinner(values[MATCH_TOSS_WINNER]);
                match.setTossDecision(values[MATCH_TOSS_DECISION]);
                match.setResult(values[MATCH_RESULT]);
                match.setDlApplied(Integer.parseInt(values[MATCH_DL_APPLIED]));
                match.setWinner(values[MATCH_WINNER]);
                match.setWinByRuns(Integer.parseInt(values[MATCH_WIN_BY_RUNS]));
                match.setWinByWickets(Integer.parseInt(values[MATCH_WIN_BY_WICKETS]));
                match.setPlayerOfMatch(values[MATCH_PLAYER_OF_MATCH]);
                match.setVenue(values[MATCH_VENUE]);
                match.setUmpire1(values[MATCH_UMPIRE1]);
                match.setUmpire2(values[MATCH_UMPIRE2]);
                match.setUmpire3(values[MATCH_UMPIRE3]);
                matchList.add(match);
            }
            sc.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return matchList;
    }

    static List<Delivery> getDeliveriesData(String PATH_DELIVERY) {
        List<Delivery> deliveryList = new ArrayList<Delivery>();
        try {
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
                delivery.setMatchId(Integer.parseInt(values[DELIVERY_MATCH_ID]));
                delivery.setInning(Integer.parseInt(values[DELIVERY_INNING]));
                delivery.setBattingTeam(values[DELIVERY_BATTING_TEAM]);
                delivery.setBowlingTeam(values[DELIVERY_BOWLING_TEAM]);
                delivery.setOver(Integer.parseInt(values[DELIVERY_OVER]));
                delivery.setBall(Integer.parseInt(values[DELIVERY_BALL]));
                delivery.setBatsMan(values[DELIVERY_BATSMAN]);
                delivery.setNonStriker(values[DELIVERY_NON_STRIKER]);
                delivery.setBowler(values[DELIVERY_BOWLER]);
                delivery.setIsSuperOver(Integer.parseInt(values[DELIVERY_IS_SUPER_OVER]));
                delivery.setWideRuns(Integer.parseInt(values[DELIVERY_WIDE_RUNS]));
                delivery.setByeRuns(Integer.parseInt(values[DELIVERY_BYE_RUNS]));
                delivery.setLegByeRuns(Integer.parseInt(values[DELIVERY_LEG_BYE_RUNS]));
                delivery.setNoBallRuns(Integer.parseInt(values[DELIVERY_NO_BALL_RUNS]));
                delivery.setPenaltyRuns(Integer.parseInt(values[DELIVERY_PENALTY_RUNS]));
                delivery.setBatsmanRuns(Integer.parseInt(values[DELIVERY_BATSMAN_RUNS]));
                delivery.setExtraRuns(Integer.parseInt(values[DELIVERY_EXTRA_RUNS]));
                delivery.setTotalRuns(Integer.parseInt(values[DELIVERY_TOTAL_RUNS]));
                delivery.setPlayerDismissed(values[DELIVERY_PLAYER_DISMISSED]);
                delivery.setDismissalKind(values[DELIVERY_DISMISSAL_KIND]);
                delivery.setFielder(values[DELIVERY_FIELDER]);
                deliveryList.add(delivery);
            }
            sc.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return deliveryList;
    }
}