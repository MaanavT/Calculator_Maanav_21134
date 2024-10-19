package com.example.calculator;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText display;
    String currentInput = "";
    String operator = "";
    double num1, num2, result;
    boolean isDegree = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);

        // Number Buttons (0-9, 00, .)
        setNumberButtonListener(R.id.button_0, "0");
        setNumberButtonListener(R.id.button_1, "1");
        setNumberButtonListener(R.id.button_2, "2");
        setNumberButtonListener(R.id.button_3, "3");
        setNumberButtonListener(R.id.button_4, "4");
        setNumberButtonListener(R.id.button_5, "5");
        setNumberButtonListener(R.id.button_6, "6");
        setNumberButtonListener(R.id.button_7, "7");
        setNumberButtonListener(R.id.button_8, "8");
        setNumberButtonListener(R.id.button_9, "9");
        setNumberButtonListener(R.id.button_double_zero, "00");
        setNumberButtonListener(R.id.button_dot, ".");

        // Operator Buttons (+, -, *, /)
        setOperatorButtonListener(R.id.button_add, "+");
        setOperatorButtonListener(R.id.button_subtract, "-");
        setOperatorButtonListener(R.id.button_multiply, "*");
        setOperatorButtonListener(R.id.button_divide, "/");

        // Clear Button (C)
        Button clearButton = findViewById(R.id.button_clear);
        clearButton.setOnClickListener(v -> {
            display.setText("");
            currentInput = "";
            operator = "";
            num1 = num2 = result = 0;
        });

        // Equal Button (=)
        Button equalButton = findViewById(R.id.button_equal);
        equalButton.setOnClickListener(v -> {
            num2 = Double.parseDouble(currentInput);
            result = calculateResult(num1, num2, operator);
            display.setText(String.valueOf(result));
            currentInput = String.valueOf(result); // Allow further operations
        });
        Button sinButton = findViewById(R.id.button_sin);
        sinButton.setOnClickListener(v -> {
            if (currentInput.isEmpty()) {
                // Show a message if no number has been entered
                Toast.makeText(MainActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    double value = Double.parseDouble(currentInput);
                    if (isDegree) {
                        value = Math.toRadians(value); // Convert to radians if input is in degrees
                    }
                    double sinValue = Math.sin(value);
                    display.setText(String.valueOf(sinValue));
                    currentInput = String.valueOf(sinValue);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Degree Button (Toggle between degrees and radians)
        Button degButton = findViewById(R.id.button_deg);
        degButton.setOnClickListener(v -> {
            isDegree = !isDegree; // Toggle between degrees and radians
            String mode = isDegree ? "Degrees" : "Radians";
            Toast.makeText(MainActivity.this, "Mode: " + mode, Toast.LENGTH_SHORT).show();
        });
    }

    private void setNumberButtonListener(int buttonId, String value) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            currentInput += value;
            display.setText(currentInput);
        });
    }

    private void setOperatorButtonListener(int buttonId, String operatorSymbol) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(v -> {
            num1 = Double.parseDouble(currentInput);
            operator = operatorSymbol;
            currentInput = "";
        });
    }

    private double calculateResult(double num1, double num2, String operator) {
        switch (operator) {
            case "+": return num1 + num2;
            case "-": return num1 - num2;
            case "*": return num1 * num2;
            case "/": return num1 / num2;
            default: return 0;
        }
    }
}
