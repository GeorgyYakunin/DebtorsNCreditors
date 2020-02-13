package com.debitscredits.debitmaneger.Model;

public class RepoetPlanet {

private String Nameg;
private String GiveAmount;
private  String TakeAmount;
private  String Balance;

public RepoetPlanet(String name, String giveamount, String takeamt,String balance)
{
this.Nameg = name;
this.GiveAmount = giveamount;
this.TakeAmount = takeamt;
this.Balance = balance;
}

    public String getNameg() {
        return Nameg;
    }

    public void setNameg(String nameg) {
        Nameg = nameg;
    }

    public String getGiveAmount() {
        return GiveAmount;
    }

    public void setGiveAmount(String giveAmount) {
        GiveAmount = giveAmount;
    }

    public String getTakeAmount() {
        return TakeAmount;
    }

    public void setTakeAmount(String takeAmount) {
        TakeAmount = takeAmount;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }
}
