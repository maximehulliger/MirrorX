package ch.mh.mirrorx.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.List;

public class Target implements GameElement {
    Vector2D pos;
    double radius = 10;
    private boolean illuminated = false;
    private Paint paint = new Paint();
    private int contactColor = Color.BLACK;

    public Target(Vector2D pos, int color) {
        this.pos = pos;
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle((float)pos.x, (float)pos.y, (float)radius, paint);
    }

    @Override
    public void onReset() {
        illuminated = false;
    }

    @Override
    public Playable getPlayable() {
        return null;
    }

    @Override
    public Contact getContact(Ray ray) {
        double A = ray.direction.magSq();
        Vector2D originMinusCenter = ray.origin.minus(pos);
        double B = 2 * (ray.direction.x * originMinusCenter.x + ray.direction.y * originMinusCenter.y);
        double C = originMinusCenter.magSq() - square(radius);
        double det = square(B) - 4 * A * C;

        if (det < 0)
            return Contact.noContact;
        else
            return new Contact(ray, this, -(B + Math.sqrt(det)) / (2 * A), 0);
    }

    @Override
    public void onContact(Contact contact, List<GameElement> elements, Path ray, int depth) {
        illuminated = true;
    }

    private double square(double x) {
        return x*x;
    }
}
