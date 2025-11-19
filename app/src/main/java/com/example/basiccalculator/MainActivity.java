package com.example.basiccalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView resultview,resultpreview;
    Button clearbtn, onebtn, twobtn, threebtn, fourbtn, fivebtn, sixbtn, sevenbtn, eightbtn, ninebtn, zerobtn, dotbtn, addbtn, subbtn, mulbtn, divbtn, equalbtn, deletebtn, bracketbtn;

    String currentInput = "";
    double firstNumber = 0;
    char currentOperator = '\0'; // no operator yet


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clearbtn = findViewById(R.id.clearbtn);
        onebtn = findViewById(R.id.onebtn);
        twobtn = findViewById(R.id.twobtn);
        threebtn = findViewById(R.id.threebtn);
        fourbtn = findViewById(R.id.fourbtn);
        fivebtn = findViewById(R.id.fivebtn);
        sixbtn = findViewById(R.id.sixbtn);
        sevenbtn = findViewById(R.id.sevenbtn);
        eightbtn = findViewById(R.id.eightbtn);
        ninebtn = findViewById(R.id.ninebtn);
        zerobtn = findViewById(R.id.zerobtn);
        dotbtn = findViewById(R.id.dotbtn);
        addbtn = findViewById(R.id.plusbtn);
        subbtn = findViewById(R.id.minusbtn);
        mulbtn = findViewById(R.id.multiplybtn);
        divbtn = findViewById(R.id.dividebtn);
        equalbtn = findViewById(R.id.equalbtn);
        bracketbtn = findViewById(R.id.bracketbtn);
        deletebtn = findViewById(R.id.deletebtn);


        resultview = findViewById(R.id.resultview);
        resultpreview = findViewById(R.id.resultpreview);


        //setting listeners
        // Set listeners
        clearbtn.setOnClickListener(this);
        onebtn.setOnClickListener(this);
        twobtn.setOnClickListener(this);
        threebtn.setOnClickListener(this);
        fourbtn.setOnClickListener(this);
        fivebtn.setOnClickListener(this);
        sixbtn.setOnClickListener(this);
        sevenbtn.setOnClickListener(this);
        eightbtn.setOnClickListener(this);
        ninebtn.setOnClickListener(this);
        zerobtn.setOnClickListener(this);
        dotbtn.setOnClickListener(this);

        addbtn.setOnClickListener(this);
        subbtn.setOnClickListener(this);
        mulbtn.setOnClickListener(this);
        divbtn.setOnClickListener(this);
        equalbtn.setOnClickListener(this);
        bracketbtn.setOnClickListener(this);
        deletebtn.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.clearbtn) {
            clearAll();
        } else if (id == R.id.onebtn) {
            appendNumber("1");
        } else if (id == R.id.twobtn) {
            appendNumber("2");
        } else if (id == R.id.threebtn) {
            appendNumber("3");
        } else if (id == R.id.fourbtn) {
            appendNumber("4");
        } else if (id == R.id.fivebtn) {
            appendNumber("5");
        } else if (id == R.id.sixbtn) {
            appendNumber("6");
        } else if (id == R.id.sevenbtn) {
            appendNumber("7");
        } else if (id == R.id.eightbtn) {
            appendNumber("8");
        } else if (id == R.id.ninebtn) {
            appendNumber("9");
        } else if (id == R.id.zerobtn) {
            appendNumber("0");
        } else if (id == R.id.dotbtn) {
            appendDot();
        } else if (id == R.id.plusbtn) {
            setOperator('+');
        } else if (id == R.id.minusbtn) {
            setOperator('-');
        } else if (id == R.id.multiplybtn) {
            setOperator('*');
        } else if (id == R.id.dividebtn) {
            setOperator('/');
        } else if (id == R.id.equalbtn) {
            calculateResult();
        } else if (id == R.id.bracketbtn) {
            appendBracket();
        } else if (id == R.id.deletebtn) {
            appendDelete();
        }
    }


        public void appendNumber(String num){

        currentInput = currentInput+num;
        resultpreview.setText(currentInput);


    }

    public void appendDot() {
        if (!currentInput.contains(".")) {
            if(currentInput.isEmpty()){
                currentInput = "0.";
            }
            else{
                currentInput = currentInput + ".";
            }
            resultpreview.setText(currentInput);


        }
    }

    public void setOperator(char op) {
        // If user hasn't typed a new number yet but already chose an operator,
        // just change the operator (e.g. + -> -)
        if (currentInput.isEmpty()) {
            if (currentOperator != '\0') {
                currentOperator = op;
                resultview.setText(String.valueOf(currentOperator));
            }
            return;
        }

        double number;
        try {
            number = Double.parseDouble(currentInput);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (currentOperator == '\0') {
            // First operator: store the number
            firstNumber = number;
        } else {

            if (!applyCurrentOperation(number)) {
                return; // division by zero handled inside
            }
        }

        currentOperator = op;
        currentInput = "";

        // Show current result and the operator
        resultpreview.setText(String.valueOf(firstNumber));
        resultview.setText(String.valueOf(currentOperator));
    }


    public void calculateResult() {
        if (currentInput.isEmpty() || currentOperator == '\0') {
            return;
        }

        double secondNumber;
        try {
            secondNumber = Double.parseDouble(currentInput);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!applyCurrentOperation(secondNumber)) {
            return; // division by zero handled inside
        }

        currentInput = String.valueOf(firstNumber);
        resultview.setText(currentInput);
        resultpreview.setText("");
        currentOperator = '\0';
    }
     public void clearAll(){
        currentInput = "";
        firstNumber = 0;
        currentOperator = '\0';
        resultview.setText("");
        resultpreview.setText("");
     }

    public void appendBracket() {
        Toast.makeText(this, "Brackets not supported yet", Toast.LENGTH_SHORT).show();
    }


    public void appendDelete(){
        if(currentInput.length()>0){
            currentInput = currentInput.substring(0,currentInput.length()-1);
            resultpreview.setText(currentInput);
        }
     }

    private boolean applyCurrentOperation(double secondNumber) {
        switch (currentOperator) {
            case '+':
                firstNumber = firstNumber + secondNumber;
                break;
            case '-':
                firstNumber = firstNumber - secondNumber;
                break;
            case '*':
                firstNumber = firstNumber * secondNumber;
                break;
            case '/':
                if (secondNumber == 0) {
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show();
                    clearAll();
                    return false;
                }
                firstNumber = firstNumber / secondNumber;
                break;
            default:
                // No operator yet, nothing to apply
                break;
        }
        return true;
    }



}