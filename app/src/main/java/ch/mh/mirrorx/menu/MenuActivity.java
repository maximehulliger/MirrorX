package ch.mh.mirrorx.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import ch.mh.mirrorx.R;
import ch.mh.mirrorx.game.GameActivity;
import ch.mh.mirrorx.game.Level;

public class MenuActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        GridView grid = (GridView)findViewById(R.id.buttonGrid);
        grid.setAdapter(new ButtonAdapter(this));
    }

    public class ButtonAdapter extends BaseAdapter {

        private Context mContext;

        public ButtonAdapter(Context c) {
            mContext = c;
        }

        @Override
        public int getCount() {
            return Level.levelCount;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                Button button = new Button(mContext);
                button.setText(""+(position+1));
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(mContext, GameActivity.class);
                        i.putExtra("level", position);
                        startActivity(i);
                    }
                });
                return button;
            } else {
                return convertView;
            }
        }
    }
}
