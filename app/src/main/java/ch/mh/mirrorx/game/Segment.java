package ch.mh.mirrorx.game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.List;

public class Segment implements GameElement {

    public Vector2D start, end;
    public Paint paint;

    public Segment(Paint paint) {
        this.paint = paint;
    }

    public Segment(Vector2D start, Vector2D end, Paint paint) {
        this(paint);
        this.start = start;
        this.end = end;
    }

    @Override
    public Contact getContact(Ray ray) {
        Vector2D v1 = ray.origin.minus(start);
        Vector2D v2 = end.minus(start);
        Vector2D v3 = new Vector2D(-ray.direction.y, ray.direction.x);
        double v2DotV3 = v2.dot(v3);
        double t2 = v1.dot(v3) / v2DotV3;
        if (t2 >= 0 && t2 <= 1)
            return new Contact(ray, this, v2.cross(v1) / v2DotV3, t2, start.plus(v2.copy().scale(t2)));
        else
            return Contact.noContact;
    }

    @Override
    public void onContact(Contact contact, List<GameElement> elements, Path ray, int depth) {
        //TODO draw circle
    }

    @Override
    public void onReset() {

    }

    @Override
    public Playable getPlayable() {
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawLine((float)start.x, (float)start.y, (float)end.x, (float)end.y, paint);
    }
}
