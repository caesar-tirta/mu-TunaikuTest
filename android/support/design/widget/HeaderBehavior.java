package android.support.design.widget;

import android.content.Context;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ScrollerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;

abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private int mActivePointerId;
    private Runnable mFlingRunnable;
    private boolean mIsBeingDragged;
    private int mLastMotionY;
    ScrollerCompat mScroller;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    private class FlingRunnable implements Runnable {
        private final V mLayout;
        private final CoordinatorLayout mParent;

        FlingRunnable(CoordinatorLayout parent, V layout) {
            this.mParent = parent;
            this.mLayout = layout;
        }

        public void run() {
            if (this.mLayout != null && HeaderBehavior.this.mScroller != null) {
                if (HeaderBehavior.this.mScroller.computeScrollOffset()) {
                    HeaderBehavior.this.setHeaderTopBottomOffset(this.mParent, this.mLayout, HeaderBehavior.this.mScroller.getCurrY());
                    ViewCompat.postOnAnimation(this.mLayout, this);
                    return;
                }
                HeaderBehavior.this.onFlingFinished(this.mParent, this.mLayout);
            }
        }
    }

    public HeaderBehavior() {
        this.mActivePointerId = INVALID_POINTER;
        this.mTouchSlop = INVALID_POINTER;
    }

    public HeaderBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mActivePointerId = INVALID_POINTER;
        this.mTouchSlop = INVALID_POINTER;
    }

    public boolean onInterceptTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        if (ev.getAction() == 2 && this.mIsBeingDragged) {
            return true;
        }
        int y;
        switch (MotionEventCompat.getActionMasked(ev)) {
            case ConstraintTableLayout.ALIGN_CENTER /*0*/:
                this.mIsBeingDragged = false;
                int x = (int) ev.getX();
                y = (int) ev.getY();
                if (canDragView(child) && parent.isPointInChildBounds(child, x, y)) {
                    this.mLastMotionY = y;
                    this.mActivePointerId = ev.getPointerId(0);
                    ensureVelocityTracker();
                    break;
                }
            case ConstraintTableLayout.ALIGN_LEFT /*1*/:
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                this.mIsBeingDragged = false;
                this.mActivePointerId = INVALID_POINTER;
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.recycle();
                    this.mVelocityTracker = null;
                    break;
                }
                break;
            case ConstraintTableLayout.ALIGN_RIGHT /*2*/:
                int activePointerId = this.mActivePointerId;
                if (activePointerId != INVALID_POINTER) {
                    int pointerIndex = ev.findPointerIndex(activePointerId);
                    if (pointerIndex != INVALID_POINTER) {
                        y = (int) ev.getY(pointerIndex);
                        if (Math.abs(y - this.mLastMotionY) > this.mTouchSlop) {
                            this.mIsBeingDragged = true;
                            this.mLastMotionY = y;
                            break;
                        }
                    }
                }
                break;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(ev);
        }
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(CoordinatorLayout parent, V child, MotionEvent ev) {
        if (this.mTouchSlop < 0) {
            this.mTouchSlop = ViewConfiguration.get(parent.getContext()).getScaledTouchSlop();
        }
        int y;
        switch (MotionEventCompat.getActionMasked(ev)) {
            case ConstraintTableLayout.ALIGN_CENTER /*0*/:
                y = (int) ev.getY();
                if (parent.isPointInChildBounds(child, (int) ev.getX(), y) && canDragView(child)) {
                    this.mLastMotionY = y;
                    this.mActivePointerId = ev.getPointerId(0);
                    ensureVelocityTracker();
                    break;
                }
                return false;
            case ConstraintTableLayout.ALIGN_LEFT /*1*/:
                if (this.mVelocityTracker != null) {
                    this.mVelocityTracker.addMovement(ev);
                    this.mVelocityTracker.computeCurrentVelocity(PointerIconCompat.TYPE_DEFAULT);
                    fling(parent, child, -getScrollRangeForDragFling(child), 0, VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId));
                    break;
                }
                break;
            case ConstraintTableLayout.ALIGN_RIGHT /*2*/:
                int activePointerIndex = ev.findPointerIndex(this.mActivePointerId);
                if (activePointerIndex != INVALID_POINTER) {
                    y = (int) ev.getY(activePointerIndex);
                    int dy = this.mLastMotionY - y;
                    if (!this.mIsBeingDragged && Math.abs(dy) > this.mTouchSlop) {
                        this.mIsBeingDragged = true;
                        dy = dy > 0 ? dy - this.mTouchSlop : dy + this.mTouchSlop;
                    }
                    if (this.mIsBeingDragged) {
                        this.mLastMotionY = y;
                        scroll(parent, child, dy, getMaxDragOffset(child), 0);
                        break;
                    }
                }
                return false;
                break;
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                break;
        }
        this.mIsBeingDragged = false;
        this.mActivePointerId = INVALID_POINTER;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.addMovement(ev);
        }
        return true;
    }

    int setHeaderTopBottomOffset(CoordinatorLayout parent, V header, int newOffset) {
        return setHeaderTopBottomOffset(parent, header, newOffset, LinearLayoutManager.INVALID_OFFSET, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout parent, V v, int newOffset, int minOffset, int maxOffset) {
        int curOffset = getTopAndBottomOffset();
        if (minOffset == 0 || curOffset < minOffset || curOffset > maxOffset) {
            return 0;
        }
        newOffset = MathUtils.constrain(newOffset, minOffset, maxOffset);
        if (curOffset == newOffset) {
            return 0;
        }
        setTopAndBottomOffset(newOffset);
        return curOffset - newOffset;
    }

    int getTopBottomOffsetForScrollingSibling() {
        return getTopAndBottomOffset();
    }

    final int scroll(CoordinatorLayout coordinatorLayout, V header, int dy, int minOffset, int maxOffset) {
        return setHeaderTopBottomOffset(coordinatorLayout, header, getTopBottomOffsetForScrollingSibling() - dy, minOffset, maxOffset);
    }

    final boolean fling(CoordinatorLayout coordinatorLayout, V layout, int minOffset, int maxOffset, float velocityY) {
        if (this.mFlingRunnable != null) {
            layout.removeCallbacks(this.mFlingRunnable);
            this.mFlingRunnable = null;
        }
        if (this.mScroller == null) {
            this.mScroller = ScrollerCompat.create(layout.getContext());
        }
        this.mScroller.fling(0, getTopAndBottomOffset(), 0, Math.round(velocityY), 0, 0, minOffset, maxOffset);
        if (this.mScroller.computeScrollOffset()) {
            this.mFlingRunnable = new FlingRunnable(coordinatorLayout, layout);
            ViewCompat.postOnAnimation(layout, this.mFlingRunnable);
            return true;
        }
        onFlingFinished(coordinatorLayout, layout);
        return false;
    }

    void onFlingFinished(CoordinatorLayout parent, V v) {
    }

    boolean canDragView(V v) {
        return false;
    }

    int getMaxDragOffset(V view) {
        return -view.getHeight();
    }

    int getScrollRangeForDragFling(V view) {
        return view.getHeight();
    }

    private void ensureVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }
}
