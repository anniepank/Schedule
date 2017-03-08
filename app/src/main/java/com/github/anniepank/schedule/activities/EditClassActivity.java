package com.github.anniepank.schedule.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.github.anniepank.schedule.AppData;
import com.github.anniepank.schedule.ClassData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anya on 2/26/17.
 */

public class EditClassActivity extends AppCompatActivity {

    @BindView(com.github.anniepank.schedule.R.id.name)
    EditText nameView;

    @BindView(com.github.anniepank.schedule.R.id.teacher)
    EditText teacherView;

    @BindView(com.github.anniepank.schedule.R.id.room)
    EditText roomView;

    public static Intent createIntent(Context context, ClassData classData) {
        Intent intent = new Intent(context, EditClassActivity.class);
        intent.putExtra("id", classData.id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.github.anniepank.schedule.R.layout.activity_edit_class);
        ButterKnife.bind(this);


        String id = getIntent().getStringExtra("id");
        ClassData classData = AppData.get(this).getById(id, ClassData.class);

        teacherView.setText(classData.teacher);
        roomView.setText(classData.room);
      /*
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
    }

}
