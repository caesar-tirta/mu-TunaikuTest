package android.support.design.widget;

import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.widget.DrawerLayout;
import com.amicel.tunaikudemo.C0203R;

class ViewUtils {
    static final Creator DEFAULT_ANIMATOR_CREATOR;

    /* renamed from: android.support.design.widget.ViewUtils.1 */
    static class C02351 implements Creator {
        C02351() {
        }

        public ValueAnimatorCompat createAnimator() {
            return new ValueAnimatorCompat(VERSION.SDK_INT >= 12 ? new ValueAnimatorCompatImplHoneycombMr1() : new ValueAnimatorCompatImplGingerbread());
        }
    }

    ViewUtils() {
    }

    static {
        DEFAULT_ANIMATOR_CREATOR = new C02351();
    }

    static ValueAnimatorCompat createAnimator() {
        return DEFAULT_ANIMATOR_CREATOR.createAnimator();
    }

    static boolean objectEquals(Object a, Object b) {
        return a == b || (a != null && a.equals(b));
    }

    static Mode parseTintMode(int value, Mode defaultMode) {
        switch (value) {
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                return Mode.SRC_OVER;
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return Mode.SRC_IN;
            case C0203R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                return Mode.SRC_ATOP;
            case C0203R.styleable.Toolbar_titleMargin /*14*/:
                return Mode.MULTIPLY;
            case C0203R.styleable.Toolbar_titleMarginStart /*15*/:
                return Mode.SCREEN;
            default:
                return defaultMode;
        }
    }
}
