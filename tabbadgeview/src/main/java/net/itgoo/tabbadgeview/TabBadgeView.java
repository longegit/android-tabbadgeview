package net.itgoo.tabbadgeview;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class TabBadgeView extends FrameLayout {

    private BadgeView mBadgeView;

    private TabBadgeReceiver mTabBadgeReceiver;

    private String mFlag;

    public TabBadgeView(Context context) {
        super(context);
    }

    public TabBadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TabBadgeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public TabBadgeView(Context context, @NonNull String flag, @NonNull Drawable drawable, @NonNull String text) {
        this(context, null);
        init(flag, drawable, text);
    }

    public TabBadgeView(Context context, @NonNull String flag, @DrawableRes int drawableResId, @StringRes int textResId) {
        this(context, null);
        init(flag, getResources().getDrawable(drawableResId), getResources().getString(textResId));
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        unregisterReceiver();
    }

    private void init(String flag,  Drawable drawable, String text) {
        mFlag = flag;

        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_tab_badge_view, this, true);
        ImageView imageView = (ImageView) view.findViewById(R.id.view_tab_badge_view_iv);
        TextView textView = (TextView) view.findViewById(R.id.view_tab_badge_view_tv);
        imageView.setImageDrawable(drawable);
        textView.setText(text);

        int horizontalMargin = getResources().getDimensionPixelSize(R.dimen.tab_badge_view_horizontal_margin);
        int verticalMargin = getResources().getDimensionPixelSize(R.dimen.tab_badge_view_vertical_margin);
        mBadgeView = new BadgeView(getContext(), imageView);
        mBadgeView.setBadgeMargin(horizontalMargin, verticalMargin);

        registerReceiver();
    }

    public String getFlag() {
        return mFlag;
    }

    /**
     * 设置Badge
     * @param badge
     */
    public void setBadgeCount(String badge) {
        mBadgeView.setText(badge);
        if (TextUtils.isEmpty(badge)) {
            mBadgeView.hide();
        } else {
            mBadgeView.show();
        }
    }

    private void registerReceiver() {
        if (getContext() instanceof Activity) {
            mTabBadgeReceiver = new TabBadgeReceiver(this);

            IntentFilter filter = new IntentFilter(TabBadgeReceiver.ACTION_TAB_BADGE_CHANGE);
            getContext().registerReceiver(mTabBadgeReceiver, filter);
        }
    }

    private void unregisterReceiver() {
        if (mTabBadgeReceiver != null) {
            getContext().unregisterReceiver(mTabBadgeReceiver);
        }
    }
}
