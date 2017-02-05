package com.eggheadgames.aboutbox;

import android.support.v7.app.AppCompatActivity;

public interface IDialog {
    void open(AppCompatActivity activity, String url, String tag);
}
