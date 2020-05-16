package com.example.demo.mapperClass;

public class Dashboard {
    public int id;
    public String name;
    public String view;

    @Override
    public String toString() {
        return String.format("Dashboard: id={%s}, \"{%s}\", доступен по адресу {%s}", this.id, this.name, this.view);
    }
}

