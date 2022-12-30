package com.scrappers.fsa.example;

public final class Adder {
    private final int x1;
    private final int x2;
    
    public Adder(int x1, int x2) {
        this.x1 = x1;
        this.x2 = x2;
    }
    
    public int add() {
        return x1 + x2;
    }
}
