package com.example.exam_task;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.exam_task.models.Category;
import com.example.exam_task.models.ResourceInfo;
import com.example.exam_task.services.ListViewAdapter;
import com.example.exam_task.services.RetrofitInstance;
import com.example.exam_task.services.TaskService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public  static final String PATH_ARG_NAME = "path_url";
    private ListView listView;
    private String resourcePath="index";
    private ListViewAdapter adapter;
    private Context ctx = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().hasExtra(PATH_ARG_NAME)) {
            resourcePath=getIntent().getStringExtra(PATH_ARG_NAME);
        }
        listView = (ListView) findViewById(R.id.Subcat);
        adapter = new ListViewAdapter(ctx);
        listView.setAdapter(adapter);
        reloadFromServer(resourcePath);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ResourceInfo resourceInfo = (ResourceInfo)adapter.getItem(position);
                if ("category".equals(resourceInfo.type)){
                    Intent intent = new Intent(ctx,MainActivity.class);
                    intent.putExtra(PATH_ARG_NAME,resourceInfo.url);
                    startActivity(intent);
                } else if ("task".equals(resourceInfo.type)){
                    Intent intentTask = new Intent(ctx,TaskActivity.class);
                    intentTask.putExtra("UrlPath",resourceInfo.url);
                    startActivity(intentTask);
                }
            }
        });
    }



    private void reloadFromServer(String categoryname){
        TaskService ts = RetrofitInstance.createTaskService();
        Call<Category> indexCatCall = ts.getCategory(categoryname);
        indexCatCall.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                Category cat = response.body();
                if (cat != null && cat.subcategories!=null) {
                    adapter.refresh(cat.subcategories);
                }
            }
            @Override
            public void onFailure(Call<Category> call, Throwable throwable) {
                Toast.makeText(MainActivity.this,"Err",Toast.LENGTH_SHORT).show();throwable.printStackTrace();
            }
        });
    }


}