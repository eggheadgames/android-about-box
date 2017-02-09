package com.eggheadgames.aboutbox.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.model.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.danielstone.materialaboutlibrary.model.MaterialAboutTitleItem;
import com.eggheadgames.aboutbox.AboutConfig;
import com.eggheadgames.aboutbox.R;
import com.eggheadgames.aboutbox.share.EmailUtil;
import com.eggheadgames.aboutbox.share.ShareUtil;

public class AboutActivity extends MaterialAboutActivity {

    public static void launch(Activity activity) {
        Intent intent = new Intent(activity, AboutActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected MaterialAboutList getMaterialAboutList(Context context) {

        final AboutConfig config = AboutConfig.getInstance();

        MaterialAboutCard.Builder generalInfoCardBuilder = new MaterialAboutCard.Builder();

        generalInfoCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text(config.appName)
                .icon(config.appIcon)
                .build());

        generalInfoCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.version)
                .subText(config.version)
                .build());


        MaterialAboutCard.Builder supportCardBuilder = new MaterialAboutCard.Builder();
        supportCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.contact_support)
                .icon(R.drawable.ic_email_black)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        EmailUtil.contactUs(AboutActivity.this);
                        if (config.analytics != null) {
                            config.analytics.logUiEvent(config.logUiEventName, getString(R.string.contact_log_event));
                        }
                    }
                })
                .build());


        MaterialAboutCard.Builder shareCardBuilder = new MaterialAboutCard.Builder();
        shareCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.leave_review)
                .icon(R.drawable.ic_review)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        openApp(config.buildType, config.packageName);
                        if (config.analytics != null) {
                            config.analytics.logUiEvent(config.logUiEventName, getString(R.string.review_log_event));
                        }
                    }
                })
                .build());
        shareCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.share)
                .icon(R.drawable.ic_share_black)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        ShareUtil.share(AboutActivity.this);
                        if (config.analytics != null) {
                            config.analytics.logUiEvent(config.logUiEventName, getString(R.string.share_log_event));
                        }
                    }
                })
                .build());


        MaterialAboutCard.Builder aboutCardBuilder = new MaterialAboutCard.Builder();
        aboutCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.try_other_apps)
                .icon(R.drawable.ic_try_other_apps)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        openPublisher(config.buildType, config.appPublisher, config.packageName);
                        if (config.analytics != null) {
                            config.analytics.logUiEvent(config.logUiEventName, getString(R.string.try_other_app_log_event));
                        }
                    }
                })
                .build());
        aboutCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(config.aboutLabelTitle)
                .icon(R.drawable.ic_about_black)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        if (config.dialog == null) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(config.companyHtmlPath)));
                        } else {
                            config.dialog.open(AboutActivity.this, config.companyHtmlPath, config.aboutLabelTitle);
                        }

                        if (config.analytics != null) {
                            config.analytics.logUiEvent(config.logUiEventName, config.aboutLabelTitle);
                        }
                    }
                })
                .build());


        MaterialAboutCard.Builder socialNetworksCardBuilder = new MaterialAboutCard.Builder();
        socialNetworksCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.facebook_label)
                .subText(config.facebookUserName)
                .icon(R.drawable.ic_facebook_24)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        getOpenFacebookIntent(AboutActivity.this, config.facebookUserName);
                        if (config.analytics != null) {
                            config.analytics.logUiEvent(config.logUiEventName, getString(R.string.facebook_log_event));
                        }
                    }
                })
                .build());
        socialNetworksCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.twitter_label)
                .subText(config.twitterUserName)
                .icon(R.drawable.ic_twitter_24dp)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        startTwitter(AboutActivity.this, config.twitterUserName);
                        if (config.analytics != null) {
                            config.analytics.logUiEvent(config.logUiEventName, getString(R.string.twitter_log_event));
                        }
                    }
                })
                .build());

        socialNetworksCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.web_label)
                .subText(config.webHomePage.replace("https://", "").replace("http://", "").replace("/", ""))
                .icon(R.drawable.ic_web_black_24dp)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(config.webHomePage)));
                        if (config.analytics != null) {
                            config.analytics.logUiEvent(config.logUiEventName, getString(R.string.website_log_event));
                        }
                    }
                })
                .build());

        MaterialAboutCard.Builder privacyCardBuilder = new MaterialAboutCard.Builder();
        privacyCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.privacy_policy)
                .icon(R.drawable.ic_privacy)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        if (config.dialog == null) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(config.privacyHtmlPath)));
                        } else {
                            config.dialog.open(AboutActivity.this, config.privacyHtmlPath, getString(R.string.privacy_policy));
                        }

                        if (config.analytics != null) {
                            config.analytics.logUiEvent(config.logUiEventName, getString(R.string.privacy_log_event));
                        }
                    }
                })
                .build());
        privacyCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.acknowledgements)
                .icon(R.drawable.ic_acknowledgements)
                .setOnClickListener(new MaterialAboutActionItem.OnClickListener() {
                    @Override
                    public void onClick() {
                        if (config.dialog == null) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(config.acknowledgmentHtmlPath)));
                        } else {
                            config.dialog.open(AboutActivity.this, config.acknowledgmentHtmlPath, getString(R.string.acknowledgements));
                        }

                        if (config.analytics != null) {
                            config.analytics.logUiEvent(config.logUiEventName, getString(R.string.acknowledgements_log_event));
                        }
                    }
                })
                .build());


        return new MaterialAboutList.Builder()
                .addCard(generalInfoCardBuilder.build())
                .addCard(supportCardBuilder.build())
                .addCard(shareCardBuilder.build())
                .addCard(aboutCardBuilder.build())
                .addCard(socialNetworksCardBuilder.build())
                .addCard(privacyCardBuilder.build())
                .build();
    }

    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.about_screen_title);
    }

    public static void getOpenFacebookIntent(Activity context, String name) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/" + name));
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/" + name));
                context.startActivity(intent);
            } catch (Exception e1) {
                Toast.makeText(context, R.string.can_not_open, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void startTwitter(Activity context, String name) {
        try {
            context.getPackageManager().getPackageInfo("com.twitter.android", 0);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?screen_name=" + name));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/" + name));
                context.startActivity(intent);
            } catch (Exception e1) {
                Toast.makeText(context, R.string.can_not_open, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void openApp(AboutConfig.BuildType buildType, String packageName) {//true if Google Play, false if Amazon Store
        String appURI = null;
        String webURI = null;
        switch (buildType) {
            case GOOGLE:
                appURI = "market://details?id=" + packageName;
                webURI = "http://play.google.com/store/apps/details?id=" + packageName;
                break;
            case AMAZON:
                appURI = "amzn://apps/android?p=" + packageName;
                webURI = "http://www.amazon.com/gp/mas/dl/android?p=" + packageName;
                break;
            default:
                //nothing
        }
        open(appURI, webURI);
    }

    public void openPublisher(AboutConfig.BuildType buildType, String publisher, String packageName) {//true if Google Play, false if Amazon Store
        String appURI = null;
        String webURI = null;
        switch (buildType) {
            case GOOGLE:
                appURI = "market://search?q=pub:" + publisher;
                webURI = "http://play.google.com/store/search?q=pub:" + publisher;
                break;
            case AMAZON:
                appURI = "amzn://apps/android?showAll=1&p=" + packageName;
                webURI = "http://www.amazon.com/gp/mas/dl/android?showAll=1&p=" + packageName;
                break;
            default:
                //nothing
        }
        open(appURI, webURI);
    }

    private void open(String appURI, String webURI) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appURI)));
        } catch (ActivityNotFoundException e1) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(webURI)));
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(this, R.string.can_not_open, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
