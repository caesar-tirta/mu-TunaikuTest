package android.support.constraint.solver.widgets;

import android.support.constraint.solver.Cache;
import android.support.constraint.solver.SolverVariable;
import android.support.v4.app.NotificationCompat.WearableExtender;
import android.support.v4.widget.DrawerLayout;
import com.amicel.tunaikudemo.BuildConfig;
import com.amicel.tunaikudemo.C0203R;
import java.util.ArrayList;
import java.util.HashSet;

public class ConstraintAnchor {
    private static final boolean ALLOW_BINARY = false;
    public static final int ANY_GROUP = Integer.MAX_VALUE;
    public static final int APPLY_GROUP_RESULTS = -2;
    public static final int AUTO_CONSTRAINT_CREATOR = 2;
    public static final int SCOUT_CREATOR = 1;
    private static final int UNSET_GONE_MARGIN = -1;
    public static final int USER_CREATOR = 0;
    public static final boolean USE_CENTER_ANCHOR = false;
    private int mConnectionCreator;
    private ConnectionType mConnectionType;
    int mGoneMargin;
    int mGroup;
    public int mMargin;
    final ConstraintWidget mOwner;
    SolverVariable mSolverVariable;
    private Strength mStrength;
    ConstraintAnchor mTarget;
    final Type mType;

    /* renamed from: android.support.constraint.solver.widgets.ConstraintAnchor.1 */
    static /* synthetic */ class C00041 {
        static final /* synthetic */ int[] f0x1d400623;

        static {
            f0x1d400623 = new int[Type.values().length];
            try {
                f0x1d400623[Type.CENTER.ordinal()] = ConstraintAnchor.SCOUT_CREATOR;
            } catch (NoSuchFieldError e) {
            }
            try {
                f0x1d400623[Type.LEFT.ordinal()] = ConstraintAnchor.AUTO_CONSTRAINT_CREATOR;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f0x1d400623[Type.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f0x1d400623[Type.TOP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f0x1d400623[Type.BOTTOM.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f0x1d400623[Type.CENTER_X.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f0x1d400623[Type.CENTER_Y.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                f0x1d400623[Type.BASELINE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public enum ConnectionType {
        RELAXED,
        STRICT
    }

    public enum Strength {
        NONE,
        STRONG,
        WEAK
    }

    public enum Type {
        NONE,
        LEFT,
        TOP,
        RIGHT,
        BOTTOM,
        BASELINE,
        CENTER,
        CENTER_X,
        CENTER_Y
    }

    public ConstraintAnchor(ConstraintWidget owner, Type type) {
        this.mMargin = 0;
        this.mGoneMargin = UNSET_GONE_MARGIN;
        this.mStrength = Strength.NONE;
        this.mConnectionType = ConnectionType.RELAXED;
        this.mConnectionCreator = 0;
        this.mGroup = ANY_GROUP;
        this.mOwner = owner;
        this.mType = type;
    }

    public SolverVariable getSolverVariable() {
        return this.mSolverVariable;
    }

    public void resetSolverVariable(Cache cache) {
        if (this.mSolverVariable == null) {
            this.mSolverVariable = new SolverVariable(android.support.constraint.solver.SolverVariable.Type.UNRESTRICTED);
        } else {
            this.mSolverVariable.reset();
        }
    }

    public void setGroup(int group) {
        this.mGroup = group;
    }

    public int getGroup() {
        return this.mGroup;
    }

    public ConstraintWidget getOwner() {
        return this.mOwner;
    }

    public Type getType() {
        return this.mType;
    }

    public int getMargin() {
        if (this.mOwner.getVisibility() == 8) {
            return 0;
        }
        if (this.mGoneMargin <= UNSET_GONE_MARGIN || this.mTarget == null || this.mTarget.mOwner.getVisibility() != 8) {
            return this.mMargin;
        }
        return this.mGoneMargin;
    }

    public Strength getStrength() {
        return this.mStrength;
    }

    public ConstraintAnchor getTarget() {
        return this.mTarget;
    }

    public ConnectionType getConnectionType() {
        return this.mConnectionType;
    }

    public void setConnectionType(ConnectionType type) {
        this.mConnectionType = type;
    }

    public int getConnectionCreator() {
        return this.mConnectionCreator;
    }

    public void setConnectionCreator(int creator) {
        this.mConnectionCreator = creator;
    }

    public void reset() {
        this.mTarget = null;
        this.mMargin = 0;
        this.mGoneMargin = UNSET_GONE_MARGIN;
        this.mStrength = Strength.STRONG;
        this.mConnectionCreator = 0;
        this.mConnectionType = ConnectionType.RELAXED;
    }

    public boolean connect(ConstraintAnchor toAnchor, int margin, Strength strength, int creator) {
        return connect(toAnchor, margin, UNSET_GONE_MARGIN, strength, creator, ALLOW_BINARY);
    }

    public boolean connect(ConstraintAnchor toAnchor, int margin, int goneMargin, Strength strength, int creator, boolean forceConnection) {
        if (toAnchor == null) {
            this.mTarget = null;
            this.mMargin = 0;
            this.mGoneMargin = UNSET_GONE_MARGIN;
            this.mStrength = Strength.NONE;
            this.mConnectionCreator = AUTO_CONSTRAINT_CREATOR;
            return true;
        } else if (!forceConnection && !isValidConnection(toAnchor)) {
            return ALLOW_BINARY;
        } else {
            this.mTarget = toAnchor;
            if (margin > 0) {
                this.mMargin = margin;
            } else {
                this.mMargin = 0;
            }
            this.mGoneMargin = goneMargin;
            this.mStrength = strength;
            this.mConnectionCreator = creator;
            return true;
        }
    }

    public boolean connect(ConstraintAnchor toAnchor, int margin, int creator) {
        return connect(toAnchor, margin, UNSET_GONE_MARGIN, Strength.STRONG, creator, ALLOW_BINARY);
    }

    public boolean connect(ConstraintAnchor toAnchor, int margin) {
        return connect(toAnchor, margin, UNSET_GONE_MARGIN, Strength.STRONG, 0, ALLOW_BINARY);
    }

    public boolean isConnected() {
        return this.mTarget != null ? true : ALLOW_BINARY;
    }

    public boolean isValidConnection(ConstraintAnchor anchor) {
        boolean z = true;
        if (anchor == null) {
            return ALLOW_BINARY;
        }
        Type target = anchor.getType();
        if (target != this.mType) {
            boolean isCompatible;
            switch (C00041.f0x1d400623[this.mType.ordinal()]) {
                case SCOUT_CREATOR /*1*/:
                    if (target == Type.BASELINE || target == Type.CENTER_X || target == Type.CENTER_Y) {
                        z = ALLOW_BINARY;
                    }
                    return z;
                case AUTO_CONSTRAINT_CREATOR /*2*/:
                case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                    if (target == Type.LEFT || target == Type.RIGHT) {
                        isCompatible = true;
                    } else {
                        isCompatible = ALLOW_BINARY;
                    }
                    if (anchor.getOwner() instanceof Guideline) {
                        if (isCompatible || target == Type.CENTER_X) {
                            isCompatible = true;
                        } else {
                            isCompatible = ALLOW_BINARY;
                        }
                    }
                    return isCompatible;
                case ConstraintWidgetContainer.OPTIMIZATION_BASIC /*4*/:
                case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                    if (target == Type.TOP || target == Type.BOTTOM) {
                        isCompatible = true;
                    } else {
                        isCompatible = ALLOW_BINARY;
                    }
                    if (anchor.getOwner() instanceof Guideline) {
                        if (isCompatible || target == Type.CENTER_Y) {
                            isCompatible = true;
                        } else {
                            isCompatible = ALLOW_BINARY;
                        }
                    }
                    return isCompatible;
                default:
                    return ALLOW_BINARY;
            }
        } else if (this.mType == Type.CENTER) {
            return ALLOW_BINARY;
        } else {
            if (this.mType != Type.BASELINE || (anchor.getOwner().hasBaseline() && getOwner().hasBaseline())) {
                return true;
            }
            return ALLOW_BINARY;
        }
    }

    public boolean isSideAnchor() {
        switch (C00041.f0x1d400623[this.mType.ordinal()]) {
            case AUTO_CONSTRAINT_CREATOR /*2*/:
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
            case ConstraintWidgetContainer.OPTIMIZATION_BASIC /*4*/:
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return true;
            default:
                return ALLOW_BINARY;
        }
    }

    public boolean isSimilarDimensionConnection(ConstraintAnchor anchor) {
        boolean z = true;
        Type target = anchor.getType();
        if (target == this.mType) {
            return true;
        }
        switch (C00041.f0x1d400623[this.mType.ordinal()]) {
            case SCOUT_CREATOR /*1*/:
                if (target == Type.BASELINE) {
                    z = ALLOW_BINARY;
                }
                return z;
            case AUTO_CONSTRAINT_CREATOR /*2*/:
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
            case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                if (target == Type.LEFT || target == Type.RIGHT || target == Type.CENTER_X) {
                    return true;
                }
                return ALLOW_BINARY;
            case ConstraintWidgetContainer.OPTIMIZATION_BASIC /*4*/:
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
            case C0203R.styleable.Toolbar_contentInsetLeft /*7*/:
            case ConstraintWidgetContainer.OPTIMIZATION_CHAIN /*8*/:
                if (target == Type.TOP || target == Type.BOTTOM || target == Type.CENTER_Y || target == Type.BASELINE) {
                    return true;
                }
                return ALLOW_BINARY;
            default:
                return ALLOW_BINARY;
        }
    }

    public void setStrength(Strength strength) {
        if (isConnected()) {
            this.mStrength = strength;
        }
    }

    public void setMargin(int margin) {
        if (isConnected()) {
            this.mMargin = margin;
        }
    }

    public void setGoneMargin(int margin) {
        if (isConnected()) {
            this.mGoneMargin = margin;
        }
    }

    public boolean isVerticalAnchor() {
        switch (C00041.f0x1d400623[this.mType.ordinal()]) {
            case SCOUT_CREATOR /*1*/:
            case AUTO_CONSTRAINT_CREATOR /*2*/:
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
            case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                return ALLOW_BINARY;
            default:
                return true;
        }
    }

    public String toString() {
        return this.mOwner.getDebugName() + ":" + this.mType.toString() + (this.mTarget != null ? " connected to " + this.mTarget.toString(new HashSet()) : BuildConfig.FLAVOR);
    }

    private String toString(HashSet<ConstraintAnchor> visited) {
        if (!visited.add(this)) {
            return "<-";
        }
        return this.mOwner.getDebugName() + ":" + this.mType.toString() + (this.mTarget != null ? " connected to " + this.mTarget.toString(visited) : BuildConfig.FLAVOR);
    }

    public int getSnapPriorityLevel() {
        switch (C00041.f0x1d400623[this.mType.ordinal()]) {
            case SCOUT_CREATOR /*1*/:
                return 3;
            case AUTO_CONSTRAINT_CREATOR /*2*/:
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
            case C0203R.styleable.Toolbar_contentInsetLeft /*7*/:
                return SCOUT_CREATOR;
            case ConstraintWidgetContainer.OPTIMIZATION_BASIC /*4*/:
                return 0;
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return 0;
            case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                return 0;
            case ConstraintWidgetContainer.OPTIMIZATION_CHAIN /*8*/:
                return AUTO_CONSTRAINT_CREATOR;
            default:
                return 0;
        }
    }

    public int getPriorityLevel() {
        switch (C00041.f0x1d400623[this.mType.ordinal()]) {
            case SCOUT_CREATOR /*1*/:
                return AUTO_CONSTRAINT_CREATOR;
            case AUTO_CONSTRAINT_CREATOR /*2*/:
                return AUTO_CONSTRAINT_CREATOR;
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                return AUTO_CONSTRAINT_CREATOR;
            case ConstraintWidgetContainer.OPTIMIZATION_BASIC /*4*/:
                return AUTO_CONSTRAINT_CREATOR;
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return AUTO_CONSTRAINT_CREATOR;
            case ConstraintWidgetContainer.OPTIMIZATION_CHAIN /*8*/:
                return SCOUT_CREATOR;
            default:
                return 0;
        }
    }

    public boolean isSnapCompatibleWith(ConstraintAnchor anchor) {
        if (this.mType == Type.CENTER) {
            return ALLOW_BINARY;
        }
        if (this.mType == anchor.getType()) {
            return true;
        }
        switch (C00041.f0x1d400623[this.mType.ordinal()]) {
            case AUTO_CONSTRAINT_CREATOR /*2*/:
                switch (C00041.f0x1d400623[anchor.getType().ordinal()]) {
                    case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                        return true;
                    case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                        return true;
                    default:
                        return ALLOW_BINARY;
                }
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                switch (C00041.f0x1d400623[anchor.getType().ordinal()]) {
                    case AUTO_CONSTRAINT_CREATOR /*2*/:
                        return true;
                    case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                        return true;
                    default:
                        return ALLOW_BINARY;
                }
            case ConstraintWidgetContainer.OPTIMIZATION_BASIC /*4*/:
                switch (C00041.f0x1d400623[anchor.getType().ordinal()]) {
                    case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                        return true;
                    case C0203R.styleable.Toolbar_contentInsetLeft /*7*/:
                        return true;
                    default:
                        return ALLOW_BINARY;
                }
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                switch (C00041.f0x1d400623[anchor.getType().ordinal()]) {
                    case ConstraintWidgetContainer.OPTIMIZATION_BASIC /*4*/:
                        return true;
                    case C0203R.styleable.Toolbar_contentInsetLeft /*7*/:
                        return true;
                    default:
                        return ALLOW_BINARY;
                }
            case FragmentManagerImpl.ANIM_STYLE_FADE_EXIT /*6*/:
                switch (C00041.f0x1d400623[anchor.getType().ordinal()]) {
                    case AUTO_CONSTRAINT_CREATOR /*2*/:
                        return true;
                    case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                        return true;
                    default:
                        return ALLOW_BINARY;
                }
            case C0203R.styleable.Toolbar_contentInsetLeft /*7*/:
                switch (C00041.f0x1d400623[anchor.getType().ordinal()]) {
                    case ConstraintWidgetContainer.OPTIMIZATION_BASIC /*4*/:
                        return true;
                    case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                        return true;
                    default:
                        return ALLOW_BINARY;
                }
            default:
                return ALLOW_BINARY;
        }
    }

    public boolean isConnectionAllowed(ConstraintWidget target, ConstraintAnchor anchor) {
        return isConnectionAllowed(target);
    }

    public boolean isConnectionAllowed(ConstraintWidget target) {
        if (isConnectionToMe(target, new HashSet())) {
            return ALLOW_BINARY;
        }
        ConstraintWidget parent = getOwner().getParent();
        if (parent == target) {
            return true;
        }
        if (target.getParent() == parent) {
            return true;
        }
        return ALLOW_BINARY;
    }

    private boolean isConnectionToMe(ConstraintWidget target, HashSet<ConstraintWidget> checked) {
        if (checked.contains(target)) {
            return ALLOW_BINARY;
        }
        checked.add(target);
        if (target == getOwner()) {
            return true;
        }
        ArrayList<ConstraintAnchor> targetAnchors = target.getAnchors();
        int targetAnchorsSize = targetAnchors.size();
        for (int i = 0; i < targetAnchorsSize; i += SCOUT_CREATOR) {
            ConstraintAnchor anchor = (ConstraintAnchor) targetAnchors.get(i);
            if (anchor.isSimilarDimensionConnection(this) && anchor.isConnected() && isConnectionToMe(anchor.getTarget().getOwner(), checked)) {
                return true;
            }
        }
        return ALLOW_BINARY;
    }

    public final ConstraintAnchor getOpposite() {
        switch (C00041.f0x1d400623[this.mType.ordinal()]) {
            case AUTO_CONSTRAINT_CREATOR /*2*/:
                return this.mOwner.mRight;
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
                return this.mOwner.mLeft;
            case ConstraintWidgetContainer.OPTIMIZATION_BASIC /*4*/:
                return this.mOwner.mBottom;
            case WearableExtender.SIZE_FULL_SCREEN /*5*/:
                return this.mOwner.mTop;
            default:
                return null;
        }
    }
}
