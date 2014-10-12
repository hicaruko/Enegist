package com.jakapong.enegist.Dataloader;

/**
 * Created by jakapong on 10/11/14 AD.
 */
public class Api {
    String host = "http://project.enegist.com/";
    String apiGetExamination = "examination/mobile.json.php";

    public String getExamination(){
        return host + apiGetExamination;
    }
}
