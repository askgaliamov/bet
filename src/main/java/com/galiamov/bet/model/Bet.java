package com.galiamov.bet.model;

import org.springframework.data.annotation.Id;

public class Bet {

    @Id
    private String id;
    private long timestamp;
    private String ip;
    private String name;
    private String type;
    private Double odd;
    private String amount;

    public void setId(String id) {
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getOdd() {
        return odd;
    }

    public void setOdd(Double odd) {
        this.odd = odd;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Bet{" +
                " name='" + name + '\'' +
                ", type=" + type +
                ", odd=" + odd +
                ", amount=" + amount +
                '}';
    }
}
