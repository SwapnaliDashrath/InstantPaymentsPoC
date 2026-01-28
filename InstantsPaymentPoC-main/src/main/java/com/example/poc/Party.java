package com.example.poc;

public class Party {
    private String name;
    private String bank;
    private String country;
    private String account;
    public Party() {}
    public Party(String name, String bank, String country, String account) {
        this.name = name; this.bank = bank; this.country = country; this.account = account;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getBank() { return bank; }
    public void setBank(String bank) { this.bank = bank; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getAccount() { return account; }
    public void setAccount(String account) { this.account = account; }
}
