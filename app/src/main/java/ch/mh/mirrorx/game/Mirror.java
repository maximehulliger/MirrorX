package ch.mh.mirrorx.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.List;

public class Mirror extends Segment implements GameElement.Playable {

    private final static double length = 100, edgeWidth = 10;
    public Vector2D pos, norm;

    private final static Paint paint = new Paint(), edgePaint = new Paint();
    static {
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(10);
        edgePaint.setAntiAlias(true);
        edgePaint.setDither(true);
        edgePaint.setColor(Color.WHITE);
        edgePaint.setStyle(Paint.Style.STROKE);
        edgePaint.setStrokeJoin(Paint.Join.ROUND);
        edgePaint.setStrokeCap(Paint.Cap.ROUND);
        edgePaint.setStrokeWidth(2);
    }

    public Mirror(double posX, double posY) {
        super(paint);
        this.pos = new Vector2D(posX, posY);
        setNorm(new Vector2D(0, -1));
    }

    @Override
    public Playable getPlayable() {
        return this;
    }

    @Override
    public Vector2D getPos() {
        return pos;
    }

    @Override
    public void move(Vector2D depl) {
        pos.add(depl);
        start.add(depl);
        end.add(depl);
    }

    @Override
    public void rotate(double angle) {
        setNorm(norm.rotate(angle));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        final int nLine = (int)(length / edgeWidth);
        Vector2D toNextStart = end.minus(start).setLength(edgeWidth);
        Vector2D toEnd = toNextStart.copy().rotate(-Math.PI/4).scale(Math.sqrt(2));
        Vector2D currentStart=start.copy();
        for (int i=0; i< nLine; i++) {
            Vector2D end = currentStart.plus(toEnd);
            canvas.drawLine((float)currentStart.x, (float)currentStart.y, (float)end.x, (float)end.y, edgePaint);
            currentStart.add(toNextStart);
        }
    }

    @Override
    public void onContact(Contact contact, List<GameElement> elements, Path ray, int depth) {
        double dirDotN = contact.ray.direction.dot(norm);
        if (dirDotN < 0) {
            //dir' = dir − 2(dir · n)n
            Vector2D newDir = contact.ray.direction.minus(norm.copy().scale(2*dirDotN));
            new Ray(contact.point, newDir, this).emit(elements, ray, depth+1);
        }
    }

    private void setNorm(Vector2D norm) {
        this.norm = norm;
        Vector2D toEnd = new Vector2D(norm.y, -norm.x).scale(length/2);
        end = pos.plus(toEnd);
        start = pos.minus(toEnd);
    }
}
