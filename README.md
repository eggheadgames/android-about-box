[![Circle CI](https://circleci.com/gh/eggheadgames/android-about-box.svg?style=svg)](https://circleci.com/gh/eggheadgames/android-about-box)
[![Release](https://jitpack.io/v/eggheadgames/android-about-box.svg)](https://jitpack.io/#eggheadgames/android-about-box)
![Downloads](https://jitpack.io/v/daniel-stoneuk/material-about-library/month.svg)
[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://github.com/eggheadgames/android-about-box/blob/develop/LICENSE)

# About Box
A modern About Box for an Android App built on the [daniel-stoneuk/material-about-library](https://github.com/daniel-stoneuk/material-about-library).

### Easily display the common items of an About Box in a modern Android friendly way

## About

Android About Box is configured with a set of (mostly) strings for the company name, twitter and Facebook accounts, website, and filenames to html files for help files, privacy policy etc.

When triggered from a menu item, it will display the app name, icon and version, provide links to contact support, leave a review, share the app, go to other apps by the same company in the app store -- as well as links to Facebook etc.

You can omit most features if they don't apply (e.g. like website), by not setting the values. 

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

## Setup AboutBox

Add AboutBox configuration to your Application class

```java
        AboutConfig aboutConfig = AboutConfig.getInstance();
        aboutConfig.appName = getString(R.string.app_name);
        aboutConfig.appIcon = R.mipmap.ic_launcher;
        aboutConfig.version = "1.0.0";
        aboutConfig.author = "Tolstoy";
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

      
```

## Open the About Box from your app

```java
        AboutActivity.launch(activity);
```



## Sharing

By default, the default Android share intent will be called with the values specified in `shareMessage` and `sharingTitle`. For example:
```java
        aboutConfig.shareMessage = getString(R.string.share_message);
        aboutConfig.sharingTitle = getString(R.string.sharing_title);
```

Alternatively, you can provide a custom sharing function (and omit `shareMessage` and `sharingTitle`):
 ```java
        aboutConfig.share = new IShare() {
            @Override
            public void share(Activity activity) {
                // do custom sharing
            }
        };
```

## Theme

If you add the following to your AndroidManifest.xml file, the About Box will use these colours. This allows you to match your app colours:

```xml
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

```xml
  <style name="AppTheme.MaterialAboutActivity" parent="Theme.Mal.Light.DarkActionBar" >
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
```
## Screenshot

<img src="extras/example.png?raw=true">

