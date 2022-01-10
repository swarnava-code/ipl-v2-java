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
    void testPrintNumberOfMatchesPlayedPerYear(){
        fail("not implemented");
    }

    @Test
    void testPrintNumberOfMatchesWonOfAllTeam(){
        fail("not implemented");
    }

    @Test
    void testPrintTheExtraRunsConcededPerTeamForParticularYear(){
        fail("not implemented");
    }

    @Test
    void testPrintTheTopEconomicalBowlersForParticularYear(){
        fail("not implemented");
    }

    @Test
    void testPrintTheWinnersWhoWinInACityLeastOneTime(){
        fail("not implemented");
    }
}