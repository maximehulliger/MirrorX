package ch.mh.mirrorx.game;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import ch.mh.mirrorx.R;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ch.mh.mirrorx.R.layout.game);
        GameView view = (GameView)findViewById(R.id.gameView);
        view.setLevel(Level.get(getIntent().getIntExtra("level", 0)));
        Button button = (Button)findViewById(R.id.gameReturnButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}
