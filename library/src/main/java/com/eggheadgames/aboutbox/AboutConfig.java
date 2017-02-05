package com.eggheadgames.aboutbox;

import android.content.Context;

public class AboutConfig {

    public enum BuildType {AMAZON, GOOGLE}

    private static AboutConfig aboutConfig;

    public static AboutConfig getInstance() {
        if (aboutConfig == null) {
            aboutConfig = new AboutConfig();
        }
        return aboutConfig;
    }

    //    general info
    public String appName;
    public int appIcon;
    public String version;
    public String aboutLabelTitle;
    public String logUiEventName;
    public String facebookUserName;
    public String twitterUserName;
    public String webHomePage;
    public String appPublisher;
    public String companyHtmlPath;
    public String privacyHtmlPath;
    public String acknowledgmentHtmlPath;
    public BuildType buildType;

    public Context context;

    //    custom analytics and dialog
    public IAnalytic analytics;
    public IDialog dialog;

    //    email
    public String emailAddress;
    public String emailSubject;
    public String emailBody;

    //    share
    public String shareMessageTitle;
    public String shareMessage;
    public String sharingTitle;

}
