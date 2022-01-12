package com.swarnava.ipl;

import org.junit.jupiter.api.*;
import java.util.*;
import static com.swarnava.ipl.Main.*;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static final String PATH_MATCHES = "csv/matches.csv";
    private static final String PATH_DELIVERY = "csv/deliveries.csv";
    static Map<String, Float> actualTopEconomicalBowlersForParticularYear;
    static Map<Integer, Integer> actualNumberOfMatchesPlayedPerYear;
    static Map<String, Integer> actualNumberOfMatchesWonOfAllTeam;
    static Map<String, Integer> actualExtraRunsConcededPerTeamForParticularYear;
    static TreeSet<String> actualWinnersWhoWinInACityLeastOneTime;
    static List<String> expectedWinnersWhoWinInACityLeastOneTime;

    @BeforeAll
    static void init() {
        List<Match> matches = matchesData(PATH_MATCHES);
        List<Delivery> deliveries = deliveriesData(PATH_DELIVERY);
        actualNumberOfMatchesPlayedPerYear = printNumberOfMatchesPlayedPerYear(matches);
        actualNumberOfMatchesWonOfAllTeam = printNumberOfMatchesWonOfAllTeam(matches);
        actualExtraRunsConcededPerTeamForParticularYear =
                printTheExtraRunsConcededPerTeamForParticularYear(matches, deliveries, 2016);
        actualTopEconomicalBowlersForParticularYear =
                printTheTopEconomicalBowlersForParticularYear(matches, deliveries, 2015);
        actualWinnersWhoWinInACityLeastOneTime =
                new TreeSet<>(printTheWinnersWhoWinInACityLeastOneTime(matches, "Kolkata"));
    }

    @DisplayName("Testing matches data")
    @Test
    void testMatches(){
        String expectedTeam = "Kolkata Knight Riders";
        String expectedTossDecision = "bat";
        String actualTeam = matchesData(PATH_MATCHES).get(2).getTeam2();
        String actualTossDecision = matchesData(PATH_MATCHES).get(4).getTossDecision();
        int expectedSize = 636;
        int actualSize = matchesData(PATH_MATCHES).size();
        assertAll(
                ()->assertEquals(expectedTeam, actualTeam,
                        "expectedTeam not matched"),
                ()->assertEquals(expectedTossDecision, actualTossDecision,
                        "expectedTossDecision not matched"),
                ()->assertEquals(expectedSize, actualSize,
                        "size not matched")
        );
    }

    @DisplayName("Testing number of matches played in the year 2016 and 2017")
    @Test
    void testNumberOfMatchesPlayedInYear(){
        int expectedNoOfMatches2017 = 59;
        int actualNoOfMatches2017 = actualNumberOfMatchesPlayedPerYear.get(2017);
        int expectedNoOfMatches2016 = 60;
        int actualNoOfMatches2016 = actualNumberOfMatchesPlayedPerYear.get(2016);
        assertAll(
                ()->assertEquals(expectedNoOfMatches2017, actualNoOfMatches2017,
                        "NumberOfMatchesPlayedIn2017 not matched"),
                ()->assertEquals(expectedNoOfMatches2016, actualNoOfMatches2016,
                        "NumberOfMatchesPlayedIn2016 not matched")
        );
    }

    @DisplayName("Testing number of matches won by GujaratLions, RoyalChallengersBangalore, KolkataKnightRiders")
    @Test
    void testNumberOfMatchesWonByTeam(){
        int expectedNumberOfMatchesWonByGujaratLions = 13;
        int actualNumberOfMatchesWonBGujaratLions = actualNumberOfMatchesWonOfAllTeam.get("Gujarat Lions");
        int expectedNumberOfMatchesWonByRoyalChallengersBangalore = 73;
        int actualNumberOfMatchesWonByRoyalChallengersBangalore =
                actualNumberOfMatchesWonOfAllTeam.get("Royal Challengers Bangalore");
        int expectedNumberOfMatchesWonByKolkataKnightRiders = 77;
        int actualNumberOfMatchesWonByKolkataKnightRiders =
                actualNumberOfMatchesWonOfAllTeam.get("Kolkata Knight Riders");
        assertAll(
                ()->assertEquals(expectedNumberOfMatchesWonByGujaratLions,
                        actualNumberOfMatchesWonBGujaratLions,
                "NumberOfMatchesWonByGujaratLions not matched"),
                ()->assertEquals(expectedNumberOfMatchesWonByRoyalChallengersBangalore,
                        actualNumberOfMatchesWonByRoyalChallengersBangalore,
                "NumberOfMatchesWonByRoyalChallengersBangalore not matched"),
                ()->assertEquals(expectedNumberOfMatchesWonByKolkataKnightRiders,
                        actualNumberOfMatchesWonByKolkataKnightRiders,
                "NumberOfMatchesWonByKolkataKnightRiders not matched")
        );
    }

    @DisplayName("Testing extra runs conceded by KingsXIPunjab and KolkataKnightRiders for 2016")
    @Test
    void testTheExtraRunsConcededPerTeamFor2016(){
        int expectedExtraRunsConcededByKingsXIPunjab = 57;
        int actualExtraRunsConcededByKingsXIPunjab =
                actualExtraRunsConcededPerTeamForParticularYear.get("Kings XI Punjab");
        int expectedExtraRunsConcededByKolkataKnightRiders = 138;
        int actualExtraRunsConcededByKolkataKnightRiders =
                actualExtraRunsConcededPerTeamForParticularYear.get("Kolkata Knight Riders");
        assertAll(
                () -> assertEquals(expectedExtraRunsConcededByKingsXIPunjab,
                        actualExtraRunsConcededByKingsXIPunjab,
                        "Extra Runs Conceded By Kings XI Punjab not matched"),
                () -> assertEquals(expectedExtraRunsConcededByKolkataKnightRiders,
                        actualExtraRunsConcededByKolkataKnightRiders,
                        "Extra Runs Conceded By Kolkata Knight Riders not matched")
        );
    }

    @DisplayName("Testing economical rate for the player V Kohli, J Yadav and RN ten Doeschate , year 2015")
    @Test
    void testEconomicalRate2015(){
        double expectedEconomicalRateForVKohli =
                Math.round(((double) 5.454545455537191)*100d)/100d;
        double actualEconomicalRateForVKohli =
                (double) Math.round(actualTopEconomicalBowlersForParticularYear.get("V Kohli")*100)/100;
        double expectedEconomicalRateForJYadav =
                Math.round(((double) 4.142857142857143)*100d)/100d;
        double actualEconomicalRateForJYadav =
                (double) Math.round(actualTopEconomicalBowlersForParticularYear.get("J Yadav")*100)/100;
        double expectedEconomicalRateForRNtenDoeschate =
                Math.round(((double) 3.4285714305306123)*100d)/100d;
        double actualEconomicalRateForRNtenDoeschate =
                (double) Math.round(actualTopEconomicalBowlersForParticularYear.get("RN ten Doeschate")*100)/100;
        assertAll(
                ()->assertEquals(expectedEconomicalRateForVKohli, actualEconomicalRateForVKohli,
                        "Economical Rate For V Kohli not matched"),
                ()->assertEquals(expectedEconomicalRateForJYadav, actualEconomicalRateForJYadav,
                        "Economical Rate For J Yadav not matched"),
                ()->assertEquals(expectedEconomicalRateForRNtenDoeschate, actualEconomicalRateForRNtenDoeschate,
                        "Economical Rate For RN ten Doeschate not matched")
        );
    }

    @DisplayName("Testing winners who win in Kolkata at least one time")
    @Test
    void testTheWinnersWhoWinInKolkataLeastOneTime(){
        expectedWinnersWhoWinInACityLeastOneTime = Arrays.asList(
                "Kolkata Knight Riders", "Gujarat Lions","Rising Pune Supergiant","Mumbai Indians",
                "Chennai Super Kings", "Rajasthan Royals", "Kings XI Punjab","Kochi Tuskers Kerala",
                "Royal Challengers Bangalore","Delhi Daredevils"
        );
        Collections.sort(expectedWinnersWhoWinInACityLeastOneTime);
        assertIterableEquals(
                expectedWinnersWhoWinInACityLeastOneTime,
                actualWinnersWhoWinInACityLeastOneTime,
                "TheWinnersWhoWinInKolkataLeastOneTime : Set not matched"
        );
    }
}