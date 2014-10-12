package com.jakapong.enegist.Dataloader;

/**
 * Created by jakapong on 10/11/14 AD.
 */
public interface ModelStatusListener {

    public void onLoadDataSuccess(String key, Object ts);

    public void onLoadDataFailed(String key);

}
