package com.debitscredits.debitmaneger.Model;

public class Planet {

private int Id;
private String Name = "null";
private String Amount = "null";
private  String Description = "null";
private  String CDate = "null";
private  String DueDate = "null";
private  String GiveType = "null";

public Planet(int id,String name,String amount,String description,String cdate,String dueDate,String giveType)
{
this.Id = id;
this.Name = name;
this.Amount = amount;
this.Description = description;
this.CDate = cdate;
this.DueDate = dueDate;
this.GiveType = giveType;
}

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getGDate() {
        return CDate;
    }

    public void setGDate(String cDate) {
        CDate = cDate;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getGiveType() {
        return GiveType;
    }

    public void setGiveType(String giveType) {
        GiveType = giveType;
    }
}
