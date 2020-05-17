package com.example.demo.mapperClass;

import java.util.Date;

public class Fields {

        public Date statuscategorychangedate;
        public int timespent;

    @Override
    public String toString() {
        return this.statuscategorychangedate+"|"+this.timespent+"|";
    }
}
