package com.example.exam_task.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String URL = "https://raw.githubusercontent.com/Kitlad/exam/main/";
    static {
        retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create()).baseUrl(URL).build();
    }

    public static TaskService createTaskService(){
        return retrofit.create(TaskService.class);
    }
}
