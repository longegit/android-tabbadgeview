package net.itgoo.tabbadgeview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

public class TabBadgeReceiver extends BroadcastReceiver {

    public static final String ACTION_TAB_BADGE_CHANGE = "net.itgoo.tabbadgeview.action.CHANGE_BADGE";

    public static final String TAB_BADGE_FLAG_KEY = "flag";

    public static final String TAB_BADGE_CHANGE_COUNT_EXTRA_KEY = "badge";

    private TabBadgeView mTabBadgeView;

    public TabBadgeReceiver(@NonNull TabBadgeView tabBadgeView) {
        mTabBadgeView = tabBadgeView;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_TAB_BADGE_CHANGE.equals(intent.getAction())) {
            String badge = intent.getStringExtra(TAB_BADGE_CHANGE_COUNT_EXTRA_KEY);
            String flag = intent.getStringExtra(TAB_BADGE_FLAG_KEY);

            if (flag != null && flag.equals(mTabBadgeView.getFlag())) {
                mTabBadgeView.setBadgeCount(badge);
            }
        }
    }
}
