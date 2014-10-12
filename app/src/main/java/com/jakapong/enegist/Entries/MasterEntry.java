package com.jakapong.enegist.Entries;

import org.codehaus.jackson.annotate.JsonAnySetter;

/**
 * Created by jakapong on 10/11/14 AD.
 */
public abstract class MasterEntry {

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        // do something: put to a Map; log a warning, whatever
    }
}