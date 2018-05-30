package com.example.user.myapplication_sharedprefrences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String BEST_SCORE_FILE="BestScore";
    private FileManager m_fileManager;
    private TextView m_textViewPoints;
    private int m_points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        m_textViewPoints=findViewById(R.id.textViewPoints);
        m_fileManager=new FileManager(this);

        try {
            String fileContent=m_fileManager.readInternalFile(BEST_SCORE_FILE);
            m_points=fileContent.length() ==0 ? 0 : Integer.parseInt(fileContent);
        }
        catch (IOException e){
            m_points=0;
        }

        updatePointsDisplay();
    }

    @Override
    protected void onPause(){
        super.onPause();
        try {
            m_fileManager.writeInternalFile(BEST_SCORE_FILE,Integer.toString(m_points),false);
        }
        catch (IOException e){
            Log.e("IOError","Could not best score");
        }

    }

    @SuppressLint("SetTextI18n")
    private void updatePointsDisplay(){
        m_textViewPoints.setText(Integer.toString(m_points));
    }

    public void onClickAddPointButton(View view){
        m_points +=1;
        updatePointsDisplay();
    }
}
