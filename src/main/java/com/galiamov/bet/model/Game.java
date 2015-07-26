package com.galiamov.bet.model;

import org.springframework.data.annotation.Id;

import java.util.Map;

public class Game {

    @Id
    private String id;
    private String name;
    private long datetime;
    private Map<String, Object> odd;

    public Game() {
    }

    public Game(String name, long datetime, Map<String, Object> odd) {
        this.name = name;
        this.datetime = datetime;
        this.odd = odd;
    }

    public void updateOdd(Map<String, Double> odd) {
        for (Map.Entry<String, Double> entry : odd.entrySet()) {
            this.odd.put(entry.getKey(), entry.getValue());
        }
    }

    public boolean isOddPresentFor(String type) {
        return odd != null && odd.containsKey(type);
    }

    public boolean isOddEqualsTo(String type, Double odd) {
        return this.odd.get(type).equals(odd);
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }

    public Map<String, Object> getOdd() {
        return odd;
    }

    public void setOdd(Map<String, Object> odd) {
        this.odd = odd;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getDatetime() {
        return datetime;
    }

    interface Odd {
    }

    class Name implements Odd {
        String name;
        String value;
    }

    class Outcome implements Odd {
        String name;
        Double value;
    }

}
