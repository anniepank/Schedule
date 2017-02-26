package com.github.anniepank.schedule.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;

import com.github.anniepank.schedule.AppData;
import com.github.anniepank.schedule.R;
import com.github.anniepank.schedule.TaskData;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditTaskActivity extends AppCompatActivity {

    public TaskData taskData; //чтобы несколько раз не добывать себе эту инфу

    public static Intent createIntent(Context context, TaskData taskData) {
        Intent intent = new Intent(context, EditTaskActivity.class);
        intent.putExtra("id", taskData.id);
        return intent;
    }

    @BindView(R.id.name)
    EditText nameView;

    @BindView(R.id.description)
    EditText descriptionView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.date)
    EditText dateView;

    @BindView(R.id.room)
    EditText roomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        ButterKnife.bind(this);

        String id = this.getIntent().getStringExtra("id");

        //получаем те самые настройки, чтобы иметь доступ к объектам списка
        taskData = AppData.get(this).getById(id, TaskData.class);

        //устанавливаем имя
        nameView.setText(taskData.name);
        descriptionView.setText(taskData.description);
        dateView.setText(taskData.getFormattedDate());
        roomView.setText(taskData.room);

        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @OnClick(R.id.date) void pickDate() {
        final Calendar date = Calendar.getInstance();
        if(taskData.date != 0) {
            date.setTimeInMillis(taskData.date);
        }
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date.set(year, month, day);
                taskData.date = date.getTimeInMillis();
                dateView.setText(taskData.getFormattedDate());
            }
        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //меняем реальное значение
        save();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_task, menu);

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
                            AppData.get(EditTaskActivity.this).tasks.remove(taskData);
                            AppData.get(EditTaskActivity.this).save(EditTaskActivity.this);
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
        taskData.name = nameView.getText().toString();
        taskData.room = roomView.getText().toString();
        taskData.description = descriptionView.getText().toString();
        AppData.get(this).save(this);
    }
}
