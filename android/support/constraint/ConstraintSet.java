package android.support.constraint;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.constraint.ConstraintLayout.LayoutParams;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ConstraintSet {
    private static final int ALPHA = 43;
    public static final int BASELINE = 5;
    private static final int BASELINE_TO_BASELINE = 1;
    public static final int BOTTOM = 4;
    private static final int BOTTOM_MARGIN = 2;
    private static final int BOTTOM_TO_BOTTOM = 3;
    private static final int BOTTOM_TO_TOP = 4;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    private static final boolean DEBUG = false;
    private static final int DIMENSION_RATIO = 5;
    private static final int EDITOR_ABSOLUTE_X = 6;
    private static final int EDITOR_ABSOLUTE_Y = 7;
    private static final int ELEVATION = 44;
    public static final int END = 7;
    private static final int END_MARGIN = 8;
    private static final int END_TO_END = 9;
    private static final int END_TO_START = 10;
    public static final int GONE = 8;
    private static final int GONE_BOTTOM_MARGIN = 11;
    private static final int GONE_END_MARGIN = 12;
    private static final int GONE_LEFT_MARGIN = 13;
    private static final int GONE_RIGHT_MARGIN = 14;
    private static final int GONE_START_MARGIN = 15;
    private static final int GONE_TOP_MARGIN = 16;
    private static final int GUIDE_BEGIN = 17;
    private static final int GUIDE_END = 18;
    private static final int GUIDE_PERCENT = 19;
    private static final int HEIGHT_DEFAULT = 55;
    private static final int HEIGHT_MAX = 57;
    private static final int HEIGHT_MIN = 59;
    public static final int HORIZONTAL = 0;
    private static final int HORIZONTAL_BIAS = 20;
    public static final int HORIZONTAL_GUIDELINE = 0;
    private static final int HORIZONTAL_STYLE = 41;
    private static final int HORIZONTAL_WEIGHT = 39;
    public static final int INVISIBLE = 4;
    private static final int LAYOUT_HEIGHT = 21;
    private static final int LAYOUT_VISIBILITY = 22;
    private static final int LAYOUT_WIDTH = 23;
    public static final int LEFT = 1;
    private static final int LEFT_MARGIN = 24;
    private static final int LEFT_TO_LEFT = 25;
    private static final int LEFT_TO_RIGHT = 26;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    private static final int ORIENTATION = 27;
    public static final int PARENT_ID = 0;
    public static final int RIGHT = 2;
    private static final int RIGHT_MARGIN = 28;
    private static final int RIGHT_TO_LEFT = 29;
    private static final int RIGHT_TO_RIGHT = 30;
    private static final int ROTATION_X = 45;
    private static final int ROTATION_Y = 46;
    private static final int SCALE_X = 47;
    private static final int SCALE_Y = 48;
    public static final int START = 6;
    private static final int START_MARGIN = 31;
    private static final int START_TO_END = 32;
    private static final int START_TO_START = 33;
    private static final String TAG = "ConstraintSet";
    public static final int TOP = 3;
    private static final int TOP_MARGIN = 34;
    private static final int TOP_TO_BOTTOM = 35;
    private static final int TOP_TO_TOP = 36;
    private static final int TRANSFORM_PIVOT_X = 49;
    private static final int TRANSFORM_PIVOT_Y = 50;
    private static final int TRANSLATION_X = 51;
    private static final int TRANSLATION_Y = 52;
    private static final int TRANSLATION_Z = 53;
    public static final int UNSET = -1;
    private static final int UNUSED = 60;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_BIAS = 37;
    public static final int VERTICAL_GUIDELINE = 1;
    private static final int VERTICAL_STYLE = 42;
    private static final int VERTICAL_WEIGHT = 40;
    private static final int VIEW_ID = 38;
    private static final int[] VISIBILITY_FLAGS;
    public static final int VISIBLE = 0;
    private static final int WIDTH_DEFAULT = 54;
    private static final int WIDTH_MAX = 56;
    private static final int WIDTH_MIN = 58;
    public static final int WRAP_CONTENT = -2;
    private static SparseIntArray mapToConstant;
    private HashMap<Integer, Constraint> mConstraints;

    private static class Constraint {
        static final int UNSET = -1;
        public float alpha;
        public boolean applyElevation;
        public int baselineToBaseline;
        public int bottomMargin;
        public int bottomToBottom;
        public int bottomToTop;
        public String dimensionRatio;
        public int editorAbsoluteX;
        public int editorAbsoluteY;
        public float elevation;
        public int endMargin;
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
        public int heightDefault;
        public int heightMax;
        public int heightMin;
        public float horizontalBias;
        public int horizontalChainStyle;
        public float horizontalWeight;
        public int leftMargin;
        public int leftToLeft;
        public int leftToRight;
        public int mHeight;
        boolean mIsGuideline;
        int mViewId;
        public int mWidth;
        public int orientation;
        public int rightMargin;
        public int rightToLeft;
        public int rightToRight;
        public float rotationX;
        public float rotationY;
        public float scaleX;
        public float scaleY;
        public int startMargin;
        public int startToEnd;
        public int startToStart;
        public int topMargin;
        public int topToBottom;
        public int topToTop;
        public float transformPivotX;
        public float transformPivotY;
        public float translationX;
        public float translationY;
        public float translationZ;
        public float verticalBias;
        public int verticalChainStyle;
        public float verticalWeight;
        public int visibility;
        public int widthDefault;
        public int widthMax;
        public int widthMin;

        private Constraint() {
            this.mIsGuideline = ConstraintSet.DEBUG;
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
            this.horizontalBias = 0.5f;
            this.verticalBias = 0.5f;
            this.dimensionRatio = null;
            this.editorAbsoluteX = UNSET;
            this.editorAbsoluteY = UNSET;
            this.orientation = UNSET;
            this.leftMargin = UNSET;
            this.rightMargin = UNSET;
            this.topMargin = UNSET;
            this.bottomMargin = UNSET;
            this.endMargin = UNSET;
            this.startMargin = UNSET;
            this.visibility = ConstraintSet.VISIBLE;
            this.goneLeftMargin = UNSET;
            this.goneTopMargin = UNSET;
            this.goneRightMargin = UNSET;
            this.goneBottomMargin = UNSET;
            this.goneEndMargin = UNSET;
            this.goneStartMargin = UNSET;
            this.verticalWeight = 0.0f;
            this.horizontalWeight = 0.0f;
            this.horizontalChainStyle = ConstraintSet.VISIBLE;
            this.verticalChainStyle = ConstraintSet.VISIBLE;
            this.alpha = 1.0f;
            this.applyElevation = ConstraintSet.DEBUG;
            this.elevation = 0.0f;
            this.rotationX = 0.0f;
            this.rotationY = 0.0f;
            this.scaleX = 1.0f;
            this.scaleY = 1.0f;
            this.transformPivotX = 0.0f;
            this.transformPivotY = 0.0f;
            this.translationX = 0.0f;
            this.translationY = 0.0f;
            this.translationZ = 0.0f;
            this.widthDefault = UNSET;
            this.heightDefault = UNSET;
            this.widthMax = UNSET;
            this.heightMax = UNSET;
            this.widthMin = UNSET;
            this.heightMin = UNSET;
        }

        public Constraint clone() {
            Constraint clone = new Constraint();
            clone.mIsGuideline = this.mIsGuideline;
            clone.mWidth = this.mWidth;
            clone.mHeight = this.mHeight;
            clone.guideBegin = this.guideBegin;
            clone.guideEnd = this.guideEnd;
            clone.guidePercent = this.guidePercent;
            clone.leftToLeft = this.leftToLeft;
            clone.leftToRight = this.leftToRight;
            clone.rightToLeft = this.rightToLeft;
            clone.rightToRight = this.rightToRight;
            clone.topToTop = this.topToTop;
            clone.topToBottom = this.topToBottom;
            clone.bottomToTop = this.bottomToTop;
            clone.bottomToBottom = this.bottomToBottom;
            clone.baselineToBaseline = this.baselineToBaseline;
            clone.startToEnd = this.startToEnd;
            clone.startToStart = this.startToStart;
            clone.endToStart = this.endToStart;
            clone.endToEnd = this.endToEnd;
            clone.horizontalBias = this.horizontalBias;
            clone.verticalBias = this.verticalBias;
            clone.dimensionRatio = this.dimensionRatio;
            clone.editorAbsoluteX = this.editorAbsoluteX;
            clone.editorAbsoluteY = this.editorAbsoluteY;
            clone.horizontalBias = this.horizontalBias;
            clone.horizontalBias = this.horizontalBias;
            clone.horizontalBias = this.horizontalBias;
            clone.horizontalBias = this.horizontalBias;
            clone.horizontalBias = this.horizontalBias;
            clone.orientation = this.orientation;
            clone.leftMargin = this.leftMargin;
            clone.rightMargin = this.rightMargin;
            clone.topMargin = this.topMargin;
            clone.bottomMargin = this.bottomMargin;
            clone.endMargin = this.endMargin;
            clone.startMargin = this.startMargin;
            clone.visibility = this.visibility;
            clone.goneLeftMargin = this.goneLeftMargin;
            clone.goneTopMargin = this.goneTopMargin;
            clone.goneRightMargin = this.goneRightMargin;
            clone.goneBottomMargin = this.goneBottomMargin;
            clone.goneEndMargin = this.goneEndMargin;
            clone.goneStartMargin = this.goneStartMargin;
            clone.verticalWeight = this.verticalWeight;
            clone.horizontalWeight = this.horizontalWeight;
            clone.horizontalChainStyle = this.horizontalChainStyle;
            clone.verticalChainStyle = this.verticalChainStyle;
            clone.alpha = this.alpha;
            clone.applyElevation = this.applyElevation;
            clone.elevation = this.elevation;
            clone.rotationX = this.rotationX;
            clone.rotationY = this.rotationY;
            clone.scaleX = this.scaleX;
            clone.scaleY = this.scaleY;
            clone.transformPivotX = this.transformPivotX;
            clone.transformPivotY = this.transformPivotY;
            clone.translationX = this.translationX;
            clone.translationY = this.translationY;
            clone.translationZ = this.translationZ;
            clone.widthDefault = this.widthDefault;
            clone.heightDefault = this.heightDefault;
            clone.widthMax = this.widthMax;
            clone.heightMax = this.heightMax;
            clone.widthMin = this.widthMin;
            clone.heightMin = this.heightMin;
            return clone;
        }

        private void fillFrom(int viewId, LayoutParams param) {
            this.mViewId = viewId;
            this.leftToLeft = param.leftToLeft;
            this.leftToRight = param.leftToRight;
            this.rightToLeft = param.rightToLeft;
            this.rightToRight = param.rightToRight;
            this.topToTop = param.topToTop;
            this.topToBottom = param.topToBottom;
            this.bottomToTop = param.bottomToTop;
            this.bottomToBottom = param.bottomToBottom;
            this.baselineToBaseline = param.baselineToBaseline;
            this.startToEnd = param.startToEnd;
            this.startToStart = param.startToStart;
            this.endToStart = param.endToStart;
            this.endToEnd = param.endToEnd;
            this.horizontalBias = param.horizontalBias;
            this.verticalBias = param.verticalBias;
            this.dimensionRatio = param.dimensionRatio;
            this.editorAbsoluteX = param.editorAbsoluteX;
            this.editorAbsoluteY = param.editorAbsoluteY;
            this.orientation = param.orientation;
            this.guidePercent = param.guidePercent;
            this.guideBegin = param.guideBegin;
            this.guideEnd = param.guideEnd;
            this.mWidth = param.width;
            this.mHeight = param.height;
            this.leftMargin = param.leftMargin;
            this.rightMargin = param.rightMargin;
            this.topMargin = param.topMargin;
            this.bottomMargin = param.bottomMargin;
            this.verticalWeight = param.verticalWeight;
            this.horizontalWeight = param.horizontalWeight;
            this.verticalChainStyle = param.verticalChainStyle;
            this.horizontalChainStyle = param.horizontalChainStyle;
            this.widthDefault = param.matchConstraintDefaultWidth;
            this.heightDefault = param.matchConstraintDefaultHeight;
            this.widthMax = param.matchConstraintMaxWidth;
            this.heightMax = param.matchConstraintMaxHeight;
            this.widthMin = param.matchConstraintMinWidth;
            this.heightMin = param.matchConstraintMinHeight;
            if (VERSION.SDK_INT >= ConstraintSet.GUIDE_BEGIN) {
                this.endMargin = param.getMarginEnd();
                this.startMargin = param.getMarginStart();
            }
        }

        public void applyTo(LayoutParams param) {
            param.leftToLeft = this.leftToLeft;
            param.leftToRight = this.leftToRight;
            param.rightToLeft = this.rightToLeft;
            param.rightToRight = this.rightToRight;
            param.topToTop = this.topToTop;
            param.topToBottom = this.topToBottom;
            param.bottomToTop = this.bottomToTop;
            param.bottomToBottom = this.bottomToBottom;
            param.baselineToBaseline = this.baselineToBaseline;
            param.startToEnd = this.startToEnd;
            param.startToStart = this.startToStart;
            param.endToStart = this.endToStart;
            param.endToEnd = this.endToEnd;
            param.leftMargin = this.leftMargin;
            param.rightMargin = this.rightMargin;
            param.topMargin = this.topMargin;
            param.bottomMargin = this.bottomMargin;
            param.goneStartMargin = this.goneStartMargin;
            param.goneEndMargin = this.goneEndMargin;
            param.horizontalBias = this.horizontalBias;
            param.verticalBias = this.verticalBias;
            param.dimensionRatio = this.dimensionRatio;
            param.editorAbsoluteX = this.editorAbsoluteX;
            param.editorAbsoluteY = this.editorAbsoluteY;
            param.verticalWeight = this.verticalWeight;
            param.horizontalWeight = this.horizontalWeight;
            param.verticalChainStyle = this.verticalChainStyle;
            param.horizontalChainStyle = this.horizontalChainStyle;
            param.matchConstraintDefaultWidth = this.widthDefault;
            param.matchConstraintDefaultHeight = this.heightDefault;
            param.matchConstraintMaxWidth = this.widthMax;
            param.matchConstraintMaxHeight = this.heightMax;
            param.matchConstraintMinWidth = this.widthMin;
            param.matchConstraintMinHeight = this.heightMin;
            param.orientation = this.orientation;
            param.guidePercent = this.guidePercent;
            param.guideBegin = this.guideBegin;
            param.guideEnd = this.guideEnd;
            param.width = this.mWidth;
            param.height = this.mHeight;
            if (VERSION.SDK_INT >= ConstraintSet.GUIDE_BEGIN) {
                param.setMarginStart(this.startMargin);
                param.setMarginEnd(this.endMargin);
            }
            param.validate();
        }
    }

    public ConstraintSet() {
        this.mConstraints = new HashMap();
    }

    static {
        VISIBILITY_FLAGS = new int[]{VISIBLE, INVISIBLE, GONE};
        mapToConstant = new SparseIntArray();
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintLeft_toLeftOf, LEFT_TO_LEFT);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintLeft_toRightOf, LEFT_TO_RIGHT);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintRight_toLeftOf, RIGHT_TO_LEFT);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintRight_toRightOf, RIGHT_TO_RIGHT);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintTop_toTopOf, TOP_TO_TOP);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintTop_toBottomOf, TOP_TO_BOTTOM);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintBottom_toTopOf, INVISIBLE);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintBottom_toBottomOf, TOP);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintBaseline_toBaselineOf, VERTICAL_GUIDELINE);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_editor_absoluteX, START);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_editor_absoluteY, END);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintGuide_begin, GUIDE_BEGIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintGuide_end, GUIDE_END);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintGuide_percent, GUIDE_PERCENT);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_orientation, ORIENTATION);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintStart_toEndOf, START_TO_END);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintStart_toStartOf, START_TO_START);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintEnd_toStartOf, END_TO_START);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintEnd_toEndOf, END_TO_END);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_goneMarginLeft, GONE_LEFT_MARGIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_goneMarginTop, GONE_TOP_MARGIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_goneMarginRight, GONE_RIGHT_MARGIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_goneMarginBottom, GONE_BOTTOM_MARGIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_goneMarginStart, GONE_START_MARGIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_goneMarginEnd, GONE_END_MARGIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintVertical_weight, VERTICAL_WEIGHT);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintHorizontal_weight, HORIZONTAL_WEIGHT);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintHorizontal_chainStyle, HORIZONTAL_STYLE);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintVertical_chainStyle, VERTICAL_STYLE);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintHorizontal_bias, HORIZONTAL_BIAS);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintVertical_bias, VERTICAL_BIAS);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintDimensionRatio, DIMENSION_RATIO);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintLeft_creator, UNUSED);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintTop_creator, UNUSED);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintRight_creator, UNUSED);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintBottom_creator, UNUSED);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintBaseline_creator, UNUSED);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_layout_marginLeft, LEFT_MARGIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_layout_marginRight, RIGHT_MARGIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_layout_marginStart, START_MARGIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_layout_marginEnd, GONE);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_layout_marginTop, TOP_MARGIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_layout_marginBottom, RIGHT);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_layout_width, LAYOUT_WIDTH);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_layout_height, LAYOUT_HEIGHT);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_visibility, LAYOUT_VISIBILITY);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_alpha, ALPHA);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_elevation, ELEVATION);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_rotationX, ROTATION_X);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_rotationY, ROTATION_Y);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_scaleX, SCALE_X);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_scaleY, SCALE_Y);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_transformPivotX, TRANSFORM_PIVOT_X);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_transformPivotY, TRANSFORM_PIVOT_Y);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_translationX, TRANSLATION_X);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_translationY, TRANSLATION_Y);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_translationZ, TRANSLATION_Z);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintWidth_default, WIDTH_DEFAULT);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintHeight_default, HEIGHT_DEFAULT);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintWidth_max, WIDTH_MAX);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintHeight_max, HEIGHT_MAX);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintWidth_min, WIDTH_MIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_layout_constraintHeight_min, HEIGHT_MIN);
        mapToConstant.append(C0002R.styleable.ConstraintSet_android_id, VIEW_ID);
    }

    public void clone(Context context, int constraintLayoutId) {
        clone((ConstraintLayout) LayoutInflater.from(context).inflate(constraintLayoutId, null));
    }

    public void clone(ConstraintSet set) {
        this.mConstraints.clear();
        for (Integer key : set.mConstraints.keySet()) {
            this.mConstraints.put(key, ((Constraint) set.mConstraints.get(key)).clone());
        }
    }

    public void clone(ConstraintLayout constraintLayout) {
        int count = constraintLayout.getChildCount();
        this.mConstraints.clear();
        for (int i = VISIBLE; i < count; i += VERTICAL_GUIDELINE) {
            View view = constraintLayout.getChildAt(i);
            LayoutParams param = (LayoutParams) view.getLayoutParams();
            int id = view.getId();
            if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
                this.mConstraints.put(Integer.valueOf(id), new Constraint());
            }
            Constraint constraint = (Constraint) this.mConstraints.get(Integer.valueOf(id));
            constraint.fillFrom(id, param);
            constraint.visibility = view.getVisibility();
            if (VERSION.SDK_INT >= GUIDE_BEGIN) {
                constraint.alpha = view.getAlpha();
                constraint.rotationX = view.getRotationX();
                constraint.rotationY = view.getRotationY();
                constraint.scaleX = view.getScaleX();
                constraint.scaleY = view.getScaleY();
                constraint.transformPivotX = view.getPivotX();
                constraint.transformPivotY = view.getPivotY();
                constraint.translationX = view.getTranslationX();
                constraint.translationY = view.getTranslationY();
                if (VERSION.SDK_INT >= LAYOUT_HEIGHT) {
                    constraint.translationZ = view.getTranslationZ();
                    if (constraint.applyElevation) {
                        constraint.elevation = view.getElevation();
                    }
                }
            }
        }
    }

    public void applyTo(ConstraintLayout constraintLayout) {
        applyToInternal(constraintLayout);
        constraintLayout.setConstraintSet(null);
    }

    void applyToInternal(ConstraintLayout constraintLayout) {
        int count = constraintLayout.getChildCount();
        HashSet<Integer> used = new HashSet(this.mConstraints.keySet());
        for (int i = VISIBLE; i < count; i += VERTICAL_GUIDELINE) {
            Constraint constraint;
            View view = constraintLayout.getChildAt(i);
            int id = view.getId();
            if (this.mConstraints.containsKey(Integer.valueOf(id))) {
                used.remove(Integer.valueOf(id));
                constraint = (Constraint) this.mConstraints.get(Integer.valueOf(id));
                LayoutParams param = (LayoutParams) view.getLayoutParams();
                constraint.applyTo(param);
                view.setLayoutParams(param);
                view.setVisibility(constraint.visibility);
                if (VERSION.SDK_INT >= GUIDE_BEGIN) {
                    view.setAlpha(constraint.alpha);
                    view.setRotationX(constraint.rotationX);
                    view.setRotationY(constraint.rotationY);
                    view.setScaleX(constraint.scaleX);
                    view.setScaleY(constraint.scaleY);
                    view.setPivotX(constraint.transformPivotX);
                    view.setPivotY(constraint.transformPivotY);
                    view.setTranslationX(constraint.translationX);
                    view.setTranslationY(constraint.translationY);
                    if (VERSION.SDK_INT >= LAYOUT_HEIGHT) {
                        view.setTranslationZ(constraint.translationZ);
                        if (constraint.applyElevation) {
                            view.setElevation(constraint.elevation);
                        }
                    }
                }
            }
        }
        Iterator it = used.iterator();
        while (it.hasNext()) {
            Integer id2 = (Integer) it.next();
            constraint = (Constraint) this.mConstraints.get(id2);
            if (constraint.mIsGuideline) {
                Guideline g = new Guideline(constraintLayout.getContext());
                g.setId(id2.intValue());
                param = constraintLayout.generateDefaultLayoutParams();
                constraint.applyTo(param);
                constraintLayout.addView(g, param);
            }
        }
    }

    public void center(int centerID, int firstID, int firstSide, int firstMargin, int secondId, int secondSide, int secondMargin, float bias) {
        if (firstMargin < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        } else if (secondMargin < 0) {
            throw new IllegalArgumentException("margin must be > 0");
        } else if (bias <= 0.0f || bias > 1.0f) {
            throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
        } else if (firstSide == VERTICAL_GUIDELINE || firstSide == RIGHT) {
            connect(centerID, VERTICAL_GUIDELINE, firstID, firstSide, firstMargin);
            connect(centerID, RIGHT, secondId, secondSide, secondMargin);
            ((Constraint) this.mConstraints.get(Integer.valueOf(centerID))).horizontalBias = bias;
        } else if (firstSide == START || firstSide == END) {
            connect(centerID, START, firstID, firstSide, firstMargin);
            connect(centerID, END, secondId, secondSide, secondMargin);
            ((Constraint) this.mConstraints.get(Integer.valueOf(centerID))).horizontalBias = bias;
        } else {
            connect(centerID, TOP, firstID, firstSide, firstMargin);
            connect(centerID, INVISIBLE, secondId, secondSide, secondMargin);
            ((Constraint) this.mConstraints.get(Integer.valueOf(centerID))).verticalBias = bias;
        }
    }

    public void centerHorizontally(int centerID, int leftId, int leftSide, int leftMargin, int rightId, int rightSide, int rightMargin, float bias) {
        connect(centerID, VERTICAL_GUIDELINE, leftId, leftSide, leftMargin);
        connect(centerID, RIGHT, rightId, rightSide, rightMargin);
        ((Constraint) this.mConstraints.get(Integer.valueOf(centerID))).horizontalBias = bias;
    }

    public void centerHorizontallyRtl(int centerID, int startId, int startSide, int startMargin, int endId, int endSide, int endMargin, float bias) {
        connect(centerID, START, startId, startSide, startMargin);
        connect(centerID, END, endId, endSide, endMargin);
        ((Constraint) this.mConstraints.get(Integer.valueOf(centerID))).horizontalBias = bias;
    }

    public void centerVertically(int centerID, int topId, int topSide, int topMargin, int bottomId, int bottomSide, int bottomMargin, float bias) {
        connect(centerID, TOP, topId, topSide, topMargin);
        connect(centerID, INVISIBLE, bottomId, bottomSide, bottomMargin);
        ((Constraint) this.mConstraints.get(Integer.valueOf(centerID))).verticalBias = bias;
    }

    public void createVerticalChain(int topId, int topSide, int bottomId, int bottomSide, int[] chainIds, float[] weights, int style) {
        if (chainIds.length < RIGHT) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        } else if (weights == null || weights.length == chainIds.length) {
            if (weights != null) {
                get(chainIds[VISIBLE]).verticalWeight = weights[VISIBLE];
            }
            get(chainIds[VISIBLE]).verticalChainStyle = style;
            connect(chainIds[VISIBLE], TOP, topId, topSide, VISIBLE);
            for (int i = VERTICAL_GUIDELINE; i < chainIds.length; i += VERTICAL_GUIDELINE) {
                int chainId = chainIds[i];
                connect(chainIds[i], TOP, chainIds[i + UNSET], INVISIBLE, VISIBLE);
                connect(chainIds[i + UNSET], INVISIBLE, chainIds[i], TOP, VISIBLE);
                if (weights != null) {
                    get(chainIds[i]).verticalWeight = weights[i];
                }
            }
            connect(chainIds[chainIds.length + UNSET], INVISIBLE, bottomId, bottomSide, VISIBLE);
        } else {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
    }

    public void createHorizontalChain(int leftId, int leftSide, int rightId, int rightSide, int[] chainIds, float[] weights, int style) {
        createHorizontalChain(leftId, leftSide, rightId, rightSide, chainIds, weights, style, VERTICAL_GUIDELINE, RIGHT);
    }

    public void createHorizontalChainRtl(int startId, int startSide, int endId, int endSide, int[] chainIds, float[] weights, int style) {
        createHorizontalChain(startId, startSide, endId, endSide, chainIds, weights, style, START, END);
    }

    private void createHorizontalChain(int leftId, int leftSide, int rightId, int rightSide, int[] chainIds, float[] weights, int style, int left, int right) {
        if (chainIds.length < RIGHT) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        } else if (weights == null || weights.length == chainIds.length) {
            if (weights != null) {
                get(chainIds[VISIBLE]).verticalWeight = weights[VISIBLE];
            }
            get(chainIds[VISIBLE]).horizontalChainStyle = style;
            connect(chainIds[VISIBLE], left, leftId, leftSide, UNSET);
            for (int i = VERTICAL_GUIDELINE; i < chainIds.length; i += VERTICAL_GUIDELINE) {
                int chainId = chainIds[i];
                connect(chainIds[i], left, chainIds[i + UNSET], right, UNSET);
                connect(chainIds[i + UNSET], right, chainIds[i], left, UNSET);
                if (weights != null) {
                    get(chainIds[i]).horizontalWeight = weights[i];
                }
            }
            connect(chainIds[chainIds.length + UNSET], right, rightId, rightSide, UNSET);
        } else {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
    }

    public void connect(int startID, int startSide, int endID, int endSide, int margin) {
        if (!this.mConstraints.containsKey(Integer.valueOf(startID))) {
            this.mConstraints.put(Integer.valueOf(startID), new Constraint());
        }
        Constraint constraint = (Constraint) this.mConstraints.get(Integer.valueOf(startID));
        switch (startSide) {
            case VERTICAL_GUIDELINE /*1*/:
                if (endSide == VERTICAL_GUIDELINE) {
                    constraint.leftToLeft = endID;
                    constraint.leftToRight = UNSET;
                } else if (endSide == RIGHT) {
                    constraint.leftToRight = endID;
                    constraint.leftToLeft = UNSET;
                } else {
                    throw new IllegalArgumentException("Left to " + sideToString(endSide) + " undefined");
                }
                constraint.leftMargin = margin;
            case RIGHT /*2*/:
                if (endSide == VERTICAL_GUIDELINE) {
                    constraint.rightToLeft = endID;
                    constraint.rightToRight = UNSET;
                } else if (endSide == RIGHT) {
                    constraint.rightToRight = endID;
                    constraint.rightToLeft = UNSET;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
                constraint.rightMargin = margin;
            case TOP /*3*/:
                if (endSide == TOP) {
                    constraint.topToTop = endID;
                    constraint.topToBottom = UNSET;
                    constraint.baselineToBaseline = UNSET;
                } else if (endSide == INVISIBLE) {
                    constraint.topToBottom = endID;
                    constraint.topToTop = UNSET;
                    constraint.baselineToBaseline = UNSET;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
                constraint.topMargin = margin;
            case INVISIBLE /*4*/:
                if (endSide == INVISIBLE) {
                    constraint.bottomToBottom = endID;
                    constraint.bottomToTop = UNSET;
                    constraint.baselineToBaseline = UNSET;
                } else if (endSide == TOP) {
                    constraint.bottomToTop = endID;
                    constraint.bottomToBottom = UNSET;
                    constraint.baselineToBaseline = UNSET;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
                constraint.bottomMargin = margin;
            case DIMENSION_RATIO /*5*/:
                if (endSide == DIMENSION_RATIO) {
                    constraint.baselineToBaseline = endID;
                    constraint.bottomToBottom = UNSET;
                    constraint.bottomToTop = UNSET;
                    constraint.topToTop = UNSET;
                    constraint.topToBottom = UNSET;
                    return;
                }
                throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
            case START /*6*/:
                if (endSide == START) {
                    constraint.startToStart = endID;
                    constraint.startToEnd = UNSET;
                } else if (endSide == END) {
                    constraint.startToEnd = endID;
                    constraint.startToStart = UNSET;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
                constraint.startMargin = margin;
            case END /*7*/:
                if (endSide == END) {
                    constraint.endToEnd = endID;
                    constraint.endToStart = UNSET;
                } else if (endSide == START) {
                    constraint.endToStart = endID;
                    constraint.endToEnd = UNSET;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
                constraint.endMargin = margin;
            default:
                throw new IllegalArgumentException(sideToString(startSide) + " to " + sideToString(endSide) + " unknown");
        }
    }

    public void connect(int startID, int startSide, int endID, int endSide) {
        if (!this.mConstraints.containsKey(Integer.valueOf(startID))) {
            this.mConstraints.put(Integer.valueOf(startID), new Constraint());
        }
        Constraint constraint = (Constraint) this.mConstraints.get(Integer.valueOf(startID));
        switch (startSide) {
            case VERTICAL_GUIDELINE /*1*/:
                if (endSide == VERTICAL_GUIDELINE) {
                    constraint.leftToLeft = endID;
                    constraint.leftToRight = UNSET;
                } else if (endSide == RIGHT) {
                    constraint.leftToRight = endID;
                    constraint.leftToLeft = UNSET;
                } else {
                    throw new IllegalArgumentException("left to " + sideToString(endSide) + " undefined");
                }
            case RIGHT /*2*/:
                if (endSide == VERTICAL_GUIDELINE) {
                    constraint.rightToLeft = endID;
                    constraint.rightToRight = UNSET;
                } else if (endSide == RIGHT) {
                    constraint.rightToRight = endID;
                    constraint.rightToLeft = UNSET;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
            case TOP /*3*/:
                if (endSide == TOP) {
                    constraint.topToTop = endID;
                    constraint.topToBottom = UNSET;
                    constraint.baselineToBaseline = UNSET;
                } else if (endSide == INVISIBLE) {
                    constraint.topToBottom = endID;
                    constraint.topToTop = UNSET;
                    constraint.baselineToBaseline = UNSET;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
            case INVISIBLE /*4*/:
                if (endSide == INVISIBLE) {
                    constraint.bottomToBottom = endID;
                    constraint.bottomToTop = UNSET;
                    constraint.baselineToBaseline = UNSET;
                } else if (endSide == TOP) {
                    constraint.bottomToTop = endID;
                    constraint.bottomToBottom = UNSET;
                    constraint.baselineToBaseline = UNSET;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
            case DIMENSION_RATIO /*5*/:
                if (endSide == DIMENSION_RATIO) {
                    constraint.baselineToBaseline = endID;
                    constraint.bottomToBottom = UNSET;
                    constraint.bottomToTop = UNSET;
                    constraint.topToTop = UNSET;
                    constraint.topToBottom = UNSET;
                    return;
                }
                throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
            case START /*6*/:
                if (endSide == START) {
                    constraint.startToStart = endID;
                    constraint.startToEnd = UNSET;
                } else if (endSide == END) {
                    constraint.startToEnd = endID;
                    constraint.startToStart = UNSET;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
            case END /*7*/:
                if (endSide == END) {
                    constraint.endToEnd = endID;
                    constraint.endToStart = UNSET;
                } else if (endSide == START) {
                    constraint.endToStart = endID;
                    constraint.endToEnd = UNSET;
                } else {
                    throw new IllegalArgumentException("right to " + sideToString(endSide) + " undefined");
                }
            default:
                throw new IllegalArgumentException(sideToString(startSide) + " to " + sideToString(endSide) + " unknown");
        }
    }

    public void centerHorizontally(int viewId, int toView) {
        if (toView == 0) {
            center(viewId, VISIBLE, VERTICAL_GUIDELINE, VISIBLE, VISIBLE, RIGHT, VISIBLE, 0.5f);
        } else {
            center(viewId, toView, RIGHT, VISIBLE, toView, VERTICAL_GUIDELINE, VISIBLE, 0.5f);
        }
    }

    public void centerHorizontallyRtl(int viewId, int toView) {
        if (toView == 0) {
            center(viewId, VISIBLE, START, VISIBLE, VISIBLE, END, VISIBLE, 0.5f);
        } else {
            center(viewId, toView, END, VISIBLE, toView, START, VISIBLE, 0.5f);
        }
    }

    public void centerVertically(int viewId, int toView) {
        if (toView == 0) {
            center(viewId, VISIBLE, TOP, VISIBLE, VISIBLE, INVISIBLE, VISIBLE, 0.5f);
        } else {
            center(viewId, toView, INVISIBLE, VISIBLE, toView, TOP, VISIBLE, 0.5f);
        }
    }

    public void clear(int viewId) {
        this.mConstraints.remove(Integer.valueOf(viewId));
    }

    public void clear(int viewId, int anchor) {
        if (this.mConstraints.containsKey(Integer.valueOf(viewId))) {
            Constraint constraint = (Constraint) this.mConstraints.get(Integer.valueOf(viewId));
            switch (anchor) {
                case VERTICAL_GUIDELINE /*1*/:
                    constraint.leftToRight = UNSET;
                    constraint.leftToLeft = UNSET;
                    constraint.leftMargin = UNSET;
                    constraint.goneLeftMargin = UNSET;
                case RIGHT /*2*/:
                    constraint.rightToRight = UNSET;
                    constraint.rightToLeft = UNSET;
                    constraint.rightMargin = UNSET;
                    constraint.goneRightMargin = UNSET;
                case TOP /*3*/:
                    constraint.topToBottom = UNSET;
                    constraint.topToTop = UNSET;
                    constraint.topMargin = UNSET;
                    constraint.goneTopMargin = UNSET;
                case INVISIBLE /*4*/:
                    constraint.bottomToTop = UNSET;
                    constraint.bottomToBottom = UNSET;
                    constraint.bottomMargin = UNSET;
                    constraint.goneBottomMargin = UNSET;
                case DIMENSION_RATIO /*5*/:
                    constraint.baselineToBaseline = UNSET;
                case START /*6*/:
                    constraint.startToEnd = UNSET;
                    constraint.startToStart = UNSET;
                    constraint.startMargin = UNSET;
                    constraint.goneStartMargin = UNSET;
                case END /*7*/:
                    constraint.endToStart = UNSET;
                    constraint.endToEnd = UNSET;
                    constraint.endMargin = UNSET;
                    constraint.goneEndMargin = UNSET;
                default:
                    throw new IllegalArgumentException("unknown constraint");
            }
        }
    }

    public void setMargin(int viewId, int anchor, int value) {
        Constraint constraint = get(viewId);
        switch (anchor) {
            case VERTICAL_GUIDELINE /*1*/:
                constraint.leftMargin = value;
            case RIGHT /*2*/:
                constraint.rightMargin = value;
            case TOP /*3*/:
                constraint.topMargin = value;
            case INVISIBLE /*4*/:
                constraint.bottomMargin = value;
            case DIMENSION_RATIO /*5*/:
                throw new IllegalArgumentException("baseline does not support margins");
            case START /*6*/:
                constraint.startMargin = value;
            case END /*7*/:
                constraint.endMargin = value;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setGoneMargin(int viewId, int anchor, int value) {
        Constraint constraint = get(viewId);
        switch (anchor) {
            case VERTICAL_GUIDELINE /*1*/:
                constraint.goneLeftMargin = value;
            case RIGHT /*2*/:
                constraint.goneRightMargin = value;
            case TOP /*3*/:
                constraint.goneTopMargin = value;
            case INVISIBLE /*4*/:
                constraint.goneBottomMargin = value;
            case DIMENSION_RATIO /*5*/:
                throw new IllegalArgumentException("baseline does not support margins");
            case START /*6*/:
                constraint.goneStartMargin = value;
            case END /*7*/:
                constraint.goneEndMargin = value;
            default:
                throw new IllegalArgumentException("unknown constraint");
        }
    }

    public void setHorizontalBias(int viewId, float bias) {
        get(viewId).horizontalBias = bias;
    }

    public void setVerticalBias(int viewId, float bias) {
        get(viewId).verticalBias = bias;
    }

    public void setDimensionRatio(int viewId, String ratio) {
        get(viewId).dimensionRatio = ratio;
    }

    public void setVisibility(int viewId, int visibility) {
        get(viewId).visibility = visibility;
    }

    public void setAlpha(int viewId, float alpha) {
        get(viewId).alpha = alpha;
    }

    public boolean getApplyElevation(int viewId) {
        return get(viewId).applyElevation;
    }

    public void setApplyElevation(int viewId, boolean apply) {
        get(viewId).applyElevation = apply;
    }

    public void setElevation(int viewId, float elevation) {
        get(viewId).elevation = elevation;
        get(viewId).applyElevation = true;
    }

    public void setRotationX(int viewId, float rotationX) {
        get(viewId).rotationX = rotationX;
    }

    public void setRotationY(int viewId, float rotationY) {
        get(viewId).rotationY = rotationY;
    }

    public void setScaleX(int viewId, float scaleX) {
        get(viewId).scaleX = scaleX;
    }

    public void setScaleY(int viewId, float scaleY) {
        get(viewId).scaleY = scaleY;
    }

    public void setTransformPivotX(int viewId, float transformPivotX) {
        get(viewId).transformPivotX = transformPivotX;
    }

    public void setTransformPivotY(int viewId, float transformPivotY) {
        get(viewId).transformPivotY = transformPivotY;
    }

    public void setTransformPivot(int viewId, float transformPivotX, float transformPivotY) {
        Constraint constraint = get(viewId);
        constraint.transformPivotY = transformPivotY;
        constraint.transformPivotX = transformPivotX;
    }

    public void setTranslationX(int viewId, float translationX) {
        get(viewId).translationX = translationX;
    }

    public void setTranslationY(int viewId, float translationY) {
        get(viewId).translationY = translationY;
    }

    public void setTranslation(int viewId, float translationX, float translationY) {
        Constraint constraint = get(viewId);
        constraint.translationX = translationX;
        constraint.translationY = translationY;
    }

    public void setTranslationZ(int viewId, float translationZ) {
        get(viewId).translationZ = translationZ;
    }

    public void constrainHeight(int viewId, int height) {
        get(viewId).mHeight = height;
    }

    public void constrainWidth(int viewId, int width) {
        get(viewId).mWidth = width;
    }

    public void constrainMaxHeight(int viewId, int height) {
        get(viewId).heightMax = height;
    }

    public void constrainMaxWidth(int viewId, int width) {
        get(viewId).widthMax = width;
    }

    public void constrainMinHeight(int viewId, int height) {
        get(viewId).heightMin = height;
    }

    public void constrainMinWidth(int viewId, int width) {
        get(viewId).widthMin = width;
    }

    public void constrainDefaultHeight(int viewId, int height) {
        get(viewId).heightDefault = height;
    }

    public void constrainDefaultWidth(int viewId, int width) {
        get(viewId).widthDefault = width;
    }

    public void setHorizontalWeight(int viewId, float weight) {
        get(viewId).horizontalWeight = weight;
    }

    public void setVerticalWeight(int viewId, float weight) {
        get(viewId).verticalWeight = weight;
    }

    public void setHorizontalChainStyle(int viewId, int chainStyle) {
        get(viewId).horizontalChainStyle = chainStyle;
    }

    public void setVerticalChainStyle(int viewId, int chainStyle) {
        get(viewId).verticalChainStyle = chainStyle;
    }

    public void addToHorizontalChain(int viewId, int leftId, int rightId) {
        int i;
        connect(viewId, VERTICAL_GUIDELINE, leftId, leftId == 0 ? VERTICAL_GUIDELINE : RIGHT, VISIBLE);
        if (rightId == 0) {
            i = RIGHT;
        } else {
            i = VERTICAL_GUIDELINE;
        }
        connect(viewId, RIGHT, rightId, i, VISIBLE);
        if (leftId != 0) {
            connect(leftId, RIGHT, viewId, VERTICAL_GUIDELINE, VISIBLE);
        }
        if (rightId != 0) {
            connect(rightId, VERTICAL_GUIDELINE, viewId, RIGHT, VISIBLE);
        }
    }

    public void addToHorizontalChainRTL(int viewId, int leftId, int rightId) {
        int i;
        connect(viewId, START, leftId, leftId == 0 ? START : END, VISIBLE);
        if (rightId == 0) {
            i = END;
        } else {
            i = START;
        }
        connect(viewId, END, rightId, i, VISIBLE);
        if (leftId != 0) {
            connect(leftId, END, viewId, START, VISIBLE);
        }
        if (rightId != 0) {
            connect(rightId, START, viewId, END, VISIBLE);
        }
    }

    public void addToVerticalChain(int viewId, int topId, int bottomId) {
        int i;
        connect(viewId, TOP, topId, topId == 0 ? TOP : INVISIBLE, VISIBLE);
        if (bottomId == 0) {
            i = INVISIBLE;
        } else {
            i = TOP;
        }
        connect(viewId, INVISIBLE, bottomId, i, VISIBLE);
        if (topId != 0) {
            connect(topId, INVISIBLE, viewId, TOP, VISIBLE);
        }
        if (topId != 0) {
            connect(bottomId, TOP, viewId, INVISIBLE, VISIBLE);
        }
    }

    public void removeFromVerticalChain(int viewId) {
        if (this.mConstraints.containsKey(Integer.valueOf(viewId))) {
            Constraint constraint = (Constraint) this.mConstraints.get(Integer.valueOf(viewId));
            int topId = constraint.topToBottom;
            int bottomId = constraint.bottomToTop;
            if (!(topId == UNSET && bottomId == UNSET)) {
                if (topId != UNSET && bottomId != UNSET) {
                    connect(topId, INVISIBLE, bottomId, TOP, VISIBLE);
                    connect(bottomId, TOP, topId, INVISIBLE, VISIBLE);
                } else if (!(topId == UNSET && bottomId == UNSET)) {
                    if (constraint.bottomToBottom != UNSET) {
                        connect(topId, INVISIBLE, constraint.bottomToBottom, INVISIBLE, VISIBLE);
                    } else if (constraint.topToTop != UNSET) {
                        connect(bottomId, TOP, constraint.topToTop, TOP, VISIBLE);
                    }
                }
            }
        }
        clear(viewId, TOP);
        clear(viewId, INVISIBLE);
    }

    public void removeFromHorizontalChain(int viewId) {
        if (this.mConstraints.containsKey(Integer.valueOf(viewId))) {
            Constraint constraint = (Constraint) this.mConstraints.get(Integer.valueOf(viewId));
            int leftId = constraint.leftToRight;
            int rightId = constraint.rightToLeft;
            if (leftId == UNSET && rightId == UNSET) {
                int startId = constraint.startToEnd;
                int endId = constraint.endToStart;
                if (!(startId == UNSET && endId == UNSET)) {
                    if (startId != UNSET && endId != UNSET) {
                        connect(startId, END, endId, START, VISIBLE);
                        connect(endId, START, leftId, END, VISIBLE);
                    } else if (!(leftId == UNSET && endId == UNSET)) {
                        if (constraint.rightToRight != UNSET) {
                            connect(leftId, END, constraint.rightToRight, END, VISIBLE);
                        } else if (constraint.leftToLeft != UNSET) {
                            connect(endId, START, constraint.leftToLeft, START, VISIBLE);
                        }
                    }
                }
                clear(viewId, START);
                clear(viewId, END);
                return;
            }
            if (leftId != UNSET && rightId != UNSET) {
                connect(leftId, RIGHT, rightId, VERTICAL_GUIDELINE, VISIBLE);
                connect(rightId, VERTICAL_GUIDELINE, leftId, RIGHT, VISIBLE);
            } else if (!(leftId == UNSET && rightId == UNSET)) {
                if (constraint.rightToRight != UNSET) {
                    connect(leftId, RIGHT, constraint.rightToRight, RIGHT, VISIBLE);
                } else if (constraint.leftToLeft != UNSET) {
                    connect(rightId, VERTICAL_GUIDELINE, constraint.leftToLeft, VERTICAL_GUIDELINE, VISIBLE);
                }
            }
            clear(viewId, VERTICAL_GUIDELINE);
            clear(viewId, RIGHT);
        }
    }

    public void create(int guidelineID, int orientation) {
        Constraint constraint = get(guidelineID);
        constraint.mIsGuideline = true;
        constraint.orientation = orientation;
    }

    public void setGuidelineBegin(int guidelineID, int margin) {
        get(guidelineID).guideBegin = margin;
        get(guidelineID).guideEnd = UNSET;
        get(guidelineID).guidePercent = -1.0f;
    }

    public void setGuidelineEnd(int guidelineID, int margin) {
        get(guidelineID).guideEnd = margin;
        get(guidelineID).guideBegin = UNSET;
        get(guidelineID).guidePercent = -1.0f;
    }

    public void setGuidelinePercent(int guidelineID, float ratio) {
        get(guidelineID).guidePercent = ratio;
        get(guidelineID).guideEnd = UNSET;
        get(guidelineID).guideBegin = UNSET;
    }

    private Constraint get(int id) {
        if (!this.mConstraints.containsKey(Integer.valueOf(id))) {
            this.mConstraints.put(Integer.valueOf(id), new Constraint());
        }
        return (Constraint) this.mConstraints.get(Integer.valueOf(id));
    }

    private String sideToString(int side) {
        switch (side) {
            case VERTICAL_GUIDELINE /*1*/:
                return "left";
            case RIGHT /*2*/:
                return "right";
            case TOP /*3*/:
                return "top";
            case INVISIBLE /*4*/:
                return "bottom";
            case DIMENSION_RATIO /*5*/:
                return "baseline";
            case START /*6*/:
                return "start";
            case END /*7*/:
                return "end";
            default:
                return "undefined";
        }
    }

    public void load(Context context, int resourceId) {
        XmlPullParser parser = context.getResources().getXml(resourceId);
        try {
            for (int eventType = parser.getEventType(); eventType != VERTICAL_GUIDELINE; eventType = parser.next()) {
                switch (eventType) {
                    case VISIBLE /*0*/:
                        String document = parser.getName();
                        break;
                    case RIGHT /*2*/:
                        String tagName = parser.getName();
                        Constraint constraint = fillFromAttributeList(context, Xml.asAttributeSet(parser));
                        if (tagName.equalsIgnoreCase("Guideline")) {
                            constraint.mIsGuideline = true;
                        }
                        this.mConstraints.put(Integer.valueOf(constraint.mViewId), constraint);
                        break;
                    case TOP /*3*/:
                        break;
                    default:
                        break;
                }
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    private static int lookupID(TypedArray a, int index, int def) {
        int ret = a.getResourceId(index, def);
        if (ret == UNSET) {
            return a.getInt(index, UNSET);
        }
        return ret;
    }

    private Constraint fillFromAttributeList(Context context, AttributeSet attrs) {
        Constraint c = new Constraint();
        TypedArray a = context.obtainStyledAttributes(attrs, C0002R.styleable.ConstraintSet);
        populateConstraint(c, a);
        a.recycle();
        return c;
    }

    private void populateConstraint(Constraint c, TypedArray a) {
        int N = a.getIndexCount();
        for (int i = VISIBLE; i < N; i += VERTICAL_GUIDELINE) {
            int attr = a.getIndex(i);
            switch (mapToConstant.get(attr)) {
                case VERTICAL_GUIDELINE /*1*/:
                    c.baselineToBaseline = lookupID(a, attr, c.baselineToBaseline);
                    break;
                case RIGHT /*2*/:
                    c.bottomMargin = a.getDimensionPixelSize(attr, c.bottomMargin);
                    break;
                case TOP /*3*/:
                    c.bottomToBottom = lookupID(a, attr, c.bottomToBottom);
                    break;
                case INVISIBLE /*4*/:
                    c.bottomToTop = lookupID(a, attr, c.bottomToTop);
                    break;
                case DIMENSION_RATIO /*5*/:
                    c.dimensionRatio = a.getString(attr);
                    break;
                case START /*6*/:
                    c.editorAbsoluteX = a.getDimensionPixelOffset(attr, c.editorAbsoluteX);
                    break;
                case END /*7*/:
                    c.editorAbsoluteY = a.getDimensionPixelOffset(attr, c.editorAbsoluteY);
                    break;
                case GONE /*8*/:
                    c.endMargin = a.getDimensionPixelSize(attr, c.endMargin);
                    break;
                case END_TO_END /*9*/:
                    c.bottomToTop = lookupID(a, attr, c.endToEnd);
                    break;
                case END_TO_START /*10*/:
                    c.endToStart = lookupID(a, attr, c.endToStart);
                    break;
                case GONE_BOTTOM_MARGIN /*11*/:
                    c.goneBottomMargin = a.getDimensionPixelSize(attr, c.goneBottomMargin);
                    break;
                case GONE_END_MARGIN /*12*/:
                    c.goneEndMargin = a.getDimensionPixelSize(attr, c.goneEndMargin);
                    break;
                case GONE_LEFT_MARGIN /*13*/:
                    c.goneLeftMargin = a.getDimensionPixelSize(attr, c.goneLeftMargin);
                    break;
                case GONE_RIGHT_MARGIN /*14*/:
                    c.goneRightMargin = a.getDimensionPixelSize(attr, c.goneRightMargin);
                    break;
                case GONE_START_MARGIN /*15*/:
                    c.goneStartMargin = a.getDimensionPixelSize(attr, c.goneStartMargin);
                    break;
                case GONE_TOP_MARGIN /*16*/:
                    c.goneTopMargin = a.getDimensionPixelSize(attr, c.goneTopMargin);
                    break;
                case GUIDE_BEGIN /*17*/:
                    c.guideBegin = a.getDimensionPixelOffset(attr, c.guideBegin);
                    break;
                case GUIDE_END /*18*/:
                    c.guideEnd = a.getDimensionPixelOffset(attr, c.guideEnd);
                    break;
                case GUIDE_PERCENT /*19*/:
                    c.guidePercent = a.getFloat(attr, c.guidePercent);
                    break;
                case HORIZONTAL_BIAS /*20*/:
                    c.horizontalBias = a.getFloat(attr, c.horizontalBias);
                    break;
                case LAYOUT_HEIGHT /*21*/:
                    c.mHeight = a.getLayoutDimension(attr, c.mHeight);
                    break;
                case LAYOUT_VISIBILITY /*22*/:
                    c.visibility = a.getInt(attr, c.visibility);
                    c.visibility = VISIBILITY_FLAGS[c.visibility];
                    break;
                case LAYOUT_WIDTH /*23*/:
                    c.mWidth = a.getLayoutDimension(attr, c.mWidth);
                    break;
                case LEFT_MARGIN /*24*/:
                    c.leftMargin = a.getDimensionPixelSize(attr, c.leftMargin);
                    break;
                case LEFT_TO_LEFT /*25*/:
                    c.leftToLeft = lookupID(a, attr, c.leftToLeft);
                    break;
                case LEFT_TO_RIGHT /*26*/:
                    c.leftToRight = lookupID(a, attr, c.leftToRight);
                    break;
                case ORIENTATION /*27*/:
                    c.orientation = a.getInt(attr, c.orientation);
                    break;
                case RIGHT_MARGIN /*28*/:
                    c.rightMargin = a.getDimensionPixelSize(attr, c.rightMargin);
                    break;
                case RIGHT_TO_LEFT /*29*/:
                    c.rightToLeft = lookupID(a, attr, c.rightToLeft);
                    break;
                case RIGHT_TO_RIGHT /*30*/:
                    c.rightToRight = lookupID(a, attr, c.rightToRight);
                    break;
                case START_MARGIN /*31*/:
                    c.startMargin = a.getDimensionPixelSize(attr, c.startMargin);
                    break;
                case START_TO_END /*32*/:
                    c.startToEnd = lookupID(a, attr, c.startToEnd);
                    break;
                case START_TO_START /*33*/:
                    c.startToStart = lookupID(a, attr, c.startToStart);
                    break;
                case TOP_MARGIN /*34*/:
                    c.topMargin = a.getDimensionPixelSize(attr, c.topMargin);
                    break;
                case TOP_TO_BOTTOM /*35*/:
                    c.topToBottom = lookupID(a, attr, c.topToBottom);
                    break;
                case TOP_TO_TOP /*36*/:
                    c.topToTop = lookupID(a, attr, c.topToTop);
                    break;
                case VERTICAL_BIAS /*37*/:
                    c.verticalBias = a.getFloat(attr, c.verticalBias);
                    break;
                case VIEW_ID /*38*/:
                    c.mViewId = a.getResourceId(attr, c.mViewId);
                    break;
                case HORIZONTAL_WEIGHT /*39*/:
                    c.horizontalWeight = a.getFloat(attr, c.horizontalWeight);
                    break;
                case VERTICAL_WEIGHT /*40*/:
                    c.verticalWeight = a.getFloat(attr, c.verticalWeight);
                    break;
                case HORIZONTAL_STYLE /*41*/:
                    c.horizontalChainStyle = a.getInt(attr, c.horizontalChainStyle);
                    break;
                case VERTICAL_STYLE /*42*/:
                    c.verticalChainStyle = a.getInt(attr, c.verticalChainStyle);
                    break;
                case ALPHA /*43*/:
                    c.alpha = a.getFloat(attr, c.alpha);
                    break;
                case ELEVATION /*44*/:
                    c.applyElevation = true;
                    c.elevation = a.getFloat(attr, c.elevation);
                    break;
                case ROTATION_X /*45*/:
                    c.rotationX = a.getFloat(attr, c.rotationX);
                    break;
                case ROTATION_Y /*46*/:
                    c.rotationY = a.getFloat(attr, c.rotationY);
                    break;
                case SCALE_X /*47*/:
                    c.scaleX = a.getFloat(attr, c.scaleX);
                    break;
                case SCALE_Y /*48*/:
                    c.scaleY = a.getFloat(attr, c.scaleY);
                    break;
                case TRANSFORM_PIVOT_X /*49*/:
                    c.transformPivotX = a.getFloat(attr, c.transformPivotX);
                    break;
                case TRANSFORM_PIVOT_Y /*50*/:
                    c.transformPivotY = a.getFloat(attr, c.transformPivotY);
                    break;
                case TRANSLATION_X /*51*/:
                    c.translationX = a.getFloat(attr, c.translationX);
                    break;
                case TRANSLATION_Y /*52*/:
                    c.translationY = a.getFloat(attr, c.translationY);
                    break;
                case TRANSLATION_Z /*53*/:
                    c.translationZ = a.getFloat(attr, c.translationZ);
                    break;
                case UNUSED /*60*/:
                    Log.w(TAG, "unused attribute 0x" + Integer.toHexString(attr) + "   " + mapToConstant.get(attr));
                    break;
                default:
                    Log.w(TAG, "Unknown attribute 0x" + Integer.toHexString(attr) + "   " + mapToConstant.get(attr));
                    break;
            }
        }
    }
}
