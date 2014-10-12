package com.jakapong.enegist.Dataloader;

import android.content.Context;
import android.util.Log;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.jakapong.enegist.Entries.ExaminationData;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jakapong on 10/11/14 AD.
 */
public class ExaminationLoader extends ModelMaster {

    HashMap<String, Object> params = new HashMap<String, Object>();

    private String url;

    public ExaminationLoader(Context context) {
        super(context, "ExaminationLoader");
    }

    public void load() {

        run();
    }

    @Override
    public void run() {

        Api api = new Api();
        url = api.getExamination();

         AQuery aq = new AQuery(context);
        aq.ajax(url, String.class, new AjaxCallback<String>() {

            @Override
            public void callback(String url, String data, AjaxStatus status) {


                if (data != null) {
                    data = "{"+"\"data\""+":"+data+"}";
                    ObjectMapper om = new ObjectMapper();
                    try {
                        ExaminationData m = om.readValue(data, ExaminationData.class);

                        if (modelStatusListener != null){
                             modelStatusListener.onLoadDataSuccess(key,  m.getData());
                         }
                    } catch (IOException e) {
                        e.printStackTrace();
                        if (modelStatusListener != null){
                           modelStatusListener.onLoadDataFailed(key
                                    + " : IOException");
                        }
                    }

                } else {
                    if (modelStatusListener != null){
                        modelStatusListener.onLoadDataFailed("goalPageLoader"
                                + ": data == null");


                    }
                }
            }
        });

    }
}
