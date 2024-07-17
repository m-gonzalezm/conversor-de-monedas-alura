package com.aluracursos.conversor.models;

public class Conversion {
    
    private String base;
    private double rate;
    private double amount;
    
    public Conversion() {}

    public Conversion(double amount, double rate, String base) {
        this.amount = amount;
        this.rate = rate;
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getRate() {
        return rate;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
