package com.example.demo.viewModels;

public class UserLoadViewModel {
    public Iterable<UserLoadModel> userLoads;
    public Iterable<String> days;

    public UserLoadViewModel(Iterable<UserLoadModel> userLoads, Iterable<String> days) {
        this.userLoads = userLoads;
        this.days = days;
    }
}
