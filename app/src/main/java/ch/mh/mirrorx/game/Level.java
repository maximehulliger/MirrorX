package ch.mh.mirrorx.game;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Level {

    public static final int levelCount = 2;

    public List<Source> sources = new ArrayList<>();
    public List<GameElement> elements = new ArrayList<>();
    public List<Target> targets = new ArrayList<>();

    public static Level get(int levelId) {
        Level level = new Level();
        switch (levelId) {
            case 0:
                level.elements.add(new Mirror(Vector2D.rel(0.5f, 0.3f)));
                level.elements.add(new Mirror(Vector2D.rel(0.5f, 0.6f)));
                level.sources.add(new Source(Vector2D.rel(0.2f, 0.2f), new Vector2D(1, 1), Color.RED));
                level.targets.add(new Target(Vector2D.rel(0.5f, 0.8f), Color.RED));
                break;
            case 1:
                level.elements.add(new Mirror(Vector2D.rel(0.5f, 0.5f)));
                level.elements.add(new Mirror(Vector2D.rel(0.5f, 0.3f)));
                level.targets.add(new Target(Vector2D.rel(0.85f, 0.15f), Color.GREEN));
                level.sources.add(new Source(Vector2D.rel(0.15f, 0.15f), new Vector2D(0, 1), Color.GREEN));
                Obstacle.Builder b = new Obstacle.Builder();
                b.add(Vector2D.rel(0.3f, 0));
                b.add(Vector2D.rel(0.3f, 0.5f));
                b.add(Vector2D.rel(0.5f, 0.7f));
                b.add(Vector2D.rel(0.7f, 0.5f));
                b.add(Vector2D.rel(0.7f, 0));
                level.elements.add(b.build());
                break;
        }
        level.elements.addAll(level.targets);
        return level;
    };
}
