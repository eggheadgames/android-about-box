package com.eggheadgames.aboutbox;

public interface IAnalytic {
    void logUiEvent(String action, String label);

    void logException(Exception e, boolean fatal);
}

