package com.mascode.dataminingbymasparudin.Helpers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.Manifest;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.label.ImageLabel;
import com.google.mlkit.vision.label.ImageLabeler;
import com.google.mlkit.vision.label.ImageLabeling;
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions;
import com.mascode.dataminingbymasparudin.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ImageHelperActivity extends AppCompatActivity {

    private Uri imageUri;
    private int PICK_IMAGE_REQUEST = 1000;
    private int CAPTURE_IMAGE_REQUEST = 1001;

    private ImageView inputImageView;
    private TextView outputTextView, outputTextView2,outputTextView3, outputTextView4, outputTextView5;
    private Button buttonPickFile;
    private File photoFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_helper);

        buttonPickFile = findViewById(R.id.buttonPickImage);
        inputImageView = findViewById(R.id.imageViewInput);
        outputTextView = findViewById(R.id.textViewOutput);
        outputTextView2 = findViewById(R.id.textViewOutput2);
        outputTextView3 = findViewById(R.id.textViewOutput3);
        outputTextView4 = findViewById(R.id.textViewOutput4);
        outputTextView5 = findViewById(R.id.textViewOutput5);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }

        buttonPickFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    public void onStartCamera(View view) {
        //create a file to share with camera
        photoFile = createPhotoFile();

        Uri fileUri = FileProvider.getUriForFile(this, "com.mascode.fileprovider", photoFile);

        //create  an intent
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        //startActivityForResult
        startActivityForResult(intent, CAPTURE_IMAGE_REQUEST);
    }

    private File createPhotoFile() {
        File photoFileDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "ML_IMAGE_HELPER");

        if(!photoFileDir.exists()){
            photoFileDir.mkdirs();
        }

        String name = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File file = new File(photoFileDir.getPath() +File.separator + name);
        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            Bitmap bitmap = loadFromUri(imageUri);
            inputImageView.setImageBitmap(bitmap);
            runClassification(bitmap);
            runClassification2(bitmap);
            runClassification3(bitmap);
            runClassification4(bitmap);
            runClassification5(bitmap);
        }else if (requestCode == CAPTURE_IMAGE_REQUEST) {
            Log.d("ML", "received callback from camera");
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            inputImageView.setImageBitmap(bitmap);
            runClassification(bitmap);
            runClassification2(bitmap);
            runClassification3(bitmap);
            runClassification4(bitmap);
            runClassification5(bitmap);
        }
    }
    private Bitmap loadFromUri(Uri uri) {
        Bitmap bitmap = null;
        try {
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O_MR1) {
                ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), uri);
                bitmap = ImageDecoder.decodeBitmap(source);

            }else {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

            }


        } catch(IOException e) {
            e.printStackTrace();
        }


        return bitmap;
    }
    protected void runClassification(Bitmap bitmap){
    }
    protected void runClassification2(Bitmap bitmap){
    }

    protected void runClassification3(Bitmap bitmap){
    }
    protected void runClassification4(Bitmap bitmap){
    }
    protected void runClassification5(Bitmap bitmap){
    }
    protected TextView getOutputTextView(){
        return outputTextView;
    }
    protected TextView getOutputTextView2(){
        return outputTextView2;
    }
    protected TextView getOutputTextView3(){
        return outputTextView3;
    }
    protected TextView getOutputTextView4(){
        return outputTextView4;
    }
    protected TextView getOutputTextView5(){
        return outputTextView5;
    }
    protected ImageView getInputImageView(){
        return inputImageView;
    }
    protected void drawDetectionResult(List<BoxWithLabel> boxes, Bitmap bitmap) {
        Bitmap outputBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(outputBitmap);
        Paint penRect = new Paint();
        penRect.setColor(Color.RED);
        penRect.setStyle(Paint.Style.STROKE);
        penRect.setStrokeWidth(8f);

        Paint penLabel = new Paint();
        penLabel.setColor(Color.YELLOW);
        penLabel.setStyle(Paint.Style.FILL_AND_STROKE);
        penLabel.setTextSize(96f);
        penLabel.setStrokeWidth(2f);

        for(BoxWithLabel boxWithLabel : boxes) {
            canvas.drawRect(boxWithLabel.rect, penRect);

            // Rect
            Rect labelSize = new Rect(0,0,0,0);
            penLabel.getTextBounds(boxWithLabel.label, 0, boxWithLabel.label.length(), labelSize);

            float fontSize = penLabel.getTextSize() * boxWithLabel.rect.width() / labelSize.width();
            if(fontSize < penLabel.getTextSize()){
                penLabel.setTextSize(fontSize);
            }
            canvas.drawText(boxWithLabel.label, boxWithLabel.rect.left, boxWithLabel.rect.top + labelSize.height(), penLabel);
        }
        getInputImageView().setImageBitmap(outputBitmap);
    }
}