package com.eggheadgames.aboutbox.activity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.danielstone.materialaboutlibrary.MaterialAboutActivity;
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem;
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickListener;
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem;
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard;
import com.danielstone.materialaboutlibrary.model.MaterialAboutList;
import com.eggheadgames.aboutbox.AboutConfig;
import com.eggheadgames.aboutbox.IAnalytic;
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

        return new MaterialAboutList.Builder()
                .addCard(buildGeneralInfoCard(config))
                .addCard(buildSupportCard(config))
                .addCard(buildShareCard(config))
                .addCard(buildAboutCard(config))
                .addCard(buildSocialNetworksCard(config))
                .addCard(buildPrivacyCard(config))
                .build();
    }

    @NonNull
    private MaterialAboutCard buildGeneralInfoCard(AboutConfig config) {
        MaterialAboutCard.Builder generalInfoCardBuilder = new MaterialAboutCard.Builder();

        generalInfoCardBuilder.addItem(new MaterialAboutTitleItem.Builder()
                .text(config.appName)
                .icon(config.appIcon)
                .build());

        generalInfoCardBuilder.addItem(new MaterialAboutActionItem.Builder()
                .text(R.string.egab_version)
                .subText(config.version)
                .build());
        return generalInfoCardBuilder.build();
    }

    @NonNull
    private MaterialAboutCard buildSupportCard(final AboutConfig config) {
        MaterialAboutCard.Builder card = new MaterialAboutCard.Builder();

        if (!TextUtils.isEmpty(config.guideHtmlPath)) {
            card.addItem(itemHelper(R.string.egab_guide, R.drawable.ic_help_green,
                    new MaterialAboutItemOnClickListener() {
                        @Override
                        public void onClick(boolean b) {
                            if (config.dialog == null) {
                                openHTMLPage(config.guideHtmlPath);
                            } else {
                                config.dialog.open(AboutActivity.this, config.guideHtmlPath, getString(R.string.egab_guide));
                            }
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_guide));
                        }
                    })
            );
        }
        card.addItem(itemHelper(R.string.egab_contact_support, R.drawable.ic_email_black,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        EmailUtil.contactUs(AboutActivity.this);
                        logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_contact_log_event));
                    }
                }));

        return card.build();
    }

    @NonNull
    private MaterialAboutCard buildShareCard(final AboutConfig config) {
        MaterialAboutCard.Builder card = new MaterialAboutCard.Builder();
        if (config.buildType != null && !TextUtils.isEmpty(config.packageName)) {
            card.addItem(itemHelper(R.string.egab_leave_review, R.drawable.ic_review,
                    new MaterialAboutItemOnClickListener() {
                        @Override
                        public void onClick(boolean b) {
                            openApp(config.buildType, config.packageName);
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_review_log_event));
                        }
                    }));
        }
        card.addItem(itemHelper(R.string.egab_share, R.drawable.ic_share_black,
                new MaterialAboutItemOnClickListener() {
                    @Override
                    public void onClick(boolean b) {
                        ShareUtil.share(AboutActivity.this);
                        logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_share_log_event));
                    }
                }));

        return card.build();
    }

    @NonNull
    private MaterialAboutCard buildAboutCard(final AboutConfig config) {
        MaterialAboutCard.Builder card = new MaterialAboutCard.Builder();
        if (config.buildType != null && !TextUtils.isEmpty(config.appPublisher) && !TextUtils.isEmpty(config.packageName)) {
            card.addItem(itemHelper(R.string.egab_try_other_apps, R.drawable.ic_try_other_apps,
                    new MaterialAboutItemOnClickListener() {
                        @Override
                        public void onClick(boolean b) {
                            openPublisher(config.buildType, config.appPublisher, config.packageName);
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_try_other_app_log_event));
                        }
                    }));
        }
        if (!TextUtils.isEmpty(config.companyHtmlPath)) {
            card.addItem(new MaterialAboutActionItem.Builder()
                    .text(config.aboutLabelTitle)
                    .icon(R.drawable.ic_about_black)
                    .setOnClickListener(new MaterialAboutItemOnClickListener() {
                        @Override
                        public void onClick(boolean b) {
                            if (config.dialog == null) {
                                openHTMLPage(config.companyHtmlPath);
                            } else {
                                config.dialog.open(AboutActivity.this, config.companyHtmlPath, config.aboutLabelTitle);
                            }
                            logUIEventName(config.analytics, config.logUiEventName, config.aboutLabelTitle);
                        }
                    })
                    .build());
        }
        return card.build();
    }

    @NonNull
    private MaterialAboutCard buildSocialNetworksCard(final AboutConfig config) {
        MaterialAboutCard.Builder card = new MaterialAboutCard.Builder();
        if (!TextUtils.isEmpty(config.facebookUserName)) {
            card.addItem(new MaterialAboutActionItem.Builder()
                    .text(R.string.egab_facebook_label)
                    .subText(config.facebookUserName)
                    .icon(R.drawable.ic_facebook_24)
                    .setOnClickListener(new MaterialAboutItemOnClickListener() {
                        @Override
                        public void onClick(boolean b) {
                            getOpenFacebookIntent(AboutActivity.this, config.facebookUserName);
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_facebook_log_event));
                        }
                    })
                    .build());
        }
        if (!TextUtils.isEmpty(config.twitterUserName)) {
            card.addItem(new MaterialAboutActionItem.Builder()
                    .text(R.string.egab_twitter_label)
                    .subText(config.twitterUserName)
                    .icon(R.drawable.ic_twitter_24dp)
                    .setOnClickListener(new MaterialAboutItemOnClickListener() {
                        @Override
                        public void onClick(boolean b) {
                            startTwitter(AboutActivity.this, config.twitterUserName);
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_twitter_log_event));
                        }
                    })
                    .build());
        }
        if (!TextUtils.isEmpty(config.webHomePage)) {
            card.addItem(new MaterialAboutActionItem.Builder()
                    .text(R.string.egab_web_label)
                    .subText(config.webHomePage.replace("https://", "").replace("http://", "").replace("/", ""))
                    .icon(R.drawable.ic_web_black_24dp)
                    .setOnClickListener(new MaterialAboutItemOnClickListener() {
                        @Override
                        public void onClick(boolean b) {
                            openHTMLPage(config.webHomePage);
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_website_log_event));
                        }
                    })
                    .build());
        }
        return card.build();
    }

    @NonNull
    private MaterialAboutCard buildPrivacyCard(final AboutConfig config) {
        MaterialAboutCard.Builder card = new MaterialAboutCard.Builder();
        if (!TextUtils.isEmpty(config.privacyHtmlPath)) {
            card.addItem(itemHelper(R.string.egab_privacy_policy, R.drawable.ic_privacy,
                    new MaterialAboutItemOnClickListener() {
                        @Override
                        public void onClick(boolean b) {
                            if (config.dialog == null) {
                                openHTMLPage(config.privacyHtmlPath);
                            } else {
                                config.dialog.open(AboutActivity.this, config.privacyHtmlPath, getString(R.string.egab_privacy_policy));
                            }

                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_privacy_log_event));
                        }
                    })
            );
        }
        if (!TextUtils.isEmpty(config.acknowledgmentHtmlPath)) {
            card.addItem(itemHelper(R.string.egab_acknowledgements, R.drawable.ic_acknowledgements,
                    new MaterialAboutItemOnClickListener() {
                        @Override
                        public void onClick(boolean b) {
                            if (config.dialog == null) {
                                openHTMLPage(config.acknowledgmentHtmlPath);
                            } else {
                                config.dialog.open(AboutActivity.this, config.acknowledgmentHtmlPath, getString(R.string.egab_acknowledgements));
                            }
                            logUIEventName(config.analytics, config.logUiEventName, getString(R.string.egab_acknowledgements_log_event));
                        }
                    })
            );
        }
        return card.build();
    }

    private MaterialAboutActionItem itemHelper(int name, int icon, MaterialAboutItemOnClickListener listener) {
        return new MaterialAboutActionItem.Builder()
                .text(name)
                .icon(icon)
                .setOnClickListener(listener)
                .build();
    }


    @Override
    protected CharSequence getActivityTitle() {
        return getString(R.string.egab_about_screen_title);
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
                Toast.makeText(context, R.string.egab_can_not_open, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(context, R.string.egab_can_not_open, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void openApp(AboutConfig.BuildType buildType, String packageName) {
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
        openApplication(appURI, webURI);
    }

    public void openPublisher(AboutConfig.BuildType buildType, String publisher, String packageName) {
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
        openApplication(appURI, webURI);
    }

    private void openApplication(String appURI, String webURI) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(appURI)));
        } catch (ActivityNotFoundException e1) {
            try {
                openHTMLPage(webURI);
            } catch (ActivityNotFoundException e2) {
                Toast.makeText(this, R.string.egab_can_not_open, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openHTMLPage(String htmlPath) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(htmlPath)));
    }

    private void logUIEventName(IAnalytic analytics, String eventType, String eventValue) {
        if (analytics != null) {
            analytics.logUiEvent(eventType, eventValue);
        }
    }
}
