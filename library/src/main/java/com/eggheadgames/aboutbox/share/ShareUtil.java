package com.eggheadgames.aboutbox.share;

import android.app.Activity;
import android.content.Intent;

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
        intent2.putExtra(Intent.EXTRA_TEXT, config.shareMessage);
        activity.startActivity(Intent.createChooser(intent2, config.sharingTitle));
    }
}
