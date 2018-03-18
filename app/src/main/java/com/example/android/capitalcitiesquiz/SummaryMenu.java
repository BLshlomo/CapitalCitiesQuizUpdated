package com.example.android.capitalcitiesquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SummaryMenu extends AppCompatActivity {

    TextView grats, summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_menu);

        // Get the intent that started this summary menu activity
        Intent myIntent = getIntent();
        int quizWrongAnswer = myIntent.getIntExtra("wrongAnswers", 1);
        int quizNoAnswer = myIntent.getIntExtra("noAnswers", 1);
        String summaryPerfect = myIntent.getStringExtra("stringPerfect");
        String summaryMistakes = myIntent.getStringExtra("stringMistakes");
        String summaryNoAnswers = myIntent.getStringExtra("stringNoAnswers");
        String summaryWorse = myIntent.getStringExtra("stringWorse");

        // Get the userName string from shared preferences.
        SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
        String userName = myPrefs.getString("name", "0");

        // Initiate summary as a summary id text view.
        grats = (TextView) findViewById(R.id.gratsHeader);
        // Change summary text view text to a congrats string resource with userName as a param.
        grats.setText(String.format(getResources().getString(R.string.congrats), userName));

        // Initiate summary as a summary id text view.
        summary = (TextView) findViewById(R.id.summary);

        // Display the string messages based on the right conditions in the summary text view.
        if (quizWrongAnswer == 0 && quizNoAnswer == 0) {
            summary.setText(summaryPerfect);

        } else if (quizNoAnswer == 0) {
            summary.setText(summaryMistakes);

        } else if (quizWrongAnswer == 0) {
            summary.setText(summaryNoAnswers);
        } else {
            summary.setText(summaryWorse);
        }
    }

    // Public void to finish this activity, go back to the previous quiz activity
    // with a result set to 1, restart the main activity
    // called when the try again button is clicked.
    public void restart(View view) {
        Intent intent = new Intent();
        setResult(1, intent);
        finish();
    }

    // Public void to finish this activity, go back to the previous quiz activity
    // with a result set to 2, check the answers,
    // called when the check your answers button is clicked.
    public void checkAnswers(View view) {
        Intent intent = new Intent();
        setResult(2, intent);
        finish();
    }

    // Public void to finish this activity, go back to the previous quiz activity
    // with a result set to 3, check the answers and show the solution to the quiz,
    // called when the see solution button is clicked.
    public void seeAnswers(View view) {
        Intent intent = new Intent();
        setResult(3, intent);
        finish();
    }

    // Public void to exit the app called when the exit button is clicked.
    public void exit(View view) {
        finishAffinity();
        System.exit(0);
    }

}
