package com.jakapong.enegist.Dataloader;

import android.content.Context;

import com.androidquery.AQuery;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by jakapong on 10/11/14 AD.
 */
public abstract class ModelMaster {
    public String key;
    protected Context context;
    protected AQuery aQuery;
    protected Api api;
    protected String TAG = "Examination";
    ModelStatusListener modelStatusListener;
    String listKeyName = "data";


    public ModelMaster(Context context, String key) {
        this.context = context;
        this.key = key;
        api = new Api();
        aQuery = new AQuery(context);


    }

    public abstract void run();

    public void setModelStatusListener(ModelStatusListener modelStatusListener) {
        this.modelStatusListener = modelStatusListener;
    }

    String getString(JSONObject jsonObject, String key) {
        try {
            return jsonObject.getString(key);
        } catch (Exception e) {
            return "";
        }
    }

    public String mapToJson(Map<String, Object> attributesMap) {

        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(convertToList(attributesMap));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "";
    }

    public Map<String, Object> convertToList(Map<String, Object> attributesMap) {
        int maxIndex = 0;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<Integer, Object> treeMap = new TreeMap<Integer, Object>();
        for (String key : attributesMap.keySet()) {
            boolean parsed = false;
            if (key != null && key.length() > 0) {
                try {
                    int index = Integer.parseInt(key);
                    if (index > maxIndex) {
                        maxIndex = index;
                    }
                    treeMap.put(index, attributesMap.get(key));
                    parsed = true;
                } catch (NumberFormatException e) {

                }
            }
            if (parsed == false) {
                Object value = attributesMap.get(key);
                if (value instanceof Map<?, ?>) {
                    Map<String, Object> objectSon = convertToList((Map<String, Object>) value);
                    resultMap.put(key, objectSon);
                } else {
                    resultMap.put(key, attributesMap.get(key));
                }
            }
        }
        if (treeMap.size() > 0) {
            Collection<Object> collection = treeMap.values();
            List<Object> list = new ArrayList<Object>();
            list.addAll(collection);
            resultMap.put(listKeyName, list);
        }
        return resultMap;
    }

    protected void sendResult(JSONObject object) {
        if (object != null) {

            try {
                if (object.getString("error_code").equals("000")) {

                    if (modelStatusListener != null) {
                        modelStatusListener.onLoadDataSuccess(key,
                                "Success!");
                    }
                } else {
                    if (modelStatusListener != null) {
                        modelStatusListener.onLoadDataFailed(key
                                + " : " + object.getString("message"));
                    }
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                if (modelStatusListener != null) {
                    modelStatusListener.onLoadDataFailed(key
                            + " : JSONException");
                }
            }

        } else {
            if (modelStatusListener != null) {
                modelStatusListener.onLoadDataFailed(key
                        + " : Server error!");
            }
        }

    }


}
