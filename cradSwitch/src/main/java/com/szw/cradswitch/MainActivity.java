package com.szw.cradswitch;


import java.util.List;

import com.szw.cradswitch.CardView.CardClickListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends Activity {

    CardView cardView;
    MyCardView adapter;
    List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1.���ÿؼ�
        cardView = (CardView) this.findViewById(R.id.cardview);
        adapter = new MyCardView(getApplicationContext());
        adapter.setData(initData());
        cardView.setAdapter(adapter);

        cardView.setCardClickListener(new CardClickListener() {
            @Override
            public void onClick(View view, int pos) {
                // TODO Auto-generated method stub
                android.widget.Toast.makeText(getApplicationContext(), pos + "", 1).show();
            }
        });
    }

    private List<String> initData() {
        // TODO Auto-generated method stub
        data = new java.util.ArrayList<String>();
        data.add("a");
        data.add("b");
        data.add("c");
        data.add("d");
        data.add("e");
        data.add("f");
        data.add("g");
        data.add("h");
        return data;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
        return super.onOptionsItemSelected(item);
    }

    public class MyCardView extends CardAdapter<String> {
        private android.view.LayoutInflater inflater;

        public MyCardView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            inflater = inflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Integer.MAX_VALUE;
        }

        @Override
        public View getCardView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.card_item, null);
            }

            TextView des = (TextView) convertView.findViewById(R.id.tv_des);

            des.setText(data.get(position % data.size()));

            return convertView;
        }

    }
}
