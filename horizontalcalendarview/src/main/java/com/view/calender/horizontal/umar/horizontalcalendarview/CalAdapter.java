package com.view.calender.horizontal.umar.horizontalcalendarview;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by UManzoor on 11/28/2017.
 */

public class CalAdapter<T extends CalAdapter.MyViewHolder> extends RecyclerView.Adapter<T> {
    protected int itemWidthPx;
    protected DayDateMonthYearModel lastDaySelected;
    protected Context context;
    protected int color = R.color.black;
    private HorizontalCalendarListener toCallBack;
    private TextView clickedTextView = null;
    private ArrayList<TextView> dateArrayList = new ArrayList<>();
    private ArrayList<TextView> dayArrayList = new ArrayList<>();
    private ArrayList<View> dividerArrayList = new ArrayList<>();
    private ArrayList<DayDateMonthYearModel> dayModelList;
    private WeekNameMode weekMode = WeekNameMode.SHORT;

    public CalAdapter(Context context, ArrayList<DayDateMonthYearModel> dayModelList) {
        this.context = context;
        this.dayModelList = dayModelList;
    }

    public void setCallback(HorizontalCalendarListener toCallBack) {
        this.toCallBack = toCallBack;
    }

    protected int getCustomLayout() {
        return R.layout.custom_day_layout;
    }

    public void setItemWidth(int widthPx) {
        this.itemWidthPx = widthPx;
    }

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(getCustomLayout(), parent, false);
        return getViewHolder(itemView);
    }

    @NonNull
    protected T getViewHolder(View itemView) {
        return (T) new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final T holder, int position) {
        String t = getWeekDayName(position);
        holder.day.setTextColor(context.getResources().getColor(color));
        holder.date.setTextColor(context.getResources().getColor(color));
        if (dayModelList.get(position).isToday) {
            updateSelectedItemUI(holder.itemView);
            lastDaySelected = dayModelList.get(position);
            try {
                CallBack cb = new CallBack(toCallBack, "newDateSelected");
                cb.invoke(dayModelList.get(position));
            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                //non
            }
        }

        holder.day.setText(t + " ");
        holder.date.setText(dayModelList.get(position).date);
        holder.itemView.setTag(position);
        dateArrayList.add(holder.date);
        dayArrayList.add(holder.day);
        dividerArrayList.add(holder.divider);
        holder.divider.setBackgroundColor(context.getResources().getColor(color));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = Integer.valueOf(v.getTag().toString());
                updateSelectedItemUI(v);

                try {
                    CallBack cb = new CallBack(toCallBack, "newDateSelected");
                    cb.invoke(dayModelList.get(pos));
                } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException e) {
                    //non
                }
                lastDaySelected = dayModelList.get(pos);
            }
        });
    }

    protected void updateSelectedItemUI(View root) {
        TextView date = root.findViewById(R.id.date);
        if (clickedTextView == null) {
            clickedTextView = date;
            clickedTextView.setBackground(context.getResources().getDrawable(R.drawable.background_selected_day));
            clickedTextView.setTextColor(context.getResources().getColor(R.color.white));
            clickedTextView.setTypeface(clickedTextView.getTypeface(), Typeface.NORMAL);
        } else {
//                    if(!dayModelList.get(pos).isToday) {
            if (lastDaySelected != null && lastDaySelected.isToday) {
                clickedTextView.setBackground(context.getResources().getDrawable(R.drawable.currect_date_background));
                clickedTextView.setTextColor(context.getResources().getColor(R.color.white));
                clickedTextView.setTypeface(clickedTextView.getTypeface(), Typeface.NORMAL);
            } else {
                clickedTextView.setBackground(null);
                clickedTextView.setTextColor(context.getResources().getColor(R.color.grayTextColor));
                clickedTextView.setTypeface(clickedTextView.getTypeface(), Typeface.NORMAL);
            }
            clickedTextView = date;
            clickedTextView.setBackground(context.getResources().getDrawable(R.drawable.background_selected_day));
            clickedTextView.setTextColor(context.getResources().getColor(R.color.white));
            clickedTextView.setTypeface(clickedTextView.getTypeface(), Typeface.NORMAL);
        }
    }

    private String getWeekDayName(int position) {
        String day = dayModelList.get(position).day;
        switch (weekMode) {
            case SHORT:
                return day.substring(0, 1);
            case MEDIUM:
                return day;
            default:
                //never happens
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return dayModelList.size();
    }

    public void add(DayDateMonthYearModel DDMYModel) {
        dayModelList.add(DDMYModel);
        notifyItemInserted(dayModelList.size() - 1);
    }

    @Override
    public void onViewAttachedToWindow(T holder) {
        holder.setIsRecyclable(false);
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(T holder) {
        holder.setIsRecyclable(false);
        super.onViewDetachedFromWindow(holder);
    }

    public void changeAccent(int color) {
        this.color = color;
        for (int i = 0; i < dateArrayList.size(); i++) {
            dayArrayList.get(i).setTextColor(context.getResources().getColor(color));
            dateArrayList.get(i).setTextColor(context.getResources().getColor(color));
            dividerArrayList.get(i).setBackgroundColor(context.getResources().getColor(color));
        }
    }

    protected void reloadData(CalAdapter adapter) {
        if (adapter != null) {
            adapter.context = this.context;
            adapter.toCallBack = this.toCallBack;
            adapter.changeAccent(this.color);
        }
    }

    public void setWeekMode(WeekNameMode weekMode) {
        this.weekMode = weekMode;
    }

    public enum WeekNameMode {
        SHORT, MEDIUM
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day, date;
        public View background;
        public View divider;
        public View root;

        public MyViewHolder(View view) {
            super(view);
            day = view.findViewById(R.id.day);
            date = view.findViewById(R.id.date);
            divider = view.findViewById(R.id.divider);
            background = date;
        }

        protected void setBackgroundColor(Drawable drawable) {
            background.setBackground(drawable);
        }
    }
}
