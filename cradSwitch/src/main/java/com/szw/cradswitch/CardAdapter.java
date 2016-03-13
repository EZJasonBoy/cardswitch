package com.szw.cradswitch;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;

public abstract class CardAdapter<T> extends BaseAdapter {

    private List<T> data;
    private Context context;

    public CardAdapter(Context context) {
        super();
        this.context = context;
        data = new java.util.ArrayList<T>();
    }

    public CardAdapter(List<T> data, Context context) {
        super();
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        FrameLayout contain = (FrameLayout) convertView;
        View cardView;
        View coverView;
        if (contain == null) {
            contain = new FrameLayout(context);
            contain.setBackgroundResource(R.drawable.meet_white_bg);
            cardView = getCardView(position, convertView, parent);
            contain.addView(cardView);
        } else {
            cardView = contain.getChildAt(0);
            coverView = getCardView(position, cardView, contain);
            //Ҫ��ɾ����Ȼ������ӣ�������治����
            contain.removeView(cardView);
            contain.addView(coverView);
        }

        return contain;
    }

    public abstract View getCardView(int position, View convertView, ViewGroup parent);

    public List<T> getData() {
        return data;
    }

    /**
     * ��������
     *
     * @param data
     */
    public void setData(List<T> data) {
        this.data = data;
    }


}
