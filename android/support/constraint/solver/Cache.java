package android.support.constraint.solver;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;

public class Cache {
    Pool<ArrayRow> arrayRowPool;
    SolverVariable[] mIndexedVariables;
    Pool<SolverVariable> solverVariablePool;

    public Cache() {
        this.arrayRowPool = new SimplePool(AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
        this.solverVariablePool = new SimplePool(AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
        this.mIndexedVariables = new SolverVariable[32];
    }
}
