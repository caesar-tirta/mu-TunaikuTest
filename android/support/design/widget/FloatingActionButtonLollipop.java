package android.support.design.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.View;

@TargetApi(21)
class FloatingActionButtonLollipop extends FloatingActionButtonIcs {
    private InsetDrawable mInsetDrawable;

    FloatingActionButtonLollipop(VisibilityAwareImageButton view, ShadowViewDelegate shadowViewDelegate, Creator animatorCreator) {
        super(view, shadowViewDelegate, animatorCreator);
    }

    void setBackgroundDrawable(ColorStateList backgroundTint, Mode backgroundTintMode, int rippleColor, int borderWidth) {
        Drawable rippleContent;
        this.mShapeDrawable = DrawableCompat.wrap(createShapeDrawable());
        DrawableCompat.setTintList(this.mShapeDrawable, backgroundTint);
        if (backgroundTintMode != null) {
            DrawableCompat.setTintMode(this.mShapeDrawable, backgroundTintMode);
        }
        if (borderWidth > 0) {
            this.mBorderDrawable = createBorderDrawable(borderWidth, backgroundTint);
            rippleContent = new LayerDrawable(new Drawable[]{this.mBorderDrawable, this.mShapeDrawable});
        } else {
            this.mBorderDrawable = null;
            rippleContent = this.mShapeDrawable;
        }
        this.mRippleDrawable = new RippleDrawable(ColorStateList.valueOf(rippleColor), rippleContent, null);
        this.mContentBackground = this.mRippleDrawable;
        this.mShadowViewDelegate.setBackgroundDrawable(this.mRippleDrawable);
    }

    void setRippleColor(int rippleColor) {
        if (this.mRippleDrawable instanceof RippleDrawable) {
            ((RippleDrawable) this.mRippleDrawable).setColor(ColorStateList.valueOf(rippleColor));
        } else {
            super.setRippleColor(rippleColor);
        }
    }

    void onElevationsChanged(float elevation, float pressedTranslationZ) {
        StateListAnimator stateListAnimator = new StateListAnimator();
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(this.mView, "elevation", new float[]{elevation}).setDuration(0)).with(ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[]{pressedTranslationZ}).setDuration(100));
        set.setInterpolator(ANIM_INTERPOLATOR);
        stateListAnimator.addState(PRESSED_ENABLED_STATE_SET, set);
        set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(this.mView, "elevation", new float[]{elevation}).setDuration(0)).with(ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[]{pressedTranslationZ}).setDuration(100));
        set.setInterpolator(ANIM_INTERPOLATOR);
        stateListAnimator.addState(FOCUSED_ENABLED_STATE_SET, set);
        set = new AnimatorSet();
        AnimatorSet anim = new AnimatorSet();
        anim.play(ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(100)).after(100);
        set.play(ObjectAnimator.ofFloat(this.mView, "elevation", new float[]{elevation}).setDuration(0)).with(anim);
        set.setInterpolator(ANIM_INTERPOLATOR);
        stateListAnimator.addState(ENABLED_STATE_SET, set);
        set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(this.mView, "elevation", new float[]{0.0f}).setDuration(0)).with(ObjectAnimator.ofFloat(this.mView, View.TRANSLATION_Z, new float[]{0.0f}).setDuration(0));
        set.setInterpolator(ANIM_INTERPOLATOR);
        stateListAnimator.addState(EMPTY_STATE_SET, set);
        this.mView.setStateListAnimator(stateListAnimator);
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            updatePadding();
        }
    }

    public float getElevation() {
        return this.mView.getElevation();
    }

    void onCompatShadowChanged() {
        updatePadding();
    }

    void onPaddingUpdated(Rect padding) {
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            this.mInsetDrawable = new InsetDrawable(this.mRippleDrawable, padding.left, padding.top, padding.right, padding.bottom);
            this.mShadowViewDelegate.setBackgroundDrawable(this.mInsetDrawable);
            return;
        }
        this.mShadowViewDelegate.setBackgroundDrawable(this.mRippleDrawable);
    }

    void onDrawableStateChanged(int[] state) {
    }

    void jumpDrawableToCurrentState() {
    }

    boolean requirePreDrawListener() {
        return false;
    }

    CircularBorderDrawable newCircularDrawable() {
        return new CircularBorderDrawableLollipop();
    }

    void getPadding(Rect rect) {
        if (this.mShadowViewDelegate.isCompatPaddingEnabled()) {
            float radius = this.mShadowViewDelegate.getRadius();
            float maxShadowSize = getElevation() + this.mPressedTranslationZ;
            int hPadding = (int) Math.ceil((double) ShadowDrawableWrapper.calculateHorizontalPadding(maxShadowSize, radius, false));
            int vPadding = (int) Math.ceil((double) ShadowDrawableWrapper.calculateVerticalPadding(maxShadowSize, radius, false));
            rect.set(hPadding, vPadding, hPadding, vPadding);
            return;
        }
        rect.set(0, 0, 0, 0);
    }
}
