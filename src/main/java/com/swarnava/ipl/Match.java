package com.swarnava.ipl;

public class Match {
    private Integer id;
    private Integer season;
    private String city;
    private String date;
    private String team1;
    private String team2;
    private String tossWinner;
    private String tossDecision;
    private String result;
    private Integer dlApplied;
    private String winner;
    private Integer winByRuns;
    private Integer winByWickets;
    private String playerOfMatch;
    private String venue;
    private String umpire1;
    private String umpire2;
    private String umpire3;

    public int getId() {return this.id;}

    public int getSeason() {return this.season;}

    public String getCity() {return city;}

    public String getDate() {return date;}

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {return team2;}

    public String getTossWinner() {return tossWinner;}

    public String getTossDecision() {return tossDecision;}

    public String getResult() {return result;}

    public int getDlApplied() {return dlApplied;}

    public String getWinner() {return winner;}

    public int getWinByRuns() {return winByRuns;}

    public int getWinByWickets() {return winByWickets;}

    public String getPlayerOfMatch() {return playerOfMatch;}

    public String getVenue() {return venue;}

    public String getUmpire1() {return umpire1;}

    public String getUmpire2() {return umpire2;}

    public String getUmpire3() {return umpire3;}

    public void setId(int id) {this.id = id;}

    public void setSeason(int season) {this.season = season;}

    public void setCity(String city) {
        this.city = city;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTeam1(String team1) {this.team1 = team1;}

    public void setTeam2(String team2) {this.team2 = team2;}

    public void setTossWinner(String tossWinner) {this.tossWinner = tossWinner;}

    public void setTossDecision(String tossDecision) {this.tossDecision = tossDecision;}

    public void setResult(String result) {this.result = result;}

    public void setDlApplied(int dlApplied) {this.dlApplied = dlApplied;}

    public void setWinner(String winner) {this.winner = winner;}

    public void setWinByRuns(int winByRuns) {this.winByRuns = winByRuns;}

    public void setWinByWickets(int winByWickets) {this.winByWickets = winByWickets;}

    public void setPlayerOfMatch(String playerOfMatch) {this.playerOfMatch = playerOfMatch;}

    public void setVenue(String venue) {this.venue = venue;}

    public void setUmpire1(String umpire1) {this.umpire1 = umpire1;}

    public void setUmpire2(String umpire2) {this.umpire2 = umpire2;}

    public void setUmpire3(String umpire3) {this.umpire3 = umpire3;}
}