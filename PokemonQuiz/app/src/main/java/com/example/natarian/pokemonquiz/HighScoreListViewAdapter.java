package com.example.natarian.pokemonquiz;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natarian on 3/12/17.
 */

public class HighScoreListViewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Score> scoreList;
    private Score score;

    public HighScoreListViewAdapter(Context context, List<Score> scoreList) {
        if (scoreList != null) {
            this.scoreList = scoreList;
        } else {
            this.scoreList = new ArrayList<>();
        }
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return scoreList.size();
    }

    @Override
    public Object getItem(int position) {
        return scoreList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.high_score_list_item, parent, false);

        if (view != null) {
            score = scoreList.get(position);
            if (score != null) {

                ImageView imvTrophy = (ImageView) view.findViewById(R.id.imvTrophy);
                TextView txtName = (TextView) view.findViewById(R.id.txtName);
                TextView txtScore = (TextView) view.findViewById(R.id.txtScore);

                Typeface type3 = Typeface.createFromAsset(view.getContext().getAssets(), "TitanOne-Regular.ttf");
                txtName.setTypeface(type3);
                txtScore.setTypeface(type3);

                switch (position) {
                    case 0:
                        imvTrophy.setImageResource(R.drawable.gold_trophy);
                        break;
                    case 1:
                        imvTrophy.setImageResource(R.drawable.silver_trophy);
                        break;
                    case 2:
                        imvTrophy.setImageResource(R.drawable.bronze_trophy);
                        break;
                    case 3:
                        imvTrophy.setImageResource(R.drawable.number4_trophy);
                        break;
                    case 4:
                        imvTrophy.setImageResource(R.drawable.number5_trophy);
                        break;
                }

                txtName.setText(score.getPlayerName());
                txtScore.setText(String.valueOf(score.getPlayerScore()));
            }
        }

        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    public void swap(List<Score> list) {
        this.scoreList.clear();
        this.scoreList.addAll(list);
        notifyDataSetChanged();
    }

}
