package com.sjprogramming;

import java.util.Map;

public interface AtmOperationInterf {
    void viewBalance();

    void withdrawAmount(double withdrawAmount);

    void depositAmount(double depositAmount);

    void viewMiniStatement();

    Map<Double, String> getMiniStatement();
}
