package com.swarnava.ipl;

public class BowlersOverAndRun {
    String bowler;
    Integer run;
    Float over;


    public boolean containsBowler(String str){
        if(bowler.equals(str))
            return true;
        return false;
    }

    public String getBowler() {
        return bowler;
    }

    public void setBowler(String bowler) {
        this.bowler = bowler;
    }

    public Integer getRun() {
        return run;
    }

    public void setRun(Integer run) {
        this.run = run;
    }

    public Float getOver() {
        return over;
    }

    public void setOver(Float over) {
        this.over = over;
    }
}
