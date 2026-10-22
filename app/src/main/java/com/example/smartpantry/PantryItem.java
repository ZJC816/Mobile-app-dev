package com.example.smartpantry;

public class PantryItem {

    private final long id;
    private final String name;
    private final double quantity;
    private final String unit;
    private final String expiry;

    public PantryItem(long id, String name, double quantity, String unit, String expiry) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiry = expiry;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getExpiry() {
        return expiry;
    }
}
