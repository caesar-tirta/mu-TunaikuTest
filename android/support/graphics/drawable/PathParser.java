package android.support.graphics.drawable;

import android.graphics.Path;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import com.amicel.tunaikudemo.C0203R;
import java.util.ArrayList;

class PathParser {
    private static final String LOGTAG = "PathParser";

    private static class ExtractFloatResult {
        int mEndPosition;
        boolean mEndWithNegOrDot;

        ExtractFloatResult() {
        }
    }

    public static class PathDataNode {
        float[] params;
        char type;

        PathDataNode(char type, float[] params) {
            this.type = type;
            this.params = params;
        }

        PathDataNode(PathDataNode n) {
            this.type = n.type;
            this.params = PathParser.copyOfRange(n.params, 0, n.params.length);
        }

        public static void nodesToPath(PathDataNode[] node, Path path) {
            float[] current = new float[6];
            char previousCommand = 'm';
            for (int i = 0; i < node.length; i++) {
                addCommand(path, current, previousCommand, node[i].type, node[i].params);
                previousCommand = node[i].type;
            }
        }

        public void interpolatePathDataNode(PathDataNode nodeFrom, PathDataNode nodeTo, float fraction) {
            for (int i = 0; i < nodeFrom.params.length; i++) {
                this.params[i] = (nodeFrom.params[i] * (1.0f - fraction)) + (nodeTo.params[i] * fraction);
            }
        }

        private static void addCommand(Path path, float[] current, char previousCmd, char cmd, float[] val) {
            int incr = 2;
            float currentX = current[0];
            float currentY = current[1];
            float ctrlPointX = current[2];
            float ctrlPointY = current[3];
            float currentSegmentStartX = current[4];
            float currentSegmentStartY = current[5];
            switch (cmd) {
                case C0203R.styleable.AppCompatTheme_imageButtonStyle /*65*/:
                case C0203R.styleable.AppCompatTheme_textColorAlertDialogListItem /*97*/:
                    incr = 7;
                    break;
                case C0203R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle /*67*/:
                case C0203R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle /*99*/:
                    incr = 6;
                    break;
                case C0203R.styleable.AppCompatTheme_listPreferredItemHeightLarge /*72*/:
                case C0203R.styleable.AppCompatTheme_colorControlNormal /*86*/:
                case C0203R.styleable.AppCompatTheme_checkboxStyle /*104*/:
                case 'v':
                    incr = 1;
                    break;
                case C0203R.styleable.AppCompatTheme_listPopupWindowStyle /*76*/:
                case C0203R.styleable.AppCompatTheme_textAppearanceListItem /*77*/:
                case C0203R.styleable.AppCompatTheme_colorPrimaryDark /*84*/:
                case C0203R.styleable.AppCompatTheme_ratingBarStyle /*108*/:
                case C0203R.styleable.AppCompatTheme_ratingBarStyleIndicator /*109*/:
                case 't':
                    incr = 2;
                    break;
                case C0203R.styleable.AppCompatTheme_panelMenuListTheme /*81*/:
                case C0203R.styleable.AppCompatTheme_colorPrimary /*83*/:
                case C0203R.styleable.AppCompatTheme_switchStyle /*113*/:
                case 's':
                    incr = 4;
                    break;
                case C0203R.styleable.AppCompatTheme_colorSwitchThumbNormal /*90*/:
                case 'z':
                    path.close();
                    currentX = currentSegmentStartX;
                    currentY = currentSegmentStartY;
                    ctrlPointX = currentSegmentStartX;
                    ctrlPointY = currentSegmentStartY;
                    path.moveTo(currentX, currentY);
                    break;
            }
            for (int k = 0; k < val.length; k += incr) {
                float f;
                float f2;
                float f3;
                float f4;
                float f5;
                boolean z;
                boolean z2;
                float reflectiveCtrlPointX;
                float reflectiveCtrlPointY;
                switch (cmd) {
                    case C0203R.styleable.AppCompatTheme_imageButtonStyle /*65*/:
                        f = val[k + 5];
                        f2 = val[k + 6];
                        f3 = val[k + 0];
                        f4 = val[k + 1];
                        f5 = val[k + 2];
                        z = val[k + 3] != 0.0f;
                        if (val[k + 4] != 0.0f) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        drawArc(path, currentX, currentY, f, f2, f3, f4, f5, z, z2);
                        currentX = val[k + 5];
                        currentY = val[k + 6];
                        ctrlPointX = currentX;
                        ctrlPointY = currentY;
                        break;
                    case C0203R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle /*67*/:
                        path.cubicTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3], val[k + 4], val[k + 5]);
                        currentX = val[k + 4];
                        currentY = val[k + 5];
                        ctrlPointX = val[k + 2];
                        ctrlPointY = val[k + 3];
                        break;
                    case C0203R.styleable.AppCompatTheme_listPreferredItemHeightLarge /*72*/:
                        path.lineTo(val[k + 0], currentY);
                        currentX = val[k + 0];
                        break;
                    case C0203R.styleable.AppCompatTheme_listPopupWindowStyle /*76*/:
                        path.lineTo(val[k + 0], val[k + 1]);
                        currentX = val[k + 0];
                        currentY = val[k + 1];
                        break;
                    case C0203R.styleable.AppCompatTheme_textAppearanceListItem /*77*/:
                        currentX = val[k + 0];
                        currentY = val[k + 1];
                        if (k <= 0) {
                            path.moveTo(val[k + 0], val[k + 1]);
                            currentSegmentStartX = currentX;
                            currentSegmentStartY = currentY;
                            break;
                        }
                        path.lineTo(val[k + 0], val[k + 1]);
                        break;
                    case C0203R.styleable.AppCompatTheme_panelMenuListTheme /*81*/:
                        path.quadTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                        ctrlPointX = val[k + 0];
                        ctrlPointY = val[k + 1];
                        currentX = val[k + 2];
                        currentY = val[k + 3];
                        break;
                    case C0203R.styleable.AppCompatTheme_colorPrimary /*83*/:
                        reflectiveCtrlPointX = currentX;
                        reflectiveCtrlPointY = currentY;
                        if (previousCmd == 'c' || previousCmd == 's' || previousCmd == 'C' || previousCmd == 'S') {
                            reflectiveCtrlPointX = (2.0f * currentX) - ctrlPointX;
                            reflectiveCtrlPointY = (2.0f * currentY) - ctrlPointY;
                        }
                        path.cubicTo(reflectiveCtrlPointX, reflectiveCtrlPointY, val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                        ctrlPointX = val[k + 0];
                        ctrlPointY = val[k + 1];
                        currentX = val[k + 2];
                        currentY = val[k + 3];
                        break;
                    case C0203R.styleable.AppCompatTheme_colorPrimaryDark /*84*/:
                        reflectiveCtrlPointX = currentX;
                        reflectiveCtrlPointY = currentY;
                        if (previousCmd == 'q' || previousCmd == 't' || previousCmd == 'Q' || previousCmd == 'T') {
                            reflectiveCtrlPointX = (2.0f * currentX) - ctrlPointX;
                            reflectiveCtrlPointY = (2.0f * currentY) - ctrlPointY;
                        }
                        path.quadTo(reflectiveCtrlPointX, reflectiveCtrlPointY, val[k + 0], val[k + 1]);
                        ctrlPointX = reflectiveCtrlPointX;
                        ctrlPointY = reflectiveCtrlPointY;
                        currentX = val[k + 0];
                        currentY = val[k + 1];
                        break;
                    case C0203R.styleable.AppCompatTheme_colorControlNormal /*86*/:
                        path.lineTo(currentX, val[k + 0]);
                        currentY = val[k + 0];
                        break;
                    case C0203R.styleable.AppCompatTheme_textColorAlertDialogListItem /*97*/:
                        f = val[k + 5] + currentX;
                        f2 = val[k + 6] + currentY;
                        f3 = val[k + 0];
                        f4 = val[k + 1];
                        f5 = val[k + 2];
                        z = val[k + 3] != 0.0f;
                        if (val[k + 4] != 0.0f) {
                            z2 = true;
                        } else {
                            z2 = false;
                        }
                        drawArc(path, currentX, currentY, f, f2, f3, f4, f5, z, z2);
                        currentX += val[k + 5];
                        currentY += val[k + 6];
                        ctrlPointX = currentX;
                        ctrlPointY = currentY;
                        break;
                    case C0203R.styleable.AppCompatTheme_buttonBarNegativeButtonStyle /*99*/:
                        path.rCubicTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3], val[k + 4], val[k + 5]);
                        ctrlPointX = currentX + val[k + 2];
                        ctrlPointY = currentY + val[k + 3];
                        currentX += val[k + 4];
                        currentY += val[k + 5];
                        break;
                    case C0203R.styleable.AppCompatTheme_checkboxStyle /*104*/:
                        path.rLineTo(val[k + 0], 0.0f);
                        currentX += val[k + 0];
                        break;
                    case C0203R.styleable.AppCompatTheme_ratingBarStyle /*108*/:
                        path.rLineTo(val[k + 0], val[k + 1]);
                        currentX += val[k + 0];
                        currentY += val[k + 1];
                        break;
                    case C0203R.styleable.AppCompatTheme_ratingBarStyleIndicator /*109*/:
                        currentX += val[k + 0];
                        currentY += val[k + 1];
                        if (k <= 0) {
                            path.rMoveTo(val[k + 0], val[k + 1]);
                            currentSegmentStartX = currentX;
                            currentSegmentStartY = currentY;
                            break;
                        }
                        path.rLineTo(val[k + 0], val[k + 1]);
                        break;
                    case C0203R.styleable.AppCompatTheme_switchStyle /*113*/:
                        path.rQuadTo(val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                        ctrlPointX = currentX + val[k + 0];
                        ctrlPointY = currentY + val[k + 1];
                        currentX += val[k + 2];
                        currentY += val[k + 3];
                        break;
                    case 's':
                        reflectiveCtrlPointX = 0.0f;
                        reflectiveCtrlPointY = 0.0f;
                        if (previousCmd == 'c' || previousCmd == 's' || previousCmd == 'C' || previousCmd == 'S') {
                            reflectiveCtrlPointX = currentX - ctrlPointX;
                            reflectiveCtrlPointY = currentY - ctrlPointY;
                        }
                        path.rCubicTo(reflectiveCtrlPointX, reflectiveCtrlPointY, val[k + 0], val[k + 1], val[k + 2], val[k + 3]);
                        ctrlPointX = currentX + val[k + 0];
                        ctrlPointY = currentY + val[k + 1];
                        currentX += val[k + 2];
                        currentY += val[k + 3];
                        break;
                    case 't':
                        reflectiveCtrlPointX = 0.0f;
                        reflectiveCtrlPointY = 0.0f;
                        if (previousCmd == 'q' || previousCmd == 't' || previousCmd == 'Q' || previousCmd == 'T') {
                            reflectiveCtrlPointX = currentX - ctrlPointX;
                            reflectiveCtrlPointY = currentY - ctrlPointY;
                        }
                        path.rQuadTo(reflectiveCtrlPointX, reflectiveCtrlPointY, val[k + 0], val[k + 1]);
                        ctrlPointX = currentX + reflectiveCtrlPointX;
                        ctrlPointY = currentY + reflectiveCtrlPointY;
                        currentX += val[k + 0];
                        currentY += val[k + 1];
                        break;
                    case 'v':
                        path.rLineTo(0.0f, val[k + 0]);
                        currentY += val[k + 0];
                        break;
                    default:
                        break;
                }
                previousCmd = cmd;
            }
            current[0] = currentX;
            current[1] = currentY;
            current[2] = ctrlPointX;
            current[3] = ctrlPointY;
            current[4] = currentSegmentStartX;
            current[5] = currentSegmentStartY;
        }

        private static void drawArc(Path p, float x0, float y0, float x1, float y1, float a, float b, float theta, boolean isMoreThanHalf, boolean isPositiveArc) {
            double thetaD = Math.toRadians((double) theta);
            double cosTheta = Math.cos(thetaD);
            double sinTheta = Math.sin(thetaD);
            double x0p = ((((double) x0) * cosTheta) + (((double) y0) * sinTheta)) / ((double) a);
            double y0p = ((((double) (-x0)) * sinTheta) + (((double) y0) * cosTheta)) / ((double) b);
            double x1p = ((((double) x1) * cosTheta) + (((double) y1) * sinTheta)) / ((double) a);
            double y1p = ((((double) (-x1)) * sinTheta) + (((double) y1) * cosTheta)) / ((double) b);
            double dx = x0p - x1p;
            double dy = y0p - y1p;
            double xm = (x0p + x1p) / 2.0d;
            double ym = (y0p + y1p) / 2.0d;
            double dsq = (dx * dx) + (dy * dy);
            if (dsq == 0.0d) {
                Log.w(PathParser.LOGTAG, " Points are coincident");
                return;
            }
            double disc = (1.0d / dsq) - 0.25d;
            if (disc < 0.0d) {
                Log.w(PathParser.LOGTAG, "Points are too far apart " + dsq);
                float adjust = (float) (Math.sqrt(dsq) / 1.99999d);
                drawArc(p, x0, y0, x1, y1, a * adjust, b * adjust, theta, isMoreThanHalf, isPositiveArc);
                return;
            }
            double cx;
            double cy;
            double s = Math.sqrt(disc);
            double sdx = s * dx;
            double sdy = s * dy;
            if (isMoreThanHalf == isPositiveArc) {
                cx = xm - sdy;
                cy = ym + sdx;
            } else {
                cx = xm + sdy;
                cy = ym - sdx;
            }
            double eta0 = Math.atan2(y0p - cy, x0p - cx);
            double sweep = Math.atan2(y1p - cy, x1p - cx) - eta0;
            if (isPositiveArc != (sweep >= 0.0d)) {
                if (sweep > 0.0d) {
                    sweep -= 6.283185307179586d;
                } else {
                    sweep += 6.283185307179586d;
                }
            }
            cx *= (double) a;
            cy *= (double) b;
            arcToBezier(p, (cx * cosTheta) - (cy * sinTheta), (cx * sinTheta) + (cy * cosTheta), (double) a, (double) b, (double) x0, (double) y0, thetaD, eta0, sweep);
        }

        private static void arcToBezier(Path p, double cx, double cy, double a, double b, double e1x, double e1y, double theta, double start, double sweep) {
            int numSegments = (int) Math.ceil(Math.abs((4.0d * sweep) / 3.141592653589793d));
            double eta1 = start;
            double cosTheta = Math.cos(theta);
            double sinTheta = Math.sin(theta);
            double cosEta1 = Math.cos(eta1);
            double sinEta1 = Math.sin(eta1);
            double ep1x = (((-a) * cosTheta) * sinEta1) - ((b * sinTheta) * cosEta1);
            double ep1y = (((-a) * sinTheta) * sinEta1) + ((b * cosTheta) * cosEta1);
            double anglePerSegment = sweep / ((double) numSegments);
            for (int i = 0; i < numSegments; i++) {
                double eta2 = eta1 + anglePerSegment;
                double sinEta2 = Math.sin(eta2);
                double cosEta2 = Math.cos(eta2);
                double e2x = (((a * cosTheta) * cosEta2) + cx) - ((b * sinTheta) * sinEta2);
                double e2y = (((a * sinTheta) * cosEta2) + cy) + ((b * cosTheta) * sinEta2);
                double ep2x = (((-a) * cosTheta) * sinEta2) - ((b * sinTheta) * cosEta2);
                double ep2y = (((-a) * sinTheta) * sinEta2) + ((b * cosTheta) * cosEta2);
                double tanDiff2 = Math.tan((eta2 - eta1) / 2.0d);
                double alpha = (Math.sin(eta2 - eta1) * (Math.sqrt(4.0d + ((3.0d * tanDiff2) * tanDiff2)) - 1.0d)) / 3.0d;
                Path path = p;
                path.cubicTo((float) (e1x + (alpha * ep1x)), (float) (e1y + (alpha * ep1y)), (float) (e2x - (alpha * ep2x)), (float) (e2y - (alpha * ep2y)), (float) e2x, (float) e2y);
                eta1 = eta2;
                e1x = e2x;
                e1y = e2y;
                ep1x = ep2x;
                ep1y = ep2y;
            }
        }
    }

    PathParser() {
    }

    static float[] copyOfRange(float[] original, int start, int end) {
        if (start > end) {
            throw new IllegalArgumentException();
        }
        int originalLength = original.length;
        if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int resultLength = end - start;
        float[] result = new float[resultLength];
        System.arraycopy(original, start, result, 0, Math.min(resultLength, originalLength - start));
        return result;
    }

    public static Path createPathFromPathData(String pathData) {
        Path path = new Path();
        PathDataNode[] nodes = createNodesFromPathData(pathData);
        if (nodes == null) {
            return null;
        }
        try {
            PathDataNode.nodesToPath(nodes, path);
            return path;
        } catch (RuntimeException e) {
            throw new RuntimeException("Error in parsing " + pathData, e);
        }
    }

    public static PathDataNode[] createNodesFromPathData(String pathData) {
        if (pathData == null) {
            return null;
        }
        int start = 0;
        int end = 1;
        ArrayList<PathDataNode> list = new ArrayList();
        while (end < pathData.length()) {
            end = nextStart(pathData, end);
            String s = pathData.substring(start, end).trim();
            if (s.length() > 0) {
                addNode(list, s.charAt(0), getFloats(s));
            }
            start = end;
            end++;
        }
        if (end - start == 1 && start < pathData.length()) {
            addNode(list, pathData.charAt(start), new float[0]);
        }
        return (PathDataNode[]) list.toArray(new PathDataNode[list.size()]);
    }

    public static PathDataNode[] deepCopyNodes(PathDataNode[] source) {
        if (source == null) {
            return null;
        }
        PathDataNode[] copy = new PathDataNode[source.length];
        for (int i = 0; i < source.length; i++) {
            copy[i] = new PathDataNode(source[i]);
        }
        return copy;
    }

    public static boolean canMorph(PathDataNode[] nodesFrom, PathDataNode[] nodesTo) {
        if (nodesFrom == null || nodesTo == null || nodesFrom.length != nodesTo.length) {
            return false;
        }
        int i = 0;
        while (i < nodesFrom.length) {
            if (nodesFrom[i].type != nodesTo[i].type || nodesFrom[i].params.length != nodesTo[i].params.length) {
                return false;
            }
            i++;
        }
        return true;
    }

    public static void updateNodes(PathDataNode[] target, PathDataNode[] source) {
        for (int i = 0; i < source.length; i++) {
            target[i].type = source[i].type;
            for (int j = 0; j < source[i].params.length; j++) {
                target[i].params[j] = source[i].params[j];
            }
        }
    }

    private static int nextStart(String s, int end) {
        while (end < s.length()) {
            char c = s.charAt(end);
            if (((c - 65) * (c - 90) <= 0 || (c - 97) * (c - 122) <= 0) && c != 'e' && c != 'E') {
                break;
            }
            end++;
        }
        return end;
    }

    private static void addNode(ArrayList<PathDataNode> list, char cmd, float[] val) {
        list.add(new PathDataNode(cmd, val));
    }

    private static float[] getFloats(String s) {
        int i = 1;
        int i2 = s.charAt(0) == 'z' ? 1 : 0;
        if (s.charAt(0) != 'Z') {
            i = 0;
        }
        if ((i2 | i) != 0) {
            return new float[0];
        }
        try {
            float[] results = new float[s.length()];
            int startPosition = 1;
            ExtractFloatResult result = new ExtractFloatResult();
            int totalLength = s.length();
            int count = 0;
            while (startPosition < totalLength) {
                int count2;
                extract(s, startPosition, result);
                int endPosition = result.mEndPosition;
                if (startPosition < endPosition) {
                    count2 = count + 1;
                    results[count] = Float.parseFloat(s.substring(startPosition, endPosition));
                } else {
                    count2 = count;
                }
                if (result.mEndWithNegOrDot) {
                    startPosition = endPosition;
                    count = count2;
                } else {
                    startPosition = endPosition + 1;
                    count = count2;
                }
            }
            return copyOfRange(results, 0, count);
        } catch (NumberFormatException e) {
            throw new RuntimeException("error in parsing \"" + s + "\"", e);
        }
    }

    private static void extract(String s, int start, ExtractFloatResult result) {
        int currentIndex;
        boolean foundSeparator = false;
        result.mEndWithNegOrDot = false;
        boolean secondDot = false;
        boolean isExponential = false;
        for (currentIndex = start; currentIndex < s.length(); currentIndex++) {
            boolean isPrevExponential = isExponential;
            isExponential = false;
            switch (s.charAt(currentIndex)) {
                case ItemTouchHelper.END /*32*/:
                case C0203R.styleable.ConstraintSet_layout_constraintRight_toRightOf /*44*/:
                    foundSeparator = true;
                    break;
                case C0203R.styleable.ConstraintSet_layout_constraintStart_toEndOf /*45*/:
                    if (!(currentIndex == start || isPrevExponential)) {
                        foundSeparator = true;
                        result.mEndWithNegOrDot = true;
                        break;
                    }
                case C0203R.styleable.ConstraintSet_layout_constraintStart_toStartOf /*46*/:
                    if (!secondDot) {
                        secondDot = true;
                        break;
                    }
                    foundSeparator = true;
                    result.mEndWithNegOrDot = true;
                    break;
                case C0203R.styleable.AppCompatTheme_searchViewStyle /*69*/:
                case C0203R.styleable.AppCompatTheme_autoCompleteTextViewStyle /*101*/:
                    isExponential = true;
                    break;
            }
            if (foundSeparator) {
                result.mEndPosition = currentIndex;
            }
        }
        result.mEndPosition = currentIndex;
    }
}
