package com.jakapong.enegist.Entries;

import java.util.ArrayList;

/**
 * Created by jakapong on 10/11/14 AD.
 */
public class ExaminationData extends MasterEntry{

        ArrayList<ExaminationEntry> data;

        public ArrayList<ExaminationEntry> getData() {
            return data;
        }

        public void setData(ArrayList<ExaminationEntry> data) {
            this.data = data;
        }

}

