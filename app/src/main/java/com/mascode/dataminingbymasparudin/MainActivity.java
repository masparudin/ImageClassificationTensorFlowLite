package com.mascode.dataminingbymasparudin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mascode.dataminingbymasparudin.Helpers.ImageHelperActivity;
import com.mascode.dataminingbymasparudin.Image.FaceDetectionActivity;
import com.mascode.dataminingbymasparudin.Image.FlowerIdentificationActivity;
import com.mascode.dataminingbymasparudin.Image.ImageClassificationActivity;
import com.mascode.dataminingbymasparudin.Image.ObjectDetectionActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onGotoImageActivity(View view){
        // start image helper activity
        Intent intent = new Intent(this, ImageClassificationActivity.class);
        startActivity(intent);
    }

    public void onGotoFlowerIdentification(View view){
        // start image helper activity
        Intent intent = new Intent(this, FlowerIdentificationActivity.class);
        startActivity(intent);
    }

    public void onGotoObjectDetection(View view){
        // start image helper activity
        Intent intent = new Intent(this, ObjectDetectionActivity.class);
        startActivity(intent);
    }
    public void onGotoFaceDetection(View view){
        // start image helper activity
        Intent intent = new Intent(this, FaceDetectionActivity.class);
        startActivity(intent);
    }
}