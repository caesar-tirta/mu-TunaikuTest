package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.v4.widget.DrawerLayout;
import java.util.ArrayList;

public class Guideline extends ConstraintWidget {
    public static final int HORIZONTAL = 0;
    public static final int RELATIVE_BEGIN = 1;
    public static final int RELATIVE_END = 2;
    public static final int RELATIVE_PERCENT = 0;
    public static final int RELATIVE_UNKNWON = -1;
    public static final int VERTICAL = 1;
    private ConstraintAnchor mAnchor;
    private Rectangle mHead;
    private int mHeadSize;
    private boolean mIsPositionRelaxed;
    private int mMinimumPosition;
    private int mOrientation;
    protected int mRelativeBegin;
    protected int mRelativeEnd;
    protected float mRelativePercent;

    /* renamed from: android.support.constraint.solver.widgets.Guideline.1 */
    static /* synthetic */ class C00071 {
        static final /* synthetic */ int[] f3x1d400623;

        static {
            f3x1d400623 = new int[Type.values().length];
            try {
                f3x1d400623[Type.LEFT.ordinal()] = Guideline.VERTICAL;
            } catch (NoSuchFieldError e) {
            }
            try {
                f3x1d400623[Type.RIGHT.ordinal()] = Guideline.RELATIVE_END;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f3x1d400623[Type.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f3x1d400623[Type.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public Guideline() {
        this.mRelativePercent = -1.0f;
        this.mRelativeBegin = RELATIVE_UNKNWON;
        this.mRelativeEnd = RELATIVE_UNKNWON;
        this.mAnchor = this.mTop;
        this.mOrientation = RELATIVE_PERCENT;
        this.mIsPositionRelaxed = false;
        this.mMinimumPosition = RELATIVE_PERCENT;
        this.mHead = new Rectangle();
        this.mHeadSize = 8;
        this.mAnchors.clear();
        this.mAnchors.add(this.mAnchor);
    }

    public int getRelativeBehaviour() {
        if (this.mRelativePercent != -1.0f) {
            return RELATIVE_PERCENT;
        }
        if (this.mRelativeBegin != RELATIVE_UNKNWON) {
            return VERTICAL;
        }
        if (this.mRelativeEnd != RELATIVE_UNKNWON) {
            return RELATIVE_END;
        }
        return RELATIVE_UNKNWON;
    }

    public Rectangle getHead() {
        this.mHead.setBounds(getDrawX() - this.mHeadSize, getDrawY() - (this.mHeadSize * RELATIVE_END), this.mHeadSize * RELATIVE_END, this.mHeadSize * RELATIVE_END);
        if (getOrientation() == 0) {
            this.mHead.setBounds(getDrawX() - (this.mHeadSize * RELATIVE_END), getDrawY() - this.mHeadSize, this.mHeadSize * RELATIVE_END, this.mHeadSize * RELATIVE_END);
        }
        return this.mHead;
    }

    public void setOrientation(int orientation) {
        if (this.mOrientation != orientation) {
            this.mOrientation = orientation;
            this.mAnchors.clear();
            if (this.mOrientation == VERTICAL) {
                this.mAnchor = this.mLeft;
            } else {
                this.mAnchor = this.mTop;
            }
            this.mAnchors.add(this.mAnchor);
        }
    }

    public ConstraintAnchor getAnchor() {
        return this.mAnchor;
    }

    public String getType() {
        return "Guideline";
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setMinimumPosition(int minimum) {
        this.mMinimumPosition = minimum;
    }

    public void setPositionRelaxed(boolean value) {
        if (this.mIsPositionRelaxed != value) {
            this.mIsPositionRelaxed = value;
        }
    }

    public ConstraintAnchor getAnchor(Type anchorType) {
        switch (C00071.f3x1d400623[anchorType.ordinal()]) {
            case VERTICAL /*1*/:
            case RELATIVE_END /*2*/:
                if (this.mOrientation == VERTICAL) {
                    return this.mAnchor;
                }
                break;
            case DrawerLayout.LOCK_MODE_UNDEFINED /*3*/:
            case ConstraintWidgetContainer.OPTIMIZATION_BASIC /*4*/:
                if (this.mOrientation == 0) {
                    return this.mAnchor;
                }
                break;
        }
        return null;
    }

    public ArrayList<ConstraintAnchor> getAnchors() {
        return this.mAnchors;
    }

    public void setGuidePercent(int value) {
        setGuidePercent(((float) value) / 100.0f);
    }

    public void setGuidePercent(float value) {
        if (value > -1.0f) {
            this.mRelativePercent = value;
            this.mRelativeBegin = RELATIVE_UNKNWON;
            this.mRelativeEnd = RELATIVE_UNKNWON;
        }
    }

    public void setGuideBegin(int value) {
        if (value > RELATIVE_UNKNWON) {
            this.mRelativePercent = -1.0f;
            this.mRelativeBegin = value;
            this.mRelativeEnd = RELATIVE_UNKNWON;
        }
    }

    public void setGuideEnd(int value) {
        if (value > RELATIVE_UNKNWON) {
            this.mRelativePercent = -1.0f;
            this.mRelativeBegin = RELATIVE_UNKNWON;
            this.mRelativeEnd = value;
        }
    }

    public float getRelativePercent() {
        return this.mRelativePercent;
    }

    public int getRelativeBegin() {
        return this.mRelativeBegin;
    }

    public int getRelativeEnd() {
        return this.mRelativeEnd;
    }

    public void addToSolver(LinearSystem system, int group) {
        ConstraintWidgetContainer parent = (ConstraintWidgetContainer) getParent();
        if (parent != null) {
            ConstraintAnchor begin = parent.getAnchor(Type.LEFT);
            ConstraintAnchor end = parent.getAnchor(Type.RIGHT);
            if (this.mOrientation == 0) {
                begin = parent.getAnchor(Type.TOP);
                end = parent.getAnchor(Type.BOTTOM);
            }
            if (this.mRelativeBegin != RELATIVE_UNKNWON) {
                system.addConstraint(LinearSystem.createRowEquals(system, system.createObjectVariable(this.mAnchor), system.createObjectVariable(begin), this.mRelativeBegin, false));
            } else if (this.mRelativeEnd != RELATIVE_UNKNWON) {
                system.addConstraint(LinearSystem.createRowEquals(system, system.createObjectVariable(this.mAnchor), system.createObjectVariable(end), -this.mRelativeEnd, false));
            } else if (this.mRelativePercent != -1.0f) {
                system.addConstraint(LinearSystem.createRowDimensionPercent(system, system.createObjectVariable(this.mAnchor), system.createObjectVariable(begin), system.createObjectVariable(end), this.mRelativePercent, this.mIsPositionRelaxed));
            }
        }
    }

    public void updateFromSolver(LinearSystem system, int group) {
        if (getParent() != null) {
            int value = system.getObjectVariableValue(this.mAnchor);
            if (this.mOrientation == VERTICAL) {
                setX(value);
                setY(RELATIVE_PERCENT);
                setHeight(getParent().getHeight());
                setWidth(RELATIVE_PERCENT);
                return;
            }
            setX(RELATIVE_PERCENT);
            setY(value);
            setWidth(getParent().getWidth());
            setHeight(RELATIVE_PERCENT);
        }
    }

    public void setDrawOrigin(int x, int y) {
        int position;
        if (this.mOrientation == VERTICAL) {
            position = x - this.mOffsetX;
            if (this.mRelativeBegin != RELATIVE_UNKNWON) {
                setGuideBegin(position);
                return;
            } else if (this.mRelativeEnd != RELATIVE_UNKNWON) {
                setGuideEnd(getParent().getWidth() - position);
                return;
            } else if (this.mRelativePercent != -1.0f) {
                setGuidePercent(((float) position) / ((float) getParent().getWidth()));
                return;
            } else {
                return;
            }
        }
        position = y - this.mOffsetY;
        if (this.mRelativeBegin != RELATIVE_UNKNWON) {
            setGuideBegin(position);
        } else if (this.mRelativeEnd != RELATIVE_UNKNWON) {
            setGuideEnd(getParent().getHeight() - position);
        } else if (this.mRelativePercent != -1.0f) {
            setGuidePercent(((float) position) / ((float) getParent().getHeight()));
        }
    }

    void inferRelativePercentPosition() {
        float percent = ((float) getX()) / ((float) getParent().getWidth());
        if (this.mOrientation == 0) {
            percent = ((float) getY()) / ((float) getParent().getHeight());
        }
        setGuidePercent(percent);
    }

    void inferRelativeBeginPosition() {
        int position = getX();
        if (this.mOrientation == 0) {
            position = getY();
        }
        setGuideBegin(position);
    }

    void inferRelativeEndPosition() {
        int position = getParent().getWidth() - getX();
        if (this.mOrientation == 0) {
            position = getParent().getHeight() - getY();
        }
        setGuideEnd(position);
    }

    public void cyclePosition() {
        if (this.mRelativeBegin != RELATIVE_UNKNWON) {
            inferRelativePercentPosition();
        } else if (this.mRelativePercent != -1.0f) {
            inferRelativeEndPosition();
        } else if (this.mRelativeEnd != RELATIVE_UNKNWON) {
            inferRelativeBeginPosition();
        }
    }
}
