package ch.mh.mirrorx.game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

public class Obstacle implements GameElement {

    private final static Paint defaultPaint = new Paint();
    static {
        defaultPaint.setAntiAlias(true);
        defaultPaint.setDither(true);
        defaultPaint.setColor(Color.WHITE);
        defaultPaint.setStyle(Paint.Style.STROKE);
        defaultPaint.setStrokeJoin(Paint.Join.ROUND);
        defaultPaint.setStrokeCap(Paint.Cap.ROUND);
        defaultPaint.setStrokeWidth(4);
    }

    List<Segment> segments = new ArrayList<>();

    public Obstacle(List<Segment> segments) {
        this.segments = segments;
    }

    @Override
    public void update() {

    }

    @Override
    public Contact getContact(Ray ray) {
        // get first mirror
        GameElement firstElement = null;
        GameElement.Contact firstContact = GameElement.Contact.noContact;
        for (GameElement e : segments) {
            GameElement.Contact c = e.getContact(ray);
            if (c != GameElement.Contact.noContact) {
                if (c.distance > 0 && firstContact.distance > c.distance) {
                    firstElement = e;
                    firstContact = c;
                }
            }
        }

        // collide against it
        if (firstElement != null)
            return firstContact;
        else
            return Contact.noContact;
    }

    @Override
    public Playable getPlayable() {
        return null;
    }

    @Override
    public void onContact(Contact contact, List<GameElement> elements, Path ray, int depth) {

    }

    @Override
    public void onReset() {

    }

    @Override
    public void draw(Canvas canvas) {
        for(Segment s : segments)
            s.draw(canvas);
    }

    public static class Builder extends ArrayList<Vector2D> {

        private final Paint paint;

        public Builder(Paint paint) {
            this.paint = paint;
        }

        public Builder() {
            this(defaultPaint);
        }

        public Obstacle build() {
            List<Segment> segments = new ArrayList<>();
            for (int i=size()-1, j=0; j<size(); i = j++) {
                segments.add(new Segment(get(i), get(j), paint));
            }
            return new Obstacle(segments);
        }
    }
}
