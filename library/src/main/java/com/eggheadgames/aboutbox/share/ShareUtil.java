package com.eggheadgames.aboutbox.share;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.eggheadgames.aboutbox.AboutBoxUtils;
import com.eggheadgames.aboutbox.AboutConfig;

public final class ShareUtil {

    private ShareUtil() {
        // Utility class
    }

    public static void share(Activity activity) {
        AboutConfig config = AboutConfig.getInstance();

        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_SEND);
        intent2.setType("text/plain");

        String shareMessage = config.shareMessage;

        if (!TextUtils.isEmpty(config.packageName) && !TextUtils.isEmpty(shareMessage) && config.buildType != null) {
            switch (config.buildType) {
                case GOOGLE:
                    shareMessage = shareMessage + AboutBoxUtils.playStoreAppURI + config.packageName;
                    break;
                case AMAZON:
                    shareMessage = shareMessage + AboutBoxUtils.amznStoreAppURI + config.packageName;
                    break;
                default:
                    break;
            }
        }

        intent2.putExtra(Intent.EXTRA_TEXT, shareMessage);

        activity.startActivity(Intent.createChooser(intent2, config.sharingTitle));
    }
}
