package com.example.android.capitalcitiesquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

public class Starter extends AppCompatActivity {

    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);

        // Method to make the app launch without a keyboard shown automatically.
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Initiate etName as the starter name et edit text.
        etName = (EditText) findViewById(R.id.starter_name_et);

        // Set of methods to change the ime option of the edit text field to done
        // needed because android:imeOption=actionDone doesn't function in the current settings.
        etName.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etName.setRawInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
    }

    // Public void to make a string out of the name edit text, upload it to shared preferences
    // for a future use in a different activity and launch the MainActivity.
    public void startQuiz(View view) {

        // Declare userName as a string containing the the edit text user input.
        String userName = etName.getText().toString();

        // Checks if there is a written text.
        if (TextUtils.isEmpty(userName)) {
            // Error message displayed if nothing is written in the edit text.
            etName.setError(getResources().getString(R.string.name_error));

        } else {
            // Upload the userName string to shared preferences.
            SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = myPrefs.edit();
            prefsEditor.putString("name", userName);
            prefsEditor.apply();

            // An intent to launch the MainActivity.
            Intent myIntent = new Intent(this, MainActivity.class);
            startActivity(myIntent);
        }
    }
}
