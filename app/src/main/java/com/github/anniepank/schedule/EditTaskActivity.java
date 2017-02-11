package com.github.anniepank.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //меняем реальное значение
        taskData.name = nameEditText.getText().toString();
        //сохранаяем в телефон
        AppDataLoader.save(this, AppData.get(this));
    }
}
