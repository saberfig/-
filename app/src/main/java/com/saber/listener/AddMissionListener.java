package com.saber.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import com.saber.R;
import com.saber.adapter.MissionListAdapter;
import com.saber.db.MySQLiteOpenHelper;
import com.saber.db.controller.MissionController;
import com.saber.db.entity.Mission;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AddMissionListener implements View.OnClickListener{

    Context context;
    MissionListAdapter adapter;

    public AddMissionListener(Context context, MissionListAdapter adapter){
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    public void onClick(View v) {
        final MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(context);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // 获取布局
        View addView = View.inflate(context, R.layout.mission_add, null);

        // 获取布局中的控件
        final EditText description = addView.findViewById(R.id.add_mission_description_edit);
        final Spinner type = addView.findViewById(R.id.add_mission_type_edit);
        final EditText startTime = addView.findViewById(R.id.add_mission_startTime_edit);
        final EditText endTime = addView.findViewById(R.id.add_mission_endTime_edit);
        final EditText score = addView.findViewById(R.id.add_mission_score_edit);

        // 设置参数
        alertDialogBuilder
            .setMessage("添加任务")
            .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String descriptionS = description.getText().toString().trim();
                    String typeS = String.valueOf(type.getSelectedItemId() + 1);
                    String startTimeS = startTime.getText().toString().trim();
                    String endTimeS = endTime.getText().toString().trim();
                    String scoreS = score.getText().toString().trim();

                    String sTime = timeFormat(startTimeS);
                    String eTime = timeFormat(endTimeS);

                    MissionController.addMission(descriptionS, typeS, sTime, eTime, scoreS, dbHelper);

                    List<Mission> missions = MissionController.getMissionList("type!=?", new String[]{"0"}, dbHelper);
                    adapter.setData(missions);
                    adapter.notifyDataSetChanged();
                }
            })
            .setNegativeButton("取消",null)
            .setView(addView);

        // 创建对话框
        alertDialogBuilder.create().show();
    }

    private String timeFormat(String time){
        String[] strings = time.split("\\.");
        String large = null;
        String small = null;
        if(2 == strings.length){
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            large = dateFormat.format(date);
            small = strings[0].trim() + ":" + strings[1].trim();

        }else if(3 == strings.length){
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("hh:mm");
            large = strings[0].trim() + "-" + strings[1].trim() + "-" +strings[2].trim();
            small = dateFormat.format(date);
        }
        return large + " " + small;
    }
}
