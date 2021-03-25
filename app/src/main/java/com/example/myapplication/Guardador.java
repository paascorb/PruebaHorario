package com.example.myapplication;

import android.util.Log;

import com.github.tlaabs.timetableview.Time;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Guardador {


    public static String saveSticker(HashMap<Integer, Pegatina> stickers) {
        JSONObject obj1 = new JSONObject();
        JSONArray arr1 = new JSONArray();
        int[] orders = getSortedKeySet(stickers);
        for (int i = 0; i < orders.length; i++) {
            JSONObject obj2 = new JSONObject();
            int idx = orders[i];
            obj2.put("idx", orders[i]);
            JSONArray arr2 = new JSONArray();//5
            ArrayList<Horario> schedules = stickers.get(idx).getSchedules();
            for (Horario schedule : schedules) {
                JSONObject obj3 = new JSONObject();
                obj3.put("classTitle", schedule.classTitle);
                obj3.put("classPlace", schedule.classPlace);
                obj3.put("professorName", schedule.getProfessorName());
                obj3.put("day", schedule.getDay());
                JSONObject obj4 = new JSONObject();//startTime
                obj4.put("hour", schedule.getStartTime().getHour());
                obj4.put("minute", schedule.getStartTime().getMinute());
                obj3.put("startTime", obj4);
                JSONObject obj5 = new JSONObject();//endtTime
                obj5.put("hour", schedule.getEndTime().getHour());
                obj5.put("minute", schedule.getEndTime().getMinute());
                obj3.put("endTime", obj5);
                arr2.add(obj3);
            }
            obj2.put("schedule", arr2);
            arr1.add(obj2);
        }
        obj1.put("sticker", arr1);
        return obj1.toString();
    }

    public static HashMap<Integer, Pegatina> loadSticker(String json) {
        HashMap<Integer, Pegatina> stickers = new HashMap<Integer, Pegatina>();
        JSONParser parser = new JSONParser();
        try {
            JSONObject obj1 = (JSONObject) parser.parse(json);
            JSONArray arr1 = (JSONArray) obj1.get("sticker");
            for (int i = 0; i < arr1.size(); i++) {
                Pegatina sticker = new Pegatina();
                JSONObject obj2 = (JSONObject) arr1.get(i);
                int idx = Integer.parseInt(obj2.get("idx").toString());
                JSONArray arr2 = (JSONArray) obj2.get("schedule");
                for (int k = 0; k < arr2.size(); k++) {
                    Horario schedule = new Horario();
                    JSONObject obj3 = (JSONObject) arr2.get(k);
                    schedule.setClassTitle(obj3.get("classTitle").toString());
                    schedule.setClassPlace(obj3.get("classPlace").toString());
                    schedule.setProfessorName(obj3.get("professorName").toString());
                    schedule.setDay(Integer.parseInt(obj3.get("day").toString()));
                    Time startTime = new Time();
                    JSONObject obj4 = (JSONObject) obj3.get("startTime");
                    startTime.setHour(Integer.parseInt(obj4.get("hour").toString()));
                    startTime.setMinute(Integer.parseInt(obj4.get("minute").toString()));
                    Time endTime = new Time();
                    JSONObject obj5 = (JSONObject) obj3.get("endTime");
                    endTime.setHour(Integer.parseInt(obj5.get("hour").toString()));
                    endTime.setMinute(Integer.parseInt(obj5.get("minute").toString()));
                    schedule.setStartTime(startTime);
                    schedule.setEndTime(endTime);
                    sticker.addSchedule(schedule);
                }
                stickers.put(idx, sticker);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return stickers;
    }


    static private int[] getSortedKeySet(HashMap<Integer, Pegatina> stickers) {
        int[] orders = new int[stickers.size()];
        int i = 0;
        for (int key : stickers.keySet()) {
            orders[i++] = key;
        }
        Arrays.sort(orders);
        return orders;
    }
}