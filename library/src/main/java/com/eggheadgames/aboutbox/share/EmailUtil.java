package com.eggheadgames.aboutbox.share;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.eggheadgames.aboutbox.AboutConfig;

import org.w3c.dom.Text;

public final class EmailUtil {

    private EmailUtil() {
        // Utility class
    }

    public static void contactUs(Activity activity) {
        AboutConfig config = AboutConfig.getInstance();

        final Uri mailto = Uri.fromParts("mailto", config.emailAddress, null);

        final String emailSubject;

        switch (config.buildType) {
            case GOOGLE:
                emailSubject = config.emailSubject + "G";
                break;
            case AMAZON:
                emailSubject = config.emailSubject + "K";
                break;
            default:
                emailSubject = config.emailSubject;
        }
        String emailBody = config.emailBody;
        if (TextUtils.isEmpty(emailBody)) {
            String deviceInfo = "";
            deviceInfo += "\n App version: " + config.version;
            deviceInfo += "\n Android version: " + Build.VERSION.RELEASE + " (" + android.os.Build.VERSION.SDK_INT + ")";
            deviceInfo += "\n OS version: " + System.getProperty("os.version") + " (" + android.os.Build.VERSION.INCREMENTAL + ")";
            deviceInfo += "\n Device: " + android.os.Build.MODEL + " (" + android.os.Build.PRODUCT + ")";

            emailBody = "Please type your question here: \n\n\n\n\n"
                    + "---------------------------" + deviceInfo;
        }

        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, mailto);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);
            activity.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (Exception e) {
            if (config.analytics != null) {
                config.analytics.logException(e, false);
            }
        }
    }
}
