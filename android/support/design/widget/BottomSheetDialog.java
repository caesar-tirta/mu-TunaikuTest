package android.support.design.widget;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.C0010R;
import android.support.design.widget.BottomSheetBehavior.BottomSheetCallback;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

public class BottomSheetDialog extends AppCompatDialog {
    private BottomSheetBehavior<FrameLayout> mBehavior;
    private BottomSheetCallback mBottomSheetCallback;
    boolean mCancelable;
    private boolean mCanceledOnTouchOutside;
    private boolean mCanceledOnTouchOutsideSet;

    /* renamed from: android.support.design.widget.BottomSheetDialog.1 */
    class C00131 implements OnClickListener {
        C00131() {
        }

        public void onClick(View view) {
            if (BottomSheetDialog.this.mCancelable && BottomSheetDialog.this.isShowing() && BottomSheetDialog.this.shouldWindowCloseOnTouchOutside()) {
                BottomSheetDialog.this.cancel();
            }
        }
    }

    /* renamed from: android.support.design.widget.BottomSheetDialog.2 */
    class C02122 extends BottomSheetCallback {
        C02122() {
        }

        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == 5) {
                BottomSheetDialog.this.dismiss();
            }
        }

        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    }

    public BottomSheetDialog(@NonNull Context context) {
        this(context, 0);
    }

    public BottomSheetDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, getThemeResId(context, theme));
        this.mCancelable = true;
        this.mCanceledOnTouchOutside = true;
        this.mBottomSheetCallback = new C02122();
        supportRequestWindowFeature(1);
    }

    protected BottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mCancelable = true;
        this.mCanceledOnTouchOutside = true;
        this.mBottomSheetCallback = new C02122();
        supportRequestWindowFeature(1);
        this.mCancelable = cancelable;
    }

    public void setContentView(@LayoutRes int layoutResId) {
        super.setContentView(wrapInBottomSheet(layoutResId, null, null));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setLayout(-1, -1);
    }

    public void setContentView(View view) {
        super.setContentView(wrapInBottomSheet(0, view, null));
    }

    public void setContentView(View view, LayoutParams params) {
        super.setContentView(wrapInBottomSheet(0, view, params));
    }

    public void setCancelable(boolean cancelable) {
        super.setCancelable(cancelable);
        if (this.mCancelable != cancelable) {
            this.mCancelable = cancelable;
            if (this.mBehavior != null) {
                this.mBehavior.setHideable(cancelable);
            }
        }
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(cancel);
        if (cancel && !this.mCancelable) {
            this.mCancelable = true;
        }
        this.mCanceledOnTouchOutside = cancel;
        this.mCanceledOnTouchOutsideSet = true;
    }

    private View wrapInBottomSheet(int layoutResId, View view, LayoutParams params) {
        CoordinatorLayout coordinator = (CoordinatorLayout) View.inflate(getContext(), C0010R.layout.design_bottom_sheet_dialog, null);
        if (layoutResId != 0 && view == null) {
            view = getLayoutInflater().inflate(layoutResId, coordinator, false);
        }
        FrameLayout bottomSheet = (FrameLayout) coordinator.findViewById(C0010R.id.design_bottom_sheet);
        this.mBehavior = BottomSheetBehavior.from(bottomSheet);
        this.mBehavior.setBottomSheetCallback(this.mBottomSheetCallback);
        this.mBehavior.setHideable(this.mCancelable);
        if (params == null) {
            bottomSheet.addView(view);
        } else {
            bottomSheet.addView(view, params);
        }
        coordinator.findViewById(C0010R.id.touch_outside).setOnClickListener(new C00131());
        return coordinator;
    }

    boolean shouldWindowCloseOnTouchOutside() {
        if (!this.mCanceledOnTouchOutsideSet) {
            if (VERSION.SDK_INT < 11) {
                this.mCanceledOnTouchOutside = true;
            } else {
                TypedArray a = getContext().obtainStyledAttributes(new int[]{16843611});
                this.mCanceledOnTouchOutside = a.getBoolean(0, true);
                a.recycle();
            }
            this.mCanceledOnTouchOutsideSet = true;
        }
        return this.mCanceledOnTouchOutside;
    }

    private static int getThemeResId(Context context, int themeId) {
        if (themeId != 0) {
            return themeId;
        }
        TypedValue outValue = new TypedValue();
        if (context.getTheme().resolveAttribute(C0010R.attr.bottomSheetDialogTheme, outValue, true)) {
            return outValue.resourceId;
        }
        return C0010R.style.Theme_Design_Light_BottomSheetDialog;
    }
}
