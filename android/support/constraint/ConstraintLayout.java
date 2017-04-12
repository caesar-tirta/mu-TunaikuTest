package android.support.constraint;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintTableLayout;
import android.support.constraint.solver.widgets.ConstraintWidget;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import android.support.constraint.solver.widgets.ConstraintWidgetContainer;
import android.support.constraint.solver.widgets.Guideline;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import java.util.ArrayList;

public class ConstraintLayout extends ViewGroup {
    static final boolean ALLOWS_EMBEDDED = false;
    private static final boolean SIMPLE_LAYOUT = true;
    private static final String TAG = "ConstraintLayout";
    public static final String VERSION = "ConstraintLayout-1.0.0";
    SparseArray<View> mChildrenByIds;
    private ConstraintSet mConstraintSet;
    private boolean mDirtyHierarchy;
    ConstraintWidgetContainer mLayoutWidget;
    private int mMaxHeight;
    private int mMaxWidth;
    private int mMinHeight;
    private int mMinWidth;
    private int mOptimizationLevel;
    private final ArrayList<ConstraintWidget> mVariableDimensionsWidgets;

    public static class LayoutParams extends MarginLayoutParams {
        public static final int BASELINE = 5;
        public static final int BOTTOM = 4;
        public static final int CHAIN_PACKED = 2;
        public static final int CHAIN_SPREAD = 0;
        public static final int CHAIN_SPREAD_INSIDE = 1;
        public static final int END = 7;
        public static final int HORIZONTAL = 0;
        public static final int LEFT = 1;
        public static final int MATCH_CONSTRAINT = 0;
        public static final int MATCH_CONSTRAINT_SPREAD = 0;
        public static final int MATCH_CONSTRAINT_WRAP = 1;
        public static final int PARENT_ID = 0;
        public static final int RIGHT = 2;
        public static final int START = 6;
        public static final int TOP = 3;
        public static final int UNSET = -1;
        public static final int VERTICAL = 1;
        public int baselineToBaseline;
        public int bottomToBottom;
        public int bottomToTop;
        public String dimensionRatio;
        int dimensionRatioSide;
        float dimensionRatioValue;
        public int editorAbsoluteX;
        public int editorAbsoluteY;
        public int endToEnd;
        public int endToStart;
        public int goneBottomMargin;
        public int goneEndMargin;
        public int goneLeftMargin;
        public int goneRightMargin;
        public int goneStartMargin;
        public int goneTopMargin;
        public int guideBegin;
        public int guideEnd;
        public float guidePercent;
        public float horizontalBias;
        public int horizontalChainStyle;
        boolean horizontalDimensionFixed;
        public float horizontalWeight;
        boolean isGuideline;
        public int leftToLeft;
        public int leftToRight;
        public int matchConstraintDefaultHeight;
        public int matchConstraintDefaultWidth;
        public int matchConstraintMaxHeight;
        public int matchConstraintMaxWidth;
        public int matchConstraintMinHeight;
        public int matchConstraintMinWidth;
        boolean needsBaseline;
        public int orientation;
        int resolveGoneLeftMargin;
        int resolveGoneRightMargin;
        float resolvedHorizontalBias;
        int resolvedLeftToLeft;
        int resolvedLeftToRight;
        int resolvedRightToLeft;
        int resolvedRightToRight;
        public int rightToLeft;
        public int rightToRight;
        public int startToEnd;
        public int startToStart;
        public int topToBottom;
        public int topToTop;
        public float verticalBias;
        public int verticalChainStyle;
        boolean verticalDimensionFixed;
        public float verticalWeight;
        ConstraintWidget widget;

        public LayoutParams(LayoutParams source) {
            super(source);
            this.guideBegin = UNSET;
            this.guideEnd = UNSET;
            this.guidePercent = -1.0f;
            this.leftToLeft = UNSET;
            this.leftToRight = UNSET;
            this.rightToLeft = UNSET;
            this.rightToRight = UNSET;
            this.topToTop = UNSET;
            this.topToBottom = UNSET;
            this.bottomToTop = UNSET;
            this.bottomToBottom = UNSET;
            this.baselineToBaseline = UNSET;
            this.startToEnd = UNSET;
            this.startToStart = UNSET;
            this.endToStart = UNSET;
            this.endToEnd = UNSET;
            this.goneLeftMargin = UNSET;
            this.goneTopMargin = UNSET;
            this.goneRightMargin = UNSET;
            this.goneBottomMargin = UNSET;
            this.goneStartMargin = UNSET;
            this.goneEndMargin = UNSET;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = VERTICAL;
            this.horizontalWeight = 0.0f;
            this.verticalWeight = 0.0f;
            this.horizontalChainStyle = PARENT_ID;
            this.verticalChainStyle = PARENT_ID;
            this.matchConstraintDefaultWidth = PARENT_ID;
            this.matchConstraintDefaultHeight = PARENT_ID;
            this.matchConstraintMinWidth = PARENT_ID;
            this.matchConstraintMinHeight = PARENT_ID;
            this.matchConstraintMaxWidth = PARENT_ID;
            this.matchConstraintMaxHeight = PARENT_ID;
            this.editorAbsoluteX = UNSET;
            this.editorAbsoluteY = UNSET;
            this.orientation = UNSET;
            this.horizontalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
            this.verticalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
            this.needsBaseline = ConstraintLayout.ALLOWS_EMBEDDED;
            this.isGuideline = ConstraintLayout.ALLOWS_EMBEDDED;
            this.resolvedLeftToLeft = UNSET;
            this.resolvedLeftToRight = UNSET;
            this.resolvedRightToLeft = UNSET;
            this.resolvedRightToRight = UNSET;
            this.resolveGoneLeftMargin = UNSET;
            this.resolveGoneRightMargin = UNSET;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            this.guideBegin = source.guideBegin;
            this.guideEnd = source.guideEnd;
            this.guidePercent = source.guidePercent;
            this.leftToLeft = source.leftToLeft;
            this.leftToRight = source.leftToRight;
            this.rightToLeft = source.rightToLeft;
            this.rightToRight = source.rightToRight;
            this.topToTop = source.topToTop;
            this.topToBottom = source.topToBottom;
            this.bottomToTop = source.bottomToTop;
            this.bottomToBottom = source.bottomToBottom;
            this.baselineToBaseline = source.baselineToBaseline;
            this.startToEnd = source.startToEnd;
            this.startToStart = source.startToStart;
            this.endToStart = source.endToStart;
            this.endToEnd = source.endToEnd;
            this.goneLeftMargin = source.goneLeftMargin;
            this.goneTopMargin = source.goneTopMargin;
            this.goneRightMargin = source.goneRightMargin;
            this.goneBottomMargin = source.goneBottomMargin;
            this.goneStartMargin = source.goneStartMargin;
            this.goneEndMargin = source.goneEndMargin;
            this.horizontalBias = source.horizontalBias;
            this.verticalBias = source.verticalBias;
            this.dimensionRatio = source.dimensionRatio;
            this.dimensionRatioValue = source.dimensionRatioValue;
            this.dimensionRatioSide = source.dimensionRatioSide;
            this.horizontalWeight = source.horizontalWeight;
            this.verticalWeight = source.verticalWeight;
            this.horizontalChainStyle = source.horizontalChainStyle;
            this.verticalChainStyle = source.verticalChainStyle;
            this.matchConstraintDefaultWidth = source.matchConstraintDefaultWidth;
            this.matchConstraintDefaultHeight = source.matchConstraintDefaultHeight;
            this.matchConstraintMinWidth = source.matchConstraintMinWidth;
            this.matchConstraintMaxWidth = source.matchConstraintMaxWidth;
            this.matchConstraintMinHeight = source.matchConstraintMinHeight;
            this.matchConstraintMaxHeight = source.matchConstraintMaxHeight;
            this.editorAbsoluteX = source.editorAbsoluteX;
            this.editorAbsoluteY = source.editorAbsoluteY;
            this.orientation = source.orientation;
            this.horizontalDimensionFixed = source.horizontalDimensionFixed;
            this.verticalDimensionFixed = source.verticalDimensionFixed;
            this.needsBaseline = source.needsBaseline;
            this.isGuideline = source.isGuideline;
            this.resolvedLeftToLeft = source.resolvedLeftToLeft;
            this.resolvedLeftToRight = source.resolvedLeftToRight;
            this.resolvedRightToLeft = source.resolvedRightToLeft;
            this.resolvedRightToRight = source.resolvedRightToRight;
            this.resolveGoneLeftMargin = source.resolveGoneLeftMargin;
            this.resolveGoneRightMargin = source.resolveGoneRightMargin;
            this.resolvedHorizontalBias = source.resolvedHorizontalBias;
            this.widget = source.widget;
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            this.guideBegin = UNSET;
            this.guideEnd = UNSET;
            this.guidePercent = -1.0f;
            this.leftToLeft = UNSET;
            this.leftToRight = UNSET;
            this.rightToLeft = UNSET;
            this.rightToRight = UNSET;
            this.topToTop = UNSET;
            this.topToBottom = UNSET;
            this.bottomToTop = UNSET;
            this.bottomToBottom = UNSET;
            this.baselineToBaseline = UNSET;
            this.startToEnd = UNSET;
            this.startToStart = UNSET;
            this.endToStart = UNSET;
            this.endToEnd = UNSET;
            this.goneLeftMargin = UNSET;
            this.goneTopMargin = UNSET;
            this.goneRightMargin = UNSET;
            this.goneBottomMargin = UNSET;
            this.goneStartMargin = UNSET;
            this.goneEndMargin = UNSET;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = VERTICAL;
            this.horizontalWeight = 0.0f;
            this.verticalWeight = 0.0f;
            this.horizontalChainStyle = PARENT_ID;
            this.verticalChainStyle = PARENT_ID;
            this.matchConstraintDefaultWidth = PARENT_ID;
            this.matchConstraintDefaultHeight = PARENT_ID;
            this.matchConstraintMinWidth = PARENT_ID;
            this.matchConstraintMinHeight = PARENT_ID;
            this.matchConstraintMaxWidth = PARENT_ID;
            this.matchConstraintMaxHeight = PARENT_ID;
            this.editorAbsoluteX = UNSET;
            this.editorAbsoluteY = UNSET;
            this.orientation = UNSET;
            this.horizontalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
            this.verticalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
            this.needsBaseline = ConstraintLayout.ALLOWS_EMBEDDED;
            this.isGuideline = ConstraintLayout.ALLOWS_EMBEDDED;
            this.resolvedLeftToLeft = UNSET;
            this.resolvedLeftToRight = UNSET;
            this.resolvedRightToLeft = UNSET;
            this.resolvedRightToRight = UNSET;
            this.resolveGoneLeftMargin = UNSET;
            this.resolveGoneRightMargin = UNSET;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
            TypedArray a = c.obtainStyledAttributes(attrs, C0002R.styleable.ConstraintLayout_Layout);
            int N = a.getIndexCount();
            for (int i = PARENT_ID; i < N; i += VERTICAL) {
                int attr = a.getIndex(i);
                if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toLeftOf) {
                    this.leftToLeft = a.getResourceId(attr, this.leftToLeft);
                    if (this.leftToLeft == UNSET) {
                        this.leftToLeft = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintLeft_toRightOf) {
                    this.leftToRight = a.getResourceId(attr, this.leftToRight);
                    if (this.leftToRight == UNSET) {
                        this.leftToRight = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintRight_toLeftOf) {
                    this.rightToLeft = a.getResourceId(attr, this.rightToLeft);
                    if (this.rightToLeft == UNSET) {
                        this.rightToLeft = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintRight_toRightOf) {
                    this.rightToRight = a.getResourceId(attr, this.rightToRight);
                    if (this.rightToRight == UNSET) {
                        this.rightToRight = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintTop_toTopOf) {
                    this.topToTop = a.getResourceId(attr, this.topToTop);
                    if (this.topToTop == UNSET) {
                        this.topToTop = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintTop_toBottomOf) {
                    this.topToBottom = a.getResourceId(attr, this.topToBottom);
                    if (this.topToBottom == UNSET) {
                        this.topToBottom = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toTopOf) {
                    this.bottomToTop = a.getResourceId(attr, this.bottomToTop);
                    if (this.bottomToTop == UNSET) {
                        this.bottomToTop = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintBottom_toBottomOf) {
                    this.bottomToBottom = a.getResourceId(attr, this.bottomToBottom);
                    if (this.bottomToBottom == UNSET) {
                        this.bottomToBottom = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_toBaselineOf) {
                    this.baselineToBaseline = a.getResourceId(attr, this.baselineToBaseline);
                    if (this.baselineToBaseline == UNSET) {
                        this.baselineToBaseline = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_editor_absoluteX) {
                    this.editorAbsoluteX = a.getDimensionPixelOffset(attr, this.editorAbsoluteX);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_editor_absoluteY) {
                    this.editorAbsoluteY = a.getDimensionPixelOffset(attr, this.editorAbsoluteY);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintGuide_begin) {
                    this.guideBegin = a.getDimensionPixelOffset(attr, this.guideBegin);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintGuide_end) {
                    this.guideEnd = a.getDimensionPixelOffset(attr, this.guideEnd);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintGuide_percent) {
                    this.guidePercent = a.getFloat(attr, this.guidePercent);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_android_orientation) {
                    this.orientation = a.getInt(attr, this.orientation);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintStart_toEndOf) {
                    this.startToEnd = a.getResourceId(attr, this.startToEnd);
                    if (this.startToEnd == UNSET) {
                        this.startToEnd = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintStart_toStartOf) {
                    this.startToStart = a.getResourceId(attr, this.startToStart);
                    if (this.startToStart == UNSET) {
                        this.startToStart = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toStartOf) {
                    this.endToStart = a.getResourceId(attr, this.endToStart);
                    if (this.endToStart == UNSET) {
                        this.endToStart = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintEnd_toEndOf) {
                    this.endToEnd = a.getResourceId(attr, this.endToEnd);
                    if (this.endToEnd == UNSET) {
                        this.endToEnd = a.getInt(attr, UNSET);
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_goneMarginLeft) {
                    this.goneLeftMargin = a.getDimensionPixelSize(attr, this.goneLeftMargin);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_goneMarginTop) {
                    this.goneTopMargin = a.getDimensionPixelSize(attr, this.goneTopMargin);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_goneMarginRight) {
                    this.goneRightMargin = a.getDimensionPixelSize(attr, this.goneRightMargin);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_goneMarginBottom) {
                    this.goneBottomMargin = a.getDimensionPixelSize(attr, this.goneBottomMargin);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_goneMarginStart) {
                    this.goneStartMargin = a.getDimensionPixelSize(attr, this.goneStartMargin);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_goneMarginEnd) {
                    this.goneEndMargin = a.getDimensionPixelSize(attr, this.goneEndMargin);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_bias) {
                    this.horizontalBias = a.getFloat(attr, this.horizontalBias);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintVertical_bias) {
                    this.verticalBias = a.getFloat(attr, this.verticalBias);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintDimensionRatio) {
                    this.dimensionRatio = a.getString(attr);
                    this.dimensionRatioValue = Float.NaN;
                    this.dimensionRatioSide = UNSET;
                    if (this.dimensionRatio != null) {
                        int len = this.dimensionRatio.length();
                        int commaIndex = this.dimensionRatio.indexOf(44);
                        if (commaIndex <= 0 || commaIndex >= len + UNSET) {
                            commaIndex = PARENT_ID;
                        } else {
                            String dimension = this.dimensionRatio.substring(PARENT_ID, commaIndex);
                            if (dimension.equalsIgnoreCase("W")) {
                                this.dimensionRatioSide = PARENT_ID;
                            } else if (dimension.equalsIgnoreCase("H")) {
                                this.dimensionRatioSide = VERTICAL;
                            }
                            commaIndex += VERTICAL;
                        }
                        int colonIndex = this.dimensionRatio.indexOf(58);
                        if (colonIndex < 0 || colonIndex >= len + UNSET) {
                            String r = this.dimensionRatio.substring(commaIndex);
                            if (r.length() > 0) {
                                try {
                                    this.dimensionRatioValue = Float.parseFloat(r);
                                } catch (NumberFormatException e) {
                                }
                            }
                        } else {
                            String nominator = this.dimensionRatio.substring(commaIndex, colonIndex);
                            String denominator = this.dimensionRatio.substring(colonIndex + VERTICAL);
                            if (nominator.length() > 0 && denominator.length() > 0) {
                                try {
                                    float nominatorValue = Float.parseFloat(nominator);
                                    float denominatorValue = Float.parseFloat(denominator);
                                    if (nominatorValue > 0.0f && denominatorValue > 0.0f) {
                                        if (this.dimensionRatioSide == VERTICAL) {
                                            this.dimensionRatioValue = Math.abs(denominatorValue / nominatorValue);
                                        } else {
                                            this.dimensionRatioValue = Math.abs(nominatorValue / denominatorValue);
                                        }
                                    }
                                } catch (NumberFormatException e2) {
                                }
                            }
                        }
                    }
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_weight) {
                    this.horizontalWeight = a.getFloat(attr, 0.0f);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintVertical_weight) {
                    this.verticalWeight = a.getFloat(attr, 0.0f);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintHorizontal_chainStyle) {
                    this.horizontalChainStyle = a.getInt(attr, PARENT_ID);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintVertical_chainStyle) {
                    this.verticalChainStyle = a.getInt(attr, PARENT_ID);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintWidth_default) {
                    this.matchConstraintDefaultWidth = a.getInt(attr, PARENT_ID);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintHeight_default) {
                    this.matchConstraintDefaultHeight = a.getInt(attr, PARENT_ID);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintWidth_min) {
                    this.matchConstraintMinWidth = a.getDimensionPixelSize(attr, this.matchConstraintMinWidth);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintWidth_max) {
                    this.matchConstraintMaxWidth = a.getDimensionPixelSize(attr, this.matchConstraintMaxWidth);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintHeight_min) {
                    this.matchConstraintMinHeight = a.getDimensionPixelSize(attr, this.matchConstraintMinHeight);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintHeight_max) {
                    this.matchConstraintMaxHeight = a.getDimensionPixelSize(attr, this.matchConstraintMaxHeight);
                } else if (!(attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintLeft_creator || attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintTop_creator || attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintRight_creator || attr == C0002R.styleable.ConstraintLayout_Layout_layout_constraintBottom_creator || attr != C0002R.styleable.ConstraintLayout_Layout_layout_constraintBaseline_creator)) {
                }
            }
            a.recycle();
            validate();
        }

        public void validate() {
            this.isGuideline = ConstraintLayout.ALLOWS_EMBEDDED;
            this.horizontalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
            this.verticalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
            if (this.width == 0 || this.width == UNSET) {
                this.horizontalDimensionFixed = ConstraintLayout.ALLOWS_EMBEDDED;
            }
            if (this.height == 0 || this.height == UNSET) {
                this.verticalDimensionFixed = ConstraintLayout.ALLOWS_EMBEDDED;
            }
            if (this.guidePercent != -1.0f || this.guideBegin != UNSET || this.guideEnd != UNSET) {
                this.isGuideline = ConstraintLayout.SIMPLE_LAYOUT;
                this.horizontalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
                this.verticalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
                if (!(this.widget instanceof Guideline)) {
                    this.widget = new Guideline();
                }
                ((Guideline) this.widget).setOrientation(this.orientation);
            }
        }

        public LayoutParams(int width, int height) {
            super(width, height);
            this.guideBegin = UNSET;
            this.guideEnd = UNSET;
            this.guidePercent = -1.0f;
            this.leftToLeft = UNSET;
            this.leftToRight = UNSET;
            this.rightToLeft = UNSET;
            this.rightToRight = UNSET;
            this.topToTop = UNSET;
            this.topToBottom = UNSET;
            this.bottomToTop = UNSET;
            this.bottomToBottom = UNSET;
            this.baselineToBaseline = UNSET;
            this.startToEnd = UNSET;
            this.startToStart = UNSET;
            this.endToStart = UNSET;
            this.endToEnd = UNSET;
            this.goneLeftMargin = UNSET;
            this.goneTopMargin = UNSET;
            this.goneRightMargin = UNSET;
            this.goneBottomMargin = UNSET;
            this.goneStartMargin = UNSET;
            this.goneEndMargin = UNSET;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = VERTICAL;
            this.horizontalWeight = 0.0f;
            this.verticalWeight = 0.0f;
            this.horizontalChainStyle = PARENT_ID;
            this.verticalChainStyle = PARENT_ID;
            this.matchConstraintDefaultWidth = PARENT_ID;
            this.matchConstraintDefaultHeight = PARENT_ID;
            this.matchConstraintMinWidth = PARENT_ID;
            this.matchConstraintMinHeight = PARENT_ID;
            this.matchConstraintMaxWidth = PARENT_ID;
            this.matchConstraintMaxHeight = PARENT_ID;
            this.editorAbsoluteX = UNSET;
            this.editorAbsoluteY = UNSET;
            this.orientation = UNSET;
            this.horizontalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
            this.verticalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
            this.needsBaseline = ConstraintLayout.ALLOWS_EMBEDDED;
            this.isGuideline = ConstraintLayout.ALLOWS_EMBEDDED;
            this.resolvedLeftToLeft = UNSET;
            this.resolvedLeftToRight = UNSET;
            this.resolvedRightToLeft = UNSET;
            this.resolvedRightToRight = UNSET;
            this.resolveGoneLeftMargin = UNSET;
            this.resolveGoneRightMargin = UNSET;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
            this.guideBegin = UNSET;
            this.guideEnd = UNSET;
            this.guidePercent = -1.0f;
            this.leftToLeft = UNSET;
            this.leftToRight = UNSET;
            this.rightToLeft = UNSET;
            this.rightToRight = UNSET;
            this.topToTop = UNSET;
            this.topToBottom = UNSET;
            this.bottomToTop = UNSET;
            this.bottomToBottom = UNSET;
            this.baselineToBaseline = UNSET;
            this.startToEnd = UNSET;
            this.startToStart = UNSET;
            this.endToStart = UNSET;
            this.endToEnd = UNSET;
            this.goneLeftMargin = UNSET;
            this.goneTopMargin = UNSET;
            this.goneRightMargin = UNSET;
            this.goneBottomMargin = UNSET;
            this.goneStartMargin = UNSET;
            this.goneEndMargin = UNSET;
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.dimensionRatioValue = 0.0f;
            this.dimensionRatioSide = VERTICAL;
            this.horizontalWeight = 0.0f;
            this.verticalWeight = 0.0f;
            this.horizontalChainStyle = PARENT_ID;
            this.verticalChainStyle = PARENT_ID;
            this.matchConstraintDefaultWidth = PARENT_ID;
            this.matchConstraintDefaultHeight = PARENT_ID;
            this.matchConstraintMinWidth = PARENT_ID;
            this.matchConstraintMinHeight = PARENT_ID;
            this.matchConstraintMaxWidth = PARENT_ID;
            this.matchConstraintMaxHeight = PARENT_ID;
            this.editorAbsoluteX = UNSET;
            this.editorAbsoluteY = UNSET;
            this.orientation = UNSET;
            this.horizontalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
            this.verticalDimensionFixed = ConstraintLayout.SIMPLE_LAYOUT;
            this.needsBaseline = ConstraintLayout.ALLOWS_EMBEDDED;
            this.isGuideline = ConstraintLayout.ALLOWS_EMBEDDED;
            this.resolvedLeftToLeft = UNSET;
            this.resolvedLeftToRight = UNSET;
            this.resolvedRightToLeft = UNSET;
            this.resolvedRightToRight = UNSET;
            this.resolveGoneLeftMargin = UNSET;
            this.resolveGoneRightMargin = UNSET;
            this.resolvedHorizontalBias = 0.5f;
            this.widget = new ConstraintWidget();
        }

        @TargetApi(17)
        public void resolveLayoutDirection(int layoutDirection) {
            boolean isRtl = ConstraintLayout.SIMPLE_LAYOUT;
            super.resolveLayoutDirection(layoutDirection);
            this.resolvedRightToLeft = UNSET;
            this.resolvedRightToRight = UNSET;
            this.resolvedLeftToLeft = UNSET;
            this.resolvedLeftToRight = UNSET;
            this.resolveGoneLeftMargin = UNSET;
            this.resolveGoneRightMargin = UNSET;
            this.resolveGoneLeftMargin = this.goneLeftMargin;
            this.resolveGoneRightMargin = this.goneRightMargin;
            this.resolvedHorizontalBias = this.horizontalBias;
            if (VERTICAL != getLayoutDirection()) {
                isRtl = ConstraintLayout.ALLOWS_EMBEDDED;
            }
            if (isRtl) {
                if (this.startToEnd != UNSET) {
                    this.resolvedRightToLeft = this.startToEnd;
                } else if (this.startToStart != UNSET) {
                    this.resolvedRightToRight = this.startToStart;
                }
                if (this.endToStart != UNSET) {
                    this.resolvedLeftToRight = this.endToStart;
                }
                if (this.endToEnd != UNSET) {
                    this.resolvedLeftToLeft = this.endToEnd;
                }
                if (this.goneStartMargin != UNSET) {
                    this.resolveGoneRightMargin = this.goneStartMargin;
                }
                if (this.goneEndMargin != UNSET) {
                    this.resolveGoneLeftMargin = this.goneEndMargin;
                }
                this.resolvedHorizontalBias = 1.0f - this.horizontalBias;
            } else {
                if (this.startToEnd != UNSET) {
                    this.resolvedLeftToRight = this.startToEnd;
                }
                if (this.startToStart != UNSET) {
                    this.resolvedLeftToLeft = this.startToStart;
                }
                if (this.endToStart != UNSET) {
                    this.resolvedRightToLeft = this.endToStart;
                }
                if (this.endToEnd != UNSET) {
                    this.resolvedRightToRight = this.endToEnd;
                }
                if (this.goneStartMargin != UNSET) {
                    this.resolveGoneLeftMargin = this.goneStartMargin;
                }
                if (this.goneEndMargin != UNSET) {
                    this.resolveGoneRightMargin = this.goneEndMargin;
                }
            }
            if (this.endToStart == UNSET && this.endToEnd == UNSET) {
                if (this.rightToLeft != UNSET) {
                    this.resolvedRightToLeft = this.rightToLeft;
                } else if (this.rightToRight != UNSET) {
                    this.resolvedRightToRight = this.rightToRight;
                }
            }
            if (this.startToStart != UNSET || this.startToEnd != UNSET) {
                return;
            }
            if (this.leftToLeft != UNSET) {
                this.resolvedLeftToLeft = this.leftToLeft;
            } else if (this.leftToRight != UNSET) {
                this.resolvedLeftToRight = this.leftToRight;
            }
        }
    }

    public ConstraintLayout(Context context) {
        super(context);
        this.mChildrenByIds = new SparseArray();
        this.mVariableDimensionsWidgets = new ArrayList(100);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mMaxWidth = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mMaxHeight = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mDirtyHierarchy = SIMPLE_LAYOUT;
        this.mOptimizationLevel = 2;
        this.mConstraintSet = null;
        init(null);
    }

    public ConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mChildrenByIds = new SparseArray();
        this.mVariableDimensionsWidgets = new ArrayList(100);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mMaxWidth = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mMaxHeight = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mDirtyHierarchy = SIMPLE_LAYOUT;
        this.mOptimizationLevel = 2;
        this.mConstraintSet = null;
        init(attrs);
    }

    public ConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mChildrenByIds = new SparseArray();
        this.mVariableDimensionsWidgets = new ArrayList(100);
        this.mLayoutWidget = new ConstraintWidgetContainer();
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mMaxWidth = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mMaxHeight = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        this.mDirtyHierarchy = SIMPLE_LAYOUT;
        this.mOptimizationLevel = 2;
        this.mConstraintSet = null;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        this.mLayoutWidget.setCompanionWidget(this);
        this.mChildrenByIds.put(getId(), this);
        this.mConstraintSet = null;
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, C0002R.styleable.ConstraintLayout_Layout);
            int N = a.getIndexCount();
            for (int i = 0; i < N; i++) {
                int attr = a.getIndex(i);
                if (attr == C0002R.styleable.ConstraintLayout_Layout_android_minWidth) {
                    this.mMinWidth = a.getDimensionPixelOffset(attr, this.mMinWidth);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_android_minHeight) {
                    this.mMinHeight = a.getDimensionPixelOffset(attr, this.mMinHeight);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_android_maxWidth) {
                    this.mMaxWidth = a.getDimensionPixelOffset(attr, this.mMaxWidth);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_android_maxHeight) {
                    this.mMaxHeight = a.getDimensionPixelOffset(attr, this.mMaxHeight);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_layout_optimizationLevel) {
                    this.mOptimizationLevel = a.getInt(attr, this.mOptimizationLevel);
                } else if (attr == C0002R.styleable.ConstraintLayout_Layout_constraintSet) {
                    int id = a.getResourceId(attr, 0);
                    this.mConstraintSet = new ConstraintSet();
                    this.mConstraintSet.load(getContext(), id);
                }
            }
            a.recycle();
        }
        this.mLayoutWidget.setOptimizationLevel(this.mOptimizationLevel);
    }

    public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (VERSION.SDK_INT < 14) {
            onViewAdded(child);
        }
    }

    public void removeView(View view) {
        super.removeView(view);
        if (VERSION.SDK_INT < 14) {
            onViewRemoved(view);
        }
    }

    public void onViewAdded(View view) {
        if (VERSION.SDK_INT >= 14) {
            super.onViewAdded(view);
        }
        ConstraintWidget widget = getViewWidget(view);
        if ((view instanceof Guideline) && !(widget instanceof Guideline)) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.widget = new Guideline();
            layoutParams.isGuideline = SIMPLE_LAYOUT;
            ((Guideline) layoutParams.widget).setOrientation(layoutParams.orientation);
            widget = layoutParams.widget;
        }
        this.mChildrenByIds.put(view.getId(), view);
        this.mDirtyHierarchy = SIMPLE_LAYOUT;
    }

    public void onViewRemoved(View view) {
        if (VERSION.SDK_INT >= 14) {
            super.onViewRemoved(view);
        }
        this.mChildrenByIds.remove(view.getId());
        this.mLayoutWidget.remove(getViewWidget(view));
        this.mDirtyHierarchy = SIMPLE_LAYOUT;
    }

    public void setMinWidth(int value) {
        if (value != this.mMinWidth) {
            this.mMinWidth = value;
            requestLayout();
        }
    }

    public void setMinHeight(int value) {
        if (value != this.mMinHeight) {
            this.mMinHeight = value;
            requestLayout();
        }
    }

    public int getMinWidth() {
        return this.mMinWidth;
    }

    public int getMinHeight() {
        return this.mMinHeight;
    }

    public void setMaxWidth(int value) {
        if (value != this.mMaxWidth) {
            this.mMaxWidth = value;
            requestLayout();
        }
    }

    public void setMaxHeight(int value) {
        if (value != this.mMaxHeight) {
            this.mMaxHeight = value;
            requestLayout();
        }
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public int getMaxHeight() {
        return this.mMaxHeight;
    }

    private void updateHierarchy() {
        int count = getChildCount();
        boolean recompute = ALLOWS_EMBEDDED;
        for (int i = 0; i < count; i++) {
            if (getChildAt(i).isLayoutRequested()) {
                recompute = SIMPLE_LAYOUT;
                break;
            }
        }
        if (recompute) {
            this.mVariableDimensionsWidgets.clear();
            setChildrenConstraints();
        }
    }

    private void setChildrenConstraints() {
        if (this.mConstraintSet != null) {
            this.mConstraintSet.applyToInternal(this);
        }
        int count = getChildCount();
        this.mLayoutWidget.removeAllChildren();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            ConstraintWidget widget = getViewWidget(child);
            if (widget != null) {
                LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
                widget.reset();
                widget.setVisibility(child.getVisibility());
                widget.setCompanionWidget(child);
                this.mLayoutWidget.add(widget);
                if (!(layoutParams.verticalDimensionFixed && layoutParams.horizontalDimensionFixed)) {
                    this.mVariableDimensionsWidgets.add(widget);
                }
                if (layoutParams.isGuideline) {
                    Guideline guideline = (Guideline) widget;
                    if (layoutParams.guideBegin != -1) {
                        guideline.setGuideBegin(layoutParams.guideBegin);
                    }
                    if (layoutParams.guideEnd != -1) {
                        guideline.setGuideEnd(layoutParams.guideEnd);
                    }
                    if (layoutParams.guidePercent != -1.0f) {
                        guideline.setGuidePercent(layoutParams.guidePercent);
                    }
                } else if (layoutParams.resolvedLeftToLeft != -1 || layoutParams.resolvedLeftToRight != -1 || layoutParams.resolvedRightToLeft != -1 || layoutParams.resolvedRightToRight != -1 || layoutParams.topToTop != -1 || layoutParams.topToBottom != -1 || layoutParams.bottomToTop != -1 || layoutParams.bottomToBottom != -1 || layoutParams.baselineToBaseline != -1 || layoutParams.editorAbsoluteX != -1 || layoutParams.editorAbsoluteY != -1 || layoutParams.width == -1 || layoutParams.height == -1) {
                    ConstraintWidget target;
                    ConstraintWidget constraintWidget;
                    ConstraintWidget constraintWidget2;
                    int resolvedLeftToLeft = layoutParams.resolvedLeftToLeft;
                    int resolvedLeftToRight = layoutParams.resolvedLeftToRight;
                    int resolvedRightToLeft = layoutParams.resolvedRightToLeft;
                    int resolvedRightToRight = layoutParams.resolvedRightToRight;
                    int resolveGoneLeftMargin = layoutParams.resolveGoneLeftMargin;
                    int resolveGoneRightMargin = layoutParams.resolveGoneRightMargin;
                    float resolvedHorizontalBias = layoutParams.resolvedHorizontalBias;
                    if (VERSION.SDK_INT < 17) {
                        resolvedLeftToLeft = layoutParams.leftToLeft;
                        resolvedLeftToRight = layoutParams.leftToRight;
                        resolvedRightToLeft = layoutParams.rightToLeft;
                        resolvedRightToRight = layoutParams.rightToRight;
                        resolveGoneLeftMargin = layoutParams.goneLeftMargin;
                        resolveGoneRightMargin = layoutParams.goneRightMargin;
                        resolvedHorizontalBias = layoutParams.horizontalBias;
                        if (resolvedLeftToLeft == -1 && resolvedLeftToRight == -1) {
                            if (layoutParams.startToStart != -1) {
                                resolvedLeftToLeft = layoutParams.startToStart;
                            } else if (layoutParams.startToEnd != -1) {
                                resolvedLeftToRight = layoutParams.startToEnd;
                            }
                        }
                        if (resolvedRightToLeft == -1 && resolvedRightToRight == -1) {
                            if (layoutParams.endToStart != -1) {
                                resolvedRightToLeft = layoutParams.endToStart;
                            } else if (layoutParams.endToEnd != -1) {
                                resolvedRightToRight = layoutParams.endToEnd;
                            }
                        }
                    }
                    if (resolvedLeftToLeft != -1) {
                        target = getTargetWidget(resolvedLeftToLeft);
                        if (target != null) {
                            widget.immediateConnect(Type.LEFT, target, Type.LEFT, layoutParams.leftMargin, resolveGoneLeftMargin);
                        }
                    } else if (resolvedLeftToRight != -1) {
                        target = getTargetWidget(resolvedLeftToRight);
                        if (target != null) {
                            widget.immediateConnect(Type.LEFT, target, Type.RIGHT, layoutParams.leftMargin, resolveGoneLeftMargin);
                        }
                    }
                    if (resolvedRightToLeft != -1) {
                        target = getTargetWidget(resolvedRightToLeft);
                        if (target != null) {
                            widget.immediateConnect(Type.RIGHT, target, Type.LEFT, layoutParams.rightMargin, resolveGoneRightMargin);
                        }
                    } else if (resolvedRightToRight != -1) {
                        target = getTargetWidget(resolvedRightToRight);
                        if (target != null) {
                            widget.immediateConnect(Type.RIGHT, target, Type.RIGHT, layoutParams.rightMargin, resolveGoneRightMargin);
                        }
                    }
                    if (layoutParams.topToTop != -1) {
                        target = getTargetWidget(layoutParams.topToTop);
                        if (target != null) {
                            constraintWidget = widget;
                            constraintWidget2 = target;
                            constraintWidget.immediateConnect(Type.TOP, constraintWidget2, Type.TOP, layoutParams.topMargin, layoutParams.goneTopMargin);
                        }
                    } else if (layoutParams.topToBottom != -1) {
                        target = getTargetWidget(layoutParams.topToBottom);
                        if (target != null) {
                            constraintWidget = widget;
                            constraintWidget2 = target;
                            constraintWidget.immediateConnect(Type.TOP, constraintWidget2, Type.BOTTOM, layoutParams.topMargin, layoutParams.goneTopMargin);
                        }
                    }
                    if (layoutParams.bottomToTop != -1) {
                        target = getTargetWidget(layoutParams.bottomToTop);
                        if (target != null) {
                            constraintWidget = widget;
                            constraintWidget2 = target;
                            constraintWidget.immediateConnect(Type.BOTTOM, constraintWidget2, Type.TOP, layoutParams.bottomMargin, layoutParams.goneBottomMargin);
                        }
                    } else if (layoutParams.bottomToBottom != -1) {
                        target = getTargetWidget(layoutParams.bottomToBottom);
                        if (target != null) {
                            constraintWidget = widget;
                            constraintWidget2 = target;
                            constraintWidget.immediateConnect(Type.BOTTOM, constraintWidget2, Type.BOTTOM, layoutParams.bottomMargin, layoutParams.goneBottomMargin);
                        }
                    }
                    if (layoutParams.baselineToBaseline != -1) {
                        View view = (View) this.mChildrenByIds.get(layoutParams.baselineToBaseline);
                        target = getTargetWidget(layoutParams.baselineToBaseline);
                        if (!(target == null || view == null || !(view.getLayoutParams() instanceof LayoutParams))) {
                            LayoutParams targetParams = (LayoutParams) view.getLayoutParams();
                            layoutParams.needsBaseline = SIMPLE_LAYOUT;
                            targetParams.needsBaseline = SIMPLE_LAYOUT;
                            widget.getAnchor(Type.BASELINE).connect(target.getAnchor(Type.BASELINE), 0, -1, Strength.STRONG, 0, SIMPLE_LAYOUT);
                            widget.getAnchor(Type.TOP).reset();
                            widget.getAnchor(Type.BOTTOM).reset();
                        }
                    }
                    if (resolvedHorizontalBias >= 0.0f && resolvedHorizontalBias != 0.5f) {
                        widget.setHorizontalBiasPercent(resolvedHorizontalBias);
                    }
                    if (layoutParams.verticalBias >= 0.0f && layoutParams.verticalBias != 0.5f) {
                        widget.setVerticalBiasPercent(layoutParams.verticalBias);
                    }
                    if (isInEditMode() && !(layoutParams.editorAbsoluteX == -1 && layoutParams.editorAbsoluteY == -1)) {
                        widget.setOrigin(layoutParams.editorAbsoluteX, layoutParams.editorAbsoluteY);
                    }
                    if (layoutParams.horizontalDimensionFixed) {
                        widget.setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                        widget.setWidth(layoutParams.width);
                    } else if (layoutParams.width == -1) {
                        widget.setHorizontalDimensionBehaviour(DimensionBehaviour.MATCH_PARENT);
                        widget.getAnchor(Type.LEFT).mMargin = layoutParams.leftMargin;
                        widget.getAnchor(Type.RIGHT).mMargin = layoutParams.rightMargin;
                    } else {
                        widget.setHorizontalDimensionBehaviour(DimensionBehaviour.MATCH_CONSTRAINT);
                        widget.setWidth(0);
                    }
                    if (layoutParams.verticalDimensionFixed) {
                        widget.setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
                        widget.setHeight(layoutParams.height);
                    } else if (layoutParams.height == -1) {
                        widget.setVerticalDimensionBehaviour(DimensionBehaviour.MATCH_PARENT);
                        widget.getAnchor(Type.TOP).mMargin = layoutParams.topMargin;
                        widget.getAnchor(Type.BOTTOM).mMargin = layoutParams.bottomMargin;
                    } else {
                        widget.setVerticalDimensionBehaviour(DimensionBehaviour.MATCH_CONSTRAINT);
                        widget.setHeight(0);
                    }
                    if (layoutParams.dimensionRatio != null) {
                        widget.setDimensionRatio(layoutParams.dimensionRatio);
                    }
                    widget.setHorizontalWeight(layoutParams.horizontalWeight);
                    widget.setVerticalWeight(layoutParams.verticalWeight);
                    widget.setHorizontalChainStyle(layoutParams.horizontalChainStyle);
                    widget.setVerticalChainStyle(layoutParams.verticalChainStyle);
                    widget.setHorizontalMatchStyle(layoutParams.matchConstraintDefaultWidth, layoutParams.matchConstraintMinWidth, layoutParams.matchConstraintMaxWidth);
                    widget.setVerticalMatchStyle(layoutParams.matchConstraintDefaultHeight, layoutParams.matchConstraintMinHeight, layoutParams.matchConstraintMaxHeight);
                }
            }
        }
    }

    private final ConstraintWidget getTargetWidget(int id) {
        if (id == 0) {
            return this.mLayoutWidget;
        }
        View view = (View) this.mChildrenByIds.get(id);
        if (view == this) {
            return this.mLayoutWidget;
        }
        return view == null ? null : ((LayoutParams) view.getLayoutParams()).widget;
    }

    private final ConstraintWidget getViewWidget(View view) {
        if (view == this) {
            return this.mLayoutWidget;
        }
        return view == null ? null : ((LayoutParams) view.getLayoutParams()).widget;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void internalMeasureChildren(int r21, int r22) {
        /*
        r20 = this;
        r18 = r20.getPaddingTop();
        r19 = r20.getPaddingBottom();
        r11 = r18 + r19;
        r18 = r20.getPaddingLeft();
        r19 = r20.getPaddingRight();
        r17 = r18 + r19;
        r15 = r20.getChildCount();
        r12 = 0;
    L_0x0019:
        if (r12 >= r15) goto L_0x0103;
    L_0x001b:
        r0 = r20;
        r4 = r0.getChildAt(r12);
        r18 = r4.getVisibility();
        r19 = 8;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x0030;
    L_0x002d:
        r12 = r12 + 1;
        goto L_0x0019;
    L_0x0030:
        r13 = r4.getLayoutParams();
        r13 = (android.support.constraint.ConstraintLayout.LayoutParams) r13;
        r14 = r13.widget;
        r0 = r13.isGuideline;
        r18 = r0;
        if (r18 != 0) goto L_0x002d;
    L_0x003e:
        r0 = r13.width;
        r16 = r0;
        r10 = r13.height;
        r0 = r13.horizontalDimensionFixed;
        r18 = r0;
        if (r18 != 0) goto L_0x008c;
    L_0x004a:
        r0 = r13.verticalDimensionFixed;
        r18 = r0;
        if (r18 != 0) goto L_0x008c;
    L_0x0050:
        r0 = r13.horizontalDimensionFixed;
        r18 = r0;
        if (r18 != 0) goto L_0x0062;
    L_0x0056:
        r0 = r13.matchConstraintDefaultWidth;
        r18 = r0;
        r19 = 1;
        r0 = r18;
        r1 = r19;
        if (r0 == r1) goto L_0x008c;
    L_0x0062:
        r0 = r13.width;
        r18 = r0;
        r19 = -1;
        r0 = r18;
        r1 = r19;
        if (r0 == r1) goto L_0x008c;
    L_0x006e:
        r0 = r13.verticalDimensionFixed;
        r18 = r0;
        if (r18 != 0) goto L_0x00ef;
    L_0x0074:
        r0 = r13.matchConstraintDefaultHeight;
        r18 = r0;
        r19 = 1;
        r0 = r18;
        r1 = r19;
        if (r0 == r1) goto L_0x008c;
    L_0x0080:
        r0 = r13.height;
        r18 = r0;
        r19 = -1;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x00ef;
    L_0x008c:
        r9 = 1;
    L_0x008d:
        r8 = 0;
        r7 = 0;
        if (r9 == 0) goto L_0x00c6;
    L_0x0091:
        if (r16 == 0) goto L_0x009b;
    L_0x0093:
        r18 = -1;
        r0 = r16;
        r1 = r18;
        if (r0 != r1) goto L_0x00f1;
    L_0x009b:
        r18 = -2;
        r0 = r21;
        r1 = r17;
        r2 = r18;
        r6 = getChildMeasureSpec(r0, r1, r2);
        r8 = 1;
    L_0x00a8:
        if (r10 == 0) goto L_0x00b0;
    L_0x00aa:
        r18 = -1;
        r0 = r18;
        if (r10 != r0) goto L_0x00fc;
    L_0x00b0:
        r18 = -2;
        r0 = r22;
        r1 = r18;
        r5 = getChildMeasureSpec(r0, r11, r1);
        r7 = 1;
    L_0x00bb:
        r4.measure(r6, r5);
        r16 = r4.getMeasuredWidth();
        r10 = r4.getMeasuredHeight();
    L_0x00c6:
        r0 = r16;
        r14.setWidth(r0);
        r14.setHeight(r10);
        if (r8 == 0) goto L_0x00d5;
    L_0x00d0:
        r0 = r16;
        r14.setWrapWidth(r0);
    L_0x00d5:
        if (r7 == 0) goto L_0x00da;
    L_0x00d7:
        r14.setWrapHeight(r10);
    L_0x00da:
        r0 = r13.needsBaseline;
        r18 = r0;
        if (r18 == 0) goto L_0x002d;
    L_0x00e0:
        r3 = r4.getBaseline();
        r18 = -1;
        r0 = r18;
        if (r3 == r0) goto L_0x002d;
    L_0x00ea:
        r14.setBaselineDistance(r3);
        goto L_0x002d;
    L_0x00ef:
        r9 = 0;
        goto L_0x008d;
    L_0x00f1:
        r0 = r21;
        r1 = r17;
        r2 = r16;
        r6 = getChildMeasureSpec(r0, r1, r2);
        goto L_0x00a8;
    L_0x00fc:
        r0 = r22;
        r5 = getChildMeasureSpec(r0, r11, r10);
        goto L_0x00bb;
    L_0x0103:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.ConstraintLayout.internalMeasureChildren(int, int):void");
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        this.mLayoutWidget.setX(paddingLeft);
        this.mLayoutWidget.setY(paddingTop);
        setSelfDimensionBehaviour(widthMeasureSpec, heightMeasureSpec);
        if (this.mDirtyHierarchy) {
            this.mDirtyHierarchy = ALLOWS_EMBEDDED;
            updateHierarchy();
        }
        internalMeasureChildren(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 0) {
            solveLinearSystem();
        }
        int childState = 0;
        int sizeDependentWidgetsCount = this.mVariableDimensionsWidgets.size();
        int heightPadding = paddingTop + getPaddingBottom();
        int widthPadding = paddingLeft + getPaddingRight();
        if (sizeDependentWidgetsCount > 0) {
            boolean needSolverPass = ALLOWS_EMBEDDED;
            boolean containerWrapWidth = this.mLayoutWidget.getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT ? SIMPLE_LAYOUT : ALLOWS_EMBEDDED;
            boolean containerWrapHeight = this.mLayoutWidget.getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT ? SIMPLE_LAYOUT : ALLOWS_EMBEDDED;
            for (int i = 0; i < sizeDependentWidgetsCount; i++) {
                ConstraintWidget widget = (ConstraintWidget) this.mVariableDimensionsWidgets.get(i);
                if (!(widget instanceof Guideline)) {
                    View child = (View) widget.getCompanionWidget();
                    if (!(child == null || child.getVisibility() == 8)) {
                        int widthSpec;
                        int heightSpec;
                        LayoutParams params = (LayoutParams) child.getLayoutParams();
                        int i2 = params.width;
                        if (r0 == -2) {
                            widthSpec = getChildMeasureSpec(widthMeasureSpec, widthPadding, params.width);
                        } else {
                            widthSpec = MeasureSpec.makeMeasureSpec(widget.getWidth(), 1073741824);
                        }
                        i2 = params.height;
                        if (r0 == -2) {
                            heightSpec = getChildMeasureSpec(heightMeasureSpec, heightPadding, params.height);
                        } else {
                            heightSpec = MeasureSpec.makeMeasureSpec(widget.getHeight(), 1073741824);
                        }
                        child.measure(widthSpec, heightSpec);
                        int measuredWidth = child.getMeasuredWidth();
                        int measuredHeight = child.getMeasuredHeight();
                        if (measuredWidth != widget.getWidth()) {
                            widget.setWidth(measuredWidth);
                            if (containerWrapWidth) {
                                if (widget.getRight() > this.mLayoutWidget.getWidth()) {
                                    this.mLayoutWidget.setWidth(Math.max(this.mMinWidth, widget.getRight() + widget.getAnchor(Type.RIGHT).getMargin()));
                                }
                            }
                            needSolverPass = SIMPLE_LAYOUT;
                        }
                        if (measuredHeight != widget.getHeight()) {
                            widget.setHeight(measuredHeight);
                            if (containerWrapHeight) {
                                if (widget.getBottom() > this.mLayoutWidget.getHeight()) {
                                    this.mLayoutWidget.setHeight(Math.max(this.mMinHeight, widget.getBottom() + widget.getAnchor(Type.BOTTOM).getMargin()));
                                }
                            }
                            needSolverPass = SIMPLE_LAYOUT;
                        }
                        if (params.needsBaseline) {
                            int baseline = child.getBaseline();
                            if (!(baseline == -1 || baseline == widget.getBaselineDistance())) {
                                widget.setBaselineDistance(baseline);
                                needSolverPass = SIMPLE_LAYOUT;
                            }
                        }
                        if (VERSION.SDK_INT >= 11) {
                            childState = combineMeasuredStates(childState, child.getMeasuredState());
                        }
                    }
                }
            }
            if (needSolverPass) {
                solveLinearSystem();
            }
        }
        int androidLayoutWidth = this.mLayoutWidget.getWidth() + widthPadding;
        int androidLayoutHeight = this.mLayoutWidget.getHeight() + heightPadding;
        if (VERSION.SDK_INT >= 11) {
            int resolvedWidthSize = resolveSizeAndState(androidLayoutWidth, widthMeasureSpec, childState);
            resolvedWidthSize = Math.min(this.mMaxWidth, resolvedWidthSize) & ViewCompat.MEASURED_SIZE_MASK;
            int resolvedHeightSize = Math.min(this.mMaxHeight, resolveSizeAndState(androidLayoutHeight, heightMeasureSpec, childState << 16)) & ViewCompat.MEASURED_SIZE_MASK;
            if (this.mLayoutWidget.isWidthMeasuredTooSmall()) {
                resolvedWidthSize |= AccessibilityEventCompat.TYPE_ASSIST_READING_CONTEXT;
            }
            if (this.mLayoutWidget.isHeightMeasuredTooSmall()) {
                resolvedHeightSize |= AccessibilityEventCompat.TYPE_ASSIST_READING_CONTEXT;
            }
            setMeasuredDimension(resolvedWidthSize, resolvedHeightSize);
            return;
        }
        setMeasuredDimension(androidLayoutWidth, androidLayoutHeight);
    }

    private void setSelfDimensionBehaviour(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightPadding = getPaddingTop() + getPaddingBottom();
        int widthPadding = getPaddingLeft() + getPaddingRight();
        DimensionBehaviour widthBehaviour = DimensionBehaviour.FIXED;
        DimensionBehaviour heightBehaviour = DimensionBehaviour.FIXED;
        int desiredWidth = 0;
        int desiredHeight = 0;
        android.view.ViewGroup.LayoutParams params = getLayoutParams();
        switch (widthMode) {
            case LinearLayoutManager.INVALID_OFFSET /*-2147483648*/:
                widthBehaviour = DimensionBehaviour.WRAP_CONTENT;
                desiredWidth = widthSize;
                break;
            case ConstraintTableLayout.ALIGN_CENTER /*0*/:
                widthBehaviour = DimensionBehaviour.WRAP_CONTENT;
                break;
            case 1073741824:
                desiredWidth = Math.min(this.mMaxWidth, widthSize) - widthPadding;
                break;
        }
        switch (heightMode) {
            case LinearLayoutManager.INVALID_OFFSET /*-2147483648*/:
                heightBehaviour = DimensionBehaviour.WRAP_CONTENT;
                desiredHeight = heightSize;
                break;
            case ConstraintTableLayout.ALIGN_CENTER /*0*/:
                heightBehaviour = DimensionBehaviour.WRAP_CONTENT;
                break;
            case 1073741824:
                desiredHeight = Math.min(this.mMaxHeight, heightSize) - heightPadding;
                break;
        }
        this.mLayoutWidget.setMinWidth(0);
        this.mLayoutWidget.setMinHeight(0);
        this.mLayoutWidget.setHorizontalDimensionBehaviour(widthBehaviour);
        this.mLayoutWidget.setWidth(desiredWidth);
        this.mLayoutWidget.setVerticalDimensionBehaviour(heightBehaviour);
        this.mLayoutWidget.setHeight(desiredHeight);
        this.mLayoutWidget.setMinWidth((this.mMinWidth - getPaddingLeft()) - getPaddingRight());
        this.mLayoutWidget.setMinHeight((this.mMinHeight - getPaddingTop()) - getPaddingBottom());
    }

    protected void solveLinearSystem() {
        this.mLayoutWidget.layout();
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int widgetsCount = getChildCount();
        boolean isInEditMode = isInEditMode();
        for (int i = 0; i < widgetsCount; i++) {
            View child = getChildAt(i);
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            if (child.getVisibility() != 8 || params.isGuideline || isInEditMode) {
                ConstraintWidget widget = params.widget;
                int l = widget.getDrawX();
                int t = widget.getDrawY();
                child.layout(l, t, l + widget.getWidth(), t + widget.getHeight());
            }
        }
    }

    public void setOptimizationLevel(int level) {
        this.mLayoutWidget.setOptimizationLevel(level);
    }

    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    protected android.view.ViewGroup.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    public void setConstraintSet(ConstraintSet set) {
        this.mConstraintSet = set;
    }

    public void requestLayout() {
        super.requestLayout();
        this.mDirtyHierarchy = SIMPLE_LAYOUT;
    }
}
