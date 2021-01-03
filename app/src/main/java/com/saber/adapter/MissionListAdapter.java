package com.saber.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import com.saber.R;
import com.saber.db.MySQLiteOpenHelper;
import com.saber.db.controller.MissionController;
import com.saber.db.entity.Mission;

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

        TextView description = convertView.findViewById(R.id.mission_text);
        Button complete = convertView.findViewById(R.id.mission_complete);
        Button update = convertView.findViewById(R.id.mission_update);
        Button delete = convertView.findViewById(R.id.mission_delete);

        description.setText(data.get(position).getDescription());
        complete.setText("完成");
        update.setText("修改");
        delete.setText("删除");

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MissionController.updateMission(data.get(position).getId(),new String[]{"type"},new String[]{"0"},dbHelper);
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
