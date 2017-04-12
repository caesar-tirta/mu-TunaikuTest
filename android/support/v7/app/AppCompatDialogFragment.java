package android.support.v7.app;

import android.app.Dialog;
import android.os.Bundle;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;

public class AppCompatDialogFragment extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AppCompatDialog(getContext(), getTheme());
    }

    public void setupDialog(Dialog dialog, int style) {
        if (dialog instanceof AppCompatDialog) {
            AppCompatDialog acd = (AppCompatDialog) dialog;
            switch (style) {
                case ConstraintTableLayout.ALIGN_LEFT /*1*/:
                case ConstraintTableLayout.ALIGN_RIGHT /*2*/:
                    break;
                case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                    dialog.getWindow().addFlags(24);
                    break;
                default:
                    return;
            }
            acd.supportRequestWindowFeature(1);
            return;
        }
        super.setupDialog(dialog, style);
    }
}
