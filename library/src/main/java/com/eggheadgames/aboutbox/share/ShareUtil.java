package com.eggheadgames.aboutbox.share;

import android.app.Activity;
import android.text.TextUtils;

import com.eggheadgames.aboutbox.AboutConfig;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;

public final class ShareUtil {

    private ShareUtil() {
        // Utility class
    }

    public static void share(Activity activity) {
        BranchUniversalObject branchUniversalObject = new BranchUniversalObject();
        final AboutConfig config = AboutConfig.getInstance();

        ShareSheetStyle shareSheetStyle = new ShareSheetStyle(activity, config.shareMessageTitle,
                config.shareMessage)
                .setCopyUrlStyle(activity.getResources().getDrawable(android.R.drawable.ic_menu_send),
                        "Copy", "Added to clipboard")
                .setMoreOptionStyle(activity.getResources().getDrawable(android.R.drawable.ic_menu_search), "Show more")
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                .setSharingTitle(config.sharingTitle);

        branchUniversalObject.showShareSheet(activity,
                new LinkProperties(), shareSheetStyle, new Branch.BranchLinkShareListener() {
                    @Override
                    public void onShareLinkDialogLaunched() {
                        //nothing
                    }

                    @Override
                    public void onShareLinkDialogDismissed() {
                        if (config.analytics != null) {
                            config.analytics.logUiEvent("Share", "Dismissed");
                        }
                    }

                    @Override
                    public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
                        if (config.analytics != null) {
                            if (error == null || TextUtils.isEmpty(error.getMessage())) {
                                config.analytics.logUiEvent("Share", sharedChannel);
                            } else {
                                config.analytics.logUiEvent("Share Failure", error.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onChannelSelected(String channelName) {
                        //nothing
                    }
                });

    }
}
