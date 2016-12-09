package ch.mh.mirrorx.game;

import android.graphics.Canvas;
import android.graphics.Path;

import java.util.List;

public interface GameElement {

    Contact getContact(Ray ray);

    void onContact(Contact contact, List<GameElement> elements, Path ray, int depth);

    void onReset();

    void update();

    void draw(Canvas canvas);

    /** Return null if the object isn't playable, or the object otherwise. */
    Playable getPlayable();

    interface Playable {
        void move(Vector2D depl);
        void rotate(double angle);
        Vector2D getPos();
    }

    class Contact {

        public static final Contact noContact = new Contact(null, null, Float.MAX_VALUE, 0);

        public Ray ray;
        public GameElement element;
        public float distance;
        public float distanceOnSegment;
        public Vector2D point;

        public Contact(Ray ray, GameElement element, float distance, float distanceOnSegment) {
            this.ray = ray;
            this.element = element;
            this.distance = distance;
            this.distanceOnSegment = distanceOnSegment;
            this.point = ray != null ? ray.origin.plus(ray.direction.copy().scale(distance)) : null;
        }
    }
}
