package com.example.joshhigham.diceroller;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Vector;


public class MainActivity extends AppCompatActivity {

    Button rollButton;
    LinearLayout scrollHistory;
    ScrollView scrollViewHistory;
    Toolbar topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scrollViewHistory = (ScrollView)findViewById(R.id.scrollViewHistory);
        scrollHistory = (LinearLayout)findViewById(R.id.rollHistory);
        topBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topBar);
        rollButton = (Button)findViewById(R.id.rollButton);
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollButtonListener();
            }
        });
    }

    private TextView complexRoller(final DiceSet d) {
        Vector<Integer> v = d.preciseRoll();
        final TextView textResult = new TextView(this);
        long result = 0;
        String s = "(";
        for (int i = 0; i < v.size(); ++i) {
            result += v.elementAt(i);
            s += Integer.toString(v.elementAt(i)) + ", ";
        }
        result = d.getModInfo(result);
        textResult.setText(s + ")" + d.modSign + Integer.toString(d.diceMod) + "\n" + result + "\n");
        textResult.setTextSize(25);

        if (result == d.getLowestPossible()) {
            textResult.setTextColor(Color.RED);
        } else if (result == d.getHighestPossible()) {
            textResult.setTextColor(Color.GREEN);
        }

        textResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiceSet hist = new DiceSet(d);
                setDiceView(new DiceSet(hist));
            }
        });
        return textResult;
    }

    private TextView simpleRoller(final DiceSet d) {
        long result = d.roll();
        final TextView textResult = new TextView(this);

        textResult.setText(d.toString() + "\n" + result + "\n");
        textResult.setTextSize(25);

        if (result == d.getLowestPossible()) {
            textResult.setTextColor(Color.RED);
        } else if (result == d.getHighestPossible()) {
            textResult.setTextColor(Color.GREEN);
        }

        textResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiceSet hist = new DiceSet(d);
                setDiceView(new DiceSet(hist));
            }
        });

        return textResult;
    }

    public void rollButtonListener() {
        // Check if the TextViews are empty
        if (       !isEmptyTextView((EditText)findViewById(R.id.numDice))
                && !isEmptyTextView((EditText)findViewById(R.id.diceSize))
                && !isEmptyTextView((EditText)findViewById(R.id.modSign))
                && !isEmptyTextView((EditText)findViewById(R.id.modNum))) {
            // Not Empty
            final DiceSet tempDice = interpretDiceView();

            final TextView textResult;
            if (!((ToggleButton)findViewById(R.id.toggleButton)).isChecked()) {
                textResult = simpleRoller(tempDice);
            } else {
                textResult = complexRoller(tempDice);
            }

            scrollHistory.addView(textResult, 0);

        } else {
            // Empty
            Toast.makeText(this, "Insufficient data", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isEmptyTextView(EditText e) {
        return e.getText().toString().trim().length() == 0;
    }

    private DiceSet interpretDiceView() {
        DiceSet temp = new DiceSet();
        temp.diceQuantity = Integer.parseInt(((EditText)findViewById(R.id.numDice)).getText().toString());
        temp.diceSize = Integer.parseInt(((EditText)findViewById(R.id.diceSize)).getText().toString());
        temp.modSign = ((EditText)findViewById(R.id.modSign)).getText().toString();
        temp.diceMod = Integer.parseInt(((EditText)findViewById(R.id.modNum)).getText().toString());

        return temp;
    }

    private void setDiceView(DiceSet d) {
        ((EditText)findViewById(R.id.numDice)).setText(Integer.toString(d.diceQuantity), TextView.BufferType.EDITABLE);
        ((EditText)findViewById(R.id.diceSize)).setText(Integer.toString(d.diceSize), TextView.BufferType.EDITABLE);
        ((EditText)findViewById(R.id.modSign)).setText(d.modSign, TextView.BufferType.EDITABLE);
        ((EditText)findViewById(R.id.modNum)).setText(Integer.toString(d.diceMod), TextView.BufferType.EDITABLE);
    }

}
