package com.hfad.kaljancalculator;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class TobaccoSettingsFragment extends Fragment {
    private Tobacco tobacco;
    private Tobacco.Details details;
    private double burleyRatio = 0.0;
    private View root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tobacco_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);

        root = view;
        setupViewsListeners();
    }

    private void setupViewsListeners() {
        
        View buttonDryGrid = root.findViewById(R.id.calculate_button_dry);
        buttonDryGrid.setOnClickListener(this::createTobaccoAndUpdateViews);

        View buttonWetGrid = root.findViewById(R.id.calculate_button_wet);
        buttonWetGrid.setOnClickListener(this::updateWetTextViews);

        View burleyCheckBox = root.findViewById(R.id.burley_ratio_checkbox);
        burleyCheckBox.setOnClickListener(this::toggleBurley);
    }

    private void createTobaccoAndUpdateViews(View view) {
        EditText totalField = root.findViewById(R.id.total);
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
        EditText burleyField = root.findViewById(R.id.burley_percent);
        String burleyPercent = burleyField.getText().toString();
        if (!burleyPercent.isEmpty()) burleyRatio = Double.parseDouble(burleyPercent) / 100;
        else burleyRatio = 0.0;
    }

    private void updateLeavesViews(){
        TextView burleyWeightField = this.root.findViewById(R.id.burley_weight);
        String burleyLeaves = String.valueOf(details.getBurleyLeaves());
        burleyWeightField.setText(burleyLeaves);

        TextView virginiaWeightField = this.root.findViewById(R.id.virginia_weight);
        String virginiaLeaves = String.valueOf(details.getVirginiaLeaves());
        virginiaWeightField.setText(virginiaLeaves);
    }

    private void updateDryTobaccoViews(){
        TextView leavesField = root.findViewById(R.id.leaves);
        TextView syropeField = root.findViewById(R.id.syrope);
        TextView glycerineField = root.findViewById(R.id.glycerine);

        String leaves = String.valueOf(tobacco.getLeaves());
        leavesField.setText(leaves);

        String syrope = String.valueOf(tobacco.getSyrope());
        syropeField.setText(syrope);

        String glycerine = String.valueOf(tobacco.getGlycerine());
        glycerineField.setText(glycerine);
    }

    private void updateWetTextViews(View view) {
        EditText wetLeavesField = root.findViewById(R.id.wet_leaves);
        String wetLeavesText = wetLeavesField.getText().toString();

        if(!wetLeavesText.isEmpty()){
            TextView waterField = root.findViewById(R.id.water);
            int wetLeaves = Integer.parseInt(wetLeavesField.getText().toString());
            String water = String.valueOf(details.getWaterNeeded(wetLeaves));
            waterField.setText(water);

            String totalWithTare;
            CheckBox bigTare = root.findViewById(R.id.big_tare_checkbox);
            if(bigTare.isChecked()){
                totalWithTare = String.valueOf(details.getTotalWithBigTare());
            }else{
                totalWithTare = String.valueOf(details.getTotalWithSmallTare());
            }
            TextView totalWithTareField = root.findViewById(R.id.total_weight_with_tare);
            totalWithTareField.setText(totalWithTare);
        }
    }

    private void toggleBurley(View view){
        CheckBox burleyIncluded = (CheckBox) view;
        View leavesContentsCard = root.findViewById(R.id.leaves_contents);

        if(burleyIncluded.isChecked()){
            leavesContentsCard.setVisibility(View.VISIBLE);
        }
        else{
            leavesContentsCard.setVisibility(View.INVISIBLE);
            burleyRatio = 0.0;
        }
    }
}