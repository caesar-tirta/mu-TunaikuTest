package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;
import android.support.constraint.solver.widgets.ConstraintWidget.DimensionBehaviour;
import com.amicel.tunaikudemo.BuildConfig;
import java.util.ArrayList;

public class ConstraintTableLayout extends ConstraintWidgetContainer {
    public static final int ALIGN_CENTER = 0;
    private static final int ALIGN_FULL = 3;
    public static final int ALIGN_LEFT = 1;
    public static final int ALIGN_RIGHT = 2;
    private ArrayList<Guideline> mHorizontalGuidelines;
    private ArrayList<HorizontalSlice> mHorizontalSlices;
    private int mNumCols;
    private int mNumRows;
    private int mPadding;
    private boolean mVerticalGrowth;
    private ArrayList<Guideline> mVerticalGuidelines;
    private ArrayList<VerticalSlice> mVerticalSlices;
    private LinearSystem system;

    class HorizontalSlice {
        ConstraintWidget bottom;
        int padding;
        ConstraintWidget top;

        HorizontalSlice() {
        }
    }

    class VerticalSlice {
        int alignment;
        ConstraintWidget left;
        int padding;
        ConstraintWidget right;

        VerticalSlice() {
            this.alignment = ConstraintTableLayout.ALIGN_LEFT;
        }
    }

    public ConstraintTableLayout() {
        this.mVerticalGrowth = true;
        this.mNumCols = ALIGN_CENTER;
        this.mNumRows = ALIGN_CENTER;
        this.mPadding = 8;
        this.mVerticalSlices = new ArrayList();
        this.mHorizontalSlices = new ArrayList();
        this.mVerticalGuidelines = new ArrayList();
        this.mHorizontalGuidelines = new ArrayList();
        this.system = null;
    }

    public ConstraintTableLayout(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.mVerticalGrowth = true;
        this.mNumCols = ALIGN_CENTER;
        this.mNumRows = ALIGN_CENTER;
        this.mPadding = 8;
        this.mVerticalSlices = new ArrayList();
        this.mHorizontalSlices = new ArrayList();
        this.mVerticalGuidelines = new ArrayList();
        this.mHorizontalGuidelines = new ArrayList();
        this.system = null;
    }

    public ConstraintTableLayout(int width, int height) {
        super(width, height);
        this.mVerticalGrowth = true;
        this.mNumCols = ALIGN_CENTER;
        this.mNumRows = ALIGN_CENTER;
        this.mPadding = 8;
        this.mVerticalSlices = new ArrayList();
        this.mHorizontalSlices = new ArrayList();
        this.mVerticalGuidelines = new ArrayList();
        this.mHorizontalGuidelines = new ArrayList();
        this.system = null;
    }

    public String getType() {
        return "ConstraintTableLayout";
    }

    public int getNumRows() {
        return this.mNumRows;
    }

    public int getNumCols() {
        return this.mNumCols;
    }

    public int getPadding() {
        return this.mPadding;
    }

    public String getColumnsAlignmentRepresentation() {
        int numSlices = this.mVerticalSlices.size();
        String result = BuildConfig.FLAVOR;
        for (int i = ALIGN_CENTER; i < numSlices; i += ALIGN_LEFT) {
            VerticalSlice slice = (VerticalSlice) this.mVerticalSlices.get(i);
            if (slice.alignment == ALIGN_LEFT) {
                result = result + "L";
            } else if (slice.alignment == 0) {
                result = result + "C";
            } else if (slice.alignment == ALIGN_FULL) {
                result = result + "F";
            } else if (slice.alignment == ALIGN_RIGHT) {
                result = result + "R";
            }
        }
        return result;
    }

    public String getColumnAlignmentRepresentation(int column) {
        VerticalSlice slice = (VerticalSlice) this.mVerticalSlices.get(column);
        if (slice.alignment == ALIGN_LEFT) {
            return "L";
        }
        if (slice.alignment == 0) {
            return "C";
        }
        if (slice.alignment == ALIGN_FULL) {
            return "F";
        }
        if (slice.alignment == ALIGN_RIGHT) {
            return "R";
        }
        return "!";
    }

    public void setNumCols(int num) {
        if (this.mVerticalGrowth && this.mNumCols != num) {
            this.mNumCols = num;
            setVerticalSlices();
            setTableDimensions();
        }
    }

    public void setNumRows(int num) {
        if (!this.mVerticalGrowth && this.mNumCols != num) {
            this.mNumRows = num;
            setHorizontalSlices();
            setTableDimensions();
        }
    }

    public boolean isVerticalGrowth() {
        return this.mVerticalGrowth;
    }

    public void setVerticalGrowth(boolean value) {
        this.mVerticalGrowth = value;
    }

    public void setPadding(int padding) {
        if (padding > ALIGN_LEFT) {
            this.mPadding = padding;
        }
    }

    public void setColumnAlignment(int column, int alignment) {
        if (column < this.mVerticalSlices.size()) {
            ((VerticalSlice) this.mVerticalSlices.get(column)).alignment = alignment;
            setChildrenConnections();
        }
    }

    public void cycleColumnAlignment(int column) {
        VerticalSlice slice = (VerticalSlice) this.mVerticalSlices.get(column);
        switch (slice.alignment) {
            case ALIGN_CENTER /*0*/:
                slice.alignment = ALIGN_RIGHT;
                break;
            case ALIGN_LEFT /*1*/:
                slice.alignment = ALIGN_CENTER;
                break;
            case ALIGN_RIGHT /*2*/:
                slice.alignment = ALIGN_LEFT;
                break;
        }
        setChildrenConnections();
    }

    public void setColumnAlignment(String alignment) {
        int n = alignment.length();
        for (int i = ALIGN_CENTER; i < n; i += ALIGN_LEFT) {
            char c = alignment.charAt(i);
            if (c == 'L') {
                setColumnAlignment(i, ALIGN_LEFT);
            } else if (c == 'C') {
                setColumnAlignment(i, ALIGN_CENTER);
            } else if (c == 'F') {
                setColumnAlignment(i, ALIGN_FULL);
            } else if (c == 'R') {
                setColumnAlignment(i, ALIGN_RIGHT);
            } else {
                setColumnAlignment(i, ALIGN_CENTER);
            }
        }
    }

    public ArrayList<Guideline> getVerticalGuidelines() {
        return this.mVerticalGuidelines;
    }

    public ArrayList<Guideline> getHorizontalGuidelines() {
        return this.mHorizontalGuidelines;
    }

    public void addToSolver(LinearSystem system, int group) {
        super.addToSolver(system, group);
        int count = this.mChildren.size();
        if (count != 0) {
            setTableDimensions();
            if (system == this.mSystem) {
                int i;
                Guideline guideline;
                boolean z;
                int num = this.mVerticalGuidelines.size();
                for (i = ALIGN_CENTER; i < num; i += ALIGN_LEFT) {
                    guideline = (Guideline) this.mVerticalGuidelines.get(i);
                    if (getHorizontalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT) {
                        z = true;
                    } else {
                        z = false;
                    }
                    guideline.setPositionRelaxed(z);
                    guideline.addToSolver(system, group);
                }
                num = this.mHorizontalGuidelines.size();
                for (i = ALIGN_CENTER; i < num; i += ALIGN_LEFT) {
                    guideline = (Guideline) this.mHorizontalGuidelines.get(i);
                    if (getVerticalDimensionBehaviour() == DimensionBehaviour.WRAP_CONTENT) {
                        z = true;
                    } else {
                        z = false;
                    }
                    guideline.setPositionRelaxed(z);
                    guideline.addToSolver(system, group);
                }
                for (i = ALIGN_CENTER; i < count; i += ALIGN_LEFT) {
                    ((ConstraintWidget) this.mChildren.get(i)).addToSolver(system, group);
                }
            }
        }
    }

    public void setTableDimensions() {
        int extra = ALIGN_CENTER;
        int count = this.mChildren.size();
        for (int i = ALIGN_CENTER; i < count; i += ALIGN_LEFT) {
            extra += ((ConstraintWidget) this.mChildren.get(i)).getContainerItemSkip();
        }
        count += extra;
        if (this.mVerticalGrowth) {
            if (this.mNumCols == 0) {
                setNumCols(ALIGN_LEFT);
            }
            int rows = count / this.mNumCols;
            if (this.mNumCols * rows < count) {
                rows += ALIGN_LEFT;
            }
            if (this.mNumRows != rows || this.mVerticalGuidelines.size() != this.mNumCols - 1) {
                this.mNumRows = rows;
                setHorizontalSlices();
            } else {
                return;
            }
        }
        if (this.mNumRows == 0) {
            setNumRows(ALIGN_LEFT);
        }
        int cols = count / this.mNumRows;
        if (this.mNumRows * cols < count) {
            cols += ALIGN_LEFT;
        }
        if (this.mNumCols != cols || this.mHorizontalGuidelines.size() != this.mNumRows - 1) {
            this.mNumCols = cols;
            setVerticalSlices();
        } else {
            return;
        }
        setChildrenConnections();
    }

    public void setDebugSolverName(LinearSystem s, String name) {
        this.system = s;
        super.setDebugSolverName(s, name);
        updateDebugSolverNames();
    }

    private void updateDebugSolverNames() {
        if (this.system != null) {
            int i;
            int num = this.mVerticalGuidelines.size();
            for (i = ALIGN_CENTER; i < num; i += ALIGN_LEFT) {
                ((Guideline) this.mVerticalGuidelines.get(i)).setDebugSolverName(this.system, getDebugName() + ".VG" + i);
            }
            num = this.mHorizontalGuidelines.size();
            for (i = ALIGN_CENTER; i < num; i += ALIGN_LEFT) {
                ((Guideline) this.mHorizontalGuidelines.get(i)).setDebugSolverName(this.system, getDebugName() + ".HG" + i);
            }
        }
    }

    private void setVerticalSlices() {
        this.mVerticalSlices.clear();
        ConstraintWidget previous = this;
        float increment = 100.0f / ((float) this.mNumCols);
        float percent = increment;
        for (int i = ALIGN_CENTER; i < this.mNumCols; i += ALIGN_LEFT) {
            VerticalSlice slice = new VerticalSlice();
            slice.left = previous;
            if (i < this.mNumCols - 1) {
                Guideline guideline = new Guideline();
                guideline.setOrientation(ALIGN_LEFT);
                guideline.setParent(this);
                guideline.setGuidePercent((int) percent);
                percent += increment;
                slice.right = guideline;
                this.mVerticalGuidelines.add(guideline);
            } else {
                slice.right = this;
            }
            previous = slice.right;
            this.mVerticalSlices.add(slice);
        }
        updateDebugSolverNames();
    }

    private void setHorizontalSlices() {
        this.mHorizontalSlices.clear();
        float increment = 100.0f / ((float) this.mNumRows);
        float percent = increment;
        ConstraintWidget previous = this;
        for (int i = ALIGN_CENTER; i < this.mNumRows; i += ALIGN_LEFT) {
            HorizontalSlice slice = new HorizontalSlice();
            slice.top = previous;
            if (i < this.mNumRows - 1) {
                Guideline guideline = new Guideline();
                guideline.setOrientation(ALIGN_CENTER);
                guideline.setParent(this);
                guideline.setGuidePercent((int) percent);
                percent += increment;
                slice.bottom = guideline;
                this.mHorizontalGuidelines.add(guideline);
            } else {
                slice.bottom = this;
            }
            previous = slice.bottom;
            this.mHorizontalSlices.add(slice);
        }
        updateDebugSolverNames();
    }

    private void setChildrenConnections() {
        int count = this.mChildren.size();
        int index = ALIGN_CENTER;
        for (int i = ALIGN_CENTER; i < count; i += ALIGN_LEFT) {
            ConstraintWidget target = (ConstraintWidget) this.mChildren.get(i);
            index += target.getContainerItemSkip();
            HorizontalSlice horizontalSlice = (HorizontalSlice) this.mHorizontalSlices.get(index / this.mNumCols);
            VerticalSlice verticalSlice = (VerticalSlice) this.mVerticalSlices.get(index % this.mNumCols);
            ConstraintWidget targetLeft = verticalSlice.left;
            ConstraintWidget targetRight = verticalSlice.right;
            ConstraintWidget targetTop = horizontalSlice.top;
            ConstraintWidget targetBottom = horizontalSlice.bottom;
            target.getAnchor(Type.LEFT).connect(targetLeft.getAnchor(Type.LEFT), this.mPadding);
            if (targetRight instanceof Guideline) {
                target.getAnchor(Type.RIGHT).connect(targetRight.getAnchor(Type.LEFT), this.mPadding);
            } else {
                target.getAnchor(Type.RIGHT).connect(targetRight.getAnchor(Type.RIGHT), this.mPadding);
            }
            switch (verticalSlice.alignment) {
                case ALIGN_LEFT /*1*/:
                    target.getAnchor(Type.LEFT).setStrength(Strength.STRONG);
                    target.getAnchor(Type.RIGHT).setStrength(Strength.WEAK);
                    break;
                case ALIGN_RIGHT /*2*/:
                    target.getAnchor(Type.LEFT).setStrength(Strength.WEAK);
                    target.getAnchor(Type.RIGHT).setStrength(Strength.STRONG);
                    break;
                case ALIGN_FULL /*3*/:
                    target.setHorizontalDimensionBehaviour(DimensionBehaviour.MATCH_CONSTRAINT);
                    break;
            }
            target.getAnchor(Type.TOP).connect(targetTop.getAnchor(Type.TOP), this.mPadding);
            if (targetBottom instanceof Guideline) {
                target.getAnchor(Type.BOTTOM).connect(targetBottom.getAnchor(Type.TOP), this.mPadding);
            } else {
                target.getAnchor(Type.BOTTOM).connect(targetBottom.getAnchor(Type.BOTTOM), this.mPadding);
            }
            index += ALIGN_LEFT;
        }
    }

    public void updateFromSolver(LinearSystem system, int group) {
        super.updateFromSolver(system, group);
        if (system == this.mSystem) {
            int i;
            int num = this.mVerticalGuidelines.size();
            for (i = ALIGN_CENTER; i < num; i += ALIGN_LEFT) {
                ((Guideline) this.mVerticalGuidelines.get(i)).updateFromSolver(system, group);
            }
            num = this.mHorizontalGuidelines.size();
            for (i = ALIGN_CENTER; i < num; i += ALIGN_LEFT) {
                ((Guideline) this.mHorizontalGuidelines.get(i)).updateFromSolver(system, group);
            }
        }
    }

    public boolean handlesInternalConstraints() {
        return true;
    }

    public void computeGuidelinesPercentPositions() {
        int i;
        int num = this.mVerticalGuidelines.size();
        for (i = ALIGN_CENTER; i < num; i += ALIGN_LEFT) {
            ((Guideline) this.mVerticalGuidelines.get(i)).inferRelativePercentPosition();
        }
        num = this.mHorizontalGuidelines.size();
        for (i = ALIGN_CENTER; i < num; i += ALIGN_LEFT) {
            ((Guideline) this.mHorizontalGuidelines.get(i)).inferRelativePercentPosition();
        }
    }
}
