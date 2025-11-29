package com.example.basiccalculator;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultview, resultpreview;
    Button clearbtn, onebtn, twobtn, threebtn, fourbtn, fivebtn, sixbtn, sevenbtn, eightbtn, ninebtn, zerobtn, dotbtn, addbtn, subbtn, mulbtn, divbtn, equalbtn, deletebtn, bracketbtn, plusminusbtn;

    String currentInput = "";
    double firstNumber = 0;
    char currentOperator = '\0';
    boolean isResultDisplayed = false;
    Vibrator vibrator;
    DecimalFormat decimalFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        decimalFormat = new DecimalFormat("#.##########");

        initializeViews();
        setClickListeners();
    }

    private void initializeViews() {
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
        plusminusbtn = findViewById(R.id.plusminusbtn);

        resultview = findViewById(R.id.resultview);
        resultpreview = findViewById(R.id.resultpreview);
    }

    private void setClickListeners() {
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
        plusminusbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hapticFeedback();
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
            setOperator('×');
        } else if (id == R.id.dividebtn) {
            setOperator('÷');
        } else if (id == R.id.equalbtn) {
            calculateResult();
        } else if (id == R.id.bracketbtn) {
            appendBracket();
        } else if (id == R.id.deletebtn) {
            appendDelete();
        } else if (id == R.id.plusminusbtn) {
            toggleSign();
        }
    }

    private void hapticFeedback() {
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(10);
        }
    }

    public void appendNumber(String num) {
        if (isResultDisplayed) {
            currentInput = "";
            isResultDisplayed = false;
        }

        if (currentInput.length() < 15) {
            currentInput = currentInput + num;
            updateDisplay();
        }
    }

    public void appendDot() {
        if (isResultDisplayed) {
            currentInput = "0";
            isResultDisplayed = false;
        }

        if (!currentInput.contains(".")) {
            if (currentInput.isEmpty()) {
                currentInput = "0.";
            } else {
                currentInput = currentInput + ".";
            }
            updateDisplay();
        }
    }

    public void setOperator(char op) {
        if (currentInput.isEmpty()) {
            if (currentOperator != '\0') {
                currentOperator = op;
                resultpreview.setText(formatNumber(firstNumber) + " " + currentOperator);
            }
            return;
        }

        double number;
        try {
            number = Double.parseDouble(currentInput);
        } catch (NumberFormatException e) {
            showError("Invalid Number");
            return;
        }

        if (currentOperator == '\0') {
            firstNumber = number;
        } else {
            if (!applyCurrentOperation(number)) {
                return;
            }
        }

        currentOperator = op;
        currentInput = "";
        isResultDisplayed = false;

        resultpreview.setText(formatNumber(firstNumber) + " " + currentOperator);
        resultview.setText("");
    }

    public void calculateResult() {
        if (currentInput.isEmpty() || currentOperator == '\0') {
            return;
        }

        double secondNumber;
        try {
            secondNumber = Double.parseDouble(currentInput);
        } catch (NumberFormatException e) {
            showError("Invalid Number");
            return;
        }

        String expression = formatNumber(firstNumber) + " " + currentOperator + " " + formatNumber(secondNumber);
        resultpreview.setText(expression);

        if (!applyCurrentOperation(secondNumber)) {
            return;
        }

        animateResult(firstNumber);
        currentInput = String.valueOf(firstNumber);
        currentOperator = '\0';
        isResultDisplayed = true;
    }

    public void clearAll() {
        currentInput = "";
        firstNumber = 0;
        currentOperator = '\0';
        isResultDisplayed = false;
        resultview.setText("");
        resultpreview.setText("");
    }

    public void appendBracket() {
        showError("Brackets not supported yet");
    }

    public void appendDelete() {
        if (isResultDisplayed) {
            clearAll();
            return;
        }

        if (currentInput.length() > 0) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            updateDisplay();
        }
    }

    public void toggleSign() {
        if (currentInput.isEmpty() || currentInput.equals("0")) {
            return;
        }

        if (currentInput.startsWith("-")) {
            currentInput = currentInput.substring(1);
        } else {
            currentInput = "-" + currentInput;
        }
        updateDisplay();
    }

    private boolean applyCurrentOperation(double secondNumber) {
        switch (currentOperator) {
            case '+':
                firstNumber = firstNumber + secondNumber;
                break;
            case '-':
                firstNumber = firstNumber - secondNumber;
                break;
            case '×':
            case '*':
                firstNumber = firstNumber * secondNumber;
                break;
            case '÷':
            case '/':
                if (secondNumber == 0) {
                    showError("Cannot divide by zero");
                    clearAll();
                    return false;
                }
                firstNumber = firstNumber / secondNumber;
                break;
            default:
                break;
        }
        return true;
    }

    private void updateDisplay() {
        resultview.setText(currentInput);
    }

    private void animateResult(double result) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            resultview.setAlpha(value);
            resultview.setScaleX(0.95f + (value * 0.05f));
            resultview.setScaleY(0.95f + (value * 0.05f));
        });
        resultview.setText(formatNumber(result));
        animator.start();
    }

    private String formatNumber(double number) {
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return decimalFormat.format(number);
        }
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}