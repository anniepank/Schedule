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
import android.widget.EditText;

import com.github.anniepank.schedule.AppData;
import com.github.anniepank.schedule.R;
import com.github.anniepank.schedule.SubjectData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by anya on 2/26/17.
 */

public class EditSubjectActivity extends AppCompatActivity {

    public static final String EXTRA_ID = "id";
    @BindView(R.id.name)
    EditText nameView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    SubjectData subjectData;

    public static final int RESULT_CODE_OK = 1, RESULT_CODE_CANCELLED = 2;

    public static Intent createIntent(Context context, SubjectData subjectData) {
        Intent intent = new Intent(context, EditSubjectActivity.class);
        intent.putExtra(EXTRA_ID, subjectData.id);
        return intent;
    }

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, EditSubjectActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_edit_subject);
        ButterKnife.bind(this);

        if (getIntent().hasExtra(EXTRA_ID)) {
            String id = getIntent().getStringExtra(EXTRA_ID);
            subjectData = AppData.get(this).getById(id, SubjectData.class);
        } else {
            subjectData = new SubjectData("");
        }

        nameView.setText(subjectData.name);

        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_subject, menu);

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
                            //TODO: delete all classes
                            AppData.get(EditSubjectActivity.this).subjects.remove(subjectData);
                            AppData.get(EditSubjectActivity.this).save(EditSubjectActivity.this);
                            setResult(RESULT_CODE_CANCELLED);
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
            setResult(RESULT_CODE_CANCELLED);
            finish();
        }

        return true;

    }

    private void save() {
        if (!getIntent().hasExtra(EXTRA_ID)) {
            AppData.get(this).subjects.add(subjectData);
        }
        subjectData.name = nameView.getText().toString();
        AppData.get(this).save(this);

        Intent intent = new Intent();
        intent.putExtra(EXTRA_ID, subjectData.id);
        setResult(RESULT_CODE_OK, intent);
    }

}
