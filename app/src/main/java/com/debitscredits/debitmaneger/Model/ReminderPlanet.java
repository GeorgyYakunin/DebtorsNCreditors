package com.debitscredits.debitmaneger.Model;

public class ReminderPlanet {

    int Id;
    String RName;
    String RAmount;
    String RDate;

    public ReminderPlanet(int id, String rName, String rAmount, String rDate)
    {
        this.Id = id;
        this.RName = rName;
        this.RAmount = rAmount;
        this.RDate = rDate;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getRName() {
        return RName;
    }

    public void setRName(String RName) {
        this.RName = RName;
    }

    public String getRAmount() {
        return RAmount;
    }

    public void setRAmount(String RAmount) {
        this.RAmount = RAmount;
    }

    public String getRDate() {
        return RDate;
    }

    public void setRDate(String RDate) {
        this.RDate = RDate;
    }
}
