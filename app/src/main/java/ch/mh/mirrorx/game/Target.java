package ch.mh.mirrorx.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;

import java.util.List;

public class Target implements GameElement {
    private static final float illuminationTime = 2;
    private static final float borderRatio = 0.2f;
    Vector2D pos;
    float radius = 20;
    private boolean illuminated = false;
    private Paint paint = new Paint(), innerPaint = new Paint();
    private int contactColor = Color.BLACK;
    private float etat = 0;


    public Target(Vector2D pos, int color) {
        this.pos = pos;
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(3);
        innerPaint.setAntiAlias(true);
        innerPaint.setDither(true);
        innerPaint.setColor(Color.BLACK);
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setStrokeJoin(Paint.Join.ROUND);
        innerPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(pos.x, pos.y, radius, paint);
        canvas.drawCircle(pos.x, pos.y, (1-borderRatio)*radius*(1-etat), innerPaint);
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
        float A = ray.direction.magSq();
        Vector2D originMinusCenter = ray.origin.minus(pos);
        float B = 2 * (ray.direction.x * originMinusCenter.x + ray.direction.y * originMinusCenter.y);
        float C = originMinusCenter.magSq() - square(radius);
        float det = square(B) - 4 * A * C;

        if (det < 0)
            return Contact.noContact;
        else
            return new Contact(ray, this, -(B + (float)Math.sqrt(det)) / (2 * A), 0);
    }

    public boolean isFull() {
        return etat == 1;
    }

    @Override
    public void update() {
        if (illuminated && contactColor == paint.getColor()) {
            if (etat < 1) {
                etat += GameThread.deltaTime/illuminationTime;
                if (etat >= 1) {
                    etat = 1;
                }
            }
        } else if (etat > 0) {
            etat -= GameThread.deltaTime/illuminationTime;
            if (etat < 0)
                etat = 0;
        }
    }

    @Override
    public void onContact(Contact contact, List<GameElement> elements, Path ray, int depth) {
        illuminated = true;
        contactColor = contact.ray.color;
    }

    private float square(float x) {
        return x*x;
    }
}
