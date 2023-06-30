package com.mascode.dataminingbymasparudin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.mascode.dataminingbymasparudin.Image.FlowerIdentificationActivity;
import com.mascode.dataminingbymasparudin.Image.TestingFlowerIdentificationActivity;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Spinner spinner = findViewById(R.id.spinner);

        Button buttonClassify = (Button)findViewById(R.id.button_classify);
        buttonClassify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String dataset = spinner.getSelectedItem().toString();
                Intent classify = new Intent(Menu.this, TestingFlowerIdentificationActivity.class);
                classify.putExtra("Dataset", dataset);
                startActivity(classify);
                
            }
        });
    }
}