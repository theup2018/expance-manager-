package com.example.deamon.expancemanager.Classes;

public class Transaction {
    private String id;
    private int amount;
    private String category;
    private String date;

    public Transaction() {
    }

    public Transaction(String id, int amount, String category, String date) {
        this.id = id;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
