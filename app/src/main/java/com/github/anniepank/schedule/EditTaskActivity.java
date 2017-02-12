package com.github.anniepank.schedule;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTaskActivity extends AppCompatActivity {

    public TaskData taskData; //чтобы несколько раз не добывать себе эту инфу

    public static Intent createIntent(Context context, TaskData taskData) {
        Intent intent = new Intent(context, EditTaskActivity.class);
        intent.putExtra("id", taskData.id);
        return intent;
    }

    @BindView(R.id.edit_task_name)
    EditText nameEditText;

    @BindView(R.id.edit_task_desc)
    EditText descEditText;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        ButterKnife.bind(this);

        String id = this.getIntent().getStringExtra("id");

        //получаем те самые настройки, чтобы иметь доступ к объектам списка
        taskData = AppData.get(this).getTaskById(id);

        //устанавливаем имя
        nameEditText.setText(taskData.name);
        descEditText.setText(taskData.description);

        setSupportActionBar(toolbar);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //меняем реальное значение
        taskData.name = nameEditText.getText().toString();
        taskData.description = descEditText.getText().toString();
        //сохранаяем в телефон
        AppData.get(this).save(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_task, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        return true;

    }
}
