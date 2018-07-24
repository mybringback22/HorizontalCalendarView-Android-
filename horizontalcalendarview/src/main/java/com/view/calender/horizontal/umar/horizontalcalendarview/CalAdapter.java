package com.view.calender.horizontal.umar.horizontalcalendarview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by UManzoor on 11/28/2017.
 */

public class CalAdapter extends RecyclerView.Adapter<CalAdapter.MyViewHolder> {

    Context context;

    Object toCallBack;

    DayDateMonthYearModel lastDaySelected;
    int color = R.color.black;


    ArrayList<DayDateMonthYearModel> dayModelList = new ArrayList<>();
    TextView clickedTextView = null;
    ArrayList<TextView> dateArrayList = new ArrayList<>();
    ArrayList<TextView> dayArrayList = new ArrayList<>();
    ArrayList<View> dividerArrayList = new ArrayList<>();

    public CalAdapter(Context context  , ArrayList<DayDateMonthYearModel> dayModelList   ){
        this.context = context;
        this.dayModelList  = dayModelList;


    }

    public void setCallback(Object toCallBack){
        this.toCallBack = toCallBack;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView day, date;
        public ImageView haveAppointment;
        public View divider;

        public MyViewHolder(View view) {
            super(view);
            day =  view.findViewById(R.id.day);
            date =  view.findViewById(R.id.date);
            haveAppointment =  view.findViewById(R.id.have_appointment);
            divider =  view.findViewById(R.id.divider);

        }
    }

    @Override
    public CalAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_day_layout, parent, false);
        return  new CalAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        char t = dayModelList.get(position).day.charAt(0);

        if(dayModelList.get(position).isToday==true){
            holder.date.setBackground(context.getResources().getDrawable(R.drawable.currect_date_background));
            try {
                CallBack cb = new CallBack(toCallBack, "newDateSelected");
                cb.invoke(dayModelList.get(position));
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

        }

        holder.day.setText(t + " ");
        holder.date.setText(dayModelList.get(position).date);
        holder.date.setTag(position);
        dateArrayList.add(holder.date);
        dayArrayList.add(holder.day);
        dividerArrayList.add(holder.divider);
        holder.day.setTextColor(context.getResources().getColor(color));
        holder.date.setTextColor(context.getResources().getColor(color));
        holder.divider.setBackgroundColor(context.getResources().getColor(color));
        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = Integer.valueOf( v.getTag().toString());
                if(clickedTextView==null) {
                    clickedTextView = (TextView) v;
                    clickedTextView.setBackground(context.getResources().getDrawable(R.drawable.background_selected_day));
                    clickedTextView.setTextColor(context.getResources().getColor(R.color.white));
                    clickedTextView.setTypeface(clickedTextView.getTypeface(), Typeface.NORMAL);
                }else{
//                    if(!dayModelList.get(pos).isToday) {
                    if(lastDaySelected!=null && lastDaySelected.isToday){
                        clickedTextView.setBackground(context.getResources().getDrawable(R.drawable.currect_date_background));
                        clickedTextView.setTextColor(context.getResources().getColor(R.color.white));
                        clickedTextView.setTypeface(clickedTextView.getTypeface(), Typeface.NORMAL);
                    }else{
                        clickedTextView.setBackground(null);
                        clickedTextView.setTextColor(context.getResources().getColor(R.color.grayTextColor));
                        clickedTextView.setTypeface(clickedTextView.getTypeface(), Typeface.NORMAL);
                    }
                    clickedTextView = (TextView) v;
                    clickedTextView.setBackground(context.getResources().getDrawable(R.drawable.background_selected_day));
                    clickedTextView.setTextColor(context.getResources().getColor(R.color.white));
                    clickedTextView.setTypeface(clickedTextView.getTypeface(), Typeface.NORMAL);
                }

                try {
//                    Toast.makeText(context, "XX"+dayModelList.get(pos).year + "-" + dayModelList.get(pos).monthNumeric+"-"+dayModelList.get(pos).date, Toast.LENGTH_SHORT).show();

                    CallBack cb = new CallBack(toCallBack, "newDateSelected");
                    cb.invoke(dayModelList.get(pos));
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }


                lastDaySelected=dayModelList.get(pos);



            }
        });


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
    public void onViewAttachedToWindow(MyViewHolder holder) {
        holder.setIsRecyclable(false);
        super.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(MyViewHolder holder) {
        holder.setIsRecyclable(false);
        super.onViewDetachedFromWindow(holder);
    }


    public void changeAccent(int color ){
        this.color = color;
        for(int i = 0 ; i < dateArrayList.size()  ; i++){
            dayArrayList.get(i).setTextColor(context.getResources().getColor(color));
            dateArrayList.get(i).setTextColor(context.getResources().getColor(color));
            dividerArrayList.get(i).setBackgroundColor(context.getResources().getColor(color));
        }
    }


//    public void addPrevious(DayDateMonthYearModel DDMYModel) {
//        dayModelList.add(0,DDMYModel);
//        notifyItemInserted(dayModelList.size() - 1);
//    }
}
