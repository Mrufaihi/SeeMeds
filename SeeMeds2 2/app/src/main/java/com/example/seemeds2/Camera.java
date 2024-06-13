package com.example.seemeds2;

import androidx.appcompat.app.AppCompatActivity;
import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Camera extends Navigaion_bar {

//    private TextView textViewPicture;
    // RETURN CODE "22 " AFTER USER TAKES PHOTO IN CAMERA APP, SO OUT APPLICATION KNOWS WHAT PHOTO USER TOOK
    private static final int REQUEST_CODE = 22;
    Button button_capture, button_copy, button_tts;
    TextView textview_data ;
    Bitmap bitmap;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        setupBottomNavigationView();

//        openCamera();
//        textViewPicture = findViewById(R.id.textview1);
//        button_capture = findViewById(R.id.buttoncamera1);
        button_copy = findViewById(R.id.buttoncopy1);
//        button_tts= findViewById(R.id.buttontts1);
        textview_data= findViewById(R.id.textview1);

        //if permission is granted, request it
        if (ContextCompat.checkSelfPermission (this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {
                    android.Manifest.permission.CAMERA
                    }, REQUEST_CODE);
        }

        openCamera();


//        //on click for  capture
//        button_capture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, REQUEST_CODE);
//            }
//        });
        //on click for copy
        button_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String scanned_text = textview_data.getText().toString();
                copyToClipBoard(scanned_text);
                }
            });


        t1 = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //if the status is not equal to error
                if (status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);  //set the language to english

                }
            }
        });


//            button_tts.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String text = textview_data.getText().toString(); // get the text from the text view
//                    t1.speak(text, TextToSpeech.QUEUE_FLUSH, null); //speak the text (flush the queue first
//                }
//            });
        }

    //vision ocr
    private void getTextFromImage(Bitmap bitmap) {
        TextRecognizer recognizer = new TextRecognizer.Builder(this ).build(); //reconginzer object from vision api
        if(!recognizer.isOperational()) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }else{ //else if it is operational then we can get the text from the image
            Frame frame= new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> textBlockSparseArray = recognizer.detect(frame);
            StringBuilder stringBuilder = new StringBuilder(); //we need to traverse each text block and get the text from it
            for (int i=0; i<textBlockSparseArray.size(); i++){
                TextBlock textBlock = textBlockSparseArray.valueAt(i); //get the text block with index i
                stringBuilder.append(textBlock.getValue()); //append the text to the string builder
                stringBuilder.append("\n"); //append a new line
            }
            //smart optimizing
            textview_data.setText(stringBuilder.toString()); //move the string builder to the text view
//            button_capture.setText("retake"); //change text of button after taking photo
            button_copy.setVisibility(View.VISIBLE); //make the copy button visible after taking photo
//            button_tts.setVisibility(View.VISIBLE); //make the tts button visible after taking photo
            //open tts automatically after camera takes photo
            openTts();
        }
    }

        //when camera takes a photo, it will return the photo to this activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
//            Uri result;
            Uri resultUri = data.getData();
            if (resultUri != null) {
                // If the Uri is not null, get the Bitmap from the Uri
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // If the Uri is null, try to get the Bitmap directly from the Intent
                bitmap = (Bitmap) data.getExtras().get("data");
            }
            if (bitmap != null) {
                getTextFromImage(bitmap);
            } else {
                Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Cancelled the camera", Toast.LENGTH_SHORT).show();
        }
    }

    //copy method
    private void copyToClipBoard(String text){
        ClipboardManager clipBoard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied data", text);
        clipBoard.setPrimaryClip(clip); //now text is copied to clipboard
        Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show();
    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CODE);
    }
    private void openTts() {
                    String text = textview_data.getText().toString(); // get the text from the text view
                    t1.speak(text, TextToSpeech.QUEUE_FLUSH, null); //speak the text (flush the queue first
                }
}













// OLD CODE IGNORE

//    //when camera takes a photo, it will return the photo to this activity
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
//            //thumbnail of  the photo user took (compressed)
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            imageViewPicture.setImageBitmap(photo); //cant use this since bitmap is only for IMG
//        } else {
//            //if user cancels the camera
//            Toast.makeText(this, "Cancelled the camera", Toast.LENGTH_SHORT).show();
//        }
//    }



// Open the camera as soon as the activity is created
//    private void openCamera() {
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(cameraIntent, REQUEST_CODE);
//    }