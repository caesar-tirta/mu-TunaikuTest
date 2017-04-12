package android.support.v4.net;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.widget.DrawerLayout;
import com.amicel.tunaikudemo.C0203R;

class ConnectivityManagerCompatHoneycombMR2 {
    ConnectivityManagerCompatHoneycombMR2() {
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return true;
        }
        switch (info.getType()) {
            case ConstraintTableLayout.ALIGN_CENTER /*0*/:
            case ConstraintTableLayout.ALIGN_RIGHT /*2*/:
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
            case ConstraintWidgetContainer.OPTIMIZATION_BASIC /*4*/:
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
            case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                return true;
            case ConstraintTableLayout.ALIGN_LEFT /*1*/:
            case C0203R.styleable.Toolbar_contentInsetLeft /*7*/:
            case C0203R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                return false;
            default:
                return true;
        }
    }
}
