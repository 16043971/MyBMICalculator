package sg.edu.rp.c346.mybmicalculator;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etWeight;
    EditText etHeight;
    Button btnCalculate;
    Button btnReset;
    TextView tvLastDate;
    TextView tvLastBMI;
    TextView tvResult;

    Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
    String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
            (now.get(Calendar.MONTH)+1) + "/" +
            now.get(Calendar.YEAR) + " " +
            now.get(Calendar.HOUR_OF_DAY) + ":" +
            now.get(Calendar.MINUTE);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etWeight = findViewById(R.id.editTextWeight);
        etHeight = findViewById(R.id.editTextHeight);
        btnCalculate = findViewById(R.id.buttonCalculate);
        btnReset = findViewById(R.id.buttonReset);
        tvLastDate = findViewById(R.id.textViewLastDate);
        tvLastBMI = findViewById(R.id.textViewLastBMI);
        tvResult = findViewById(R.id.textViewResult);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Float bmi = Float.parseFloat(etWeight.getText().toString()) / (Float.parseFloat(etHeight.getText().toString()) * Float.parseFloat(etHeight.getText().toString()));
                tvLastDate.setText("Last Calculated Date: " + datetime);
                tvLastBMI.setText("Last Calculated BMI: " + Float.toString(bmi));
                etWeight.setHint(R.string.weight);
                etHeight.setHint(R.string.height);
                if(bmi < 18.5){
                    tvResult.setText("You are underweight");
                }
                else if (bmi < 24.9){
                    tvResult.setText("Your BMI is normal");
                }
                else if (bmi < 29.9){
                    tvResult.setText("You are overweight");
                }
                else if (bmi >= 30){
                    tvResult.setText("You are obese");
                }
            }
        });
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etHeight.setText("");
                etWeight.setText("");
                etWeight.setHint(R.string.weight);
                etHeight.setHint(R.string.height);
                tvLastBMI.setText("Last Calculated BMI: ");
                tvLastDate.setText("Last Calculated Date: ");
                tvResult.setText("");
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.clear().commit();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!etHeight.getText().toString().isEmpty() && !etWeight.getText().toString().isEmpty()){
            String lastDate = tvLastDate.getText().toString();
            String lastBMI = tvLastBMI.getText().toString();
            String result = tvResult.getText().toString();
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor prefEdit = prefs.edit();
            prefEdit.putString("LastDate",lastDate);
            prefEdit.putString("Result",result);
            prefEdit.putString("LastBMI",lastBMI);
            prefEdit.commit();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String lastDate = prefs.getString("LastDate","Last Calculated Date: ");
        String lastBMI = prefs.getString("LastBMI","Last Calculated BMI: 0.0");
        String result = prefs.getString("Result","");
        tvLastDate.setText(lastDate);
        tvResult.setText(result);
        tvLastBMI.setText(lastBMI);
    }
}
