package com.view.calender.horizontal.umar.horizontalcalendarview.material;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.view.calender.horizontal.umar.horizontalcalendarview.CalAdapter;
import com.view.calender.horizontal.umar.horizontalcalendarview.DayDateMonthYearModel;
import com.view.calender.horizontal.umar.horizontalcalendarview.R;

import java.util.ArrayList;

/**
 * Created by UManzoor on 11/28/2017.
 */

public class MaterialCalAdapter extends CalAdapter<MaterialCalAdapter.MaterialViewHolder> {
    private Pair<View, Pair<TextView, TextView>> clickedView = null;

    public MaterialCalAdapter(Context context, ArrayList<DayDateMonthYearModel> dayModelList) {
        super(context, dayModelList);
    }

    @Override
    protected int getCustomLayout() {
        return R.layout.material_custom_day_layout;
    }

    @Override
    public void onBindViewHolder(MaterialViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.updateWidth();
    }

    @Override
    protected void updateSelectedItemUI(View root) {
        View clicked = root.findViewById(R.id.background);
        TextView day = root.findViewById(R.id.day);
        TextView date = root.findViewById(R.id.date);
        if (clickedView == null) {
            clickedView = new Pair(clicked, new Pair(date, day));
            clickedView.first.setAlpha(1f);
            clickedView.first.setVisibility(View.VISIBLE);
            clickedView.second.first.setTextSize(24);
            clickedView.second.first.setTextColor(Color.WHITE);
            clickedView.second.second.setTextColor(Color.WHITE);
        } else {
            if (lastDaySelected != null && lastDaySelected.isToday) {
                clickedView.first.setAlpha(0.3f);
                clickedView.first.setVisibility(View.VISIBLE);
                clickedView.second.first.setTextSize(24);
                clickedView.second.first.setTextColor(context.getResources().getColor(color));
                clickedView.second.second.setTextColor(context.getResources().getColor(color));
            } else {
                clickedView.first.setVisibility(View.INVISIBLE);
                clickedView.second.first.setTextSize(18);
                clickedView.second.first.setTextColor(context.getResources().getColor(color));
                clickedView.second.second.setTextColor(context.getResources().getColor(color));
            }
            clickedView = new Pair(clicked, new Pair(date, day));
            clickedView.first.setAlpha(1f);
            clickedView.second.first.setTextSize(24);
            clickedView.second.first.setTextColor(Color.WHITE);
            clickedView.second.second.setTextColor(Color.WHITE);
            clickedView.first.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    protected MaterialViewHolder getViewHolder(View itemView) {
        return new MaterialViewHolder(itemView);
    }


    class MaterialViewHolder extends CalAdapter.MyViewHolder {
        MaterialViewHolder(View view) {
            super(view);
            background = view.findViewById(R.id.background);
        }

        @Override
        protected void setBackgroundColor(Drawable drawable) {
            this.background.setVisibility(View.VISIBLE);
        }

        void updateWidth() {
            //to show only 7 days on screen at single time
            setNewWidth(day);
            setNewWidth(date);
            setNewWidth(background);
        }

        private void setNewWidth(View v) {
            if (v.getMeasuredWidth() != itemWidthPx) {
                v.setLayoutParams(getNewParams(v));
            }
        }

        private FrameLayout.LayoutParams getNewParams(View root) {
            return new FrameLayout.LayoutParams(itemWidthPx, root.getLayoutParams().height);
        }
    }
}
