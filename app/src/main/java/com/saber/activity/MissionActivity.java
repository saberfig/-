package com.saber.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.saber.R;
import com.saber.adapter.MissionListAdapter;
import com.saber.db.entity.Mission;
import com.saber.db.controller.MissionController;
import com.saber.db.MySQLiteOpenHelper;
import com.saber.listener.AddMissionListener;

import java.util.*;

public class MissionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MySQLiteOpenHelper dbHelper = new MySQLiteOpenHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        List<Mission> missions = MissionController.getMissionList("type!=?", new String[]{"0"}, dbHelper);
        ListView listView = findViewById(R.id.mission_list);
        MissionListAdapter adapter = new MissionListAdapter(getApplicationContext(),missions);
        listView.setAdapter(adapter);

        Button addMission = findViewById(R.id.add_mission);
        addMission.setOnClickListener(new AddMissionListener(MissionActivity.this,adapter));
    }

}
