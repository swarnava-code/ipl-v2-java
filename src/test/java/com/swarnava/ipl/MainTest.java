package com.swarnava.ipl;

import org.junit.jupiter.api.*;
import java.util.*;
import static com.swarnava.ipl.Main.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    static Map<String, Float> actualTopEconomicalBowlersForParticularYear;
    static Map<Integer, Integer> actualNumberOfMatchesPlayedPerYear;
    static Map<String, Integer> actualNumberOfMatchesWonOfAllTeam;
    static Map<String, Integer> actualExtraRunsConcededPerTeamForParticularYear;
    static TreeSet<String> actualWinnersWhoWinInACityLeastOneTime;
    static List<String> expectedWinnersWhoWinInACityLeastOneTime;

    @BeforeAll
    static void init() {
        if (setConnection()) {
            actualNumberOfMatchesPlayedPerYear = printNumberOfMatchesPlayedPerYear();
            actualNumberOfMatchesWonOfAllTeam = printNumberOfMatchesWonOfAllTeam();
            actualExtraRunsConcededPerTeamForParticularYear = printTheExtraRunsConcededPerTeamForParticularYear(2016);
            actualTopEconomicalBowlersForParticularYear = printTheTopEconomicalBowlersForParticularYear(2015);
            actualWinnersWhoWinInACityLeastOneTime = new TreeSet<>(printTheWinnersWhoWinInACityLeastOneTime("Kolkata"));
        }
    }

    @Test
    void testNumberOfMatchesPlayedIn2016(){
        int expectedNoOfMatches2016 = 60;
        int actualNoOfMatches2016 = actualNumberOfMatchesPlayedPerYear.get(2016);
        assertEquals(expectedNoOfMatches2016, actualNoOfMatches2016, "NumberOfMatchesPlayedIn2016 not matched");
    }

    @Test
    void testNumberOfMatchesPlayedIn2017(){
        int expectedNoOfMatches2017 = 59;
        int actualNoOfMatches2017 = actualNumberOfMatchesPlayedPerYear.get(2017);
        assertEquals(expectedNoOfMatches2017, actualNoOfMatches2017, "NumberOfMatchesPlayedIn2017 not matched");
    }

    @Test
    void testNumberOfMatchesWonByKolkataKnightRiders(){
        int expectedNumberOfMatchesWonByKolkataKnightRiders = 77;
        int actualNumberOfMatchesWonByKolkataKnightRiders = actualNumberOfMatchesWonOfAllTeam.get("Kolkata Knight Riders");
        assertEquals(expectedNumberOfMatchesWonByKolkataKnightRiders, actualNumberOfMatchesWonByKolkataKnightRiders,
                "NumberOfMatchesWonByKolkataKnightRiders not matched");
    }

    @Test
    void testNumberOfMatchesWonByRoyalChallengersBangalore(){
        int expectedNumberOfMatchesWonByRoyalChallengersBangalore = 73;
        int actualNumberOfMatchesWonByRoyalChallengersBangalore = actualNumberOfMatchesWonOfAllTeam.get("Royal Challengers Bangalore");
        assertEquals(expectedNumberOfMatchesWonByRoyalChallengersBangalore, actualNumberOfMatchesWonByRoyalChallengersBangalore,
                "NumberOfMatchesWonByRoyalChallengersBangalore not matched");
    }

    @Test
    void testNumberOfMatchesWonByGujaratLions(){
        int expectedNumberOfMatchesWonByGujaratLions = 13;
        int actualNumberOfMatchesWonBGujaratLions = actualNumberOfMatchesWonOfAllTeam.get("Gujarat Lions");
        assertEquals(expectedNumberOfMatchesWonByGujaratLions, actualNumberOfMatchesWonBGujaratLions,
        "NumberOfMatchesWonByGujaratLions not matched");
    }

    @Test
    void testTheExtraRunsConcededByKingsXIPunjabFor2016(){
        int expectedExtraRunsConcededByKingsXIPunjab = 57;
        int actualExtraRunsConcededByKingsXIPunjab = actualExtraRunsConcededPerTeamForParticularYear.get("Kings XI Punjab");
        assertEquals(expectedExtraRunsConcededByKingsXIPunjab, actualExtraRunsConcededByKingsXIPunjab, "Extra Runs Conceded By Kings XI Punjab not matched");
    }

    @Test
    void testTheExtraRunsConcededByKolkataKnightRidersFor2016(){
        int expectedExtraRunsConcededByKolkataKnightRiders = 138;
        int actualExtraRunsConcededByKolkataKnightRiders = actualExtraRunsConcededPerTeamForParticularYear.get("Kolkata Knight Riders");
        assertEquals(expectedExtraRunsConcededByKolkataKnightRiders, actualExtraRunsConcededByKolkataKnightRiders, "Extra Runs Conceded By Kings XI Punjab not matched");
    }

    @Test
    void testEconomicalRateForRNtenDoeschate2015(){
        double expectedEconomicalRateForRNtenDoeschate =
                Math.round(((double) 3.4285714305306123)*100d)/100d;
        double actualEconomicalRateForRNtenDoeschate =
                (double) Math.round(actualTopEconomicalBowlersForParticularYear.get("RN ten Doeschate")*100)/100;
        assertEquals(expectedEconomicalRateForRNtenDoeschate, actualEconomicalRateForRNtenDoeschate,
                "Economical Rate For RN ten Doeschate not matched");
    }

    @Test
    void testEconomicalRateForJYadav2015(){
        double expectedEconomicalRateForJYadav =
                Math.round(((double) 4.142857142857143)*100d)/100d;
        double actualEconomicalRateForJYadav =
                (double) Math.round(actualTopEconomicalBowlersForParticularYear.get("J Yadav")*100)/100;
        assertEquals(expectedEconomicalRateForJYadav, actualEconomicalRateForJYadav,
                "Economical Rate For J Yadav not matched");
    }

    @Test
    void testEconomicalRateForVKohli2015(){
        double expectedEconomicalRateForVKohli =
                Math.round(((double) 5.454545455537191)*100d)/100d;
        double actualEconomicalRateForVKohli =
                (double) Math.round(actualTopEconomicalBowlersForParticularYear.get("V Kohli")*100)/100;
        assertEquals(expectedEconomicalRateForVKohli, actualEconomicalRateForVKohli,
                "Economical Rate For V Kohli not matched");
    }

    @Test
    void testTheWinnersWhoWinInKolkataLeastOneTime(){
        expectedWinnersWhoWinInACityLeastOneTime = Arrays.asList(
                "Kolkata Knight Riders", "Gujarat Lions","Rising Pune Supergiant","Mumbai Indians", "Chennai Super Kings",
                "Rajasthan Royals", "Kings XI Punjab","Kochi Tuskers Kerala","Royal Challengers Bangalore","Delhi Daredevils"
        );
        Collections.sort(expectedWinnersWhoWinInACityLeastOneTime);
        assertIterableEquals(
                expectedWinnersWhoWinInACityLeastOneTime,
                actualWinnersWhoWinInACityLeastOneTime,
                "TheWinnersWhoWinInKolkataLeastOneTime : Set not matched"
        );
    }
}