package com.github.anniepank.schedule.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.anniepank.schedule.AppData;
import com.github.anniepank.schedule.ClassData;
import com.github.anniepank.schedule.R;
import com.github.anniepank.schedule.WeekItem;
import com.github.anniepank.schedule.activities.EditClassActivity;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WeekFragment extends Fragment {

    @BindView(R.id.week_items)
    TableLayout weekItemsView;

    public WeekFragment() {
        // Required empty public constructor
        setHasOptionsMenu(true);
    }

    public void onActivityResult() {
        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        ButterKnife.bind(this, view);
        this.refresh();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_week, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            return true;
        }
        return false;
    }

    private void refresh() {
        weekItemsView.removeAllViews();

        LinkedList<ClassData> classes = AppData.get(this.getContext()).classes;

        int firstClass = 9999;
        int lastClass = 0;

        for (ClassData i : classes) {
            firstClass = Math.min(firstClass, i.timeSlot);
            lastClass = Math.max(lastClass, i.timeSlot);
        }

        String[] dayNames = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int row = 0; row < 6; row++) {
            TableRow tableRow = new TableRow(this.getContext());
            weekItemsView.addView(tableRow);

            ViewGroup dayNameView = (ViewGroup) getLayoutInflater(null).inflate(R.layout.week_day_header, tableRow, true);
            ((TextView) dayNameView.findViewById(R.id.text)).setText(dayNames[row]);



            for (int slot = firstClass; slot <= lastClass; slot++) {
                LinearLayout cell = new LinearLayout(this.getContext());
                tableRow.addView(cell);
                for (ClassData cls : classes) {
                    if (cls.timeSlot == slot && cls.day == row) {
                        WeekItem weekItem = new WeekItem(this.getContext(), cls);

                        final ClassData cls2 = cls;
                        weekItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivityForResult(EditClassActivity.createIntent(getContext(), cls2), 1);
                            }
                        });

                        cell.addView(weekItem);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                        );
                        weekItem.setLayoutParams(params);
                    }
                }
            }
        }
    }
}
