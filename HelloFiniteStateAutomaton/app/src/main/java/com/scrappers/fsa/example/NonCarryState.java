package com.scrappers.fsa.example;

import com.scrappers.fsa.core.state.AutoState;
import java.util.logging.Logger;
import java.util.logging.Level;

public final class NonCarryState implements AutoState<BitsAdder, Integer> {
    
    private BitsAdder adder;
    private Integer carry = Integer.valueOf(0);
    private final Logger LOGGER = Logger.getLogger(CarryState.class.getName());
    
    @Override
    public void invoke(BitsAdder adder) {
        this.adder = adder;
        adder.output = adder.add();

        if (adder.output == 2) {
            // output = 0 and carry = 1
            adder.output = 0;
            carry = Integer.valueOf(1);
        }/* else, carry = 0 and output = 1 :-) */

        LOGGER.log(Level.INFO, "NonCarryState invoked, shifted to a new present state !");
        LOGGER.log(Level.INFO, String.valueOf(adder.output));
    }

    @Override
    public BitsAdder getInput() {
        return this.adder;
    }

    @Override
    public Integer getStateTracer() {
        return carry;
    }
}