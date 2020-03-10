package com.example.mobilecomputing_task1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText etFirstValue;
    private EditText etSecondValue;
    private TextView tvAddResult;
    private TextView tvSubtractResult;
    private TextView tvMultiplicationResult;
    private TextView tvDivisionResult;
    private Button btnCalculate;
    private Button btnWriteToTextFile;
    private TextView tvFinalResult;
    private String TextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }
        });

        btnWriteToTextFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TextResult = tvAddResult.getText().toString() + "\n" +
                            tvSubtractResult.getText().toString() + "\n" +
                            tvMultiplicationResult.getText().toString() + "\n" +
                            tvDivisionResult.getText().toString();


                    String result = writeToTextFile(TextResult);
                    tvFinalResult.setText(result);
                    tvFinalResult.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initializeComponents(){
        etFirstValue = findViewById(R.id.val_1);
        etSecondValue = findViewById(R.id.val_2);

        tvAddResult = findViewById(R.id.tv_add);
        tvSubtractResult = findViewById(R.id.tv_sub);
        tvMultiplicationResult = findViewById(R.id.tv_mult);
        tvDivisionResult = findViewById(R.id.tv_div);

        btnCalculate = findViewById(R.id.btn_calc);
        btnWriteToTextFile = findViewById(R.id.btn_write);

        tvFinalResult = findViewById(R.id.result);
    }

    private void calculate(){
        float value1 = Float.parseFloat(etFirstValue.getText().toString());
        float value2 = Float.parseFloat(etSecondValue.getText().toString());

        tvAddResult.setText(value1 + " + " + value2 + " = " + (value1+value2));
        tvSubtractResult.setText(value1 + " - " + value2 + " = " + (value1-value2));
        tvMultiplicationResult.setText(value1 + " x " + value2 + " = " + (value1*value2));
        if(value2 != 0){
            tvDivisionResult.setText(value1 + " / " + value2 + " = " + (value1/value2));
        }else{
            tvFinalResult.setText("Cannot divide by 0");
            tvFinalResult.setTextColor(getResources().getColor(R.color.colorAccent));
            tvDivisionResult.setVisibility(View.INVISIBLE);
            return;
        }

        tvFinalResult.setText("Calculations done.");
        tvFinalResult.setTextColor(getResources().getColor(R.color.colorPrimary));
        tvDivisionResult.setVisibility(View.VISIBLE);
    }

    private String writeToTextFile(String result) throws IOException {


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // No explanation needed; request the permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);

            return "Try again after granting permission";

        }else{
            String DIRECTORY_PATH = Environment.getExternalStorageDirectory().toString();

            File root = new File(DIRECTORY_PATH);
            File gpxfile = new File(root, "result.txt");
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(result);
            writer.flush();
            writer.close();

            return "DONE writing to external storage.";
        }
    }
}
