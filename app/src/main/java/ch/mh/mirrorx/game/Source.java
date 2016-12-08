package ch.mh.mirrorx.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.List;

/**
 * Created by Max on 29.11.2016.
 */
public class Source {

    private final double triangleLength = 10, triangleWidth = 20;
    private final Vector2D triangleLeft, triangleRight;
    private final Paint rayPaint = new Paint();
    private final Path ray = new Path();
    private final Ray sourceRay;

    public Source(Vector2D pos, Vector2D dir, int color) {
        sourceRay = new Ray(pos, dir, null);

        Vector2D triangleOrigin = pos.minus(dir.copy().scale(triangleLength));
        Vector2D originToLeft = dir.crossZn().scale(triangleWidth/2);
        triangleLeft = triangleOrigin.plus(originToLeft);
        triangleRight = triangleOrigin.minus(originToLeft);

        rayPaint.setAntiAlias(true);
        rayPaint.setColor(color);
        rayPaint.setStyle(Paint.Style.STROKE);
        rayPaint.setStrokeJoin(Paint.Join.MITER);
        rayPaint.setStrokeWidth(4f);
    }

    public void emit(List<GameElement> elements) {
        ray.reset();
        ray.moveTo((float)sourceRay.origin.x, (float)sourceRay.origin.y);
        sourceRay.emit(elements, ray, 0);
    }

    public void draw(Canvas canvas) {
        canvas.drawLine((float)triangleLeft.x, (float)triangleLeft.y, (float)triangleRight.x, (float)triangleRight.y, rayPaint);
        canvas.drawLine((float)triangleRight.x, (float)triangleRight.y, (float)sourceRay.origin.x, (float)sourceRay.origin.y, rayPaint);
        canvas.drawLine((float)sourceRay.origin.x, (float)sourceRay.origin.y, (float)triangleLeft.x, (float)triangleLeft.y, rayPaint);
    }

    public void drawRay(Canvas canvas) {
        canvas.drawPath(ray, rayPaint);
    }
}
