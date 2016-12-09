package ch.mh.mirrorx.game;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class Level {

    public static final int levelCount = 2;

    public List<Source> sources = new ArrayList<>();
    public List<GameElement> elements = new ArrayList<>();

    public static Level get(int levelId) {
        Level level = new Level();
        switch (levelId) {
            case 0:
                level.elements.add(new Mirror(200, 250));
                level.elements.add(new Mirror(200, 500));
                level.sources.add(new Source(new Vector2D(100, 100), new Vector2D(1, 1), Color.RED));
                break;
            case 1:
                level.elements.add(new Mirror(200, 250));
                level.elements.add(new Mirror(200, 500));
                level.elements.add(new Target(new Vector2D(450, 50), Color.GREEN));
                level.sources.add(new Source(new Vector2D(100, 100), new Vector2D(0, 1), Color.GREEN));
                Obstacle.Builder b = new Obstacle.Builder();
                b.add(new Vector2D(200, 0));
                b.add(new Vector2D(200, 400));
                b.add(new Vector2D(300, 500));
                b.add(new Vector2D(400, 400));
                b.add(new Vector2D(400, 0));
                level.elements.add(b.build());
                break;
        }
        return level;
    };
}
