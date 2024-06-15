package biplapneupane.com.np;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBracketOpen, buttonBracketClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonSub, buttonEquals;
    MaterialButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button0;
    MaterialButton buttonAC, buttonDot;
    TextView biplap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        buttonC = findViewById(R.id.button_c);
        buttonBracketOpen = findViewById(R.id.button_open_bracket);
        buttonBracketClose = findViewById(R.id.button_close_bracket);

        buttonDivide = findViewById(R.id.button_divide);
        buttonMultiply = findViewById(R.id.button_multiply);
        buttonPlus = findViewById(R.id.button_add);
        buttonSub = findViewById(R.id.button_subtract);
        buttonEquals = findViewById(R.id.button_equals);

        button1 = findViewById(R.id.button_1);
        button2 = findViewById(R.id.button_2);
        button3 = findViewById(R.id.button_3);
        button4 = findViewById(R.id.button_4);
        button5 = findViewById(R.id.button_5);
        button6 = findViewById(R.id.button_6);
        button7 = findViewById(R.id.button_7);
        button8 = findViewById(R.id.button_8);
        button9 = findViewById(R.id.button_9);
        button0 = findViewById(R.id.button_0);

        buttonAC = findViewById(R.id.button_ac);
        buttonDot = findViewById(R.id.button_dot);
        biplap= findViewById(R.id.biplapneupane);

        buttonC.setOnClickListener(this);
        buttonBracketOpen.setOnClickListener(this);
        buttonBracketClose.setOnClickListener(this);

        buttonDivide.setOnClickListener(this);
        buttonMultiply.setOnClickListener(this);
        buttonPlus.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonEquals.setOnClickListener(this);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);
        button5.setOnClickListener(this);
        button6.setOnClickListener(this);
        button7.setOnClickListener(this);
        button8.setOnClickListener(this);
        button9.setOnClickListener(this);
        button0.setOnClickListener(this);

        buttonAC.setOnClickListener(this);
        buttonDot.setOnClickListener(this);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
                    insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
            return insets.consumeSystemWindowInsets();
        });
    }


    @Override
    public void onClick(View v) {
        MaterialButton button = (MaterialButton) v;
        String buttonText = button.getText().toString();
        String dataCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
//        if (buttonText.equals("=")) {
//            // Evaluate the expression and set the result
//            String finalResult = getResult(dataCalculate);
//            solutionTv.setText(finalResult);
//            resultTv.setText(finalResult);
//            if (finalResult.isEmpty()) {
//                solutionTv.setText("0");
//                resultTv.setText('0');
//            }
//            return;
//        }
        if (buttonText.equals("=")) {
            // Evaluate the expression and set the result
            String finalResult = getResult(dataCalculate);
            if (finalResult.equals("Error") || finalResult.isEmpty()) {
                solutionTv.setText("0");
                resultTv.setText("0");
            } else {
                solutionTv.setText(finalResult);
                resultTv.setText(finalResult);
            }
            return;
        }

        if (buttonText.equals("C")) {
            if (!dataCalculate.isEmpty()) {
                dataCalculate = dataCalculate.substring(0, dataCalculate.length() - 1);
            }
            if (dataCalculate.isEmpty()) {
                dataCalculate = "0";
            }

        } else {
            // Check if the last character is an operator or point
            char lastChar = dataCalculate.isEmpty() ? '\0' : dataCalculate.charAt(dataCalculate.length() - 1);
            char currentChar = buttonText.charAt(0);

            if (isOperator(currentChar)) {
                // Check if last character was also an operator
                if (isOperator(lastChar)) {
                    // Replace the last operator with the current one
                    dataCalculate = dataCalculate.substring(0, dataCalculate.length() - 1);
                }
            } else if (currentChar == '.') {
                // Check if there is already a decimal point before a number or operator
                if (dataCalculate.isEmpty() || isOperator(lastChar) || lastChar == '.') {
                    return; // Invalid, do nothing
                }

                // Scan backwards to check if there is already a decimal point before
                boolean foundDecimal = false;
                for (int i = dataCalculate.length() - 1; i >= 0; i--) {
                    char ch = dataCalculate.charAt(i);
                    if (isOperator(ch)) {
                        break; // Stop scanning if we hit an operator
                    } else if (ch == '.') {
                        foundDecimal = true;
                        break;
                    }
                }
                if (foundDecimal) {
                    return; // Invalid, do nothing
                }
            }

            // Append the button text to dataCalculate
            dataCalculate += buttonText;
        }

        solutionTv.setText(dataCalculate);
        String finalResult = getResult(dataCalculate);
        if (!finalResult.equals("Error")) {
            resultTv.setText(finalResult);
        }
    }

    // Helper method to check if a character is an operator
    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;

        } catch (Exception e) {
            return "Error";
        }
    }

    public void openLinkBiplap(View view) {
            String url = "https://www.biplapneupane.com.np/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            }
}
