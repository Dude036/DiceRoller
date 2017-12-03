package com.example.joshhigham.diceroller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.joshhigham.diceroller.Dice;

public class Percentage extends AppCompatActivity {
    TextView prediction;
    TextView suggestedMod;
    TextView suggestedRoll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prediction = findViewById(R.id.probabilityTextView);
        suggestedMod = findViewById(R.id.suggestedModTextView);
        suggestedRoll = findViewById(R.id.suggestedDiceRoll);
    }

    public void percentButtonListener(View v) {
        if (       !isEmptyEditText((EditText)findViewById(R.id.percentAmount))
                && !isEmptyEditText((EditText)findViewById(R.id.percentDiceAmount))
                && !isEmptyEditText((EditText)findViewById(R.id.percentDiceQuantity))) {
            // Filled with everything
            double pred = getPredictionPercent(
                    Integer.parseInt(((EditText)findViewById(R.id.percentAmount)).getText().toString()),
                    Integer.parseInt(((EditText)findViewById(R.id.percentDiceAmount)).getText().toString()),
                    Integer.parseInt(((EditText)findViewById(R.id.percentDiceQuantity)).getText().toString())
            );
            setPredictionPercent(pred);
            setSuggestedMod(
                    Integer.parseInt(((EditText)findViewById(R.id.percentAmount)).getText().toString()),
                    Integer.parseInt(((EditText)findViewById(R.id.percentDiceAmount)).getText().toString()),
                    Integer.parseInt(((EditText)findViewById(R.id.percentDiceQuantity)).getText().toString()));
        } else if (!isEmptyEditText((EditText)findViewById(R.id.percentAmount))) {
            // Filled with just the predicted amount
            Dice d = getSuggestedRoll(Integer.parseInt(((EditText)findViewById(R.id.percentAmount)).getText().toString()));
            suggestedRoll.setText(d.toString());
        } else {
            Toast.makeText(this, "Insufficient data", Toast.LENGTH_SHORT).show();
        }
    }

    public double getPredictionPercent(int requiredAmount, int diceAmount, int diceSize) {
        if (requiredAmount <= diceAmount) {
            // Guaranteed Win
            return 100;
        }
        if (diceSize <= 0 || diceAmount <= 0) {
            diceAmount = diceSize = 1;
        }
        double allOutcomes = diceAmount * diceSize;
        double successfulOutcomes = allOutcomes - requiredAmount;
        return (successfulOutcomes / allOutcomes) * 100;
    }

    public void setPredictionPercent(double d) {
        String s = d >= 0 ? Double.toString(d) : "Impossible" ;
        prediction.setText(s + "%");
    }

    public void setSuggestedMod(int requiredAmount, int diceAmount, int diceSize) {
        String s;
        if (getPredictionPercent(requiredAmount, diceAmount, diceSize) >= 100) {
            s = "N/A";
        } else {
            double addMod = (requiredAmount - averageAmount(diceAmount, diceSize)) * .75;
            if (addMod < diceSize * 5) {
                if (addMod < 0) {
                    addMod = 0;
                }
                s = "Add " + Double.toString(addMod);
            } else {
                double multMod = requiredAmount / averageAmount(diceAmount, diceSize);
                s = "Multiply " + Double.toString(multMod);
            }
        }
        // Set information
        suggestedMod.setText(s);

    }

    private Dice getSuggestedRoll(int amount) {
        Dice d = new Dice(0, 0, "+", 0);
        if (amount % 2 == 0) {
            d.diceMod = 0;
        } else {
            d.diceMod = 0;
        }

        if (amount % 20 == 0) {
            d.diceSize = 20;
            Double D =  1.75 * amount;
            d.diceQuantity = D.intValue() / 20;
        } else if (amount % 10 == 0) {
            d.diceSize = 10;
            Double D =  1.75 * amount;
            d.diceQuantity = D.intValue() / 10;
        } else if (amount % 8 == 0) {
            d.diceSize = 8;
            Double D =  1.75 * amount;
            d.diceQuantity = D.intValue() / 8;
        } else if (amount % 6 == 0) {
            d.diceSize = 6;
            Double D =  1.75 * amount;
            d.diceQuantity = D.intValue() / 6;
        } else if (amount % 4 == 0) {
            d.diceSize = 4;
            Double D =  1.75 * amount;
            d.diceQuantity = D.intValue() / 4;
        } else {
            d.diceSize = 2;
            Double D =  1.75 * amount;
            d.diceQuantity = D.intValue() / 2;
        }

        return d;
    }

    private double averageAmount(int da, int ds) { return (da * ds) / 2; }

    public void complete_percent(View v) {
        finish();
    }

    private boolean isEmptyEditText(EditText e) {
        return e.getText().toString().trim().length() == 0;
    }
}
