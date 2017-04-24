package com.github.anniepank.schedule.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.anniepank.schedule.AppData;
import com.github.anniepank.schedule.ClassData;
import com.github.anniepank.schedule.R;
import com.github.anniepank.schedule.SubjectData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anya on 2/26/17.
 */

public class EditClassActivity extends AppCompatActivity {

    @BindView(com.github.anniepank.schedule.R.id.teacher)
    EditText teacherView;

    @BindView(com.github.anniepank.schedule.R.id.room)
    EditText roomView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.subject)
    Spinner subjectView;

    ClassData classData;

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
        classData = AppData.get(this).getById(id, ClassData.class);

        teacherView.setText(classData.teacher);
        roomView.setText(classData.room);

        ArrayAdapter<SubjectData> spinnerArrayAdapter = new ArrayAdapter<SubjectData>(this,
                android.R.layout.simple_spinner_item, AppData.get(this).subjects.toArray(new SubjectData[]{}));
        subjectView.setAdapter(spinnerArrayAdapter);
        subjectView.setSelection(spinnerArrayAdapter.getPosition(AppData.get(this).getById(classData.subjectId, SubjectData.class)));

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        save();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_class, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AppData.get(EditClassActivity.this).classes.remove(classData);
                            AppData.get(EditClassActivity.this).save(EditClassActivity.this);
                            finish();
                        }
                    })
                    .show();
        }
        if (item.getItemId() == R.id.save) {
            save();
            finish();
        }

        if (item.getItemId() == android.R.id.home) {
            save();
            finish();
        }

        return true;

    }

    private void save() {
        classData.teacher = teacherView.getText().toString();
        classData.room = roomView.getText().toString();
        classData.subjectId = ((SubjectData) subjectView.getSelectedItem()).id;
        AppData.get(this).save(this);
    }

}
