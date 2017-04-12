package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.Cache;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor.ConnectionType;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.widget.DrawerLayout;
import com.amicel.tunaikudemo.BuildConfig;
import com.amicel.tunaikudemo.C0203R;
import java.util.ArrayList;

public class ConstraintWidget {
    private static final boolean AUTOTAG_CENTER = false;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    public static float DEFAULT_BIAS = 0.0f;
    protected static final int DIRECT = 2;
    public static final int GONE = 8;
    public static final int HORIZONTAL = 0;
    public static final int INVISIBLE = 4;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    protected static final int SOLVER = 1;
    public static final int UNKNOWN = -1;
    public static final int VERTICAL = 1;
    public static final int VISIBLE = 0;
    protected ArrayList<ConstraintAnchor> mAnchors;
    ConstraintAnchor mBaseline;
    int mBaselineDistance;
    ConstraintAnchor mBottom;
    boolean mBottomHasCentered;
    ConstraintAnchor mCenter;
    ConstraintAnchor mCenterX;
    ConstraintAnchor mCenterY;
    private Object mCompanionWidget;
    private int mContainerItemSkip;
    private String mDebugName;
    protected float mDimensionRatio;
    protected int mDimensionRatioSide;
    int mDistToBottom;
    int mDistToLeft;
    int mDistToRight;
    int mDistToTop;
    private int mDrawHeight;
    private int mDrawWidth;
    private int mDrawX;
    private int mDrawY;
    int mHeight;
    float mHorizontalBiasPercent;
    boolean mHorizontalChainFixedPosition;
    int mHorizontalChainStyle;
    DimensionBehaviour mHorizontalDimensionBehaviour;
    ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution;
    float mHorizontalWeight;
    boolean mHorizontalWrapVisited;
    ConstraintAnchor mLeft;
    boolean mLeftHasCentered;
    int mMatchConstraintDefaultHeight;
    int mMatchConstraintDefaultWidth;
    int mMatchConstraintMaxHeight;
    int mMatchConstraintMaxWidth;
    int mMatchConstraintMinHeight;
    int mMatchConstraintMinWidth;
    protected int mMinHeight;
    protected int mMinWidth;
    protected int mOffsetX;
    protected int mOffsetY;
    ConstraintWidget mParent;
    ConstraintAnchor mRight;
    boolean mRightHasCentered;
    private int mSolverBottom;
    private int mSolverLeft;
    private int mSolverRight;
    private int mSolverTop;
    ConstraintAnchor mTop;
    boolean mTopHasCentered;
    private String mType;
    float mVerticalBiasPercent;
    boolean mVerticalChainFixedPosition;
    int mVerticalChainStyle;
    DimensionBehaviour mVerticalDimensionBehaviour;
    ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution;
    float mVerticalWeight;
    boolean mVerticalWrapVisited;
    private int mVisibility;
    int mWidth;
    private int mWrapHeight;
    private int mWrapWidth;
    protected int mX;
    protected int mY;

    /* renamed from: android.support.constraint.solver.widgets.ConstraintWidget.1 */
    static /* synthetic */ class C00051 {
        static final /* synthetic */ int[] f1x1d400623;

        static {
            f1x1d400623 = new int[Type.values().length];
            try {
                f1x1d400623[Type.LEFT.ordinal()] = ConstraintWidget.VERTICAL;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1x1d400623[Type.TOP.ordinal()] = ConstraintWidget.DIRECT;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1x1d400623[Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1x1d400623[Type.BOTTOM.ordinal()] = ConstraintWidget.INVISIBLE;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1x1d400623[Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1x1d400623[Type.CENTER_X.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1x1d400623[Type.CENTER_Y.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f1x1d400623[Type.CENTER.ordinal()] = ConstraintWidget.GONE;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public enum ContentAlignment {
        BEGIN,
        MIDDLE,
        END,
        TOP,
        VERTICAL_MIDDLE,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    static {
        DEFAULT_BIAS = 0.5f;
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mWidth = MATCH_CONSTRAINT_SPREAD;
        this.mHeight = MATCH_CONSTRAINT_SPREAD;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = UNKNOWN;
        this.mX = MATCH_CONSTRAINT_SPREAD;
        this.mY = MATCH_CONSTRAINT_SPREAD;
        this.mDrawX = MATCH_CONSTRAINT_SPREAD;
        this.mDrawY = MATCH_CONSTRAINT_SPREAD;
        this.mDrawWidth = MATCH_CONSTRAINT_SPREAD;
        this.mDrawHeight = MATCH_CONSTRAINT_SPREAD;
        this.mOffsetX = MATCH_CONSTRAINT_SPREAD;
        this.mOffsetY = MATCH_CONSTRAINT_SPREAD;
        this.mBaselineDistance = MATCH_CONSTRAINT_SPREAD;
        this.mMinWidth = MATCH_CONSTRAINT_SPREAD;
        this.mMinHeight = MATCH_CONSTRAINT_SPREAD;
        this.mWrapWidth = MATCH_CONSTRAINT_SPREAD;
        this.mWrapHeight = MATCH_CONSTRAINT_SPREAD;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mCompanionWidget = null;
        this.mContainerItemSkip = MATCH_CONSTRAINT_SPREAD;
        this.mVisibility = MATCH_CONSTRAINT_SPREAD;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalWrapVisited = AUTOTAG_CENTER;
        this.mVerticalWrapVisited = AUTOTAG_CENTER;
        this.mHorizontalChainStyle = MATCH_CONSTRAINT_SPREAD;
        this.mVerticalChainStyle = MATCH_CONSTRAINT_SPREAD;
        this.mHorizontalChainFixedPosition = AUTOTAG_CENTER;
        this.mVerticalChainFixedPosition = AUTOTAG_CENTER;
        this.mHorizontalWeight = 0.0f;
        this.mVerticalWeight = 0.0f;
        this.mHorizontalResolution = UNKNOWN;
        this.mVerticalResolution = UNKNOWN;
    }

    public ConstraintWidget() {
        this.mHorizontalResolution = UNKNOWN;
        this.mVerticalResolution = UNKNOWN;
        this.mMatchConstraintDefaultWidth = MATCH_CONSTRAINT_SPREAD;
        this.mMatchConstraintDefaultHeight = MATCH_CONSTRAINT_SPREAD;
        this.mMatchConstraintMinWidth = MATCH_CONSTRAINT_SPREAD;
        this.mMatchConstraintMaxWidth = MATCH_CONSTRAINT_SPREAD;
        this.mMatchConstraintMinHeight = MATCH_CONSTRAINT_SPREAD;
        this.mMatchConstraintMaxHeight = MATCH_CONSTRAINT_SPREAD;
        this.mLeft = new ConstraintAnchor(this, Type.LEFT);
        this.mTop = new ConstraintAnchor(this, Type.TOP);
        this.mRight = new ConstraintAnchor(this, Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, Type.CENTER_Y);
        this.mCenter = new ConstraintAnchor(this, Type.CENTER);
        this.mAnchors = new ArrayList();
        this.mParent = null;
        this.mWidth = MATCH_CONSTRAINT_SPREAD;
        this.mHeight = MATCH_CONSTRAINT_SPREAD;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = UNKNOWN;
        this.mSolverLeft = MATCH_CONSTRAINT_SPREAD;
        this.mSolverTop = MATCH_CONSTRAINT_SPREAD;
        this.mSolverRight = MATCH_CONSTRAINT_SPREAD;
        this.mSolverBottom = MATCH_CONSTRAINT_SPREAD;
        this.mX = MATCH_CONSTRAINT_SPREAD;
        this.mY = MATCH_CONSTRAINT_SPREAD;
        this.mDrawX = MATCH_CONSTRAINT_SPREAD;
        this.mDrawY = MATCH_CONSTRAINT_SPREAD;
        this.mDrawWidth = MATCH_CONSTRAINT_SPREAD;
        this.mDrawHeight = MATCH_CONSTRAINT_SPREAD;
        this.mOffsetX = MATCH_CONSTRAINT_SPREAD;
        this.mOffsetY = MATCH_CONSTRAINT_SPREAD;
        this.mBaselineDistance = MATCH_CONSTRAINT_SPREAD;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mContainerItemSkip = MATCH_CONSTRAINT_SPREAD;
        this.mVisibility = MATCH_CONSTRAINT_SPREAD;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalChainStyle = MATCH_CONSTRAINT_SPREAD;
        this.mVerticalChainStyle = MATCH_CONSTRAINT_SPREAD;
        this.mHorizontalWeight = 0.0f;
        this.mVerticalWeight = 0.0f;
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        addAnchors();
    }

    public ConstraintWidget(int x, int y, int width, int height) {
        this.mHorizontalResolution = UNKNOWN;
        this.mVerticalResolution = UNKNOWN;
        this.mMatchConstraintDefaultWidth = MATCH_CONSTRAINT_SPREAD;
        this.mMatchConstraintDefaultHeight = MATCH_CONSTRAINT_SPREAD;
        this.mMatchConstraintMinWidth = MATCH_CONSTRAINT_SPREAD;
        this.mMatchConstraintMaxWidth = MATCH_CONSTRAINT_SPREAD;
        this.mMatchConstraintMinHeight = MATCH_CONSTRAINT_SPREAD;
        this.mMatchConstraintMaxHeight = MATCH_CONSTRAINT_SPREAD;
        this.mLeft = new ConstraintAnchor(this, Type.LEFT);
        this.mTop = new ConstraintAnchor(this, Type.TOP);
        this.mRight = new ConstraintAnchor(this, Type.RIGHT);
        this.mBottom = new ConstraintAnchor(this, Type.BOTTOM);
        this.mBaseline = new ConstraintAnchor(this, Type.BASELINE);
        this.mCenterX = new ConstraintAnchor(this, Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, Type.CENTER_Y);
        this.mCenter = new ConstraintAnchor(this, Type.CENTER);
        this.mAnchors = new ArrayList();
        this.mParent = null;
        this.mWidth = MATCH_CONSTRAINT_SPREAD;
        this.mHeight = MATCH_CONSTRAINT_SPREAD;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = UNKNOWN;
        this.mSolverLeft = MATCH_CONSTRAINT_SPREAD;
        this.mSolverTop = MATCH_CONSTRAINT_SPREAD;
        this.mSolverRight = MATCH_CONSTRAINT_SPREAD;
        this.mSolverBottom = MATCH_CONSTRAINT_SPREAD;
        this.mX = MATCH_CONSTRAINT_SPREAD;
        this.mY = MATCH_CONSTRAINT_SPREAD;
        this.mDrawX = MATCH_CONSTRAINT_SPREAD;
        this.mDrawY = MATCH_CONSTRAINT_SPREAD;
        this.mDrawWidth = MATCH_CONSTRAINT_SPREAD;
        this.mDrawHeight = MATCH_CONSTRAINT_SPREAD;
        this.mOffsetX = MATCH_CONSTRAINT_SPREAD;
        this.mOffsetY = MATCH_CONSTRAINT_SPREAD;
        this.mBaselineDistance = MATCH_CONSTRAINT_SPREAD;
        this.mHorizontalBiasPercent = DEFAULT_BIAS;
        this.mVerticalBiasPercent = DEFAULT_BIAS;
        this.mHorizontalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mVerticalDimensionBehaviour = DimensionBehaviour.FIXED;
        this.mContainerItemSkip = MATCH_CONSTRAINT_SPREAD;
        this.mVisibility = MATCH_CONSTRAINT_SPREAD;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalChainStyle = MATCH_CONSTRAINT_SPREAD;
        this.mVerticalChainStyle = MATCH_CONSTRAINT_SPREAD;
        this.mHorizontalWeight = 0.0f;
        this.mVerticalWeight = 0.0f;
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        this.mX = x;
        this.mY = y;
        this.mWidth = width;
        this.mHeight = height;
        addAnchors();
        forceUpdateDrawPosition();
    }

    public ConstraintWidget(int width, int height) {
        this(MATCH_CONSTRAINT_SPREAD, MATCH_CONSTRAINT_SPREAD, width, height);
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable(cache);
        this.mTop.resetSolverVariable(cache);
        this.mRight.resetSolverVariable(cache);
        this.mBottom.resetSolverVariable(cache);
        this.mBaseline.resetSolverVariable(cache);
        this.mCenter.resetSolverVariable(cache);
        this.mCenterX.resetSolverVariable(cache);
        this.mCenterY.resetSolverVariable(cache);
    }

    public void resetGroups() {
        int numAnchors = this.mAnchors.size();
        for (int i = MATCH_CONSTRAINT_SPREAD; i < numAnchors; i += VERTICAL) {
            ((ConstraintAnchor) this.mAnchors.get(i)).mGroup = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        }
    }

    private void addAnchors() {
        this.mAnchors.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mBaseline);
    }

    public boolean isRoot() {
        return this.mParent == null ? true : AUTOTAG_CENTER;
    }

    public boolean isRootContainer() {
        return (!(this instanceof ConstraintWidgetContainer) || (this.mParent != null && (this.mParent instanceof ConstraintWidgetContainer))) ? AUTOTAG_CENTER : true;
    }

    public boolean isInsideConstraintLayout() {
        ConstraintWidget widget = getParent();
        if (widget == null) {
            return AUTOTAG_CENTER;
        }
        while (widget != null) {
            if (widget instanceof ConstraintWidgetContainer) {
                return true;
            }
            widget = widget.getParent();
        }
        return AUTOTAG_CENTER;
    }

    public boolean hasAncestor(ConstraintWidget widget) {
        ConstraintWidget parent = getParent();
        if (parent == widget) {
            return true;
        }
        if (parent == widget.getParent()) {
            return AUTOTAG_CENTER;
        }
        while (parent != null) {
            if (parent == widget) {
                return true;
            }
            if (parent == widget.getParent()) {
                return true;
            }
            parent = parent.getParent();
        }
        return AUTOTAG_CENTER;
    }

    public WidgetContainer getRootWidgetContainer() {
        ConstraintWidget root = this;
        while (root.getParent() != null) {
            root = root.getParent();
        }
        if (root instanceof WidgetContainer) {
            return (WidgetContainer) root;
        }
        return null;
    }

    public ConstraintWidget getParent() {
        return this.mParent;
    }

    public void setParent(ConstraintWidget widget) {
        this.mParent = widget;
    }

    public String getType() {
        return this.mType;
    }

    public void setType(String type) {
        this.mType = type;
    }

    public void setVisibility(int visibility) {
        this.mVisibility = visibility;
    }

    public int getVisibility() {
        return this.mVisibility;
    }

    public String getDebugName() {
        return this.mDebugName;
    }

    public void setDebugName(String name) {
        this.mDebugName = name;
    }

    public void setDebugSolverName(LinearSystem system, String name) {
        this.mDebugName = name;
        SolverVariable left = system.createObjectVariable(this.mLeft);
        SolverVariable top = system.createObjectVariable(this.mTop);
        SolverVariable right = system.createObjectVariable(this.mRight);
        SolverVariable bottom = system.createObjectVariable(this.mBottom);
        left.setName(name + ".left");
        top.setName(name + ".top");
        right.setName(name + ".right");
        bottom.setName(name + ".bottom");
        if (this.mBaselineDistance > 0) {
            system.createObjectVariable(this.mBaseline).setName(name + ".baseline");
        }
    }

    public String toString() {
        return (this.mType != null ? "type: " + this.mType + " " : BuildConfig.FLAVOR) + (this.mDebugName != null ? "id: " + this.mDebugName + " " : BuildConfig.FLAVOR) + "(" + this.mX + ", " + this.mY + ") - (" + this.mWidth + " x " + this.mHeight + ")" + " wrap: (" + this.mWrapWidth + " x " + this.mWrapHeight + ")";
    }

    int getInternalDrawX() {
        return this.mDrawX;
    }

    int getInternalDrawY() {
        return this.mDrawY;
    }

    public int getInternalDrawRight() {
        return this.mDrawX + this.mDrawWidth;
    }

    public int getInternalDrawBottom() {
        return this.mDrawY + this.mDrawHeight;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int getWidth() {
        if (this.mVisibility == GONE) {
            return MATCH_CONSTRAINT_SPREAD;
        }
        return this.mWidth;
    }

    public int getOptimizerWrapWidth() {
        int w = this.mWidth;
        if (this.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT) {
            return w;
        }
        if (this.mMatchConstraintDefaultWidth == VERTICAL) {
            w = Math.max(this.mMatchConstraintMinWidth, w);
        } else if (this.mMatchConstraintMinWidth > 0) {
            w = this.mMatchConstraintMinWidth;
            this.mWidth = w;
        } else {
            w = MATCH_CONSTRAINT_SPREAD;
        }
        if (this.mMatchConstraintMaxWidth <= 0 || this.mMatchConstraintMaxWidth >= w) {
            return w;
        }
        return this.mMatchConstraintMaxWidth;
    }

    public int getOptimizerWrapHeight() {
        int h = this.mHeight;
        if (this.mVerticalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT) {
            return h;
        }
        if (this.mMatchConstraintDefaultHeight == VERTICAL) {
            h = Math.max(this.mMatchConstraintMinHeight, h);
        } else if (this.mMatchConstraintMinHeight > 0) {
            h = this.mMatchConstraintMinHeight;
            this.mHeight = h;
        } else {
            h = MATCH_CONSTRAINT_SPREAD;
        }
        if (this.mMatchConstraintMaxHeight <= 0 || this.mMatchConstraintMaxHeight >= h) {
            return h;
        }
        return this.mMatchConstraintMaxHeight;
    }

    public int getWrapWidth() {
        return this.mWrapWidth;
    }

    public int getHeight() {
        if (this.mVisibility == GONE) {
            return MATCH_CONSTRAINT_SPREAD;
        }
        return this.mHeight;
    }

    public int getWrapHeight() {
        return this.mWrapHeight;
    }

    public int getDrawX() {
        return this.mDrawX + this.mOffsetX;
    }

    public int getDrawY() {
        return this.mDrawY + this.mOffsetY;
    }

    public int getDrawWidth() {
        return this.mDrawWidth;
    }

    public int getDrawHeight() {
        return this.mDrawHeight;
    }

    public int getDrawBottom() {
        return getDrawY() + this.mDrawHeight;
    }

    public int getDrawRight() {
        return getDrawX() + this.mDrawWidth;
    }

    protected int getRootX() {
        return this.mX + this.mOffsetX;
    }

    protected int getRootY() {
        return this.mY + this.mOffsetY;
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public int getLeft() {
        return getX();
    }

    public int getTop() {
        return getY();
    }

    public int getRight() {
        return getX() + this.mWidth;
    }

    public int getBottom() {
        return getY() + this.mHeight;
    }

    public float getHorizontalBiasPercent() {
        return this.mHorizontalBiasPercent;
    }

    public float getVerticalBiasPercent() {
        return this.mVerticalBiasPercent;
    }

    public boolean hasBaseline() {
        return this.mBaselineDistance > 0 ? true : AUTOTAG_CENTER;
    }

    public int getBaselineDistance() {
        return this.mBaselineDistance;
    }

    public Object getCompanionWidget() {
        return this.mCompanionWidget;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public void setX(int x) {
        this.mX = x;
    }

    public void setY(int y) {
        this.mY = y;
    }

    public void setOrigin(int x, int y) {
        this.mX = x;
        this.mY = y;
    }

    public void setOffset(int x, int y) {
        this.mOffsetX = x;
        this.mOffsetY = y;
    }

    public void setGoneMargin(Type type, int goneMargin) {
        switch (C00051.f1x1d400623[type.ordinal()]) {
            case VERTICAL /*1*/:
                this.mLeft.mGoneMargin = goneMargin;
            case DIRECT /*2*/:
                this.mTop.mGoneMargin = goneMargin;
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                this.mRight.mGoneMargin = goneMargin;
            case INVISIBLE /*4*/:
                this.mBottom.mGoneMargin = goneMargin;
            default:
        }
    }

    public void updateDrawPosition() {
        int left = this.mX;
        int top = this.mY;
        int right = this.mX + this.mWidth;
        int bottom = this.mY + this.mHeight;
        this.mDrawX = left;
        this.mDrawY = top;
        this.mDrawWidth = right - left;
        this.mDrawHeight = bottom - top;
    }

    public void forceUpdateDrawPosition() {
        int left = this.mX;
        int top = this.mY;
        int right = this.mX + this.mWidth;
        int bottom = this.mY + this.mHeight;
        this.mDrawX = left;
        this.mDrawY = top;
        this.mDrawWidth = right - left;
        this.mDrawHeight = bottom - top;
    }

    public void setDrawOrigin(int x, int y) {
        this.mDrawX = x - this.mOffsetX;
        this.mDrawY = y - this.mOffsetY;
        this.mX = this.mDrawX;
        this.mY = this.mDrawY;
    }

    public void setDrawX(int x) {
        this.mDrawX = x - this.mOffsetX;
        this.mX = this.mDrawX;
    }

    public void setDrawY(int y) {
        this.mDrawY = y - this.mOffsetY;
        this.mY = this.mDrawY;
    }

    public void setDrawWidth(int drawWidth) {
        this.mDrawWidth = drawWidth;
    }

    public void setDrawHeight(int drawHeight) {
        this.mDrawHeight = drawHeight;
    }

    public void setWidth(int w) {
        this.mWidth = w;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setHeight(int h) {
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setHorizontalMatchStyle(int horizontalMatchStyle, int min, int max) {
        this.mMatchConstraintDefaultWidth = horizontalMatchStyle;
        this.mMatchConstraintMinWidth = min;
        this.mMatchConstraintMaxWidth = max;
    }

    public void setVerticalMatchStyle(int verticalMatchStyle, int min, int max) {
        this.mMatchConstraintDefaultHeight = verticalMatchStyle;
        this.mMatchConstraintMinHeight = min;
        this.mMatchConstraintMaxHeight = max;
    }

    public void setDimensionRatio(String ratio) {
        if (ratio == null || ratio.length() == 0) {
            this.mDimensionRatio = 0.0f;
            return;
        }
        int dimensionRatioSide = UNKNOWN;
        float dimensionRatio = 0.0f;
        int len = ratio.length();
        int commaIndex = ratio.indexOf(44);
        if (commaIndex <= 0 || commaIndex >= len + UNKNOWN) {
            commaIndex = MATCH_CONSTRAINT_SPREAD;
        } else {
            String dimension = ratio.substring(MATCH_CONSTRAINT_SPREAD, commaIndex);
            if (dimension.equalsIgnoreCase("W")) {
                dimensionRatioSide = MATCH_CONSTRAINT_SPREAD;
            } else if (dimension.equalsIgnoreCase("H")) {
                dimensionRatioSide = VERTICAL;
            }
            commaIndex += VERTICAL;
        }
        int colonIndex = ratio.indexOf(58);
        if (colonIndex < 0 || colonIndex >= len + UNKNOWN) {
            String r = ratio.substring(commaIndex);
            if (r.length() > 0) {
                try {
                    dimensionRatio = Float.parseFloat(r);
                } catch (NumberFormatException e) {
                }
            }
        } else {
            String nominator = ratio.substring(commaIndex, colonIndex);
            String denominator = ratio.substring(colonIndex + VERTICAL);
            if (nominator.length() > 0 && denominator.length() > 0) {
                try {
                    float nominatorValue = Float.parseFloat(nominator);
                    float denominatorValue = Float.parseFloat(denominator);
                    if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                        dimensionRatio = dimensionRatioSide == VERTICAL ? Math.abs(denominatorValue / nominatorValue) : Math.abs(nominatorValue / denominatorValue);
                    }
                } catch (NumberFormatException e2) {
                }
            }
        }
        if (dimensionRatio > 0.0f) {
            this.mDimensionRatio = dimensionRatio;
            this.mDimensionRatioSide = dimensionRatioSide;
        }
    }

    public void setDimensionRatio(float ratio, int dimensionRatioSide) {
        this.mDimensionRatio = ratio;
        this.mDimensionRatioSide = dimensionRatioSide;
    }

    public float getDimensionRatio() {
        return this.mDimensionRatio;
    }

    public int getDimensionRatioSide() {
        return this.mDimensionRatioSide;
    }

    public void setHorizontalBiasPercent(float horizontalBiasPercent) {
        this.mHorizontalBiasPercent = horizontalBiasPercent;
    }

    public void setVerticalBiasPercent(float verticalBiasPercent) {
        this.mVerticalBiasPercent = verticalBiasPercent;
    }

    public void setMinWidth(int w) {
        this.mMinWidth = w;
    }

    public void setMinHeight(int h) {
        this.mMinHeight = h;
    }

    public void setWrapWidth(int w) {
        this.mWrapWidth = w;
    }

    public void setWrapHeight(int h) {
        this.mWrapHeight = h;
    }

    public void setDimension(int w, int h) {
        this.mWidth = w;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setFrame(int left, int top, int right, int bottom) {
        int w = right - left;
        int h = bottom - top;
        this.mX = left;
        this.mY = top;
        if (this.mVisibility == GONE) {
            this.mWidth = MATCH_CONSTRAINT_SPREAD;
            this.mHeight = MATCH_CONSTRAINT_SPREAD;
            return;
        }
        if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.FIXED && w < this.mWidth) {
            w = this.mWidth;
        }
        if (this.mVerticalDimensionBehaviour == DimensionBehaviour.FIXED && h < this.mHeight) {
            h = this.mHeight;
        }
        this.mWidth = w;
        this.mHeight = h;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setHorizontalDimension(int left, int right) {
        this.mX = left;
        this.mWidth = right - left;
        if (this.mWidth < this.mMinWidth) {
            this.mWidth = this.mMinWidth;
        }
    }

    public void setVerticalDimension(int top, int bottom) {
        this.mY = top;
        this.mHeight = bottom - top;
        if (this.mHeight < this.mMinHeight) {
            this.mHeight = this.mMinHeight;
        }
    }

    public void setBaselineDistance(int baseline) {
        this.mBaselineDistance = baseline;
    }

    public void setCompanionWidget(Object companion) {
        this.mCompanionWidget = companion;
    }

    public void setContainerItemSkip(int skip) {
        if (skip >= 0) {
            this.mContainerItemSkip = skip;
        } else {
            this.mContainerItemSkip = MATCH_CONSTRAINT_SPREAD;
        }
    }

    public int getContainerItemSkip() {
        return this.mContainerItemSkip;
    }

    public void setHorizontalWeight(float horizontalWeight) {
        this.mHorizontalWeight = horizontalWeight;
    }

    public void setVerticalWeight(float verticalWeight) {
        this.mVerticalWeight = verticalWeight;
    }

    public void setHorizontalChainStyle(int horizontalChainStyle) {
        this.mHorizontalChainStyle = horizontalChainStyle;
    }

    public int getHorizontalChainStyle() {
        return this.mHorizontalChainStyle;
    }

    public void setVerticalChainStyle(int verticalChainStyle) {
        this.mVerticalChainStyle = verticalChainStyle;
    }

    public int getVerticalChainStyle() {
        return this.mVerticalChainStyle;
    }

    public void connectedTo(ConstraintWidget source) {
    }

    public void immediateConnect(Type startType, ConstraintWidget target, Type endType, int margin, int goneMargin) {
        getAnchor(startType).connect(target.getAnchor(endType), margin, goneMargin, Strength.STRONG, MATCH_CONSTRAINT_SPREAD, true);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin, int creator) {
        connect(from, to, margin, Strength.STRONG, creator);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin) {
        connect(from, to, margin, Strength.STRONG, (int) MATCH_CONSTRAINT_SPREAD);
    }

    public void connect(ConstraintAnchor from, ConstraintAnchor to, int margin, Strength strength, int creator) {
        if (from.getOwner() == this) {
            connect(from.getType(), to.getOwner(), to.getType(), margin, strength, creator);
        }
    }

    public void connect(Type constraintFrom, ConstraintWidget target, Type constraintTo, int margin) {
        connect(constraintFrom, target, constraintTo, margin, Strength.STRONG);
    }

    public void connect(Type constraintFrom, ConstraintWidget target, Type constraintTo) {
        connect(constraintFrom, target, constraintTo, (int) MATCH_CONSTRAINT_SPREAD, Strength.STRONG);
    }

    public void connect(Type constraintFrom, ConstraintWidget target, Type constraintTo, int margin, Strength strength) {
        connect(constraintFrom, target, constraintTo, margin, strength, MATCH_CONSTRAINT_SPREAD);
    }

    public void connect(Type constraintFrom, ConstraintWidget target, Type constraintTo, int margin, Strength strength, int creator) {
        ConstraintAnchor left;
        ConstraintAnchor right;
        ConstraintAnchor top;
        ConstraintAnchor bottom;
        if (constraintFrom == Type.CENTER) {
            if (constraintTo == Type.CENTER) {
                left = getAnchor(Type.LEFT);
                right = getAnchor(Type.RIGHT);
                top = getAnchor(Type.TOP);
                bottom = getAnchor(Type.BOTTOM);
                boolean centerX = AUTOTAG_CENTER;
                boolean centerY = AUTOTAG_CENTER;
                if ((left == null || !left.isConnected()) && (right == null || !right.isConnected())) {
                    connect(Type.LEFT, target, Type.LEFT, MATCH_CONSTRAINT_SPREAD, strength, creator);
                    connect(Type.RIGHT, target, Type.RIGHT, MATCH_CONSTRAINT_SPREAD, strength, creator);
                    centerX = true;
                }
                if ((top == null || !top.isConnected()) && (bottom == null || !bottom.isConnected())) {
                    connect(Type.TOP, target, Type.TOP, MATCH_CONSTRAINT_SPREAD, strength, creator);
                    connect(Type.BOTTOM, target, Type.BOTTOM, MATCH_CONSTRAINT_SPREAD, strength, creator);
                    centerY = true;
                }
                if (centerX && centerY) {
                    getAnchor(Type.CENTER).connect(target.getAnchor(Type.CENTER), MATCH_CONSTRAINT_SPREAD, creator);
                } else if (centerX) {
                    getAnchor(Type.CENTER_X).connect(target.getAnchor(Type.CENTER_X), MATCH_CONSTRAINT_SPREAD, creator);
                } else if (centerY) {
                    getAnchor(Type.CENTER_Y).connect(target.getAnchor(Type.CENTER_Y), MATCH_CONSTRAINT_SPREAD, creator);
                }
            } else if (constraintTo == Type.LEFT || constraintTo == Type.RIGHT) {
                connect(Type.LEFT, target, constraintTo, MATCH_CONSTRAINT_SPREAD, strength, creator);
                connect(Type.RIGHT, target, constraintTo, MATCH_CONSTRAINT_SPREAD, strength, creator);
                getAnchor(Type.CENTER).connect(target.getAnchor(constraintTo), MATCH_CONSTRAINT_SPREAD, creator);
            } else if (constraintTo == Type.TOP || constraintTo == Type.BOTTOM) {
                connect(Type.TOP, target, constraintTo, MATCH_CONSTRAINT_SPREAD, strength, creator);
                connect(Type.BOTTOM, target, constraintTo, MATCH_CONSTRAINT_SPREAD, strength, creator);
                getAnchor(Type.CENTER).connect(target.getAnchor(constraintTo), MATCH_CONSTRAINT_SPREAD, creator);
            }
        } else if (constraintFrom == Type.CENTER_X && (constraintTo == Type.LEFT || constraintTo == Type.RIGHT)) {
            left = getAnchor(Type.LEFT);
            targetAnchor = target.getAnchor(constraintTo);
            right = getAnchor(Type.RIGHT);
            left.connect(targetAnchor, MATCH_CONSTRAINT_SPREAD, creator);
            right.connect(targetAnchor, MATCH_CONSTRAINT_SPREAD, creator);
            getAnchor(Type.CENTER_X).connect(targetAnchor, MATCH_CONSTRAINT_SPREAD, creator);
        } else if (constraintFrom == Type.CENTER_Y && (constraintTo == Type.TOP || constraintTo == Type.BOTTOM)) {
            targetAnchor = target.getAnchor(constraintTo);
            getAnchor(Type.TOP).connect(targetAnchor, MATCH_CONSTRAINT_SPREAD, creator);
            getAnchor(Type.BOTTOM).connect(targetAnchor, MATCH_CONSTRAINT_SPREAD, creator);
            getAnchor(Type.CENTER_Y).connect(targetAnchor, MATCH_CONSTRAINT_SPREAD, creator);
        } else if (constraintFrom == Type.CENTER_X && constraintTo == Type.CENTER_X) {
            getAnchor(Type.LEFT).connect(target.getAnchor(Type.LEFT), MATCH_CONSTRAINT_SPREAD, creator);
            getAnchor(Type.RIGHT).connect(target.getAnchor(Type.RIGHT), MATCH_CONSTRAINT_SPREAD, creator);
            r0 = getAnchor(Type.CENTER_X);
            centerX.connect(target.getAnchor(constraintTo), MATCH_CONSTRAINT_SPREAD, creator);
        } else if (constraintFrom == Type.CENTER_Y && constraintTo == Type.CENTER_Y) {
            getAnchor(Type.TOP).connect(target.getAnchor(Type.TOP), MATCH_CONSTRAINT_SPREAD, creator);
            getAnchor(Type.BOTTOM).connect(target.getAnchor(Type.BOTTOM), MATCH_CONSTRAINT_SPREAD, creator);
            r0 = getAnchor(Type.CENTER_Y);
            centerY.connect(target.getAnchor(constraintTo), MATCH_CONSTRAINT_SPREAD, creator);
        } else {
            ConstraintAnchor fromAnchor = getAnchor(constraintFrom);
            ConstraintAnchor toAnchor = target.getAnchor(constraintTo);
            if (fromAnchor.isValidConnection(toAnchor)) {
                if (constraintFrom == Type.BASELINE) {
                    top = getAnchor(Type.TOP);
                    bottom = getAnchor(Type.BOTTOM);
                    if (top != null) {
                        top.reset();
                    }
                    if (bottom != null) {
                        bottom.reset();
                    }
                    margin = MATCH_CONSTRAINT_SPREAD;
                } else if (constraintFrom == Type.TOP || constraintFrom == Type.BOTTOM) {
                    ConstraintAnchor baseline = getAnchor(Type.BASELINE);
                    if (baseline != null) {
                        baseline.reset();
                    }
                    center = getAnchor(Type.CENTER);
                    if (center.getTarget() != toAnchor) {
                        center.reset();
                    }
                    opposite = getAnchor(constraintFrom).getOpposite();
                    centerY = getAnchor(Type.CENTER_Y);
                    if (centerY.isConnected()) {
                        opposite.reset();
                        centerY.reset();
                    }
                } else if (constraintFrom == Type.LEFT || constraintFrom == Type.RIGHT) {
                    center = getAnchor(Type.CENTER);
                    if (center.getTarget() != toAnchor) {
                        center.reset();
                    }
                    opposite = getAnchor(constraintFrom).getOpposite();
                    centerX = getAnchor(Type.CENTER_X);
                    if (centerX.isConnected()) {
                        opposite.reset();
                        centerX.reset();
                    }
                }
                fromAnchor.connect(toAnchor, margin, strength, creator);
                toAnchor.getOwner().connectedTo(fromAnchor.getOwner());
            }
        }
    }

    public void resetAllConstraints() {
        resetAnchors();
        setVerticalBiasPercent(DEFAULT_BIAS);
        setHorizontalBiasPercent(DEFAULT_BIAS);
        if (!(this instanceof ConstraintWidgetContainer)) {
            if (getHorizontalDimensionBehaviour() == DimensionBehaviour.MATCH_CONSTRAINT) {
                if (getWidth() == getWrapWidth()) {
                    setHorizontalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
                } else if (getWidth() > getMinWidth()) {
                    setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
            }
            if (getVerticalDimensionBehaviour() != DimensionBehaviour.MATCH_CONSTRAINT) {
                return;
            }
            if (getHeight() == getWrapHeight()) {
                setVerticalDimensionBehaviour(DimensionBehaviour.WRAP_CONTENT);
            } else if (getHeight() > getMinHeight()) {
                setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
            }
        }
    }

    public void resetAnchor(ConstraintAnchor anchor) {
        if (getParent() == null || !(getParent() instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            ConstraintAnchor left = getAnchor(Type.LEFT);
            ConstraintAnchor right = getAnchor(Type.RIGHT);
            ConstraintAnchor top = getAnchor(Type.TOP);
            ConstraintAnchor bottom = getAnchor(Type.BOTTOM);
            ConstraintAnchor center = getAnchor(Type.CENTER);
            ConstraintAnchor centerX = getAnchor(Type.CENTER_X);
            ConstraintAnchor centerY = getAnchor(Type.CENTER_Y);
            if (anchor == center) {
                if (left.isConnected() && right.isConnected() && left.getTarget() == right.getTarget()) {
                    left.reset();
                    right.reset();
                }
                if (top.isConnected() && bottom.isConnected() && top.getTarget() == bottom.getTarget()) {
                    top.reset();
                    bottom.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == centerX) {
                if (left.isConnected() && right.isConnected() && left.getTarget().getOwner() == right.getTarget().getOwner()) {
                    left.reset();
                    right.reset();
                }
                this.mHorizontalBiasPercent = 0.5f;
            } else if (anchor == centerY) {
                if (top.isConnected() && bottom.isConnected() && top.getTarget().getOwner() == bottom.getTarget().getOwner()) {
                    top.reset();
                    bottom.reset();
                }
                this.mVerticalBiasPercent = 0.5f;
            } else if (anchor == left || anchor == right) {
                if (left.isConnected() && left.getTarget() == right.getTarget()) {
                    center.reset();
                }
            } else if ((anchor == top || anchor == bottom) && top.isConnected() && top.getTarget() == bottom.getTarget()) {
                center.reset();
            }
            anchor.reset();
        }
    }

    public void resetAnchors() {
        ConstraintWidget parent = getParent();
        if (parent == null || !(parent instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            int mAnchorsSize = this.mAnchors.size();
            for (int i = MATCH_CONSTRAINT_SPREAD; i < mAnchorsSize; i += VERTICAL) {
                ((ConstraintAnchor) this.mAnchors.get(i)).reset();
            }
        }
    }

    public void resetAnchors(int connectionCreator) {
        ConstraintWidget parent = getParent();
        if (parent == null || !(parent instanceof ConstraintWidgetContainer) || !((ConstraintWidgetContainer) getParent()).handlesInternalConstraints()) {
            int mAnchorsSize = this.mAnchors.size();
            for (int i = MATCH_CONSTRAINT_SPREAD; i < mAnchorsSize; i += VERTICAL) {
                ConstraintAnchor anchor = (ConstraintAnchor) this.mAnchors.get(i);
                if (connectionCreator == anchor.getConnectionCreator()) {
                    if (anchor.isVerticalAnchor()) {
                        setVerticalBiasPercent(DEFAULT_BIAS);
                    } else {
                        setHorizontalBiasPercent(DEFAULT_BIAS);
                    }
                    anchor.reset();
                }
            }
        }
    }

    public void disconnectWidget(ConstraintWidget widget) {
        ArrayList<ConstraintAnchor> anchors = getAnchors();
        int anchorsSize = anchors.size();
        for (int i = MATCH_CONSTRAINT_SPREAD; i < anchorsSize; i += VERTICAL) {
            ConstraintAnchor anchor = (ConstraintAnchor) anchors.get(i);
            if (anchor.isConnected() && anchor.getTarget().getOwner() == widget) {
                anchor.reset();
            }
        }
    }

    public void disconnectUnlockedWidget(ConstraintWidget widget) {
        ArrayList<ConstraintAnchor> anchors = getAnchors();
        int anchorsSize = anchors.size();
        for (int i = MATCH_CONSTRAINT_SPREAD; i < anchorsSize; i += VERTICAL) {
            ConstraintAnchor anchor = (ConstraintAnchor) anchors.get(i);
            if (anchor.isConnected() && anchor.getTarget().getOwner() == widget && anchor.getConnectionCreator() == DIRECT) {
                anchor.reset();
            }
        }
    }

    public ConstraintAnchor getAnchor(Type anchorType) {
        switch (C00051.f1x1d400623[anchorType.ordinal()]) {
            case VERTICAL /*1*/:
                return this.mLeft;
            case DIRECT /*2*/:
                return this.mTop;
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                return this.mRight;
            case INVISIBLE /*4*/:
                return this.mBottom;
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return this.mBaseline;
            case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                return this.mCenterX;
            case C0203R.styleable.Toolbar_contentInsetLeft /*7*/:
                return this.mCenterY;
            case GONE /*8*/:
                return this.mCenter;
            default:
                return null;
        }
    }

    public DimensionBehaviour getHorizontalDimensionBehaviour() {
        return this.mHorizontalDimensionBehaviour;
    }

    public DimensionBehaviour getVerticalDimensionBehaviour() {
        return this.mVerticalDimensionBehaviour;
    }

    public void setHorizontalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mHorizontalDimensionBehaviour = behaviour;
        if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            setWidth(this.mWrapWidth);
        }
    }

    public void setVerticalDimensionBehaviour(DimensionBehaviour behaviour) {
        this.mVerticalDimensionBehaviour = behaviour;
        if (this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
            setHeight(this.mWrapHeight);
        }
    }

    public boolean isInHorizontalChain() {
        if ((this.mLeft.mTarget == null || this.mLeft.mTarget.mTarget != this.mLeft) && (this.mRight.mTarget == null || this.mRight.mTarget.mTarget != this.mRight)) {
            return AUTOTAG_CENTER;
        }
        return true;
    }

    public ConstraintWidget getHorizontalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInHorizontalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(Type.LEFT);
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            ConstraintAnchor targetAnchor = target == null ? null : target.getAnchor(Type.RIGHT).getTarget();
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    public boolean isInVerticalChain() {
        if ((this.mTop.mTarget == null || this.mTop.mTarget.mTarget != this.mTop) && (this.mBottom.mTarget == null || this.mBottom.mTarget.mTarget != this.mBottom)) {
            return AUTOTAG_CENTER;
        }
        return true;
    }

    public ConstraintWidget getVerticalChainControlWidget() {
        ConstraintWidget found = null;
        if (!isInVerticalChain()) {
            return null;
        }
        ConstraintWidget tmp = this;
        while (found == null && tmp != null) {
            ConstraintAnchor anchor = tmp.getAnchor(Type.TOP);
            ConstraintAnchor targetOwner = anchor == null ? null : anchor.getTarget();
            ConstraintWidget target = targetOwner == null ? null : targetOwner.getOwner();
            if (target == getParent()) {
                return tmp;
            }
            ConstraintAnchor targetAnchor = target == null ? null : target.getAnchor(Type.BOTTOM).getTarget();
            if (targetAnchor == null || targetAnchor.getOwner() == tmp) {
                tmp = target;
            } else {
                found = tmp;
            }
        }
        return found;
    }

    public void addToSolver(LinearSystem system) {
        addToSolver(system, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }

    public void addToSolver(LinearSystem system, int group) {
        ArrayRow row;
        SolverVariable begin;
        SolverVariable end;
        SolverVariable beginTarget;
        SolverVariable endTarget;
        SolverVariable left = null;
        SolverVariable right = null;
        SolverVariable top = null;
        SolverVariable bottom = null;
        SolverVariable baseline = null;
        if (group == Integer.MAX_VALUE || this.mLeft.mGroup == group) {
            left = system.createObjectVariable(this.mLeft);
        }
        if (group == Integer.MAX_VALUE || this.mRight.mGroup == group) {
            right = system.createObjectVariable(this.mRight);
        }
        if (group == Integer.MAX_VALUE || this.mTop.mGroup == group) {
            top = system.createObjectVariable(this.mTop);
        }
        if (group == Integer.MAX_VALUE || this.mBottom.mGroup == group) {
            bottom = system.createObjectVariable(this.mBottom);
        }
        if (group == Integer.MAX_VALUE || this.mBaseline.mGroup == group) {
            baseline = system.createObjectVariable(this.mBaseline);
        }
        boolean inHorizontalChain = AUTOTAG_CENTER;
        boolean inVerticalChain = AUTOTAG_CENTER;
        if (this.mParent != null) {
            if ((this.mLeft.mTarget != null && this.mLeft.mTarget.mTarget == this.mLeft) || (this.mRight.mTarget != null && this.mRight.mTarget.mTarget == this.mRight)) {
                ((ConstraintWidgetContainer) this.mParent).addChain(this, MATCH_CONSTRAINT_SPREAD);
                inHorizontalChain = true;
            }
            if ((this.mTop.mTarget != null && this.mTop.mTarget.mTarget == this.mTop) || (this.mBottom.mTarget != null && this.mBottom.mTarget.mTarget == this.mBottom)) {
                ((ConstraintWidgetContainer) this.mParent).addChain(this, VERTICAL);
                inVerticalChain = true;
            }
            if (this.mParent.getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT && !inHorizontalChain) {
                if (this.mLeft.mTarget == null || this.mLeft.mTarget.mOwner != this.mParent) {
                    SolverVariable parentLeft = system.createObjectVariable(this.mParent.mLeft);
                    row = system.createRow();
                    row.createRowGreaterThan(left, parentLeft, system.createSlackVariable(), MATCH_CONSTRAINT_SPREAD);
                    system.addConstraint(row);
                } else if (this.mLeft.mTarget != null && this.mLeft.mTarget.mOwner == this.mParent) {
                    this.mLeft.setConnectionType(ConnectionType.STRICT);
                }
                if (this.mRight.mTarget == null || this.mRight.mTarget.mOwner != this.mParent) {
                    SolverVariable parentRight = system.createObjectVariable(this.mParent.mRight);
                    row = system.createRow();
                    row.createRowGreaterThan(parentRight, right, system.createSlackVariable(), MATCH_CONSTRAINT_SPREAD);
                    system.addConstraint(row);
                } else if (this.mRight.mTarget != null && this.mRight.mTarget.mOwner == this.mParent) {
                    this.mRight.setConnectionType(ConnectionType.STRICT);
                }
            }
            if (this.mParent.getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT && !inVerticalChain) {
                if (this.mTop.mTarget == null || this.mTop.mTarget.mOwner != this.mParent) {
                    SolverVariable parentTop = system.createObjectVariable(this.mParent.mTop);
                    row = system.createRow();
                    row.createRowGreaterThan(top, parentTop, system.createSlackVariable(), MATCH_CONSTRAINT_SPREAD);
                    system.addConstraint(row);
                } else if (this.mTop.mTarget != null && this.mTop.mTarget.mOwner == this.mParent) {
                    this.mTop.setConnectionType(ConnectionType.STRICT);
                }
                if (this.mBottom.mTarget == null || this.mBottom.mTarget.mOwner != this.mParent) {
                    SolverVariable parentBottom = system.createObjectVariable(this.mParent.mBottom);
                    row = system.createRow();
                    row.createRowGreaterThan(parentBottom, bottom, system.createSlackVariable(), MATCH_CONSTRAINT_SPREAD);
                    system.addConstraint(row);
                } else if (this.mBottom.mTarget != null && this.mBottom.mTarget.mOwner == this.mParent) {
                    this.mBottom.setConnectionType(ConnectionType.STRICT);
                }
            }
        }
        int width = this.mWidth;
        if (width < this.mMinWidth) {
            width = this.mMinWidth;
        }
        int height = this.mHeight;
        if (height < this.mMinHeight) {
            height = this.mMinHeight;
        }
        boolean horizontalDimensionFixed = this.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT ? true : AUTOTAG_CENTER;
        boolean verticalDimensionFixed = this.mVerticalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT ? true : AUTOTAG_CENTER;
        if (!(horizontalDimensionFixed || this.mLeft == null || this.mRight == null || (this.mLeft.mTarget != null && this.mRight.mTarget != null))) {
            horizontalDimensionFixed = true;
        }
        if (!(verticalDimensionFixed || this.mTop == null || this.mBottom == null || ((this.mTop.mTarget != null && this.mBottom.mTarget != null) || (this.mBaselineDistance != 0 && (this.mBaseline == null || (this.mTop.mTarget != null && this.mBaseline.mTarget != null)))))) {
            verticalDimensionFixed = true;
        }
        boolean useRatio = AUTOTAG_CENTER;
        int dimensionRatioSide = this.mDimensionRatioSide;
        float dimensionRatio = this.mDimensionRatio;
        if (this.mDimensionRatio > 0.0f && this.mVisibility != GONE) {
            if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT && this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                useRatio = true;
                if (horizontalDimensionFixed && !verticalDimensionFixed) {
                    dimensionRatioSide = MATCH_CONSTRAINT_SPREAD;
                } else if (!horizontalDimensionFixed && verticalDimensionFixed) {
                    dimensionRatioSide = VERTICAL;
                    if (this.mDimensionRatioSide == UNKNOWN) {
                        dimensionRatio = 1.0f / dimensionRatio;
                    }
                }
            } else if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                dimensionRatioSide = MATCH_CONSTRAINT_SPREAD;
                width = (int) (((float) this.mHeight) * dimensionRatio);
                horizontalDimensionFixed = true;
            } else if (this.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                dimensionRatioSide = VERTICAL;
                if (this.mDimensionRatioSide == UNKNOWN) {
                    dimensionRatio = 1.0f / dimensionRatio;
                }
                height = (int) (((float) this.mWidth) * dimensionRatio);
                verticalDimensionFixed = true;
            }
        }
        boolean useHorizontalRatio = (useRatio && (dimensionRatioSide == 0 || dimensionRatioSide == UNKNOWN)) ? true : AUTOTAG_CENTER;
        boolean wrapContent = (this.mHorizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT && (this instanceof ConstraintWidgetContainer)) ? true : AUTOTAG_CENTER;
        if (this.mHorizontalResolution != DIRECT && (group == Integer.MAX_VALUE || (this.mLeft.mGroup == group && this.mRight.mGroup == group))) {
            if (!useHorizontalRatio || this.mLeft.mTarget == null || this.mRight.mTarget == null) {
                applyConstraints(system, wrapContent, horizontalDimensionFixed, this.mLeft, this.mRight, this.mX, this.mX + width, width, this.mMinWidth, this.mHorizontalBiasPercent, useHorizontalRatio, inHorizontalChain, this.mMatchConstraintDefaultWidth, this.mMatchConstraintMinWidth, this.mMatchConstraintMaxWidth);
            } else {
                begin = system.createObjectVariable(this.mLeft);
                end = system.createObjectVariable(this.mRight);
                beginTarget = system.createObjectVariable(this.mLeft.getTarget());
                endTarget = system.createObjectVariable(this.mRight.getTarget());
                system.addGreaterThan(begin, beginTarget, this.mLeft.getMargin(), 3);
                system.addLowerThan(end, endTarget, this.mRight.getMargin() * UNKNOWN, 3);
                if (!inHorizontalChain) {
                    system.addCentering(begin, beginTarget, this.mLeft.getMargin(), this.mHorizontalBiasPercent, endTarget, end, this.mRight.getMargin(), INVISIBLE);
                }
            }
        }
        if (this.mVerticalResolution != DIRECT) {
            wrapContent = (this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT && (this instanceof ConstraintWidgetContainer)) ? true : AUTOTAG_CENTER;
            boolean useVerticalRatio = (useRatio && (dimensionRatioSide == VERTICAL || dimensionRatioSide == UNKNOWN)) ? true : AUTOTAG_CENTER;
            if (this.mBaselineDistance > 0) {
                ConstraintAnchor endAnchor = this.mBottom;
                if (group == Integer.MAX_VALUE || (this.mBottom.mGroup == group && this.mBaseline.mGroup == group)) {
                    system.addEquality(baseline, top, getBaselineDistance(), 5);
                }
                int originalHeight = height;
                if (this.mBaseline.mTarget != null) {
                    height = this.mBaselineDistance;
                    endAnchor = this.mBaseline;
                }
                if (group == Integer.MAX_VALUE || (this.mTop.mGroup == group && endAnchor.mGroup == group)) {
                    if (!useVerticalRatio || this.mTop.mTarget == null || this.mBottom.mTarget == null) {
                        applyConstraints(system, wrapContent, verticalDimensionFixed, this.mTop, endAnchor, this.mY, this.mY + height, height, this.mMinHeight, this.mVerticalBiasPercent, useVerticalRatio, inVerticalChain, this.mMatchConstraintDefaultHeight, this.mMatchConstraintMinHeight, this.mMatchConstraintMaxHeight);
                        system.addEquality(bottom, top, originalHeight, 5);
                    } else {
                        begin = system.createObjectVariable(this.mTop);
                        end = system.createObjectVariable(this.mBottom);
                        beginTarget = system.createObjectVariable(this.mTop.getTarget());
                        endTarget = system.createObjectVariable(this.mBottom.getTarget());
                        system.addGreaterThan(begin, beginTarget, this.mTop.getMargin(), 3);
                        system.addLowerThan(end, endTarget, this.mBottom.getMargin() * UNKNOWN, 3);
                        if (!inVerticalChain) {
                            system.addCentering(begin, beginTarget, this.mTop.getMargin(), this.mVerticalBiasPercent, endTarget, end, this.mBottom.getMargin(), INVISIBLE);
                        }
                    }
                }
            } else if (group == Integer.MAX_VALUE || (this.mTop.mGroup == group && this.mBottom.mGroup == group)) {
                if (!useVerticalRatio || this.mTop.mTarget == null || this.mBottom.mTarget == null) {
                    applyConstraints(system, wrapContent, verticalDimensionFixed, this.mTop, this.mBottom, this.mY, this.mY + height, height, this.mMinHeight, this.mVerticalBiasPercent, useVerticalRatio, inVerticalChain, this.mMatchConstraintDefaultHeight, this.mMatchConstraintMinHeight, this.mMatchConstraintMaxHeight);
                } else {
                    begin = system.createObjectVariable(this.mTop);
                    end = system.createObjectVariable(this.mBottom);
                    beginTarget = system.createObjectVariable(this.mTop.getTarget());
                    endTarget = system.createObjectVariable(this.mBottom.getTarget());
                    system.addGreaterThan(begin, beginTarget, this.mTop.getMargin(), 3);
                    system.addLowerThan(end, endTarget, this.mBottom.getMargin() * UNKNOWN, 3);
                    if (!inVerticalChain) {
                        system.addCentering(begin, beginTarget, this.mTop.getMargin(), this.mVerticalBiasPercent, endTarget, end, this.mBottom.getMargin(), INVISIBLE);
                    }
                }
            }
            if (useRatio) {
                row = system.createRow();
                if (group != Integer.MAX_VALUE && (this.mLeft.mGroup != group || this.mRight.mGroup != group)) {
                    return;
                }
                if (dimensionRatioSide == 0) {
                    system.addConstraint(row.createRowDimensionRatio(right, left, bottom, top, dimensionRatio));
                } else if (dimensionRatioSide == VERTICAL) {
                    system.addConstraint(row.createRowDimensionRatio(bottom, top, right, left, dimensionRatio));
                } else {
                    if (this.mMatchConstraintMinWidth > 0) {
                        system.addGreaterThan(right, left, this.mMatchConstraintMinWidth, 3);
                    }
                    if (this.mMatchConstraintMinHeight > 0) {
                        system.addGreaterThan(bottom, top, this.mMatchConstraintMinHeight, 3);
                    }
                    row.createRowDimensionRatio(right, left, bottom, top, dimensionRatio);
                    SolverVariable error1 = system.createErrorVariable();
                    SolverVariable error2 = system.createErrorVariable();
                    error1.strength = INVISIBLE;
                    error2.strength = INVISIBLE;
                    row.addError(error1, error2);
                    system.addConstraint(row);
                }
            }
        }
    }

    private void applyConstraints(LinearSystem system, boolean wrapContent, boolean dimensionFixed, ConstraintAnchor beginAnchor, ConstraintAnchor endAnchor, int beginPosition, int endPosition, int dimension, int minDimension, float bias, boolean useRatio, boolean inChain, int matchConstraintDefault, int matchMinDimension, int matchMaxDimension) {
        SolverVariable begin = system.createObjectVariable(beginAnchor);
        SolverVariable end = system.createObjectVariable(endAnchor);
        SolverVariable beginTarget = system.createObjectVariable(beginAnchor.getTarget());
        SolverVariable endTarget = system.createObjectVariable(endAnchor.getTarget());
        int beginAnchorMargin = beginAnchor.getMargin();
        int endAnchorMargin = endAnchor.getMargin();
        if (this.mVisibility == GONE) {
            dimension = MATCH_CONSTRAINT_SPREAD;
            dimensionFixed = true;
        }
        if (beginTarget == null && endTarget == null) {
            system.addConstraint(system.createRow().createRowEquals(begin, beginPosition));
            if (!useRatio) {
                if (wrapContent) {
                    system.addConstraint(LinearSystem.createRowEquals(system, end, begin, minDimension, true));
                } else if (dimensionFixed) {
                    system.addConstraint(LinearSystem.createRowEquals(system, end, begin, dimension, AUTOTAG_CENTER));
                } else {
                    system.addConstraint(system.createRow().createRowEquals(end, endPosition));
                }
            }
        } else if (beginTarget != null && endTarget == null) {
            system.addConstraint(system.createRow().createRowEquals(begin, beginTarget, beginAnchorMargin));
            if (wrapContent) {
                system.addConstraint(LinearSystem.createRowEquals(system, end, begin, minDimension, true));
            } else if (!useRatio) {
                if (dimensionFixed) {
                    system.addConstraint(system.createRow().createRowEquals(end, begin, dimension));
                } else {
                    system.addConstraint(system.createRow().createRowEquals(end, endPosition));
                }
            }
        } else if (beginTarget == null && endTarget != null) {
            system.addConstraint(system.createRow().createRowEquals(end, endTarget, endAnchorMargin * UNKNOWN));
            if (wrapContent) {
                system.addConstraint(LinearSystem.createRowEquals(system, end, begin, minDimension, true));
            } else if (!useRatio) {
                if (dimensionFixed) {
                    system.addConstraint(system.createRow().createRowEquals(end, begin, dimension));
                } else {
                    system.addConstraint(system.createRow().createRowEquals(begin, beginPosition));
                }
            }
        } else if (dimensionFixed) {
            if (wrapContent) {
                system.addConstraint(LinearSystem.createRowEquals(system, end, begin, minDimension, true));
            } else {
                system.addConstraint(system.createRow().createRowEquals(end, begin, dimension));
            }
            if (beginAnchor.getStrength() != endAnchor.getStrength()) {
                SolverVariable slack;
                ArrayRow row;
                if (beginAnchor.getStrength() == Strength.STRONG) {
                    system.addConstraint(system.createRow().createRowEquals(begin, beginTarget, beginAnchorMargin));
                    slack = system.createSlackVariable();
                    row = system.createRow();
                    row.createRowLowerThan(end, endTarget, slack, endAnchorMargin * UNKNOWN);
                    system.addConstraint(row);
                    return;
                }
                slack = system.createSlackVariable();
                row = system.createRow();
                row.createRowGreaterThan(begin, beginTarget, slack, beginAnchorMargin);
                system.addConstraint(row);
                system.addConstraint(system.createRow().createRowEquals(end, endTarget, endAnchorMargin * UNKNOWN));
            } else if (beginTarget == endTarget) {
                system.addConstraint(LinearSystem.createRowCentering(system, begin, beginTarget, MATCH_CONSTRAINT_SPREAD, 0.5f, endTarget, end, MATCH_CONSTRAINT_SPREAD, true));
            } else if (!inChain) {
                system.addConstraint(LinearSystem.createRowGreaterThan(system, begin, beginTarget, beginAnchorMargin, beginAnchor.getConnectionType() != ConnectionType.STRICT ? true : AUTOTAG_CENTER));
                system.addConstraint(LinearSystem.createRowLowerThan(system, end, endTarget, endAnchorMargin * UNKNOWN, endAnchor.getConnectionType() != ConnectionType.STRICT ? true : AUTOTAG_CENTER));
                system.addConstraint(LinearSystem.createRowCentering(system, begin, beginTarget, beginAnchorMargin, bias, endTarget, end, endAnchorMargin, AUTOTAG_CENTER));
            }
        } else if (useRatio) {
            system.addGreaterThan(begin, beginTarget, beginAnchorMargin, 3);
            system.addLowerThan(end, endTarget, endAnchorMargin * UNKNOWN, 3);
            system.addConstraint(LinearSystem.createRowCentering(system, begin, beginTarget, beginAnchorMargin, bias, endTarget, end, endAnchorMargin, true));
        } else if (!inChain) {
            if (matchConstraintDefault == VERTICAL) {
                if (matchMinDimension > dimension) {
                    dimension = matchMinDimension;
                }
                if (matchMaxDimension > 0) {
                    if (matchMaxDimension < dimension) {
                        dimension = matchMaxDimension;
                    } else {
                        system.addLowerThan(end, begin, matchMaxDimension, 3);
                    }
                }
                system.addEquality(end, begin, dimension, 3);
                system.addGreaterThan(begin, beginTarget, beginAnchorMargin, DIRECT);
                system.addLowerThan(end, endTarget, -endAnchorMargin, DIRECT);
                system.addCentering(begin, beginTarget, beginAnchorMargin, bias, endTarget, end, endAnchorMargin, INVISIBLE);
            } else if (matchMinDimension == 0 && matchMaxDimension == 0) {
                system.addConstraint(system.createRow().createRowEquals(begin, beginTarget, beginAnchorMargin));
                system.addConstraint(system.createRow().createRowEquals(end, endTarget, endAnchorMargin * UNKNOWN));
            } else {
                if (matchMaxDimension > 0) {
                    system.addLowerThan(end, begin, matchMaxDimension, 3);
                }
                system.addGreaterThan(begin, beginTarget, beginAnchorMargin, DIRECT);
                system.addLowerThan(end, endTarget, -endAnchorMargin, DIRECT);
                system.addCentering(begin, beginTarget, beginAnchorMargin, bias, endTarget, end, endAnchorMargin, INVISIBLE);
            }
        }
    }

    public void updateFromSolver(LinearSystem system, int group) {
        if (group == ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED) {
            setFrame(system.getObjectVariableValue(this.mLeft), system.getObjectVariableValue(this.mTop), system.getObjectVariableValue(this.mRight), system.getObjectVariableValue(this.mBottom));
        } else if (group == -2) {
            setFrame(this.mSolverLeft, this.mSolverTop, this.mSolverRight, this.mSolverBottom);
        } else {
            if (this.mLeft.mGroup == group) {
                this.mSolverLeft = system.getObjectVariableValue(this.mLeft);
            }
            if (this.mTop.mGroup == group) {
                this.mSolverTop = system.getObjectVariableValue(this.mTop);
            }
            if (this.mRight.mGroup == group) {
                this.mSolverRight = system.getObjectVariableValue(this.mRight);
            }
            if (this.mBottom.mGroup == group) {
                this.mSolverBottom = system.getObjectVariableValue(this.mBottom);
            }
        }
    }

    public void updateFromSolver(LinearSystem system) {
        updateFromSolver(system, ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED);
    }
}
