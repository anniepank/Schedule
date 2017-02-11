package com.github.anniepank.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class TodoFragment extends Fragment {

    @BindView(R.id.task_list) ListView taskList;
    TaskListAdapter adapter;

    public TodoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todo, container, false);
        ButterKnife.bind(this, view);

        adapter = new TaskListAdapter(getContext(), AppData.get(getContext()).tasks);
        taskList.setAdapter(adapter);
        return view;
    }

    public void onActivityResult() {
        //меняется список(имена например)
        adapter.notifyDataSetChanged();
    }

    class TaskListAdapter extends ArrayAdapter<TaskData> {

        public TaskListAdapter(Context context, List<TaskData> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
            View view = getLayoutInflater(null).inflate(R.layout.task_item, parent, false);
            TextView nameView = (TextView)view.findViewById(R.id.name);
            nameView.setText(this.getItem(position).name);

            //opens edit activity for task
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TaskData taskData = TaskListAdapter.this.getItem(position);
                    Intent intent = EditTaskActivity.createIntent(TaskListAdapter.this.getContext(), taskData);
                    //для обновления элеентов списка, но result принять не сможет
                    startActivityForResult(intent, 1);
                }
            });
            return view;
        }
    }
}