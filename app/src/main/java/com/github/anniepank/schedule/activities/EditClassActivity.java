package com.github.anniepank.schedule.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.anniepank.schedule.AppData;
import com.github.anniepank.schedule.ClassData;
import com.github.anniepank.schedule.R;
import com.github.anniepank.schedule.SubjectData;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.anniepank.schedule.R.id.subject;

/**
 * Created by anya on 2/26/17.
 */

public class EditClassActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_NEW_SUBJECT = 1;
    @BindView(com.github.anniepank.schedule.R.id.teacher)
    EditText teacherView;

    @BindView(com.github.anniepank.schedule.R.id.room)
    EditText roomView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(subject)
    Spinner subjectView;

    @BindView(R.id.day)
    Spinner dayView;

    @BindView(R.id.pair)
    Spinner timeView;

    ClassData classData;

    public static Intent createIntent(Context context, ClassData classData) {
        Intent intent = new Intent(context, EditClassActivity.class);
        intent.putExtra("id", classData.id);
        return intent;
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, EditClassActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.github.anniepank.schedule.R.layout.activity_edit_class);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("id")) {
            String id = getIntent().getStringExtra("id");
            classData = AppData.get(this).getById(id, ClassData.class);
        } else {
            classData = new ClassData();
            classData.subjectId = AppData.get(this).subjects.get(0).id;
            classData.timeSlot = 0;
        }

        teacherView.setText(classData.teacher);
        roomView.setText(classData.room);

        setupSubjectSpinner(AppData.get(this).getById(classData.subjectId, SubjectData.class));

        dayView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday"}));
        dayView.setSelection(classData.day);

        timeView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, new String[]{"1", "2", "3", "4", "5"}));
        timeView.setSelection(classData.timeSlot);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @NonNull
    private void setupSubjectSpinner(SubjectData selection) {
        final List<SubjectData> subjects = new LinkedList<SubjectData>(AppData.get(this).subjects);
        subjects.add(new SubjectData("new subject"));

        ArrayAdapter<SubjectData> spinnerArrayAdapter = new ArrayAdapter<SubjectData>(this,
                android.R.layout.simple_spinner_item, subjects.toArray(new SubjectData[]{}));
        subjectView.setAdapter(spinnerArrayAdapter);

        subjectView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == subjects.size() - 1) {
                    startActivityForResult(EditSubjectActivity.createIntent(EditClassActivity.this),
                            REQUEST_CODE_NEW_SUBJECT);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subjectView.setSelection(spinnerArrayAdapter.getPosition(selection));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_NEW_SUBJECT) {
            if (resultCode == EditSubjectActivity.RESULT_CODE_OK) {
                classData.subjectId = data.getStringExtra(EditSubjectActivity.EXTRA_ID);
            }
            setupSubjectSpinner(AppData.get(this).getById(classData.subjectId, SubjectData.class));
        }
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
            finish();
        }

        return true;

    }

    private void save() {
        if (!getIntent().hasExtra("id")) {
            AppData.get(this).classes.add(classData);
        }
        classData.teacher = teacherView.getText().toString();
        classData.room = roomView.getText().toString();
        classData.subjectId = ((SubjectData) subjectView.getSelectedItem()).id;
        classData.day = dayView.getSelectedItemPosition();
        classData.timeSlot = timeView.getSelectedItemPosition();
        AppData.get(this).save(this);
    }

}
