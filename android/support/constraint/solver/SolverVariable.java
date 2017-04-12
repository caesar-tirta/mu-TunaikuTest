package android.support.constraint.solver;

import com.amicel.tunaikudemo.BuildConfig;
import java.util.Arrays;

public class SolverVariable {
    private static final boolean INTERNAL_DEBUG = false;
    static final int MAX_STRENGTH = 6;
    public static final int STRENGTH_EQUALITY = 5;
    public static final int STRENGTH_HIGH = 3;
    public static final int STRENGTH_HIGHEST = 4;
    public static final int STRENGTH_LOW = 1;
    public static final int STRENGTH_MEDIUM = 2;
    public static final int STRENGTH_NONE = 0;
    private static int uniqueId;
    public float computedValue;
    int definitionId;
    public int id;
    ArrayRow[] mClientEquations;
    int mClientEquationsCount;
    private String mName;
    Type mType;
    public int strength;
    float[] strengthVector;

    /* renamed from: android.support.constraint.solver.SolverVariable.1 */
    static /* synthetic */ class C00031 {
        static final /* synthetic */ int[] $SwitchMap$android$support$constraint$solver$SolverVariable$Type;

        static {
            $SwitchMap$android$support$constraint$solver$SolverVariable$Type = new int[Type.values().length];
            try {
                $SwitchMap$android$support$constraint$solver$SolverVariable$Type[Type.UNRESTRICTED.ordinal()] = SolverVariable.STRENGTH_LOW;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$android$support$constraint$solver$SolverVariable$Type[Type.CONSTANT.ordinal()] = SolverVariable.STRENGTH_MEDIUM;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$android$support$constraint$solver$SolverVariable$Type[Type.SLACK.ordinal()] = SolverVariable.STRENGTH_HIGH;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$android$support$constraint$solver$SolverVariable$Type[Type.ERROR.ordinal()] = SolverVariable.STRENGTH_HIGHEST;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public enum Type {
        UNRESTRICTED,
        CONSTANT,
        SLACK,
        ERROR,
        UNKNOWN
    }

    static {
        uniqueId = STRENGTH_LOW;
    }

    private static String getUniqueName(Type type) {
        uniqueId += STRENGTH_LOW;
        switch (C00031.$SwitchMap$android$support$constraint$solver$SolverVariable$Type[type.ordinal()]) {
            case STRENGTH_LOW /*1*/:
                return "U" + uniqueId;
            case STRENGTH_MEDIUM /*2*/:
                return "C" + uniqueId;
            case STRENGTH_HIGH /*3*/:
                return "S" + uniqueId;
            case STRENGTH_HIGHEST /*4*/:
                return "e" + uniqueId;
            default:
                return "V" + uniqueId;
        }
    }

    public SolverVariable(String name, Type type) {
        this.id = -1;
        this.definitionId = -1;
        this.strength = 0;
        this.strengthVector = new float[MAX_STRENGTH];
        this.mClientEquations = new ArrayRow[8];
        this.mClientEquationsCount = 0;
        this.mName = name;
        this.mType = type;
    }

    public SolverVariable(Type type) {
        this.id = -1;
        this.definitionId = -1;
        this.strength = 0;
        this.strengthVector = new float[MAX_STRENGTH];
        this.mClientEquations = new ArrayRow[8];
        this.mClientEquationsCount = 0;
        this.mType = type;
    }

    void clearStrengths() {
        for (int i = 0; i < MAX_STRENGTH; i += STRENGTH_LOW) {
            this.strengthVector[i] = 0.0f;
        }
    }

    String strengthsToString() {
        String representation = this + "[";
        for (int j = 0; j < this.strengthVector.length; j += STRENGTH_LOW) {
            representation = representation + this.strengthVector[j];
            if (j < this.strengthVector.length - 1) {
                representation = representation + ", ";
            } else {
                representation = representation + "] ";
            }
        }
        return representation;
    }

    void addClientEquation(ArrayRow equation) {
        int i = 0;
        while (i < this.mClientEquationsCount) {
            if (this.mClientEquations[i] != equation) {
                i += STRENGTH_LOW;
            } else {
                return;
            }
        }
        if (this.mClientEquationsCount >= this.mClientEquations.length) {
            this.mClientEquations = (ArrayRow[]) Arrays.copyOf(this.mClientEquations, this.mClientEquations.length * STRENGTH_MEDIUM);
        }
        this.mClientEquations[this.mClientEquationsCount] = equation;
        this.mClientEquationsCount += STRENGTH_LOW;
    }

    void removeClientEquation(ArrayRow equation) {
        for (int i = 0; i < this.mClientEquationsCount; i += STRENGTH_LOW) {
            if (this.mClientEquations[i] == equation) {
                for (int j = 0; j < (this.mClientEquationsCount - i) - 1; j += STRENGTH_LOW) {
                    this.mClientEquations[i + j] = this.mClientEquations[(i + j) + STRENGTH_LOW];
                }
                this.mClientEquationsCount--;
                return;
            }
        }
    }

    public void reset() {
        this.mName = null;
        this.mType = Type.UNKNOWN;
        this.strength = 0;
        this.id = -1;
        this.definitionId = -1;
        this.computedValue = 0.0f;
        this.mClientEquationsCount = 0;
    }

    public String getName() {
        return this.mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setType(Type type) {
        this.mType = type;
    }

    public String toString() {
        return BuildConfig.FLAVOR + this.mName;
    }
}
