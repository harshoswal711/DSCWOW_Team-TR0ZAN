package com.example.buddycop;

public class Fir {

    private String No;
    private String Status;
    private String Description;

    public Fir(  String Description,String No,String Status) {
        this.No = No;
        this.Status = Status;
        this.Description = Description;
    }

    public Fir() {
    }

    public String getNo() {
        return No;
    }

    public void setNo(String no) {
        this.No = no;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }
}
