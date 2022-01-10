package com.swarnava.ipl;

import org.junit.jupiter.api.*;
import java.util.*;
import static com.swarnava.ipl.Main.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    static Main main = new Main();
    static Map<String, Float> actualTopEconomicalBowlersForParticularYear;
    static Map<Integer, Integer> actualNumberOfMatchesPlayedPerYear;
    static Map<String, Integer> actualNumberOfMatchesWonOfAllTeam;
    static Map<String, Integer> actualExtraRunsConcededPerTeamForParticularYear;
    static TreeSet<String> actualWinnersWhoWinInACityLeastOneTime;
    static Map<String, Float> expectedTopEconomicalBowlersForParticularYear;
    static Map<Integer, Integer> expectedNumberOfMatchesPlayedPerYear;
    static Map<String, Integer> expectedNumberOfMatchesWonOfAllTeam;
    static Map<String, Integer> expectedExtraRunsConcededPerTeamForParticularYear;
    static List<String> expectedWinnersWhoWinInACityLeastOneTime;

    @BeforeAll
    static void init() {
        if (main.setConnection()) {
            actualNumberOfMatchesPlayedPerYear = printNumberOfMatchesPlayedPerYear();
            actualNumberOfMatchesWonOfAllTeam = printNumberOfMatchesWonOfAllTeam();
            actualExtraRunsConcededPerTeamForParticularYear = printTheExtraRunsConcededPerTeamForParticularYear(2016);
            actualTopEconomicalBowlersForParticularYear = printTheTopEconomicalBowlersForParticularYear(2015);
            actualWinnersWhoWinInACityLeastOneTime = new TreeSet<>(printTheWinnersWhoWinInACityLeastOneTime("Kolkata"));
        }
    }

    @Test
    void testNumberOfMatchesPlayedPerYear(){
        int expectedNoOfMatches2016 = 60;
        int expectedNoOfMatches2017 = 59;
        int actualNoOfMatches2016 = actualNumberOfMatchesPlayedPerYear.get(2016);
        int actualNoOfMatches2017 = actualNumberOfMatchesPlayedPerYear.get(2017);
        assertEquals(expectedNoOfMatches2016, actualNoOfMatches2016);
        assertEquals(expectedNoOfMatches2017, actualNoOfMatches2017);
    }

    @Test
    void testNumberOfMatchesWonOfAllTeam(){
        fail("not implemented");
    }

    @Test
    void testTheExtraRunsConcededPerTeamForParticularYear(){
        fail("not implemented");
    }

    @Test
    void testTheTopEconomicalBowlersForParticularYear(){
        fail("not implemented");
    }

    @Test
    void testTheWinnersWhoWinInACityLeastOneTime(){
        expectedWinnersWhoWinInACityLeastOneTime = Arrays.asList(
                "Kolkata Knight Riders", "Gujarat Lions","Rising Pune Supergiant","Mumbai Indians","Chennai Super Kings","Rajasthan Royals",
                "Kings XI Punjab","Kochi Tuskers Kerala","Royal Challengers Bangalore","Delhi Daredevils"
        );
        Collections.sort(expectedWinnersWhoWinInACityLeastOneTime);
        assertIterableEquals(
                expectedWinnersWhoWinInACityLeastOneTime,
                actualWinnersWhoWinInACityLeastOneTime
        );
    }
}