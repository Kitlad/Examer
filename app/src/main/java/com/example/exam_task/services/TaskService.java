package com.example.exam_task.services;

import com.example.exam_task.models.CPartTask;
import com.example.exam_task.models.Category;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface TaskService {
    @GET("{category_name}.json")
    Call<Category> getCategory(@Path("category_name")String categoryName);
    @GET("{category_name}.json")
    Call<CPartTask> getCPartTask(@Path("category_name")String categoryName);
}
