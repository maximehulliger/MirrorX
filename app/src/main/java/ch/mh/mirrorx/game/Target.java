package ch.mh.mirrorx.game;

import android.graphics.Canvas;
import android.graphics.Path;

import java.util.List;

public class Target implements GameElement {
    Vector2D pos;
    double radius = 10;
    private boolean illuminated = false;

    @Override
    public void draw(Canvas canvas) {

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
        return null;
    }

    @Override
    public void onContact(Contact contact, List<GameElement> elements, Path ray, int depth) {
        illuminated = true;
    }
}
