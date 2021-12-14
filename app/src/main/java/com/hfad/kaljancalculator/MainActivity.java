package com.hfad.kaljancalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private Tobacco tobacco;
    private Tobacco.Details details;
    private double burleyRatio = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupViewsListeners();
    }

    private void setupViewsListeners() {
        View buttonDryGrid = findViewById(R.id.calculate_button_dry);
        buttonDryGrid.setOnClickListener(this::createTobaccoAndUpdateViews);

        View buttonWetGrid = findViewById(R.id.calculate_button_wet);
        buttonWetGrid.setOnClickListener(this::updateWetTextViews);

        View burleyCheckBox = findViewById(R.id.burley_ratio_checkbox);
        burleyCheckBox.setOnClickListener(this::toggleBurley);
    }

    private void createTobaccoAndUpdateViews(View view) {
        EditText totalField = findViewById(R.id.total);
        String totalText = totalField.getText().toString();
        if(!totalText.isEmpty()){
            int total = Integer.parseInt(totalText);
            createTobacco(total);

            updateLeavesViews();

            updateDryTobaccoViews();
        }
    }

    private void createTobacco(int total) {
        setBurleyRatio();

        tobacco = new Tobacco.Builder()
                .setDesiredTotal(total)
                .setBurleyRatio(burleyRatio)
                .build();
        details = tobacco.new Details();
    }

    private void setBurleyRatio(){
        EditText burleyField = findViewById(R.id.burley_percent);
        String burleyPercent = burleyField.getText().toString();
        if (!burleyPercent.isEmpty()) burleyRatio = Double.parseDouble(burleyPercent) / 100;
        else burleyRatio = 0.0;
    }

    private void updateLeavesViews(){
        TextView burleyWeightField = this.findViewById(R.id.burley_weight);
        String burleyLeaves = String.valueOf(details.getBurleyLeaves());
        burleyWeightField.setText(burleyLeaves);

        TextView virginiaWeightField = this.findViewById(R.id.virginia_weight);
        String virginiaLeaves = String.valueOf(details.getVirginiaLeaves());
        virginiaWeightField.setText(virginiaLeaves);
    }

    private void updateDryTobaccoViews(){
        TextView leavesField = findViewById(R.id.leaves);
        TextView syropeField = findViewById(R.id.syrope);
        TextView glycerineField = findViewById(R.id.glycerine);

        String leaves = String.valueOf(tobacco.getLeaves());
        leavesField.setText(leaves);

        String syrope = String.valueOf(tobacco.getSyrope());
        syropeField.setText(syrope);

        String glycerine = String.valueOf(tobacco.getGlycerine());
        glycerineField.setText(glycerine);
    }

    private void updateWetTextViews(View view) {
        EditText wetLeavesField = findViewById(R.id.wet_leaves);
        String wetLeavesText = wetLeavesField.getText().toString();

        if(!wetLeavesText.isEmpty()){
            TextView waterField = findViewById(R.id.water);
            int wetLeaves = Integer.parseInt(wetLeavesField.getText().toString());
            String water = String.valueOf(details.getWaterNeeded(wetLeaves));
            waterField.setText(water);

            String totalWithTare;
            CheckBox bigTare = findViewById(R.id.big_tare_checkbox);
            if(bigTare.isChecked()){
                totalWithTare = String.valueOf(details.getTotalWithBigTare());
            }else{
               totalWithTare = String.valueOf(details.getTotalWithSmallTare());
            }
            TextView totalWithTareField = findViewById(R.id.total_weight_with_tare);
            totalWithTareField.setText(totalWithTare);
        }
    }

    private void toggleBurley(View view){
        CheckBox burleyIncluded = (CheckBox) view;
        View leavesContentsCard = findViewById(R.id.leaves_contents);

        if(burleyIncluded.isChecked()){
            leavesContentsCard.setVisibility(View.VISIBLE);
        }
        else{
            leavesContentsCard.setVisibility(View.INVISIBLE);
            burleyRatio = 0.0;
        }
    }
}