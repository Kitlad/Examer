package com.example.exam_task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.exam_task.models.CPartTask;
import com.example.exam_task.services.RetrofitInstance;
import com.example.exam_task.services.TaskService;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.net.URLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskActivity extends AppCompatActivity {
    private Context ctx = this;
    private TaskService taskService = RetrofitInstance.createTaskService();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_intent);

        ProgressBar taskProgress = (ProgressBar) findViewById(R.id.taskProgress);
        Intent intent = getIntent();
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        String UrlPath= intent.getStringExtra("UrlPath");
        TextView taskNameView = (TextView) findViewById(R.id.taskNameView);
        TextView dataView = (TextView) findViewById(R.id.dataView);
        TextView solutionView = (TextView) findViewById(R.id.solutionView);
        TextView answerView = (TextView) findViewById(R.id.answerView);
        Button solutionBtn = (Button) findViewById(R.id.solutionBtn);
        Call<CPartTask> cPartTaskCall = taskService.getCPartTask(UrlPath);
        cPartTaskCall.enqueue(new Callback<CPartTask>() {
            @Override
            public void onResponse(Call<CPartTask> call, Response<CPartTask> response) {
                CPartTask cPartTask = response.body();
                if (cPartTask != null) {
                    taskNameView.setText(cPartTask.name);
                    dataView.setText(cPartTask.text);
                    taskProgress.setVisibility(ListView.VISIBLE);
                    Picasso.get().load(cPartTask.pic).into(imageView);

                    solutionBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (solutionView.getText() == ""){
                                solutionView.setText(cPartTask.solution);
                                answerView.setText("Oтвет:"+cPartTask.answer);
                            } else{
                                solutionView.setText("");
                                answerView.setText("");

                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<CPartTask> call, Throwable throwable) {
                Toast.makeText(TaskActivity.this,"Err",Toast.LENGTH_SHORT).show();throwable.printStackTrace();
            }

        });
        taskProgress.setVisibility(View.INVISIBLE);
    }
}
