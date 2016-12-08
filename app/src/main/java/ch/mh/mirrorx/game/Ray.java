package ch.mh.mirrorx.game;

import android.graphics.Path;

import java.util.List;

public class Ray {

    public final Vector2D origin, direction;
    private final Mirror source;

    public Ray(Vector2D origin, Vector2D direction, Mirror source) {
        this.origin = origin;
        this.direction = direction.normalize();
        this.source = source;
    }

    public final static int depthMax = 10000;

    public void emit(List<GameElement> elements, Path ray, int depth) {
        if (depth > depthMax)
            return;

        // get first mirror
        GameElement firstElement = null;
        GameElement.Contact firstContact = GameElement.Contact.noContact;
        for (GameElement e : elements) {
            if (e != source) {
                GameElement.Contact c = e.getContact(this);
                if (c != GameElement.Contact.noContact) {
                    if (c.distance > 0 && firstContact.distance > c.distance) {
                        firstElement = e;
                        firstContact = c;
                    }
                }
            }
        }

        // collide against it
        if (firstElement != null) {
            Vector2D collisionPoint = firstContact.point;
            // ray to it
            ray.quadTo((float)origin.x, (float)origin.y, (float)collisionPoint.x, (float)collisionPoint.y);

            // si on va contre le miroir, on réfléchit
            firstElement.onContact(firstContact, elements, ray, depth+1);
        } else {
            Vector2D lastRayEnd = origin.plus(direction.copy().scale(2000));
            ray.quadTo((float)origin.x, (float)origin.y, (float)lastRayEnd.x, (float)lastRayEnd.y);
        }
    }
}
