package com.eggheadgames.aboutbox.share;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.eggheadgames.aboutbox.AboutConfig;

public final class EmailUtil {

    private EmailUtil() {
        // Utility class
    }

    public static void contactUs(Activity activity) {
        AboutConfig config = AboutConfig.getInstance();

        final Uri mailto = Uri.fromParts("mailto", config.emailAddress, null);

        final String emailSubject;

        if (config.buildType == AboutConfig.BuildType.GOOGLE) {
            emailSubject = config.emailSubject + "G";
        } else if (config.buildType == AboutConfig.BuildType.AMAZON) {
            emailSubject = config.emailSubject + "K";
        } else {
            emailSubject = config.emailSubject;
        }

        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, mailto);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, config.emailBody);
            activity.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        } catch (Exception e) {
            if (config.analytics != null) {
                config.analytics.logException(e, false);
            }
        }

    }
}
