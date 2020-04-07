package com.test.rpc.service;

/**
 * @author Yu
 * @since 2020/4/7
 */
public class SimpleCalculation implements Calculate {

    @Override
    public int sum(int factor1, int factor2) {
        return factor1 + factor2;
    }
}
