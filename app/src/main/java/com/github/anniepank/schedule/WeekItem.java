package com.github.anniepank.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.github.anniepank.schedule.R.id.room;

/**
 * Created by anya on 3/8/17.
 */

public class WeekItem extends FrameLayout {
    @BindView(R.id.subject_name)
    TextView subjectNameView;

    @BindView(room)
    TextView roomView;

    private ClassData classData;

    public WeekItem(Context context, ClassData classData) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.week_item, null, false);
        this.addView(view);

        ButterKnife.bind(this);

        this.classData = classData;

        subjectNameView.setText(AppData.get(this.getContext()).getById(classData.subjectId, SubjectData.class).name);
        roomView.setText(classData.room);

    }
}
