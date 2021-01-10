package com.saber.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.saber.R;
import com.saber.db.MySQLiteOpenHelper;
import com.saber.db.controller.MissionController;
import com.saber.db.entity.Mission;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MissionListAdapter extends BaseAdapter {

    private Context context;

    private List<Mission> data;

    public MissionListAdapter(Context context,List<Mission> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.mission_list_item,parent,false);
        final MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(context);

        final LinearLayout layout = convertView.findViewById(R.id.mission_layout);
        final TextView description = convertView.findViewById(R.id.mission_text);
        final TextView type = convertView.findViewById(R.id.mission_type);
        final TextView time = convertView.findViewById(R.id.mission_time);
        final TextView score = convertView.findViewById(R.id.mission_score);
        final Button complete = convertView.findViewById(R.id.mission_complete);
        final Button update = convertView.findViewById(R.id.mission_update);
        final Button delete = convertView.findViewById(R.id.mission_delete);

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date startTime = null;
        Date endTime = null;
        String sTime = null;
        String eTime = null;
        try {
            startTime = dateFormat.parse(data.get(position).getStartTime());
            endTime = dateFormat.parse(data.get(position).getEndTime());
        } catch (ParseException e) {
            e.printStackTrace();
            Log.i("调试","出问题了");
        }

        if("1".equals(data.get(position).getType())){
            layout.setBackgroundColor(ContextCompat.getColor(context,R.color.everyday));
            type.setText("每日");

            DateFormat tempDateFormat = new SimpleDateFormat("HH:mm");
            if(startTime != null && endTime != null){
                sTime = tempDateFormat.format(startTime);
                eTime = tempDateFormat.format(endTime);
            }
            time.setText(sTime + "-" + eTime);

        }else if("2".equals(data.get(position).getType())){
            layout.setBackgroundColor(ContextCompat.getColor(context,R.color.today));
            type.setText("今日");

            DateFormat tempDateFormat = new SimpleDateFormat("HH:mm");
            if(startTime != null && endTime != null){
                sTime = tempDateFormat.format(startTime);
                eTime = tempDateFormat.format(endTime);
            }
            time.setText(sTime + "-" + eTime);

        }else{
            layout.setBackgroundColor(ContextCompat.getColor(context,R.color.longtime));
            type.setText("长期");

            DateFormat tempDateFormat = new SimpleDateFormat("yyyy.MM.dd");
            if(startTime != null && endTime != null){
                sTime = tempDateFormat.format(startTime);
                eTime = tempDateFormat.format(endTime);
            }
            time.setText(sTime + "-" + eTime);
        }

        description.setText(data.get(position).getDescription());
        score.setText(data.get(position).getScore());

        if("1".equals(data.get(position).getStrikethrough())){
            description.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            type.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            time.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            score.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MissionController.updateMission(data.get(position).getId(),new String[]{"type"},new String[]{"0"},dbHelper);
                data = MissionController.getMissionList("type!=?", new String[]{"0"}, dbHelper);
                MissionListAdapter.this.notifyDataSetChanged();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // lllllllllllllllllllll 这里需要改
                if("0".equals(data.get(position).getStrikethrough())){
                    MissionController.updateMission(data.get(position).getId(),new String[]{"strikethrough"},new String[]{"1"},dbHelper);
                }else{
                    MissionController.updateMission(data.get(position).getId(),new String[]{"strikethrough"},new String[]{"0"},dbHelper);
                }

                data = MissionController.getMissionList("type!=?", new String[]{"0"}, dbHelper);
                MissionListAdapter.this.notifyDataSetChanged();
            }
        });

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MissionController.deleteMission(data.get(position).getId(),dbHelper);
                data.remove(position);
                MissionListAdapter.this.notifyDataSetChanged();
            }
        });

        return convertView;
    }

    public List<Mission> getData() {
        return data;
    }

    public void setData(List<Mission> data) {
        this.data = data;
    }
}
