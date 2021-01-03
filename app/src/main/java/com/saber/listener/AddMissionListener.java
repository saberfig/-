package com.saber.listener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ListView;
import com.saber.R;
import com.saber.activity.MissionActivity;
import com.saber.adapter.MissionListAdapter;
import com.saber.db.MySQLiteOpenHelper;
import com.saber.db.controller.MissionController;
import com.saber.db.entity.Mission;

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
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        // 获取布局
        View addView = View.inflate(context, R.layout.mission_add, null);

        // 获取布局中的控件
        final EditText description = addView.findViewById(R.id.add_mission_description_edit);
        final EditText type = addView.findViewById(R.id.add_mission_type_edit);
        final EditText endTime = addView.findViewById(R.id.add_mission_endTime_edit);
        final EditText father = addView.findViewById(R.id.add_mission_father_edit);
        final EditText score = addView.findViewById(R.id.add_mission_score_edit);

        // 设置参数
        alertDialogBuilder
            .setMessage("添加任务")
            .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String descriptionS = description.getText().toString().trim();
                    String typeS = type.getText().toString().trim();
                    String endTimeS = endTime.getText().toString().trim();
                    String fatherS = father.getText().toString().trim();
                    String scoreS = score.getText().toString().trim();

                    MissionController.addMission(descriptionS, typeS, endTimeS, fatherS, scoreS, dbHelper);

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
}
