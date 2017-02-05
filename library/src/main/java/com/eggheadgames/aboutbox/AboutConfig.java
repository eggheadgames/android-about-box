package com.eggheadgames.aboutbox;

public class AboutConfig {

    public enum BuildType {AMAZON, GOOGLE}

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
    public String packageName;

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

    public static class SingletonHolder {
        public static final AboutConfig HOLDER_INSTANCE = new AboutConfig();
    }

    public static AboutConfig getInstance() {
        return SingletonHolder.HOLDER_INSTANCE;
    }

}
