package com.example.xseries.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xseries.Model.Series_Model;
import com.example.xseries.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.util.HashMap;

public class Video_Input extends AppCompatActivity {


    EditText etUrl, etTitle;
    String url, filename;
    Button upBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_input);

        etUrl = findViewById(R.id.et_url);
        etTitle = findViewById(R.id.et_title);

        upBtn = findViewById(R.id.up_button);


//        String filename = url.substring(url.lastIndexOf("/") + 1);


        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (etUrl.getText().toString().equals("")) {
                    etUrl.setError("please enter url");
                    etUrl.requestFocus();
                } else {

                    url = etUrl.getText().toString();
                    filename = etTitle.getText().toString();

                    File file = new File(url);

//        Log.d("Files", "File Path:" + filePath);
                    Log.d("FilesUP", "File URL: " + url);
                    Log.d("FilesUP", "File name: " + file);

                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(url, new HashMap<String, String>());
                    String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    long timeInMillisec = Long.parseLong(time);

                    long minutes = (timeInMillisec / 1000) / 60;
                    long seconds = (timeInMillisec / 1000) % 60;

                    String mins = minutes + ":" + seconds;
                    Log.d("FilesUP", "File duration: " + mins);
//

                    DatabaseReference uploadVid = FirebaseDatabase.getInstance().getReference().child("Videos");

                    String keyVal = String.valueOf(uploadVid.push());
                    Uri uri = Uri.parse(keyVal);
                    String token = uri.getLastPathSegment();


                    Series_Model series_model = new Series_Model(token, url, url, mins, filename);

                    uploadVid.child(token).setValue(series_model);
                    Toast.makeText(Video_Input.this, "Video Uploaded", Toast.LENGTH_SHORT).show();

                }


            }

        });


    }


}