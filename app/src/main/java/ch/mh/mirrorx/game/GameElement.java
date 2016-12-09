package ch.mh.mirrorx.game;

import android.graphics.Canvas;
import android.graphics.Path;

import java.util.List;

/**
 * Created by Max on 04.12.2016.
 */

public interface GameElement {

    Contact getContact(Ray ray);

    void onContact(Contact contact, List<GameElement> elements, Path ray, int depth);

    void onReset();

    void draw(Canvas canvas);

    /** Return null if the object isn't playable, or the object otherwise. */
    Playable getPlayable();

    interface Playable {
        void move(Vector2D depl);
        void rotate(double angle);
        Vector2D getPos();
    }

    class Contact {

        public static final Contact noContact = new Contact(null, null, Double.MAX_VALUE, 0);

        public Ray ray;
        public GameElement element;
        public double distance;
        public double distanceOnSegment;
        public Vector2D point;

        public Contact(Ray ray, GameElement element, double distance, double distanceOnSegment) {
            this.ray = ray;
            this.element = element;
            this.distance = distance;
            this.distanceOnSegment = distanceOnSegment;
            this.point = ray != null ? ray.origin.plus(ray.direction.copy().scale(distance)) : null;
        }
    }
}
