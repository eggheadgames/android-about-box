[![Circle CI](https://circleci.com/gh/eggheadgames/android-about-box.svg?style=svg)](https://circleci.com/gh/eggheadgames/android-about-box)
[![Release](https://jitpack.io/v/eggheadgames/android-about-box.svg)](https://jitpack.io/#eggheadgames/android-about-box)

# About Box
A modern About Box for an Android App built on the [daniel-stoneuk/material-about-library](https://github.com/daniel-stoneuk/material-about-library).

### Easily display the common items of an About Box in a modern Android friendly way

## About

Android About Box is configured with a set of (mostly) strings for the company name, twitter and Facebook accounts, website, and filenames to html files for help files, privacy policy etc.  

When triggered from a menu item, it will display the app name, icon and version, provide links to contact support, leave a review, share the app, go to other apps by the same company in the app store -- as well as links to Facebook etc.

## Installation Instructions

Add the JitPack.io repository to your root `build.gradle`:

```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

Add a dependency to your application related `build.gradle`

```gradle
dependencies {
    compile 'com.github.eggheadgames:android-about-box:<actual version>'
}
```

## Example
### Setup Branch.io

Branch.io integration can be found [here](https://github.com/BranchMetrics/android-branch-deep-linking)

### Setup AboutBox

Add AboutBox configuration to your Application class

```java
        AboutConfig aboutConfig = AboutConfig.getInstance();
        aboutConfig.appName = getString(R.string.app_name);
        aboutConfig.appIcon = R.mipmap.ic_launcher;
        aboutConfig.version = "1.0.0";
        aboutConfig.aboutLabelTitle = "About App";
        aboutConfig.packageName = getApplicationContext().getPackageName();
        aboutConfig.buildType = google ? AboutConfig.BuildType.GOOGLE : AboutConfig.BuildType.AMAZON;

        aboutConfig.facebookUserName = FACEBOOK_USER_NAME;
        aboutConfig.twitterUserName = TWITTER_USER_NAME;
        aboutConfig.webHomePage = WEB_HOME_PAGE;

        // app publisher for "Try Other Apps" item
        aboutConfig.appPublisher = APP_PUBLISHER;

        // if pages are stored locally, then you need to override aboutConfig.dialog to be able use custom WebView
        aboutConfig.companyHtmlPath = COMPANY_HTML_PATH;
        aboutConfig.privacyHtmlPath = PRIVACY_HTML_PATH;
        aboutConfig.acknowledgmentHtmlPath = ACKNOWLEDGMENT_HTML_PATH;

        aboutConfig.dialog = new IDialog() {
            @Override
            public void open(AppCompatActivity appCompatActivity, String url, String tag) {
               // handle custom implementations of WebView. It will be called when user click to web items. (Example: "Privacy", "Acknowledgments" and "About")
            }
        };

        aboutConfig.analytics = new IAnalytic() {
            @Override
            public void logUiEvent(String s, String s1) {
               // handle log events.
            }

            @Override
            public void logException(Exception e, boolean b) {
               // handle exception events.
            }
        };
        // set it only if aboutConfig.analytics is defined.
        aboutConfig.logUiEventName = "Log";

        // Contact Support email details
        aboutConfig.emailAddress = EMAIL_ADDRESS;
        aboutConfig.emailSubject = EMAIL_SUBJECT;
        aboutConfig.emailBody = EMAIL_BODY;

        // Branch.io labels.
        aboutConfig.shareMessageTitle = getString(R.string.share_message_title);
        aboutConfig.shareMessage = getString(R.string.share_message);
        aboutConfig.sharingTitle = getString(R.string.sharing_title);
```

Open AboutBox screen

```java
        AboutActivity.launch(activity);
```

## Theme

Add to your AndroidManifest.xml file

```
        <activity
            android:name="com.eggheadgames.aboutbox.activity.AboutActivity"
            android:theme="@style/AppTheme.MaterialAboutActivity"/>
```

Ensure that `AppTheme.MaterialAboutActivity` theme extends either of these themes, and apply primary & accent colours:
```
Theme.Mal.Light.DarkActionBar
Theme.Mal.Light.LightActionBar
Theme.Mal.Dark.LightActionBar
Theme.Mal.Dark.DarkActionBar
```

```
  <style name="AppTheme.MaterialAboutActivity" parent="Theme.Mal.Light.DarkActionBar" >
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
```
## Screenshot

<img src="extras/example.png?raw=true">

