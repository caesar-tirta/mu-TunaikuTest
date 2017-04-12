package android.support.constraint.solver.widgets;

import android.support.constraint.solver.ArrayRow;
import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.SolverVariable;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import android.support.v4.app.NotificationCompat.WearableExtender;
import java.util.ArrayList;
import java.util.Arrays;

public class ConstraintWidgetContainer extends WidgetContainer {
    static boolean ALLOW_ROOT_GROUP = false;
    private static final int CHAIN_FIRST = 0;
    private static final int CHAIN_FIRST_VISIBLE = 2;
    private static final int CHAIN_LAST = 1;
    private static final int CHAIN_LAST_VISIBLE = 3;
    private static final boolean DEBUG = false;
    private static final int FLAG_CHAIN_DANGLING = 1;
    private static final int FLAG_CHAIN_OPTIMIZE = 0;
    private static final int FLAG_RECOMPUTE_BOUNDS = 2;
    private static final int MAX_ITERATIONS = 8;
    public static final int OPTIMIZATION_ALL = 2;
    public static final int OPTIMIZATION_BASIC = 4;
    public static final int OPTIMIZATION_CHAIN = 8;
    public static final int OPTIMIZATION_NONE = 1;
    private static final boolean USE_SNAPSHOT = true;
    private static final boolean USE_THREAD = false;
    private boolean[] flags;
    protected LinearSystem mBackgroundSystem;
    private ConstraintWidget[] mChainEnds;
    private boolean mHeightMeasuredTooSmall;
    private ConstraintWidget[] mHorizontalChainsArray;
    private int mHorizontalChainsSize;
    private ConstraintWidget[] mMatchConstraintsChainedWidgets;
    private int mOptimizationLevel;
    int mPaddingBottom;
    int mPaddingLeft;
    int mPaddingRight;
    int mPaddingTop;
    private Snapshot mSnapshot;
    protected LinearSystem mSystem;
    private ConstraintWidget[] mVerticalChainsArray;
    private int mVerticalChainsSize;
    private boolean mWidthMeasuredTooSmall;
    int mWrapHeight;
    int mWrapWidth;

    /* renamed from: android.support.constraint.solver.widgets.ConstraintWidgetContainer.2 */
    static /* synthetic */ class C00062 {
        static final /* synthetic */ int[] f2x1d400623;

        static {
            f2x1d400623 = new int[Type.values().length];
            try {
                f2x1d400623[Type.LEFT.ordinal()] = ConstraintWidgetContainer.OPTIMIZATION_NONE;
            } catch (NoSuchFieldError e) {
            }
            try {
                f2x1d400623[Type.TOP.ordinal()] = ConstraintWidgetContainer.OPTIMIZATION_ALL;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f2x1d400623[Type.RIGHT.ordinal()] = ConstraintWidgetContainer.CHAIN_LAST_VISIBLE;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f2x1d400623[Type.BOTTOM.ordinal()] = ConstraintWidgetContainer.OPTIMIZATION_BASIC;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f2x1d400623[Type.BASELINE.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    static {
        ALLOW_ROOT_GROUP = USE_SNAPSHOT;
    }

    public ConstraintWidgetContainer() {
        this.mSystem = new LinearSystem();
        this.mBackgroundSystem = null;
        this.mHorizontalChainsSize = FLAG_CHAIN_OPTIMIZE;
        this.mVerticalChainsSize = FLAG_CHAIN_OPTIMIZE;
        this.mMatchConstraintsChainedWidgets = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mVerticalChainsArray = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mHorizontalChainsArray = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mOptimizationLevel = OPTIMIZATION_ALL;
        this.flags = new boolean[CHAIN_LAST_VISIBLE];
        this.mChainEnds = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mWidthMeasuredTooSmall = DEBUG;
        this.mHeightMeasuredTooSmall = DEBUG;
    }

    public ConstraintWidgetContainer(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.mSystem = new LinearSystem();
        this.mBackgroundSystem = null;
        this.mHorizontalChainsSize = FLAG_CHAIN_OPTIMIZE;
        this.mVerticalChainsSize = FLAG_CHAIN_OPTIMIZE;
        this.mMatchConstraintsChainedWidgets = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mVerticalChainsArray = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mHorizontalChainsArray = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mOptimizationLevel = OPTIMIZATION_ALL;
        this.flags = new boolean[CHAIN_LAST_VISIBLE];
        this.mChainEnds = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mWidthMeasuredTooSmall = DEBUG;
        this.mHeightMeasuredTooSmall = DEBUG;
    }

    public ConstraintWidgetContainer(int width, int height) {
        super(width, height);
        this.mSystem = new LinearSystem();
        this.mBackgroundSystem = null;
        this.mHorizontalChainsSize = FLAG_CHAIN_OPTIMIZE;
        this.mVerticalChainsSize = FLAG_CHAIN_OPTIMIZE;
        this.mMatchConstraintsChainedWidgets = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mVerticalChainsArray = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mHorizontalChainsArray = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mOptimizationLevel = OPTIMIZATION_ALL;
        this.flags = new boolean[CHAIN_LAST_VISIBLE];
        this.mChainEnds = new ConstraintWidget[OPTIMIZATION_BASIC];
        this.mWidthMeasuredTooSmall = DEBUG;
        this.mHeightMeasuredTooSmall = DEBUG;
    }

    public void setOptimizationLevel(int value) {
        this.mOptimizationLevel = value;
    }

    public String getType() {
        return "ConstraintLayout";
    }

    public void reset() {
        this.mSystem.reset();
        this.mPaddingLeft = FLAG_CHAIN_OPTIMIZE;
        this.mPaddingRight = FLAG_CHAIN_OPTIMIZE;
        this.mPaddingTop = FLAG_CHAIN_OPTIMIZE;
        this.mPaddingBottom = FLAG_CHAIN_OPTIMIZE;
        super.reset();
    }

    public boolean isWidthMeasuredTooSmall() {
        return this.mWidthMeasuredTooSmall;
    }

    public boolean isHeightMeasuredTooSmall() {
        return this.mHeightMeasuredTooSmall;
    }

    public static ConstraintWidgetContainer createContainer(ConstraintWidgetContainer container, String name, ArrayList<ConstraintWidget> widgets, int padding) {
        Rectangle bounds = WidgetContainer.getBounds(widgets);
        if (bounds.width == 0 || bounds.height == 0) {
            return null;
        }
        if (padding > 0) {
            int maxPadding = Math.min(bounds.f4x, bounds.f5y);
            if (padding > maxPadding) {
                padding = maxPadding;
            }
            bounds.grow(padding, padding);
        }
        container.setOrigin(bounds.f4x, bounds.f5y);
        container.setDimension(bounds.width, bounds.height);
        container.setDebugName(name);
        ConstraintWidget parent = ((ConstraintWidget) widgets.get(FLAG_CHAIN_OPTIMIZE)).getParent();
        int widgetsSize = widgets.size();
        for (int i = FLAG_CHAIN_OPTIMIZE; i < widgetsSize; i += OPTIMIZATION_NONE) {
            ConstraintWidget widget = (ConstraintWidget) widgets.get(i);
            if (widget.getParent() == parent) {
                container.add(widget);
                widget.setX(widget.getX() - bounds.f4x);
                widget.setY(widget.getY() - bounds.f5y);
            }
        }
        return container;
    }

    public boolean addChildrenToSolver(LinearSystem system, int group) {
        addToSolver(system, group);
        int count = this.mChildren.size();
        boolean setMatchParent = DEBUG;
        if (this.mOptimizationLevel != OPTIMIZATION_ALL && this.mOptimizationLevel != OPTIMIZATION_BASIC) {
            setMatchParent = USE_SNAPSHOT;
        } else if (optimize(system)) {
            return DEBUG;
        }
        for (int i = FLAG_CHAIN_OPTIMIZE; i < count; i += OPTIMIZATION_NONE) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof ConstraintWidgetContainer) {
                DimensionBehaviour horizontalBehaviour = widget.mHorizontalDimensionBehaviour;
                DimensionBehaviour verticalBehaviour = widget.mVerticalDimensionBehaviour;
                if (horizontalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setHorizontalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
                if (verticalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setVerticalDimensionBehaviour(DimensionBehaviour.FIXED);
                }
                widget.addToSolver(system, group);
                if (horizontalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setHorizontalDimensionBehaviour(horizontalBehaviour);
                }
                if (verticalBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                    widget.setVerticalDimensionBehaviour(verticalBehaviour);
                }
            } else {
                if (setMatchParent) {
                    Optimizer.checkMatchParent(this, system, widget);
                }
                widget.addToSolver(system, group);
            }
        }
        if (this.mHorizontalChainsSize > 0) {
            applyHorizontalChain(system);
        }
        if (this.mVerticalChainsSize > 0) {
            applyVerticalChain(system);
        }
        return USE_SNAPSHOT;
    }

    private boolean optimize(LinearSystem system) {
        int i;
        int count = this.mChildren.size();
        boolean done = DEBUG;
        int dv = FLAG_CHAIN_OPTIMIZE;
        int dh = FLAG_CHAIN_OPTIMIZE;
        int n = FLAG_CHAIN_OPTIMIZE;
        for (i = FLAG_CHAIN_OPTIMIZE; i < count; i += OPTIMIZATION_NONE) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            widget.mHorizontalResolution = -1;
            widget.mVerticalResolution = -1;
            if (widget.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT || widget.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                widget.mHorizontalResolution = OPTIMIZATION_NONE;
                widget.mVerticalResolution = OPTIMIZATION_NONE;
            }
        }
        while (!done) {
            int prev = dv;
            int preh = dh;
            dv = FLAG_CHAIN_OPTIMIZE;
            dh = FLAG_CHAIN_OPTIMIZE;
            n += OPTIMIZATION_NONE;
            for (i = FLAG_CHAIN_OPTIMIZE; i < count; i += OPTIMIZATION_NONE) {
                widget = (ConstraintWidget) this.mChildren.get(i);
                if (widget.mHorizontalResolution == -1) {
                    if (this.mHorizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                        widget.mHorizontalResolution = OPTIMIZATION_NONE;
                    } else {
                        Optimizer.checkHorizontalSimpleDependency(this, system, widget);
                    }
                }
                if (widget.mVerticalResolution == -1) {
                    if (this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT) {
                        widget.mVerticalResolution = OPTIMIZATION_NONE;
                    } else {
                        Optimizer.checkVerticalSimpleDependency(this, system, widget);
                    }
                }
                if (widget.mVerticalResolution == -1) {
                    dv += OPTIMIZATION_NONE;
                }
                if (widget.mHorizontalResolution == -1) {
                    dh += OPTIMIZATION_NONE;
                }
            }
            if (dv == 0 && dh == 0) {
                done = USE_SNAPSHOT;
            } else if (prev == dv && preh == dh) {
                done = USE_SNAPSHOT;
            }
        }
        int sh = FLAG_CHAIN_OPTIMIZE;
        int sv = FLAG_CHAIN_OPTIMIZE;
        for (i = FLAG_CHAIN_OPTIMIZE; i < count; i += OPTIMIZATION_NONE) {
            widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget.mHorizontalResolution == OPTIMIZATION_NONE || widget.mHorizontalResolution == -1) {
                sh += OPTIMIZATION_NONE;
            }
            if (widget.mVerticalResolution == OPTIMIZATION_NONE || widget.mVerticalResolution == -1) {
                sv += OPTIMIZATION_NONE;
            }
        }
        if (sh == 0 && sv == 0) {
            return USE_SNAPSHOT;
        }
        return DEBUG;
    }

    private void applyHorizontalChain(LinearSystem system) {
        for (int i = FLAG_CHAIN_OPTIMIZE; i < this.mHorizontalChainsSize; i += OPTIMIZATION_NONE) {
            ConstraintWidget first = this.mHorizontalChainsArray[i];
            int numMatchConstraints = countMatchConstraintsChainedWidgets(system, this.mChainEnds, this.mHorizontalChainsArray[i], FLAG_CHAIN_OPTIMIZE, this.flags);
            ConstraintWidget currentWidget = this.mChainEnds[OPTIMIZATION_ALL];
            if (currentWidget != null) {
                if (this.flags[OPTIMIZATION_NONE]) {
                    int x = first.getDrawX();
                    while (currentWidget != null) {
                        system.addEquality(currentWidget.mLeft.mSolverVariable, x);
                        x += (currentWidget.mLeft.getMargin() + currentWidget.getWidth()) + currentWidget.mRight.getMargin();
                        currentWidget = currentWidget.mHorizontalNextWidget;
                    }
                } else {
                    boolean isChainSpread = first.mHorizontalChainStyle == 0 ? USE_SNAPSHOT : DEBUG;
                    boolean isChainPacked = first.mHorizontalChainStyle == OPTIMIZATION_ALL ? USE_SNAPSHOT : DEBUG;
                    ConstraintWidget widget = first;
                    boolean isWrapContent = this.mHorizontalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT ? USE_SNAPSHOT : DEBUG;
                    if ((this.mOptimizationLevel == OPTIMIZATION_ALL || this.mOptimizationLevel == OPTIMIZATION_CHAIN) && this.flags[FLAG_CHAIN_OPTIMIZE] && widget.mHorizontalChainFixedPosition && !isChainPacked && !isWrapContent && first.mHorizontalChainStyle == 0) {
                        Optimizer.applyDirectResolutionHorizontalChain(this, system, numMatchConstraints, widget);
                    } else if (numMatchConstraints == 0 || isChainPacked) {
                        ConstraintAnchor left;
                        ConstraintAnchor right;
                        SolverVariable leftTarget;
                        ConstraintWidget previousVisibleWidget = null;
                        ConstraintWidget lastWidget = null;
                        ConstraintWidget firstVisibleWidget = currentWidget;
                        boolean isLast = DEBUG;
                        while (currentWidget != null) {
                            ConstraintWidget next = currentWidget.mHorizontalNextWidget;
                            if (next == null) {
                                lastWidget = this.mChainEnds[OPTIMIZATION_NONE];
                                isLast = USE_SNAPSHOT;
                            }
                            if (isChainPacked) {
                                left = currentWidget.mLeft;
                                margin = left.getMargin();
                                if (previousVisibleWidget != null) {
                                    margin += previousVisibleWidget.mRight.getMargin();
                                }
                                strength = OPTIMIZATION_NONE;
                                if (firstVisibleWidget != currentWidget) {
                                    strength = CHAIN_LAST_VISIBLE;
                                }
                                system.addGreaterThan(left.mSolverVariable, left.mTarget.mSolverVariable, margin, strength);
                                if (currentWidget.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                                    right = currentWidget.mRight;
                                    if (currentWidget.mMatchConstraintDefaultWidth == OPTIMIZATION_NONE) {
                                        system.addEquality(right.mSolverVariable, left.mSolverVariable, Math.max(currentWidget.mMatchConstraintMinWidth, currentWidget.getWidth()), CHAIN_LAST_VISIBLE);
                                    } else {
                                        system.addGreaterThan(left.mSolverVariable, left.mTarget.mSolverVariable, left.mMargin, CHAIN_LAST_VISIBLE);
                                        system.addLowerThan(right.mSolverVariable, left.mSolverVariable, currentWidget.mMatchConstraintMinWidth, CHAIN_LAST_VISIBLE);
                                    }
                                }
                            } else if (isChainSpread || !isLast || previousVisibleWidget == null) {
                                if (isChainSpread || isLast || previousVisibleWidget != null) {
                                    left = currentWidget.mLeft;
                                    right = currentWidget.mRight;
                                    leftMargin = left.getMargin();
                                    rightMargin = right.getMargin();
                                    system.addGreaterThan(left.mSolverVariable, left.mTarget.mSolverVariable, leftMargin, OPTIMIZATION_NONE);
                                    system.addLowerThan(right.mSolverVariable, right.mTarget.mSolverVariable, -rightMargin, OPTIMIZATION_NONE);
                                    leftTarget = left.mTarget != null ? left.mTarget.mSolverVariable : null;
                                    if (previousVisibleWidget == null) {
                                        leftTarget = first.mLeft.mTarget != null ? first.mLeft.mTarget.mSolverVariable : null;
                                    }
                                    if (next == null) {
                                        next = lastWidget.mRight.mTarget != null ? lastWidget.mRight.mTarget.mOwner : null;
                                    }
                                    if (next != null) {
                                        rightTarget = next.mLeft.mSolverVariable;
                                        if (isLast) {
                                            rightTarget = lastWidget.mRight.mTarget != null ? lastWidget.mRight.mTarget.mSolverVariable : null;
                                        }
                                        if (!(leftTarget == null || rightTarget == null)) {
                                            system.addCentering(left.mSolverVariable, leftTarget, leftMargin, 0.5f, rightTarget, right.mSolverVariable, rightMargin, OPTIMIZATION_BASIC);
                                        }
                                    }
                                } else if (currentWidget.mLeft.mTarget == null) {
                                    system.addEquality(currentWidget.mLeft.mSolverVariable, currentWidget.getDrawX());
                                } else {
                                    system.addEquality(currentWidget.mLeft.mSolverVariable, first.mLeft.mTarget.mSolverVariable, currentWidget.mLeft.getMargin(), 5);
                                }
                            } else if (currentWidget.mRight.mTarget == null) {
                                system.addEquality(currentWidget.mRight.mSolverVariable, currentWidget.getDrawRight());
                            } else {
                                system.addEquality(currentWidget.mRight.mSolverVariable, lastWidget.mRight.mTarget.mSolverVariable, -currentWidget.mRight.getMargin(), 5);
                            }
                            previousVisibleWidget = currentWidget;
                            if (isLast) {
                                currentWidget = null;
                            } else {
                                currentWidget = next;
                            }
                        }
                        if (isChainPacked) {
                            left = firstVisibleWidget.mLeft;
                            right = lastWidget.mRight;
                            leftMargin = left.getMargin();
                            rightMargin = right.getMargin();
                            leftTarget = first.mLeft.mTarget != null ? first.mLeft.mTarget.mSolverVariable : null;
                            rightTarget = lastWidget.mRight.mTarget != null ? lastWidget.mRight.mTarget.mSolverVariable : null;
                            if (!(leftTarget == null || rightTarget == null)) {
                                system.addLowerThan(right.mSolverVariable, rightTarget, -rightMargin, OPTIMIZATION_NONE);
                                system.addCentering(left.mSolverVariable, leftTarget, leftMargin, first.mHorizontalBiasPercent, rightTarget, right.mSolverVariable, rightMargin, OPTIMIZATION_BASIC);
                            }
                        }
                    } else {
                        ConstraintWidget previous = null;
                        float totalWeights = 0.0f;
                        while (currentWidget != null) {
                            if (currentWidget.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT) {
                                margin = currentWidget.mLeft.getMargin();
                                if (previous != null) {
                                    margin += previous.mRight.getMargin();
                                }
                                strength = CHAIN_LAST_VISIBLE;
                                if (currentWidget.mLeft.mTarget.mOwner.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                                    strength = OPTIMIZATION_ALL;
                                }
                                system.addGreaterThan(currentWidget.mLeft.mSolverVariable, currentWidget.mLeft.mTarget.mSolverVariable, margin, strength);
                                margin = currentWidget.mRight.getMargin();
                                if (currentWidget.mRight.mTarget.mOwner.mLeft.mTarget != null && currentWidget.mRight.mTarget.mOwner.mLeft.mTarget.mOwner == currentWidget) {
                                    margin += currentWidget.mRight.mTarget.mOwner.mLeft.getMargin();
                                }
                                strength = CHAIN_LAST_VISIBLE;
                                if (currentWidget.mRight.mTarget.mOwner.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                                    strength = OPTIMIZATION_ALL;
                                }
                                system.addLowerThan(currentWidget.mRight.mSolverVariable, currentWidget.mRight.mTarget.mSolverVariable, -margin, strength);
                            } else {
                                totalWeights += currentWidget.mHorizontalWeight;
                                margin = FLAG_CHAIN_OPTIMIZE;
                                if (currentWidget.mRight.mTarget != null) {
                                    margin = currentWidget.mRight.getMargin();
                                    if (currentWidget != this.mChainEnds[CHAIN_LAST_VISIBLE]) {
                                        margin += currentWidget.mRight.mTarget.mOwner.mLeft.getMargin();
                                    }
                                }
                                system.addGreaterThan(currentWidget.mRight.mSolverVariable, currentWidget.mLeft.mSolverVariable, FLAG_CHAIN_OPTIMIZE, OPTIMIZATION_NONE);
                                system.addLowerThan(currentWidget.mRight.mSolverVariable, currentWidget.mRight.mTarget.mSolverVariable, -margin, OPTIMIZATION_NONE);
                            }
                            previous = currentWidget;
                            currentWidget = currentWidget.mHorizontalNextWidget;
                        }
                        if (numMatchConstraints == OPTIMIZATION_NONE) {
                            ConstraintWidget w = this.mMatchConstraintsChainedWidgets[FLAG_CHAIN_OPTIMIZE];
                            leftMargin = w.mLeft.getMargin();
                            if (w.mLeft.mTarget != null) {
                                leftMargin += w.mLeft.mTarget.getMargin();
                            }
                            rightMargin = w.mRight.getMargin();
                            if (w.mRight.mTarget != null) {
                                rightMargin += w.mRight.mTarget.getMargin();
                            }
                            rightTarget = widget.mRight.mTarget.mSolverVariable;
                            if (w == this.mChainEnds[CHAIN_LAST_VISIBLE]) {
                                rightTarget = this.mChainEnds[OPTIMIZATION_NONE].mRight.mTarget.mSolverVariable;
                            }
                            if (w.mMatchConstraintDefaultWidth == OPTIMIZATION_NONE) {
                                system.addGreaterThan(widget.mLeft.mSolverVariable, widget.mLeft.mTarget.mSolverVariable, leftMargin, OPTIMIZATION_NONE);
                                system.addLowerThan(widget.mRight.mSolverVariable, rightTarget, -rightMargin, OPTIMIZATION_NONE);
                                system.addEquality(widget.mRight.mSolverVariable, widget.mLeft.mSolverVariable, widget.getWidth(), OPTIMIZATION_ALL);
                            } else {
                                system.addEquality(w.mLeft.mSolverVariable, w.mLeft.mTarget.mSolverVariable, leftMargin, OPTIMIZATION_NONE);
                                system.addEquality(w.mRight.mSolverVariable, rightTarget, -rightMargin, OPTIMIZATION_NONE);
                            }
                        } else {
                            for (int j = FLAG_CHAIN_OPTIMIZE; j < numMatchConstraints - 1; j += OPTIMIZATION_NONE) {
                                ConstraintWidget current = this.mMatchConstraintsChainedWidgets[j];
                                ConstraintWidget nextWidget = this.mMatchConstraintsChainedWidgets[j + OPTIMIZATION_NONE];
                                SolverVariable left2 = current.mLeft.mSolverVariable;
                                SolverVariable right2 = current.mRight.mSolverVariable;
                                SolverVariable nextLeft = nextWidget.mLeft.mSolverVariable;
                                SolverVariable nextRight = nextWidget.mRight.mSolverVariable;
                                if (nextWidget == this.mChainEnds[CHAIN_LAST_VISIBLE]) {
                                    nextRight = this.mChainEnds[OPTIMIZATION_NONE].mRight.mSolverVariable;
                                }
                                margin = current.mLeft.getMargin();
                                if (!(current.mLeft.mTarget == null || current.mLeft.mTarget.mOwner.mRight.mTarget == null || current.mLeft.mTarget.mOwner.mRight.mTarget.mOwner != current)) {
                                    margin += current.mLeft.mTarget.mOwner.mRight.getMargin();
                                }
                                system.addGreaterThan(left2, current.mLeft.mTarget.mSolverVariable, margin, OPTIMIZATION_ALL);
                                margin = current.mRight.getMargin();
                                if (!(current.mRight.mTarget == null || current.mHorizontalNextWidget == null)) {
                                    margin += current.mHorizontalNextWidget.mLeft.mTarget != null ? current.mHorizontalNextWidget.mLeft.getMargin() : FLAG_CHAIN_OPTIMIZE;
                                }
                                system.addLowerThan(right2, current.mRight.mTarget.mSolverVariable, -margin, OPTIMIZATION_ALL);
                                if (j + OPTIMIZATION_NONE == numMatchConstraints - 1) {
                                    margin = nextWidget.mLeft.getMargin();
                                    if (!(nextWidget.mLeft.mTarget == null || nextWidget.mLeft.mTarget.mOwner.mRight.mTarget == null || nextWidget.mLeft.mTarget.mOwner.mRight.mTarget.mOwner != nextWidget)) {
                                        margin += nextWidget.mLeft.mTarget.mOwner.mRight.getMargin();
                                    }
                                    system.addGreaterThan(nextLeft, nextWidget.mLeft.mTarget.mSolverVariable, margin, OPTIMIZATION_ALL);
                                    ConstraintAnchor anchor = nextWidget.mRight;
                                    if (nextWidget == this.mChainEnds[CHAIN_LAST_VISIBLE]) {
                                        anchor = this.mChainEnds[OPTIMIZATION_NONE].mRight;
                                    }
                                    margin = anchor.getMargin();
                                    if (!(anchor.mTarget == null || anchor.mTarget.mOwner.mLeft.mTarget == null || anchor.mTarget.mOwner.mLeft.mTarget.mOwner != nextWidget)) {
                                        margin += anchor.mTarget.mOwner.mLeft.getMargin();
                                    }
                                    system.addLowerThan(nextRight, anchor.mTarget.mSolverVariable, -margin, OPTIMIZATION_ALL);
                                }
                                if (widget.mMatchConstraintMaxWidth > 0) {
                                    system.addLowerThan(right2, left2, widget.mMatchConstraintMaxWidth, OPTIMIZATION_ALL);
                                }
                                ArrayRow row = system.createRow();
                                row.createRowEqualDimension(current.mHorizontalWeight, totalWeights, nextWidget.mHorizontalWeight, left2, current.mLeft.getMargin(), right2, current.mRight.getMargin(), nextLeft, nextWidget.mLeft.getMargin(), nextRight, nextWidget.mRight.getMargin());
                                system.addConstraint(row);
                            }
                        }
                    }
                }
            }
        }
    }

    private void applyVerticalChain(LinearSystem system) {
        for (int i = FLAG_CHAIN_OPTIMIZE; i < this.mVerticalChainsSize; i += OPTIMIZATION_NONE) {
            ConstraintWidget first = this.mVerticalChainsArray[i];
            int numMatchConstraints = countMatchConstraintsChainedWidgets(system, this.mChainEnds, this.mVerticalChainsArray[i], OPTIMIZATION_NONE, this.flags);
            ConstraintWidget currentWidget = this.mChainEnds[OPTIMIZATION_ALL];
            if (currentWidget != null) {
                if (this.flags[OPTIMIZATION_NONE]) {
                    int y = first.getDrawY();
                    while (currentWidget != null) {
                        system.addEquality(currentWidget.mTop.mSolverVariable, y);
                        y += (currentWidget.mTop.getMargin() + currentWidget.getHeight()) + currentWidget.mBottom.getMargin();
                        currentWidget = currentWidget.mVerticalNextWidget;
                    }
                } else {
                    boolean isChainSpread = first.mVerticalChainStyle == 0 ? USE_SNAPSHOT : DEBUG;
                    boolean isChainPacked = first.mVerticalChainStyle == OPTIMIZATION_ALL ? USE_SNAPSHOT : DEBUG;
                    ConstraintWidget widget = first;
                    boolean isWrapContent = this.mVerticalDimensionBehaviour == DimensionBehaviour.WRAP_CONTENT ? USE_SNAPSHOT : DEBUG;
                    if ((this.mOptimizationLevel == OPTIMIZATION_ALL || this.mOptimizationLevel == OPTIMIZATION_CHAIN) && this.flags[FLAG_CHAIN_OPTIMIZE] && widget.mVerticalChainFixedPosition && !isChainPacked && !isWrapContent && first.mVerticalChainStyle == 0) {
                        Optimizer.applyDirectResolutionVerticalChain(this, system, numMatchConstraints, widget);
                    } else if (numMatchConstraints == 0 || isChainPacked) {
                        ConstraintAnchor top;
                        ConstraintAnchor bottom;
                        SolverVariable topTarget;
                        ConstraintWidget previousVisibleWidget = null;
                        ConstraintWidget lastWidget = null;
                        ConstraintWidget firstVisibleWidget = currentWidget;
                        boolean isLast = DEBUG;
                        while (currentWidget != null) {
                            ConstraintWidget next = currentWidget.mVerticalNextWidget;
                            if (next == null) {
                                lastWidget = this.mChainEnds[OPTIMIZATION_NONE];
                                isLast = USE_SNAPSHOT;
                            }
                            if (isChainPacked) {
                                top = currentWidget.mTop;
                                margin = top.getMargin();
                                if (previousVisibleWidget != null) {
                                    margin += previousVisibleWidget.mBottom.getMargin();
                                }
                                strength = OPTIMIZATION_NONE;
                                if (firstVisibleWidget != currentWidget) {
                                    strength = CHAIN_LAST_VISIBLE;
                                }
                                SolverVariable source = null;
                                SolverVariable target = null;
                                if (top.mTarget != null) {
                                    source = top.mSolverVariable;
                                    target = top.mTarget.mSolverVariable;
                                } else if (currentWidget.mBaseline.mTarget != null) {
                                    source = currentWidget.mBaseline.mSolverVariable;
                                    target = currentWidget.mBaseline.mTarget.mSolverVariable;
                                    margin -= top.getMargin();
                                }
                                if (!(source == null || target == null)) {
                                    system.addGreaterThan(source, target, margin, strength);
                                }
                                if (currentWidget.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                                    bottom = currentWidget.mBottom;
                                    if (currentWidget.mMatchConstraintDefaultHeight == OPTIMIZATION_NONE) {
                                        system.addEquality(bottom.mSolverVariable, top.mSolverVariable, Math.max(currentWidget.mMatchConstraintMinHeight, currentWidget.getHeight()), CHAIN_LAST_VISIBLE);
                                    } else {
                                        system.addGreaterThan(top.mSolverVariable, top.mTarget.mSolverVariable, top.mMargin, CHAIN_LAST_VISIBLE);
                                        system.addLowerThan(bottom.mSolverVariable, top.mSolverVariable, currentWidget.mMatchConstraintMinHeight, CHAIN_LAST_VISIBLE);
                                    }
                                }
                            } else if (isChainSpread || !isLast || previousVisibleWidget == null) {
                                if (isChainSpread || isLast || previousVisibleWidget != null) {
                                    top = currentWidget.mTop;
                                    bottom = currentWidget.mBottom;
                                    topMargin = top.getMargin();
                                    bottomMargin = bottom.getMargin();
                                    system.addGreaterThan(top.mSolverVariable, top.mTarget.mSolverVariable, topMargin, OPTIMIZATION_NONE);
                                    system.addLowerThan(bottom.mSolverVariable, bottom.mTarget.mSolverVariable, -bottomMargin, OPTIMIZATION_NONE);
                                    topTarget = top.mTarget != null ? top.mTarget.mSolverVariable : null;
                                    if (previousVisibleWidget == null) {
                                        topTarget = first.mTop.mTarget != null ? first.mTop.mTarget.mSolverVariable : null;
                                    }
                                    if (next == null) {
                                        next = lastWidget.mBottom.mTarget != null ? lastWidget.mBottom.mTarget.mOwner : null;
                                    }
                                    if (next != null) {
                                        bottomTarget = next.mTop.mSolverVariable;
                                        if (isLast) {
                                            bottomTarget = lastWidget.mBottom.mTarget != null ? lastWidget.mBottom.mTarget.mSolverVariable : null;
                                        }
                                        if (!(topTarget == null || bottomTarget == null)) {
                                            system.addCentering(top.mSolverVariable, topTarget, topMargin, 0.5f, bottomTarget, bottom.mSolverVariable, bottomMargin, OPTIMIZATION_BASIC);
                                        }
                                    }
                                } else if (currentWidget.mTop.mTarget == null) {
                                    system.addEquality(currentWidget.mTop.mSolverVariable, currentWidget.getDrawY());
                                } else {
                                    system.addEquality(currentWidget.mTop.mSolverVariable, first.mTop.mTarget.mSolverVariable, currentWidget.mTop.getMargin(), 5);
                                }
                            } else if (currentWidget.mBottom.mTarget == null) {
                                system.addEquality(currentWidget.mBottom.mSolverVariable, currentWidget.getDrawBottom());
                            } else {
                                system.addEquality(currentWidget.mBottom.mSolverVariable, lastWidget.mBottom.mTarget.mSolverVariable, -currentWidget.mBottom.getMargin(), 5);
                            }
                            previousVisibleWidget = currentWidget;
                            if (isLast) {
                                currentWidget = null;
                            } else {
                                currentWidget = next;
                            }
                        }
                        if (isChainPacked) {
                            top = firstVisibleWidget.mTop;
                            bottom = lastWidget.mBottom;
                            topMargin = top.getMargin();
                            bottomMargin = bottom.getMargin();
                            topTarget = first.mTop.mTarget != null ? first.mTop.mTarget.mSolverVariable : null;
                            bottomTarget = lastWidget.mBottom.mTarget != null ? lastWidget.mBottom.mTarget.mSolverVariable : null;
                            if (!(topTarget == null || bottomTarget == null)) {
                                system.addLowerThan(bottom.mSolverVariable, bottomTarget, -bottomMargin, OPTIMIZATION_NONE);
                                system.addCentering(top.mSolverVariable, topTarget, topMargin, first.mVerticalBiasPercent, bottomTarget, bottom.mSolverVariable, bottomMargin, OPTIMIZATION_BASIC);
                            }
                        }
                    } else {
                        ConstraintWidget previous = null;
                        float totalWeights = 0.0f;
                        while (currentWidget != null) {
                            if (currentWidget.mVerticalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT) {
                                margin = currentWidget.mTop.getMargin();
                                if (previous != null) {
                                    margin += previous.mBottom.getMargin();
                                }
                                strength = CHAIN_LAST_VISIBLE;
                                if (currentWidget.mTop.mTarget.mOwner.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                                    strength = OPTIMIZATION_ALL;
                                }
                                system.addGreaterThan(currentWidget.mTop.mSolverVariable, currentWidget.mTop.mTarget.mSolverVariable, margin, strength);
                                margin = currentWidget.mBottom.getMargin();
                                if (currentWidget.mBottom.mTarget.mOwner.mTop.mTarget != null && currentWidget.mBottom.mTarget.mOwner.mTop.mTarget.mOwner == currentWidget) {
                                    margin += currentWidget.mBottom.mTarget.mOwner.mTop.getMargin();
                                }
                                strength = CHAIN_LAST_VISIBLE;
                                if (currentWidget.mBottom.mTarget.mOwner.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT) {
                                    strength = OPTIMIZATION_ALL;
                                }
                                system.addLowerThan(currentWidget.mBottom.mSolverVariable, currentWidget.mBottom.mTarget.mSolverVariable, -margin, strength);
                            } else {
                                totalWeights += currentWidget.mVerticalWeight;
                                margin = FLAG_CHAIN_OPTIMIZE;
                                if (currentWidget.mBottom.mTarget != null) {
                                    margin = currentWidget.mBottom.getMargin();
                                    if (currentWidget != this.mChainEnds[CHAIN_LAST_VISIBLE]) {
                                        margin += currentWidget.mBottom.mTarget.mOwner.mTop.getMargin();
                                    }
                                }
                                system.addGreaterThan(currentWidget.mBottom.mSolverVariable, currentWidget.mTop.mSolverVariable, FLAG_CHAIN_OPTIMIZE, OPTIMIZATION_NONE);
                                system.addLowerThan(currentWidget.mBottom.mSolverVariable, currentWidget.mBottom.mTarget.mSolverVariable, -margin, OPTIMIZATION_NONE);
                            }
                            previous = currentWidget;
                            currentWidget = currentWidget.mVerticalNextWidget;
                        }
                        if (numMatchConstraints == OPTIMIZATION_NONE) {
                            ConstraintWidget w = this.mMatchConstraintsChainedWidgets[FLAG_CHAIN_OPTIMIZE];
                            topMargin = w.mTop.getMargin();
                            if (w.mTop.mTarget != null) {
                                topMargin += w.mTop.mTarget.getMargin();
                            }
                            bottomMargin = w.mBottom.getMargin();
                            if (w.mBottom.mTarget != null) {
                                bottomMargin += w.mBottom.mTarget.getMargin();
                            }
                            bottomTarget = widget.mBottom.mTarget.mSolverVariable;
                            if (w == this.mChainEnds[CHAIN_LAST_VISIBLE]) {
                                bottomTarget = this.mChainEnds[OPTIMIZATION_NONE].mBottom.mTarget.mSolverVariable;
                            }
                            if (w.mMatchConstraintDefaultHeight == OPTIMIZATION_NONE) {
                                system.addGreaterThan(widget.mTop.mSolverVariable, widget.mTop.mTarget.mSolverVariable, topMargin, OPTIMIZATION_NONE);
                                system.addLowerThan(widget.mBottom.mSolverVariable, bottomTarget, -bottomMargin, OPTIMIZATION_NONE);
                                system.addEquality(widget.mBottom.mSolverVariable, widget.mTop.mSolverVariable, widget.getHeight(), OPTIMIZATION_ALL);
                            } else {
                                system.addEquality(w.mTop.mSolverVariable, w.mTop.mTarget.mSolverVariable, topMargin, OPTIMIZATION_NONE);
                                system.addEquality(w.mBottom.mSolverVariable, bottomTarget, -bottomMargin, OPTIMIZATION_NONE);
                            }
                        } else {
                            for (int j = FLAG_CHAIN_OPTIMIZE; j < numMatchConstraints - 1; j += OPTIMIZATION_NONE) {
                                ConstraintWidget current = this.mMatchConstraintsChainedWidgets[j];
                                ConstraintWidget nextWidget = this.mMatchConstraintsChainedWidgets[j + OPTIMIZATION_NONE];
                                SolverVariable top2 = current.mTop.mSolverVariable;
                                SolverVariable bottom2 = current.mBottom.mSolverVariable;
                                SolverVariable nextTop = nextWidget.mTop.mSolverVariable;
                                SolverVariable nextBottom = nextWidget.mBottom.mSolverVariable;
                                if (nextWidget == this.mChainEnds[CHAIN_LAST_VISIBLE]) {
                                    nextBottom = this.mChainEnds[OPTIMIZATION_NONE].mBottom.mSolverVariable;
                                }
                                margin = current.mTop.getMargin();
                                if (!(current.mTop.mTarget == null || current.mTop.mTarget.mOwner.mBottom.mTarget == null || current.mTop.mTarget.mOwner.mBottom.mTarget.mOwner != current)) {
                                    margin += current.mTop.mTarget.mOwner.mBottom.getMargin();
                                }
                                system.addGreaterThan(top2, current.mTop.mTarget.mSolverVariable, margin, OPTIMIZATION_ALL);
                                margin = current.mBottom.getMargin();
                                if (!(current.mBottom.mTarget == null || current.mVerticalNextWidget == null)) {
                                    margin += current.mVerticalNextWidget.mTop.mTarget != null ? current.mVerticalNextWidget.mTop.getMargin() : FLAG_CHAIN_OPTIMIZE;
                                }
                                system.addLowerThan(bottom2, current.mBottom.mTarget.mSolverVariable, -margin, OPTIMIZATION_ALL);
                                if (j + OPTIMIZATION_NONE == numMatchConstraints - 1) {
                                    margin = nextWidget.mTop.getMargin();
                                    if (!(nextWidget.mTop.mTarget == null || nextWidget.mTop.mTarget.mOwner.mBottom.mTarget == null || nextWidget.mTop.mTarget.mOwner.mBottom.mTarget.mOwner != nextWidget)) {
                                        margin += nextWidget.mTop.mTarget.mOwner.mBottom.getMargin();
                                    }
                                    system.addGreaterThan(nextTop, nextWidget.mTop.mTarget.mSolverVariable, margin, OPTIMIZATION_ALL);
                                    ConstraintAnchor anchor = nextWidget.mBottom;
                                    if (nextWidget == this.mChainEnds[CHAIN_LAST_VISIBLE]) {
                                        anchor = this.mChainEnds[OPTIMIZATION_NONE].mBottom;
                                    }
                                    margin = anchor.getMargin();
                                    if (!(anchor.mTarget == null || anchor.mTarget.mOwner.mTop.mTarget == null || anchor.mTarget.mOwner.mTop.mTarget.mOwner != nextWidget)) {
                                        margin += anchor.mTarget.mOwner.mTop.getMargin();
                                    }
                                    system.addLowerThan(nextBottom, anchor.mTarget.mSolverVariable, -margin, OPTIMIZATION_ALL);
                                }
                                if (widget.mMatchConstraintMaxHeight > 0) {
                                    system.addLowerThan(bottom2, top2, widget.mMatchConstraintMaxHeight, OPTIMIZATION_ALL);
                                }
                                ArrayRow row = system.createRow();
                                row.createRowEqualDimension(current.mVerticalWeight, totalWeights, nextWidget.mVerticalWeight, top2, current.mTop.getMargin(), bottom2, current.mBottom.getMargin(), nextTop, nextWidget.mTop.getMargin(), nextBottom, nextWidget.mBottom.getMargin());
                                system.addConstraint(row);
                            }
                        }
                    }
                }
            }
        }
    }

    public void updateChildrenFromSolver(LinearSystem system, int group, boolean[] flags) {
        flags[OPTIMIZATION_ALL] = DEBUG;
        updateFromSolver(system, group);
        int count = this.mChildren.size();
        for (int i = FLAG_CHAIN_OPTIMIZE; i < count; i += OPTIMIZATION_NONE) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            widget.updateFromSolver(system, group);
            if (widget.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT && widget.getWidth() < widget.getWrapWidth()) {
                flags[OPTIMIZATION_ALL] = USE_SNAPSHOT;
            }
            if (widget.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT && widget.getHeight() < widget.getWrapHeight()) {
                flags[OPTIMIZATION_ALL] = USE_SNAPSHOT;
            }
        }
    }

    public void setPadding(int left, int top, int right, int bottom) {
        this.mPaddingLeft = left;
        this.mPaddingTop = top;
        this.mPaddingRight = right;
        this.mPaddingBottom = bottom;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void layout() {
        /*
        r24 = this;
        r0 = r24;
        r0 = r0.mX;
        r16 = r0;
        r0 = r24;
        r0 = r0.mY;
        r17 = r0;
        r15 = r24.getWidth();
        r14 = r24.getHeight();
        r21 = 0;
        r0 = r21;
        r1 = r24;
        r1.mWidthMeasuredTooSmall = r0;
        r21 = 0;
        r0 = r21;
        r1 = r24;
        r1.mHeightMeasuredTooSmall = r0;
        r0 = r24;
        r0 = r0.mParent;
        r21 = r0;
        if (r21 == 0) goto L_0x0179;
    L_0x002c:
        r0 = r24;
        r0 = r0.mSnapshot;
        r21 = r0;
        if (r21 != 0) goto L_0x0043;
    L_0x0034:
        r21 = new android.support.constraint.solver.widgets.Snapshot;
        r0 = r21;
        r1 = r24;
        r0.<init>(r1);
        r0 = r21;
        r1 = r24;
        r1.mSnapshot = r0;
    L_0x0043:
        r0 = r24;
        r0 = r0.mSnapshot;
        r21 = r0;
        r0 = r21;
        r1 = r24;
        r0.updateFrom(r1);
        r0 = r24;
        r0 = r0.mPaddingLeft;
        r21 = r0;
        r0 = r24;
        r1 = r21;
        r0.setX(r1);
        r0 = r24;
        r0 = r0.mPaddingTop;
        r21 = r0;
        r0 = r24;
        r1 = r21;
        r0.setY(r1);
        r24.resetAnchors();
        r0 = r24;
        r0 = r0.mSystem;
        r21 = r0;
        r21 = r21.getCache();
        r0 = r24;
        r1 = r21;
        r0.resetSolverVariables(r1);
    L_0x007e:
        r20 = 0;
        r0 = r24;
        r13 = r0.mVerticalDimensionBehaviour;
        r0 = r24;
        r12 = r0.mHorizontalDimensionBehaviour;
        r0 = r24;
        r0 = r0.mOptimizationLevel;
        r21 = r0;
        r22 = 2;
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x014b;
    L_0x0096:
        r0 = r24;
        r0 = r0.mVerticalDimensionBehaviour;
        r21 = r0;
        r22 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r0 = r21;
        r1 = r22;
        if (r0 == r1) goto L_0x00b2;
    L_0x00a4:
        r0 = r24;
        r0 = r0.mHorizontalDimensionBehaviour;
        r21 = r0;
        r22 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x014b;
    L_0x00b2:
        r0 = r24;
        r0 = r0.mChildren;
        r21 = r0;
        r0 = r24;
        r0 = r0.flags;
        r22 = r0;
        r0 = r24;
        r1 = r21;
        r2 = r22;
        r0.findWrapSize(r1, r2);
        r0 = r24;
        r0 = r0.flags;
        r21 = r0;
        r22 = 0;
        r20 = r21[r22];
        if (r15 <= 0) goto L_0x00eb;
    L_0x00d3:
        if (r14 <= 0) goto L_0x00eb;
    L_0x00d5:
        r0 = r24;
        r0 = r0.mWrapWidth;
        r21 = r0;
        r0 = r21;
        if (r0 > r15) goto L_0x00e9;
    L_0x00df:
        r0 = r24;
        r0 = r0.mWrapHeight;
        r21 = r0;
        r0 = r21;
        if (r0 <= r14) goto L_0x00eb;
    L_0x00e9:
        r20 = 0;
    L_0x00eb:
        if (r20 == 0) goto L_0x014b;
    L_0x00ed:
        r0 = r24;
        r0 = r0.mHorizontalDimensionBehaviour;
        r21 = r0;
        r22 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x011c;
    L_0x00fb:
        r21 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        r0 = r21;
        r1 = r24;
        r1.mHorizontalDimensionBehaviour = r0;
        if (r15 <= 0) goto L_0x018b;
    L_0x0105:
        r0 = r24;
        r0 = r0.mWrapWidth;
        r21 = r0;
        r0 = r21;
        if (r15 >= r0) goto L_0x018b;
    L_0x010f:
        r21 = 1;
        r0 = r21;
        r1 = r24;
        r1.mWidthMeasuredTooSmall = r0;
        r0 = r24;
        r0.setWidth(r15);
    L_0x011c:
        r0 = r24;
        r0 = r0.mVerticalDimensionBehaviour;
        r21 = r0;
        r22 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x014b;
    L_0x012a:
        r21 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        r0 = r21;
        r1 = r24;
        r1.mVerticalDimensionBehaviour = r0;
        if (r14 <= 0) goto L_0x01a4;
    L_0x0134:
        r0 = r24;
        r0 = r0.mWrapHeight;
        r21 = r0;
        r0 = r21;
        if (r14 >= r0) goto L_0x01a4;
    L_0x013e:
        r21 = 1;
        r0 = r21;
        r1 = r24;
        r1.mHeightMeasuredTooSmall = r0;
        r0 = r24;
        r0.setHeight(r14);
    L_0x014b:
        r24.resetChains();
        r0 = r24;
        r0 = r0.mChildren;
        r21 = r0;
        r4 = r21.size();
        r8 = 0;
    L_0x0159:
        if (r8 >= r4) goto L_0x01bc;
    L_0x015b:
        r0 = r24;
        r0 = r0.mChildren;
        r21 = r0;
        r0 = r21;
        r18 = r0.get(r8);
        r18 = (android.support.constraint.solver.widgets.ConstraintWidget) r18;
        r0 = r18;
        r0 = r0 instanceof android.support.constraint.solver.widgets.WidgetContainer;
        r21 = r0;
        if (r21 == 0) goto L_0x0176;
    L_0x0171:
        r18 = (android.support.constraint.solver.widgets.WidgetContainer) r18;
        r18.layout();
    L_0x0176:
        r8 = r8 + 1;
        goto L_0x0159;
    L_0x0179:
        r21 = 0;
        r0 = r21;
        r1 = r24;
        r1.mX = r0;
        r21 = 0;
        r0 = r21;
        r1 = r24;
        r1.mY = r0;
        goto L_0x007e;
    L_0x018b:
        r0 = r24;
        r0 = r0.mMinWidth;
        r21 = r0;
        r0 = r24;
        r0 = r0.mWrapWidth;
        r22 = r0;
        r21 = java.lang.Math.max(r21, r22);
        r0 = r24;
        r1 = r21;
        r0.setWidth(r1);
        goto L_0x011c;
    L_0x01a4:
        r0 = r24;
        r0 = r0.mMinHeight;
        r21 = r0;
        r0 = r24;
        r0 = r0.mWrapHeight;
        r22 = r0;
        r21 = java.lang.Math.max(r21, r22);
        r0 = r24;
        r1 = r21;
        r0.setHeight(r1);
        goto L_0x014b;
    L_0x01bc:
        r11 = 1;
        r5 = 0;
    L_0x01be:
        if (r11 == 0) goto L_0x03d9;
    L_0x01c0:
        r5 = r5 + 1;
        r0 = r24;
        r0 = r0.mSystem;	 Catch:{ Exception -> 0x0252 }
        r21 = r0;
        r21.reset();	 Catch:{ Exception -> 0x0252 }
        r0 = r24;
        r0 = r0.mSystem;	 Catch:{ Exception -> 0x0252 }
        r21 = r0;
        r22 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r0 = r24;
        r1 = r21;
        r2 = r22;
        r11 = r0.addChildrenToSolver(r1, r2);	 Catch:{ Exception -> 0x0252 }
        if (r11 == 0) goto L_0x01e9;
    L_0x01e0:
        r0 = r24;
        r0 = r0.mSystem;	 Catch:{ Exception -> 0x0252 }
        r21 = r0;
        r21.minimize();	 Catch:{ Exception -> 0x0252 }
    L_0x01e9:
        if (r11 == 0) goto L_0x0257;
    L_0x01eb:
        r0 = r24;
        r0 = r0.mSystem;
        r21 = r0;
        r22 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r0 = r24;
        r0 = r0.flags;
        r23 = r0;
        r0 = r24;
        r1 = r21;
        r2 = r22;
        r3 = r23;
        r0.updateChildrenFromSolver(r1, r2, r3);
    L_0x0205:
        r11 = 0;
        r21 = 8;
        r0 = r21;
        if (r5 >= r0) goto L_0x0325;
    L_0x020c:
        r0 = r24;
        r0 = r0.flags;
        r21 = r0;
        r22 = 2;
        r21 = r21[r22];
        if (r21 == 0) goto L_0x0325;
    L_0x0218:
        r9 = 0;
        r10 = 0;
        r8 = 0;
    L_0x021b:
        if (r8 >= r4) goto L_0x02d1;
    L_0x021d:
        r0 = r24;
        r0 = r0.mChildren;
        r21 = r0;
        r0 = r21;
        r18 = r0.get(r8);
        r18 = (android.support.constraint.solver.widgets.ConstraintWidget) r18;
        r0 = r18;
        r0 = r0.mX;
        r21 = r0;
        r22 = r18.getWidth();
        r21 = r21 + r22;
        r0 = r21;
        r9 = java.lang.Math.max(r9, r0);
        r0 = r18;
        r0 = r0.mY;
        r21 = r0;
        r22 = r18.getHeight();
        r21 = r21 + r22;
        r0 = r21;
        r10 = java.lang.Math.max(r10, r0);
        r8 = r8 + 1;
        goto L_0x021b;
    L_0x0252:
        r6 = move-exception;
        r6.printStackTrace();
        goto L_0x01e9;
    L_0x0257:
        r0 = r24;
        r0 = r0.mSystem;
        r21 = r0;
        r22 = 2147483647; // 0x7fffffff float:NaN double:1.060997895E-314;
        r0 = r24;
        r1 = r21;
        r2 = r22;
        r0.updateFromSolver(r1, r2);
        r8 = 0;
    L_0x026a:
        if (r8 >= r4) goto L_0x0205;
    L_0x026c:
        r0 = r24;
        r0 = r0.mChildren;
        r21 = r0;
        r0 = r21;
        r18 = r0.get(r8);
        r18 = (android.support.constraint.solver.widgets.ConstraintWidget) r18;
        r0 = r18;
        r0 = r0.mHorizontalDimensionBehaviour;
        r21 = r0;
        r22 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x02a4;
    L_0x0288:
        r21 = r18.getWidth();
        r22 = r18.getWrapWidth();
        r0 = r21;
        r1 = r22;
        if (r0 >= r1) goto L_0x02a4;
    L_0x0296:
        r0 = r24;
        r0 = r0.flags;
        r21 = r0;
        r22 = 2;
        r23 = 1;
        r21[r22] = r23;
        goto L_0x0205;
    L_0x02a4:
        r0 = r18;
        r0 = r0.mVerticalDimensionBehaviour;
        r21 = r0;
        r22 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x02ce;
    L_0x02b2:
        r21 = r18.getHeight();
        r22 = r18.getWrapHeight();
        r0 = r21;
        r1 = r22;
        if (r0 >= r1) goto L_0x02ce;
    L_0x02c0:
        r0 = r24;
        r0 = r0.flags;
        r21 = r0;
        r22 = 2;
        r23 = 1;
        r21[r22] = r23;
        goto L_0x0205;
    L_0x02ce:
        r8 = r8 + 1;
        goto L_0x026a;
    L_0x02d1:
        r0 = r24;
        r0 = r0.mMinWidth;
        r21 = r0;
        r0 = r21;
        r9 = java.lang.Math.max(r0, r9);
        r0 = r24;
        r0 = r0.mMinHeight;
        r21 = r0;
        r0 = r21;
        r10 = java.lang.Math.max(r0, r10);
        r21 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r0 = r21;
        if (r12 != r0) goto L_0x0307;
    L_0x02ef:
        r21 = r24.getWidth();
        r0 = r21;
        if (r0 >= r9) goto L_0x0307;
    L_0x02f7:
        r0 = r24;
        r0.setWidth(r9);
        r21 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r0 = r21;
        r1 = r24;
        r1.mHorizontalDimensionBehaviour = r0;
        r20 = 1;
        r11 = 1;
    L_0x0307:
        r21 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r0 = r21;
        if (r13 != r0) goto L_0x0325;
    L_0x030d:
        r21 = r24.getHeight();
        r0 = r21;
        if (r0 >= r10) goto L_0x0325;
    L_0x0315:
        r0 = r24;
        r0.setHeight(r10);
        r21 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r0 = r21;
        r1 = r24;
        r1.mVerticalDimensionBehaviour = r0;
        r20 = 1;
        r11 = 1;
    L_0x0325:
        r0 = r24;
        r0 = r0.mMinWidth;
        r21 = r0;
        r22 = r24.getWidth();
        r19 = java.lang.Math.max(r21, r22);
        r21 = r24.getWidth();
        r0 = r19;
        r1 = r21;
        if (r0 <= r1) goto L_0x034f;
    L_0x033d:
        r0 = r24;
        r1 = r19;
        r0.setWidth(r1);
        r21 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        r0 = r21;
        r1 = r24;
        r1.mHorizontalDimensionBehaviour = r0;
        r20 = 1;
        r11 = 1;
    L_0x034f:
        r0 = r24;
        r0 = r0.mMinHeight;
        r21 = r0;
        r22 = r24.getHeight();
        r7 = java.lang.Math.max(r21, r22);
        r21 = r24.getHeight();
        r0 = r21;
        if (r7 <= r0) goto L_0x0375;
    L_0x0365:
        r0 = r24;
        r0.setHeight(r7);
        r21 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        r0 = r21;
        r1 = r24;
        r1.mVerticalDimensionBehaviour = r0;
        r20 = 1;
        r11 = 1;
    L_0x0375:
        if (r20 != 0) goto L_0x01be;
    L_0x0377:
        r0 = r24;
        r0 = r0.mHorizontalDimensionBehaviour;
        r21 = r0;
        r22 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x03a7;
    L_0x0385:
        if (r15 <= 0) goto L_0x03a7;
    L_0x0387:
        r21 = r24.getWidth();
        r0 = r21;
        if (r0 <= r15) goto L_0x03a7;
    L_0x038f:
        r21 = 1;
        r0 = r21;
        r1 = r24;
        r1.mWidthMeasuredTooSmall = r0;
        r20 = 1;
        r21 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        r0 = r21;
        r1 = r24;
        r1.mHorizontalDimensionBehaviour = r0;
        r0 = r24;
        r0.setWidth(r15);
        r11 = 1;
    L_0x03a7:
        r0 = r24;
        r0 = r0.mVerticalDimensionBehaviour;
        r21 = r0;
        r22 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        r0 = r21;
        r1 = r22;
        if (r0 != r1) goto L_0x01be;
    L_0x03b5:
        if (r14 <= 0) goto L_0x01be;
    L_0x03b7:
        r21 = r24.getHeight();
        r0 = r21;
        if (r0 <= r14) goto L_0x01be;
    L_0x03bf:
        r21 = 1;
        r0 = r21;
        r1 = r24;
        r1.mHeightMeasuredTooSmall = r0;
        r20 = 1;
        r21 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED;
        r0 = r21;
        r1 = r24;
        r1.mVerticalDimensionBehaviour = r0;
        r0 = r24;
        r0.setHeight(r14);
        r11 = 1;
        goto L_0x01be;
    L_0x03d9:
        r0 = r24;
        r0 = r0.mParent;
        r21 = r0;
        if (r21 == 0) goto L_0x0461;
    L_0x03e1:
        r0 = r24;
        r0 = r0.mMinWidth;
        r21 = r0;
        r22 = r24.getWidth();
        r19 = java.lang.Math.max(r21, r22);
        r0 = r24;
        r0 = r0.mMinHeight;
        r21 = r0;
        r22 = r24.getHeight();
        r7 = java.lang.Math.max(r21, r22);
        r0 = r24;
        r0 = r0.mSnapshot;
        r21 = r0;
        r0 = r21;
        r1 = r24;
        r0.applyTo(r1);
        r0 = r24;
        r0 = r0.mPaddingLeft;
        r21 = r0;
        r21 = r21 + r19;
        r0 = r24;
        r0 = r0.mPaddingRight;
        r22 = r0;
        r21 = r21 + r22;
        r0 = r24;
        r1 = r21;
        r0.setWidth(r1);
        r0 = r24;
        r0 = r0.mPaddingTop;
        r21 = r0;
        r21 = r21 + r7;
        r0 = r24;
        r0 = r0.mPaddingBottom;
        r22 = r0;
        r21 = r21 + r22;
        r0 = r24;
        r1 = r21;
        r0.setHeight(r1);
    L_0x0438:
        if (r20 == 0) goto L_0x0442;
    L_0x043a:
        r0 = r24;
        r0.mHorizontalDimensionBehaviour = r12;
        r0 = r24;
        r0.mVerticalDimensionBehaviour = r13;
    L_0x0442:
        r0 = r24;
        r0 = r0.mSystem;
        r21 = r0;
        r21 = r21.getCache();
        r0 = r24;
        r1 = r21;
        r0.resetSolverVariables(r1);
        r21 = r24.getRootConstraintContainer();
        r0 = r24;
        r1 = r21;
        if (r0 != r1) goto L_0x0460;
    L_0x045d:
        r24.updateDrawPosition();
    L_0x0460:
        return;
    L_0x0461:
        r0 = r16;
        r1 = r24;
        r1.mX = r0;
        r0 = r17;
        r1 = r24;
        r1.mY = r0;
        goto L_0x0438;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidgetContainer.layout():void");
    }

    static int setGroup(ConstraintAnchor anchor, int group) {
        int oldGroup = anchor.mGroup;
        if (anchor.mOwner.getParent() == null) {
            return group;
        }
        if (oldGroup <= group) {
            return oldGroup;
        }
        anchor.mGroup = group;
        ConstraintAnchor opposite = anchor.getOpposite();
        ConstraintAnchor target = anchor.mTarget;
        if (opposite != null) {
            group = setGroup(opposite, group);
        }
        if (target != null) {
            group = setGroup(target, group);
        }
        if (opposite != null) {
            group = setGroup(opposite, group);
        }
        anchor.mGroup = group;
        return group;
    }

    public int layoutFindGroupsSimple() {
        int size = this.mChildren.size();
        for (int j = FLAG_CHAIN_OPTIMIZE; j < size; j += OPTIMIZATION_NONE) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(j);
            widget.mLeft.mGroup = FLAG_CHAIN_OPTIMIZE;
            widget.mRight.mGroup = FLAG_CHAIN_OPTIMIZE;
            widget.mTop.mGroup = OPTIMIZATION_NONE;
            widget.mBottom.mGroup = OPTIMIZATION_NONE;
            widget.mBaseline.mGroup = OPTIMIZATION_NONE;
        }
        return OPTIMIZATION_ALL;
    }

    public void findHorizontalWrapRecursive(ConstraintWidget widget, boolean[] flags) {
        boolean z = DEBUG;
        if (widget.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT && widget.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT && widget.mDimensionRatio > 0.0f) {
            flags[FLAG_CHAIN_OPTIMIZE] = DEBUG;
            return;
        }
        int w = widget.getOptimizerWrapWidth();
        if (widget.mHorizontalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT || widget.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT || widget.mDimensionRatio <= 0.0f) {
            int distToRight = w;
            int distToLeft = w;
            ConstraintWidget leftWidget = null;
            ConstraintWidget rightWidget = null;
            widget.mHorizontalWrapVisited = USE_SNAPSHOT;
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == OPTIMIZATION_NONE) {
                    distToLeft = FLAG_CHAIN_OPTIMIZE;
                    distToRight = FLAG_CHAIN_OPTIMIZE;
                    if (guideline.getRelativeBegin() != -1) {
                        distToLeft = guideline.getRelativeBegin();
                    } else if (guideline.getRelativeEnd() != -1) {
                        distToRight = guideline.getRelativeEnd();
                    }
                }
            } else if (!widget.mRight.isConnected() && !widget.mLeft.isConnected()) {
                distToLeft += widget.getX();
            } else if (widget.mRight.mTarget == null || widget.mLeft.mTarget == null || (widget.mRight.mTarget != widget.mLeft.mTarget && (widget.mRight.mTarget.mOwner != widget.mLeft.mTarget.mOwner || widget.mRight.mTarget.mOwner == widget.mParent))) {
                if (widget.mRight.mTarget != null) {
                    rightWidget = widget.mRight.mTarget.mOwner;
                    distToRight += widget.mRight.getMargin();
                    if (!(rightWidget.isRoot() || rightWidget.mHorizontalWrapVisited)) {
                        findHorizontalWrapRecursive(rightWidget, flags);
                    }
                }
                if (widget.mLeft.mTarget != null) {
                    leftWidget = widget.mLeft.mTarget.mOwner;
                    distToLeft += widget.mLeft.getMargin();
                    if (!(leftWidget.isRoot() || leftWidget.mHorizontalWrapVisited)) {
                        findHorizontalWrapRecursive(leftWidget, flags);
                    }
                }
                if (!(widget.mRight.mTarget == null || rightWidget.isRoot())) {
                    boolean z2;
                    if (widget.mRight.mTarget.mType == Type.RIGHT) {
                        distToRight += rightWidget.mDistToRight - rightWidget.getOptimizerWrapWidth();
                    } else if (widget.mRight.mTarget.getType() == Type.LEFT) {
                        distToRight += rightWidget.mDistToRight;
                    }
                    if (rightWidget.mRightHasCentered || !(rightWidget.mLeft.mTarget == null || rightWidget.mRight.mTarget == null || rightWidget.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT)) {
                        z2 = USE_SNAPSHOT;
                    } else {
                        z2 = DEBUG;
                    }
                    widget.mRightHasCentered = z2;
                    if (widget.mRightHasCentered) {
                        if (rightWidget.mLeft.mTarget != null) {
                            if (rightWidget.mLeft.mTarget.mOwner != widget) {
                            }
                        }
                        distToRight += distToRight - rightWidget.mDistToRight;
                    }
                }
                if (!(widget.mLeft.mTarget == null || leftWidget.isRoot())) {
                    if (widget.mLeft.mTarget.getType() == Type.LEFT) {
                        distToLeft += leftWidget.mDistToLeft - leftWidget.getOptimizerWrapWidth();
                    } else if (widget.mLeft.mTarget.getType() == Type.RIGHT) {
                        distToLeft += leftWidget.mDistToLeft;
                    }
                    if (leftWidget.mLeftHasCentered || !(leftWidget.mLeft.mTarget == null || leftWidget.mRight.mTarget == null || leftWidget.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT)) {
                        z = USE_SNAPSHOT;
                    }
                    widget.mLeftHasCentered = z;
                    if (widget.mLeftHasCentered) {
                        if (leftWidget.mRight.mTarget != null) {
                            if (leftWidget.mRight.mTarget.mOwner != widget) {
                            }
                        }
                        distToLeft += distToLeft - leftWidget.mDistToLeft;
                    }
                }
            } else {
                flags[FLAG_CHAIN_OPTIMIZE] = DEBUG;
                return;
            }
            if (widget.getVisibility() == OPTIMIZATION_CHAIN) {
                distToLeft -= widget.mWidth;
                distToRight -= widget.mWidth;
            }
            widget.mDistToLeft = distToLeft;
            widget.mDistToRight = distToRight;
            return;
        }
        flags[FLAG_CHAIN_OPTIMIZE] = DEBUG;
    }

    public void findVerticalWrapRecursive(ConstraintWidget widget, boolean[] flags) {
        boolean z = DEBUG;
        if (widget.mVerticalDimensionBehaviour != DimensionBehaviour.MATCH_CONSTRAINT || widget.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT || widget.mDimensionRatio <= 0.0f) {
            int h = widget.getOptimizerWrapHeight();
            int distToTop = h;
            int distToBottom = h;
            ConstraintWidget topWidget = null;
            ConstraintWidget bottomWidget = null;
            widget.mVerticalWrapVisited = USE_SNAPSHOT;
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == 0) {
                    distToTop = FLAG_CHAIN_OPTIMIZE;
                    distToBottom = FLAG_CHAIN_OPTIMIZE;
                    if (guideline.getRelativeBegin() != -1) {
                        distToTop = guideline.getRelativeBegin();
                    } else if (guideline.getRelativeEnd() != -1) {
                        distToBottom = guideline.getRelativeEnd();
                    }
                }
            } else if (widget.mBaseline.mTarget == null && widget.mTop.mTarget == null && widget.mBottom.mTarget == null) {
                distToTop += widget.getY();
            } else if (widget.mBottom.mTarget != null && widget.mTop.mTarget != null && (widget.mBottom.mTarget == widget.mTop.mTarget || (widget.mBottom.mTarget.mOwner == widget.mTop.mTarget.mOwner && widget.mBottom.mTarget.mOwner != widget.mParent))) {
                flags[FLAG_CHAIN_OPTIMIZE] = DEBUG;
                return;
            } else if (widget.mBaseline.isConnected()) {
                ConstraintWidget baseLineWidget = widget.mBaseline.mTarget.getOwner();
                if (!baseLineWidget.mVerticalWrapVisited) {
                    findVerticalWrapRecursive(baseLineWidget, flags);
                }
                distToTop = Math.max((baseLineWidget.mDistToTop - baseLineWidget.mHeight) + h, h);
                distToBottom = Math.max((baseLineWidget.mDistToBottom - baseLineWidget.mHeight) + h, h);
                if (widget.getVisibility() == OPTIMIZATION_CHAIN) {
                    distToTop -= widget.mHeight;
                    distToBottom -= widget.mHeight;
                }
                widget.mDistToTop = distToTop;
                widget.mDistToBottom = distToBottom;
                return;
            } else {
                if (widget.mTop.isConnected()) {
                    topWidget = widget.mTop.mTarget.getOwner();
                    distToTop += widget.mTop.getMargin();
                    if (!(topWidget.isRoot() || topWidget.mVerticalWrapVisited)) {
                        findVerticalWrapRecursive(topWidget, flags);
                    }
                }
                if (widget.mBottom.isConnected()) {
                    bottomWidget = widget.mBottom.mTarget.getOwner();
                    distToBottom += widget.mBottom.getMargin();
                    if (!(bottomWidget.isRoot() || bottomWidget.mVerticalWrapVisited)) {
                        findVerticalWrapRecursive(bottomWidget, flags);
                    }
                }
                if (!(widget.mTop.mTarget == null || topWidget.isRoot())) {
                    boolean z2;
                    if (widget.mTop.mTarget.getType() == Type.TOP) {
                        distToTop += topWidget.mDistToTop - topWidget.getOptimizerWrapHeight();
                    } else if (widget.mTop.mTarget.getType() == Type.BOTTOM) {
                        distToTop += topWidget.mDistToTop;
                    }
                    if (topWidget.mTopHasCentered || !(topWidget.mTop.mTarget == null || topWidget.mTop.mTarget.mOwner == widget || topWidget.mBottom.mTarget == null || topWidget.mBottom.mTarget.mOwner == widget || topWidget.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT)) {
                        z2 = USE_SNAPSHOT;
                    } else {
                        z2 = DEBUG;
                    }
                    widget.mTopHasCentered = z2;
                    if (widget.mTopHasCentered) {
                        if (topWidget.mBottom.mTarget != null) {
                            if (topWidget.mBottom.mTarget.mOwner != widget) {
                            }
                        }
                        distToTop += distToTop - topWidget.mDistToTop;
                    }
                }
                if (!(widget.mBottom.mTarget == null || bottomWidget.isRoot())) {
                    if (widget.mBottom.mTarget.getType() == Type.BOTTOM) {
                        distToBottom += bottomWidget.mDistToBottom - bottomWidget.getOptimizerWrapHeight();
                    } else if (widget.mBottom.mTarget.getType() == Type.TOP) {
                        distToBottom += bottomWidget.mDistToBottom;
                    }
                    if (bottomWidget.mBottomHasCentered || !(bottomWidget.mTop.mTarget == null || bottomWidget.mTop.mTarget.mOwner == widget || bottomWidget.mBottom.mTarget == null || bottomWidget.mBottom.mTarget.mOwner == widget || bottomWidget.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_CONSTRAINT)) {
                        z = USE_SNAPSHOT;
                    }
                    widget.mBottomHasCentered = z;
                    if (widget.mBottomHasCentered) {
                        if (bottomWidget.mTop.mTarget != null) {
                            if (bottomWidget.mTop.mTarget.mOwner != widget) {
                            }
                        }
                        distToBottom += distToBottom - bottomWidget.mDistToBottom;
                    }
                }
            }
            if (widget.getVisibility() == OPTIMIZATION_CHAIN) {
                distToTop -= widget.mHeight;
                distToBottom -= widget.mHeight;
            }
            widget.mDistToTop = distToTop;
            widget.mDistToBottom = distToBottom;
            return;
        }
        flags[FLAG_CHAIN_OPTIMIZE] = DEBUG;
    }

    public void findWrapSize(ArrayList<ConstraintWidget> children, boolean[] flags) {
        int j;
        int maxTopDist = FLAG_CHAIN_OPTIMIZE;
        int maxLeftDist = FLAG_CHAIN_OPTIMIZE;
        int maxRightDist = FLAG_CHAIN_OPTIMIZE;
        int maxBottomDist = FLAG_CHAIN_OPTIMIZE;
        int maxConnectWidth = FLAG_CHAIN_OPTIMIZE;
        int maxConnectHeight = FLAG_CHAIN_OPTIMIZE;
        int size = children.size();
        flags[FLAG_CHAIN_OPTIMIZE] = USE_SNAPSHOT;
        for (j = FLAG_CHAIN_OPTIMIZE; j < size; j += OPTIMIZATION_NONE) {
            ConstraintWidget widget = (ConstraintWidget) children.get(j);
            if (!widget.isRoot()) {
                if (!widget.mHorizontalWrapVisited) {
                    findHorizontalWrapRecursive(widget, flags);
                }
                if (!widget.mVerticalWrapVisited) {
                    findVerticalWrapRecursive(widget, flags);
                }
                if (flags[FLAG_CHAIN_OPTIMIZE]) {
                    int i;
                    int connectWidth = (widget.mDistToLeft + widget.mDistToRight) - widget.getWidth();
                    int connectHeight = (widget.mDistToTop + widget.mDistToBottom) - widget.getHeight();
                    if (widget.mHorizontalDimensionBehaviour == DimensionBehaviour.MATCH_PARENT) {
                        i = widget.mLeft.mMargin;
                        connectWidth = (widget.getWidth() + r0) + widget.mRight.mMargin;
                    }
                    if (widget.mVerticalDimensionBehaviour == DimensionBehaviour.MATCH_PARENT) {
                        i = widget.mTop.mMargin;
                        connectHeight = (widget.getHeight() + r0) + widget.mBottom.mMargin;
                    }
                    if (widget.getVisibility() == OPTIMIZATION_CHAIN) {
                        connectWidth = FLAG_CHAIN_OPTIMIZE;
                        connectHeight = FLAG_CHAIN_OPTIMIZE;
                    }
                    maxLeftDist = Math.max(maxLeftDist, widget.mDistToLeft);
                    maxRightDist = Math.max(maxRightDist, widget.mDistToRight);
                    maxBottomDist = Math.max(maxBottomDist, widget.mDistToBottom);
                    maxTopDist = Math.max(maxTopDist, widget.mDistToTop);
                    maxConnectWidth = Math.max(maxConnectWidth, connectWidth);
                    maxConnectHeight = Math.max(maxConnectHeight, connectHeight);
                } else {
                    return;
                }
            }
        }
        this.mWrapWidth = Math.max(this.mMinWidth, Math.max(Math.max(maxLeftDist, maxRightDist), maxConnectWidth));
        this.mWrapHeight = Math.max(this.mMinHeight, Math.max(Math.max(maxTopDist, maxBottomDist), maxConnectHeight));
        for (j = FLAG_CHAIN_OPTIMIZE; j < size; j += OPTIMIZATION_NONE) {
            ConstraintWidget child = (ConstraintWidget) children.get(j);
            child.mHorizontalWrapVisited = DEBUG;
            child.mVerticalWrapVisited = DEBUG;
            child.mLeftHasCentered = DEBUG;
            child.mRightHasCentered = DEBUG;
            child.mTopHasCentered = DEBUG;
            child.mBottomHasCentered = DEBUG;
        }
    }

    public int layoutFindGroups() {
        int j;
        Type[] dir = new Type[]{Type.LEFT, Type.RIGHT, Type.TOP, Type.BASELINE, Type.BOTTOM};
        int label = OPTIMIZATION_NONE;
        int size = this.mChildren.size();
        for (j = FLAG_CHAIN_OPTIMIZE; j < size; j += OPTIMIZATION_NONE) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(j);
            ConstraintAnchor anchor = widget.mLeft;
            if (anchor.mTarget == null) {
                anchor.mGroup = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            } else if (setGroup(anchor, label) == label) {
                label += OPTIMIZATION_NONE;
            }
            anchor = widget.mTop;
            if (anchor.mTarget == null) {
                anchor.mGroup = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            } else if (setGroup(anchor, label) == label) {
                label += OPTIMIZATION_NONE;
            }
            anchor = widget.mRight;
            if (anchor.mTarget == null) {
                anchor.mGroup = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            } else if (setGroup(anchor, label) == label) {
                label += OPTIMIZATION_NONE;
            }
            anchor = widget.mBottom;
            if (anchor.mTarget == null) {
                anchor.mGroup = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            } else if (setGroup(anchor, label) == label) {
                label += OPTIMIZATION_NONE;
            }
            anchor = widget.mBaseline;
            if (anchor.mTarget == null) {
                anchor.mGroup = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
            } else if (setGroup(anchor, label) == label) {
                label += OPTIMIZATION_NONE;
            }
        }
        boolean notDone = USE_SNAPSHOT;
        int count = FLAG_CHAIN_OPTIMIZE;
        int fix = FLAG_CHAIN_OPTIMIZE;
        while (notDone) {
            notDone = DEBUG;
            count += OPTIMIZATION_NONE;
            j = FLAG_CHAIN_OPTIMIZE;
            while (j < size) {
                widget = (ConstraintWidget) this.mChildren.get(j);
                int i = FLAG_CHAIN_OPTIMIZE;
                while (true) {
                    int length = dir.length;
                    if (i < r0) {
                        anchor = null;
                        switch (C00062.f2x1d400623[dir[i].ordinal()]) {
                            case OPTIMIZATION_NONE /*1*/:
                                anchor = widget.mLeft;
                                break;
                            case OPTIMIZATION_ALL /*2*/:
                                anchor = widget.mTop;
                                break;
                            case CHAIN_LAST_VISIBLE /*3*/:
                                anchor = widget.mRight;
                                break;
                            case OPTIMIZATION_BASIC /*4*/:
                                anchor = widget.mBottom;
                                break;
                            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                                anchor = widget.mBaseline;
                                break;
                        }
                        ConstraintAnchor target = anchor.mTarget;
                        if (target != null) {
                            if (!(target.mOwner.getParent() == null || target.mGroup == anchor.mGroup)) {
                                length = anchor.mGroup > target.mGroup ? target.mGroup : anchor.mGroup;
                                anchor.mGroup = length;
                                target.mGroup = length;
                                fix += OPTIMIZATION_NONE;
                                notDone = USE_SNAPSHOT;
                            }
                            ConstraintAnchor opposite = target.getOpposite();
                            if (!(opposite == null || opposite.mGroup == anchor.mGroup)) {
                                length = anchor.mGroup > opposite.mGroup ? opposite.mGroup : anchor.mGroup;
                                anchor.mGroup = length;
                                opposite.mGroup = length;
                                fix += OPTIMIZATION_NONE;
                                notDone = USE_SNAPSHOT;
                            }
                        }
                        i += OPTIMIZATION_NONE;
                    } else {
                        j += OPTIMIZATION_NONE;
                    }
                }
            }
        }
        int[] table = new int[((this.mChildren.size() * dir.length) + OPTIMIZATION_NONE)];
        Arrays.fill(table, -1);
        j = FLAG_CHAIN_OPTIMIZE;
        int index = FLAG_CHAIN_OPTIMIZE;
        while (j < size) {
            int i2;
            widget = (ConstraintWidget) this.mChildren.get(j);
            anchor = widget.mLeft;
            length = anchor.mGroup;
            if (r0 != Integer.MAX_VALUE) {
                int g;
                g = anchor.mGroup;
                if (table[g] == -1) {
                    i2 = index + OPTIMIZATION_NONE;
                    table[g] = index;
                } else {
                    i2 = index;
                }
                anchor.mGroup = table[g];
            } else {
                i2 = index;
            }
            anchor = widget.mTop;
            length = anchor.mGroup;
            if (r0 != Integer.MAX_VALUE) {
                g = anchor.mGroup;
                if (table[g] == -1) {
                    index = i2 + OPTIMIZATION_NONE;
                    table[g] = i2;
                    i2 = index;
                }
                anchor.mGroup = table[g];
            }
            anchor = widget.mRight;
            length = anchor.mGroup;
            if (r0 != Integer.MAX_VALUE) {
                g = anchor.mGroup;
                if (table[g] == -1) {
                    index = i2 + OPTIMIZATION_NONE;
                    table[g] = i2;
                    i2 = index;
                }
                anchor.mGroup = table[g];
            }
            anchor = widget.mBottom;
            length = anchor.mGroup;
            if (r0 != Integer.MAX_VALUE) {
                g = anchor.mGroup;
                if (table[g] == -1) {
                    index = i2 + OPTIMIZATION_NONE;
                    table[g] = i2;
                    i2 = index;
                }
                anchor.mGroup = table[g];
            }
            anchor = widget.mBaseline;
            length = anchor.mGroup;
            if (r0 != Integer.MAX_VALUE) {
                g = anchor.mGroup;
                if (table[g] == -1) {
                    index = i2 + OPTIMIZATION_NONE;
                    table[g] = i2;
                    i2 = index;
                }
                anchor.mGroup = table[g];
            }
            j += OPTIMIZATION_NONE;
            index = i2;
        }
        return index;
    }

    public void layoutWithGroup(int numOfGroups) {
        int i;
        int prex = this.mX;
        int prey = this.mY;
        if (this.mParent != null) {
            if (this.mSnapshot == null) {
                this.mSnapshot = new Snapshot(this);
            }
            this.mSnapshot.updateFrom(this);
            this.mX = FLAG_CHAIN_OPTIMIZE;
            this.mY = FLAG_CHAIN_OPTIMIZE;
            resetAnchors();
            resetSolverVariables(this.mSystem.getCache());
        } else {
            this.mX = FLAG_CHAIN_OPTIMIZE;
            this.mY = FLAG_CHAIN_OPTIMIZE;
        }
        int count = this.mChildren.size();
        for (i = FLAG_CHAIN_OPTIMIZE; i < count; i += OPTIMIZATION_NONE) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof WidgetContainer) {
                ((WidgetContainer) widget).layout();
            }
        }
        this.mLeft.mGroup = FLAG_CHAIN_OPTIMIZE;
        this.mRight.mGroup = FLAG_CHAIN_OPTIMIZE;
        this.mTop.mGroup = OPTIMIZATION_NONE;
        this.mBottom.mGroup = OPTIMIZATION_NONE;
        this.mSystem.reset();
        for (i = FLAG_CHAIN_OPTIMIZE; i < numOfGroups; i += OPTIMIZATION_NONE) {
            try {
                addToSolver(this.mSystem, i);
                this.mSystem.minimize();
                updateFromSolver(this.mSystem, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
            updateFromSolver(this.mSystem, -2);
        }
        if (this.mParent != null) {
            int width = getWidth();
            int height = getHeight();
            this.mSnapshot.applyTo(this);
            setWidth(width);
            setHeight(height);
        } else {
            this.mX = prex;
            this.mY = prey;
        }
        if (this == getRootConstraintContainer()) {
            updateDrawPosition();
        }
    }

    public boolean handlesInternalConstraints() {
        return DEBUG;
    }

    public ArrayList<Guideline> getVerticalGuidelines() {
        ArrayList<Guideline> guidelines = new ArrayList();
        int mChildrenSize = this.mChildren.size();
        for (int i = FLAG_CHAIN_OPTIMIZE; i < mChildrenSize; i += OPTIMIZATION_NONE) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == OPTIMIZATION_NONE) {
                    guidelines.add(guideline);
                }
            }
        }
        return guidelines;
    }

    public ArrayList<Guideline> getHorizontalGuidelines() {
        ArrayList<Guideline> guidelines = new ArrayList();
        int mChildrenSize = this.mChildren.size();
        for (int i = FLAG_CHAIN_OPTIMIZE; i < mChildrenSize; i += OPTIMIZATION_NONE) {
            ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
            if (widget instanceof Guideline) {
                Guideline guideline = (Guideline) widget;
                if (guideline.getOrientation() == 0) {
                    guidelines.add(guideline);
                }
            }
        }
        return guidelines;
    }

    public LinearSystem getSystem() {
        return this.mSystem;
    }

    private void resetChains() {
        this.mHorizontalChainsSize = FLAG_CHAIN_OPTIMIZE;
        this.mVerticalChainsSize = FLAG_CHAIN_OPTIMIZE;
    }

    void addChain(ConstraintWidget constraintWidget, int type) {
        ConstraintWidget widget = constraintWidget;
        if (type == 0) {
            while (widget.mLeft.mTarget != null && widget.mLeft.mTarget.mOwner.mRight.mTarget != null && widget.mLeft.mTarget.mOwner.mRight.mTarget == widget.mLeft && widget.mLeft.mTarget.mOwner != widget) {
                widget = widget.mLeft.mTarget.mOwner;
            }
            addHorizontalChain(widget);
        } else if (type == OPTIMIZATION_NONE) {
            while (widget.mTop.mTarget != null && widget.mTop.mTarget.mOwner.mBottom.mTarget != null && widget.mTop.mTarget.mOwner.mBottom.mTarget == widget.mTop && widget.mTop.mTarget.mOwner != widget) {
                widget = widget.mTop.mTarget.mOwner;
            }
            addVerticalChain(widget);
        }
    }

    private void addHorizontalChain(ConstraintWidget widget) {
        int i = FLAG_CHAIN_OPTIMIZE;
        while (i < this.mHorizontalChainsSize) {
            if (this.mHorizontalChainsArray[i] != widget) {
                i += OPTIMIZATION_NONE;
            } else {
                return;
            }
        }
        if (this.mHorizontalChainsSize + OPTIMIZATION_NONE >= this.mHorizontalChainsArray.length) {
            this.mHorizontalChainsArray = (ConstraintWidget[]) Arrays.copyOf(this.mHorizontalChainsArray, this.mHorizontalChainsArray.length * OPTIMIZATION_ALL);
        }
        this.mHorizontalChainsArray[this.mHorizontalChainsSize] = widget;
        this.mHorizontalChainsSize += OPTIMIZATION_NONE;
    }

    private void addVerticalChain(ConstraintWidget widget) {
        int i = FLAG_CHAIN_OPTIMIZE;
        while (i < this.mVerticalChainsSize) {
            if (this.mVerticalChainsArray[i] != widget) {
                i += OPTIMIZATION_NONE;
            } else {
                return;
            }
        }
        if (this.mVerticalChainsSize + OPTIMIZATION_NONE >= this.mVerticalChainsArray.length) {
            this.mVerticalChainsArray = (ConstraintWidget[]) Arrays.copyOf(this.mVerticalChainsArray, this.mVerticalChainsArray.length * OPTIMIZATION_ALL);
        }
        this.mVerticalChainsArray[this.mVerticalChainsSize] = widget;
        this.mVerticalChainsSize += OPTIMIZATION_NONE;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int countMatchConstraintsChainedWidgets(android.support.constraint.solver.LinearSystem r12, android.support.constraint.solver.widgets.ConstraintWidget[] r13, android.support.constraint.solver.widgets.ConstraintWidget r14, int r15, boolean[] r16) {
        /*
        r11 = this;
        r0 = 0;
        r7 = 0;
        r8 = 1;
        r16[r7] = r8;
        r7 = 1;
        r8 = 0;
        r16[r7] = r8;
        r7 = 0;
        r8 = 0;
        r13[r7] = r8;
        r7 = 2;
        r8 = 0;
        r13[r7] = r8;
        r7 = 1;
        r8 = 0;
        r13[r7] = r8;
        r7 = 3;
        r8 = 0;
        r13[r7] = r8;
        if (r15 != 0) goto L_0x0110;
    L_0x001b:
        r4 = 1;
        r2 = r14;
        r5 = 0;
        r7 = r14.mLeft;
        r7 = r7.mTarget;
        if (r7 == 0) goto L_0x002d;
    L_0x0024:
        r7 = r14.mLeft;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        if (r7 == r11) goto L_0x002d;
    L_0x002c:
        r4 = 0;
    L_0x002d:
        r7 = 0;
        r14.mHorizontalNextWidget = r7;
        r3 = 0;
        r7 = r14.getVisibility();
        r8 = 8;
        if (r7 == r8) goto L_0x003a;
    L_0x0039:
        r3 = r14;
    L_0x003a:
        r6 = r3;
    L_0x003b:
        r7 = r14.mRight;
        r7 = r7.mTarget;
        if (r7 == 0) goto L_0x00a2;
    L_0x0041:
        r7 = 0;
        r14.mHorizontalNextWidget = r7;
        r7 = r14.getVisibility();
        r8 = 8;
        if (r7 == r8) goto L_0x00d3;
    L_0x004c:
        if (r3 != 0) goto L_0x004f;
    L_0x004e:
        r3 = r14;
    L_0x004f:
        if (r6 == 0) goto L_0x0055;
    L_0x0051:
        if (r6 == r14) goto L_0x0055;
    L_0x0053:
        r6.mHorizontalNextWidget = r14;
    L_0x0055:
        r6 = r14;
    L_0x0056:
        r7 = r14.getVisibility();
        r8 = 8;
        if (r7 == r8) goto L_0x0096;
    L_0x005e:
        r7 = r14.mHorizontalDimensionBehaviour;
        r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (r7 != r8) goto L_0x0096;
    L_0x0064:
        r7 = r14.mVerticalDimensionBehaviour;
        r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (r7 != r8) goto L_0x006e;
    L_0x006a:
        r7 = 0;
        r8 = 0;
        r16[r7] = r8;
    L_0x006e:
        r7 = r14.mDimensionRatio;
        r8 = 0;
        r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1));
        if (r7 > 0) goto L_0x0096;
    L_0x0075:
        r7 = 0;
        r8 = 0;
        r16[r7] = r8;
        r7 = r0 + 1;
        r8 = r11.mMatchConstraintsChainedWidgets;
        r8 = r8.length;
        if (r7 < r8) goto L_0x008f;
    L_0x0080:
        r7 = r11.mMatchConstraintsChainedWidgets;
        r8 = r11.mMatchConstraintsChainedWidgets;
        r8 = r8.length;
        r8 = r8 * 2;
        r7 = java.util.Arrays.copyOf(r7, r8);
        r7 = (android.support.constraint.solver.widgets.ConstraintWidget[]) r7;
        r11.mMatchConstraintsChainedWidgets = r7;
    L_0x008f:
        r7 = r11.mMatchConstraintsChainedWidgets;
        r1 = r0 + 1;
        r7[r0] = r14;
        r0 = r1;
    L_0x0096:
        r7 = r14.mRight;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        r7 = r7.mLeft;
        r7 = r7.mTarget;
        if (r7 != 0) goto L_0x00f1;
    L_0x00a2:
        r7 = r14.mRight;
        r7 = r7.mTarget;
        if (r7 == 0) goto L_0x00b1;
    L_0x00a8:
        r7 = r14.mRight;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        if (r7 == r11) goto L_0x00b1;
    L_0x00b0:
        r4 = 0;
    L_0x00b1:
        r7 = r2.mLeft;
        r7 = r7.mTarget;
        if (r7 == 0) goto L_0x00bd;
    L_0x00b7:
        r7 = r5.mRight;
        r7 = r7.mTarget;
        if (r7 != 0) goto L_0x00c1;
    L_0x00bd:
        r7 = 1;
        r8 = 1;
        r16[r7] = r8;
    L_0x00c1:
        r2.mHorizontalChainFixedPosition = r4;
        r7 = 0;
        r5.mHorizontalNextWidget = r7;
        r7 = 0;
        r13[r7] = r2;
        r7 = 2;
        r13[r7] = r3;
        r7 = 1;
        r13[r7] = r5;
        r7 = 3;
        r13[r7] = r6;
    L_0x00d2:
        return r0;
    L_0x00d3:
        r7 = r14.mLeft;
        r7 = r7.mSolverVariable;
        r8 = r14.mLeft;
        r8 = r8.mTarget;
        r8 = r8.mSolverVariable;
        r9 = 0;
        r10 = 5;
        r12.addEquality(r7, r8, r9, r10);
        r7 = r14.mRight;
        r7 = r7.mSolverVariable;
        r8 = r14.mLeft;
        r8 = r8.mSolverVariable;
        r9 = 0;
        r10 = 5;
        r12.addEquality(r7, r8, r9, r10);
        goto L_0x0056;
    L_0x00f1:
        r7 = r14.mRight;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        r7 = r7.mLeft;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        if (r7 != r14) goto L_0x00a2;
    L_0x00ff:
        r7 = r14.mRight;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        if (r7 == r14) goto L_0x00a2;
    L_0x0107:
        r7 = r14.mRight;
        r7 = r7.mTarget;
        r14 = r7.mOwner;
        r5 = r14;
        goto L_0x003b;
    L_0x0110:
        r4 = 1;
        r2 = r14;
        r5 = 0;
        r7 = r14.mTop;
        r7 = r7.mTarget;
        if (r7 == 0) goto L_0x0122;
    L_0x0119:
        r7 = r14.mTop;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        if (r7 == r11) goto L_0x0122;
    L_0x0121:
        r4 = 0;
    L_0x0122:
        r7 = 0;
        r14.mVerticalNextWidget = r7;
        r3 = 0;
        r7 = r14.getVisibility();
        r8 = 8;
        if (r7 == r8) goto L_0x012f;
    L_0x012e:
        r3 = r14;
    L_0x012f:
        r6 = r3;
    L_0x0130:
        r7 = r14.mBottom;
        r7 = r7.mTarget;
        if (r7 == 0) goto L_0x0197;
    L_0x0136:
        r7 = 0;
        r14.mVerticalNextWidget = r7;
        r7 = r14.getVisibility();
        r8 = 8;
        if (r7 == r8) goto L_0x01c9;
    L_0x0141:
        if (r3 != 0) goto L_0x0144;
    L_0x0143:
        r3 = r14;
    L_0x0144:
        if (r6 == 0) goto L_0x014a;
    L_0x0146:
        if (r6 == r14) goto L_0x014a;
    L_0x0148:
        r6.mVerticalNextWidget = r14;
    L_0x014a:
        r6 = r14;
    L_0x014b:
        r7 = r14.getVisibility();
        r8 = 8;
        if (r7 == r8) goto L_0x018b;
    L_0x0153:
        r7 = r14.mVerticalDimensionBehaviour;
        r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (r7 != r8) goto L_0x018b;
    L_0x0159:
        r7 = r14.mHorizontalDimensionBehaviour;
        r8 = android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
        if (r7 != r8) goto L_0x0163;
    L_0x015f:
        r7 = 0;
        r8 = 0;
        r16[r7] = r8;
    L_0x0163:
        r7 = r14.mDimensionRatio;
        r8 = 0;
        r7 = (r7 > r8 ? 1 : (r7 == r8 ? 0 : -1));
        if (r7 > 0) goto L_0x018b;
    L_0x016a:
        r7 = 0;
        r8 = 0;
        r16[r7] = r8;
        r7 = r0 + 1;
        r8 = r11.mMatchConstraintsChainedWidgets;
        r8 = r8.length;
        if (r7 < r8) goto L_0x0184;
    L_0x0175:
        r7 = r11.mMatchConstraintsChainedWidgets;
        r8 = r11.mMatchConstraintsChainedWidgets;
        r8 = r8.length;
        r8 = r8 * 2;
        r7 = java.util.Arrays.copyOf(r7, r8);
        r7 = (android.support.constraint.solver.widgets.ConstraintWidget[]) r7;
        r11.mMatchConstraintsChainedWidgets = r7;
    L_0x0184:
        r7 = r11.mMatchConstraintsChainedWidgets;
        r1 = r0 + 1;
        r7[r0] = r14;
        r0 = r1;
    L_0x018b:
        r7 = r14.mBottom;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        r7 = r7.mTop;
        r7 = r7.mTarget;
        if (r7 != 0) goto L_0x01e7;
    L_0x0197:
        r7 = r14.mBottom;
        r7 = r7.mTarget;
        if (r7 == 0) goto L_0x01a6;
    L_0x019d:
        r7 = r14.mBottom;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        if (r7 == r11) goto L_0x01a6;
    L_0x01a5:
        r4 = 0;
    L_0x01a6:
        r7 = r2.mTop;
        r7 = r7.mTarget;
        if (r7 == 0) goto L_0x01b2;
    L_0x01ac:
        r7 = r5.mBottom;
        r7 = r7.mTarget;
        if (r7 != 0) goto L_0x01b6;
    L_0x01b2:
        r7 = 1;
        r8 = 1;
        r16[r7] = r8;
    L_0x01b6:
        r2.mVerticalChainFixedPosition = r4;
        r7 = 0;
        r5.mVerticalNextWidget = r7;
        r7 = 0;
        r13[r7] = r2;
        r7 = 2;
        r13[r7] = r3;
        r7 = 1;
        r13[r7] = r5;
        r7 = 3;
        r13[r7] = r6;
        goto L_0x00d2;
    L_0x01c9:
        r7 = r14.mTop;
        r7 = r7.mSolverVariable;
        r8 = r14.mTop;
        r8 = r8.mTarget;
        r8 = r8.mSolverVariable;
        r9 = 0;
        r10 = 5;
        r12.addEquality(r7, r8, r9, r10);
        r7 = r14.mBottom;
        r7 = r7.mSolverVariable;
        r8 = r14.mTop;
        r8 = r8.mSolverVariable;
        r9 = 0;
        r10 = 5;
        r12.addEquality(r7, r8, r9, r10);
        goto L_0x014b;
    L_0x01e7:
        r7 = r14.mBottom;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        r7 = r7.mTop;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        if (r7 != r14) goto L_0x0197;
    L_0x01f5:
        r7 = r14.mBottom;
        r7 = r7.mTarget;
        r7 = r7.mOwner;
        if (r7 == r14) goto L_0x0197;
    L_0x01fd:
        r7 = r14.mBottom;
        r7 = r7.mTarget;
        r14 = r7.mOwner;
        r5 = r14;
        goto L_0x0130;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.constraint.solver.widgets.ConstraintWidgetContainer.countMatchConstraintsChainedWidgets(android.support.constraint.solver.LinearSystem, android.support.constraint.solver.widgets.ConstraintWidget[], android.support.constraint.solver.widgets.ConstraintWidget, int, boolean[]):int");
    }
}
