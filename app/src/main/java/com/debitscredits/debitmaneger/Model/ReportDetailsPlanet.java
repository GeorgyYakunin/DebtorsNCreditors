package com.debitscredits.debitmaneger.Model;

public class ReportDetailsPlanet {

private String TypeDetail;
private String Dt;
private  String DueDt;
private  String Amount;

public ReportDetailsPlanet(String typeDetail, String giveDt, String takeDt, String amount)
{
this.TypeDetail = typeDetail;
this.Dt = giveDt;
this.DueDt = takeDt;
this.Amount = amount;
}

    public String getTypeDetail() {
        return TypeDetail;
    }

    public void setTypeDetail(String typeDetail) {
        TypeDetail = typeDetail;
    }

    public String getDt() {
        return Dt;
    }

    public void setDt(String dt) {
        Dt = dt;
    }

    public String getDueDt() {
        return DueDt;
    }

    public void setDueDt(String dueDt) {
        DueDt = dueDt;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
