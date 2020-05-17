package com.example.demo.mapperClass;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Issue {
    public Long id;
    public String key;

    @Override
    public String toString() {
        return this.id+"|"+this.key+"|";
    }
}
