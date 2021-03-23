package com.example.myapplication;

import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class Pegatina implements Serializable {
    private ArrayList<TextView> view;
    private ArrayList<Horario> schedules;

    public Pegatina() {
        this.view = new ArrayList<TextView>();
        this.schedules = new ArrayList<Horario>();
    }

    public void addTextView(TextView v){
        view.add(v);
    }

    public void addSchedule(Horario schedule){
        schedules.add(schedule);
    }

    public ArrayList<TextView> getView() {
        return view;
    }

    public ArrayList<Horario> getSchedules() {
        return schedules;
    }
}
