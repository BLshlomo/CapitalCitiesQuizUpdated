package com.example.android.capitalcitiesquiz;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    boolean reset, etBudaError, etSwissError, etChinaError;
    int quizScore, quizCorrectAnswers, quizWrongAnswer, quizNoAnswer, resultCode, action;
    int rbCountAustralia, rbCountMostVisited, llCountBolivia, rbCountBiggestLandSize, llCountBrazil, rbCountBerlinUnification;
    RadioGroup rgAustralia, rgMostVisited, rgBiggestLandSize, rgBerlinUnification;
    LinearLayout llBolivia, llBrazil;
    EditText etBudapest, etSwitzerland, etChina;
    AppCompatRadioButton rbCanberra, rbBangkok, rbTokyo, rbCorrectYear;
    AppCompatCheckBox cbLaPaz, cbSantaCruz, cbCochabamba, cbSucre, cbRio, cbPaulo, cbRecife, cbBrasilia, cbSalvador;
    Button mainBtn;
    String summaryPerfect, summaryMistakes, summaryNoAnswers, summaryWorse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables
        mainBtn = (Button) findViewById(R.id.submit_quiz_btn);

        // Initialize radio groups and linear layouts of checkboxes answers.
        rgAustralia = (RadioGroup) findViewById(R.id.australia_rg);
        rgMostVisited = (RadioGroup) findViewById(R.id.most_visited_rg);
        llBolivia = (LinearLayout) findViewById(R.id.bolivia_ll);
        rgBiggestLandSize = (RadioGroup) findViewById(R.id.biggest_land_size_rg);
        llBrazil = (LinearLayout) findViewById(R.id.brazil_ll);
        rgBerlinUnification = (RadioGroup) findViewById(R.id.unification_berlin_rg);

        // Initialize correct answers radio buttons.
        rbCanberra = (AppCompatRadioButton) findViewById(R.id.canberra_cptl_australia);
        rbBangkok = (AppCompatRadioButton) findViewById(R.id.bangkok_most_visited);
        rbTokyo = (AppCompatRadioButton) findViewById(R.id.tokyo_land_size);
        rbCorrectYear = (AppCompatRadioButton) findViewById(R.id.year_3_unification_berlin);

        // Initialize Bolivia linear layout checkboxes.
        cbLaPaz = (AppCompatCheckBox) findViewById(R.id.la_paz_cptl_bolivia);
        cbSantaCruz = (AppCompatCheckBox) findViewById(R.id.santa_cruz_cptl_bolivia);
        cbCochabamba = (AppCompatCheckBox) findViewById(R.id.cochabamba_cptl_bolivia);
        cbSucre = (AppCompatCheckBox) findViewById(R.id.sucre_cptl_bolivia);

        // Initialize Brazil linear layout checkboxes.
        cbRio = (AppCompatCheckBox) findViewById(R.id.rio_cptl_brazil);
        cbPaulo = (AppCompatCheckBox) findViewById(R.id.paulo_cptl_brazil);
        cbRecife = (AppCompatCheckBox) findViewById(R.id.recife_cptl_brazil);
        cbBrasilia = (AppCompatCheckBox) findViewById(R.id.brasilia_cptl_brazil);
        cbSalvador = (AppCompatCheckBox) findViewById(R.id.salvador_cptl_brazil);

        // Initialize etBudapest as budapest_et edit text.
        etBudapest = (EditText) findViewById(R.id.budapest_et);
        // Initialize etSwitzerland as switzerland_et edit text.
        etSwitzerland = (EditText) findViewById(R.id.switzerland_et);
        // Initialize etChina as china_et edit text.
        etChina = (EditText) findViewById(R.id.china_et);

        // Set of methods to change the ime option of the edit text fields to done
        // needed because android:imeOption=actionDone doesn't function in the current settings.
        etBudapest.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etBudapest.setRawInputType(InputType.TYPE_CLASS_TEXT);
        etSwitzerland.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etSwitzerland.setRawInputType(InputType.TYPE_CLASS_TEXT);
        etChina.setImeOptions(EditorInfo.IME_ACTION_DONE);
        etChina.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }

    // Use onSaveInstanceState(Bundle) and onRestoreInstanceState
    // onSaveInstanceState
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.

        savedInstanceState.putBoolean("answers", reset);
        savedInstanceState.putInt("rotate", action);

        // Check if the see Solution method activated.
        if (action == 3) {

            // Changes the Buda error boolean to true and pass it to savedInstanceState bundle
            // if etBudapest has a border error drawable.
            if (etBudapest.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,
                    R.drawable.border_error).getConstantState())) {
                etBudaError = true;
                savedInstanceState.putBoolean("buda", etBudaError);
            }

            // Changes the Swiss error boolean to true and pass it to savedInstanceState bundle
            // if etSwitzerland has a border error drawable.
            if (etSwitzerland.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,
                    R.drawable.border_error).getConstantState())) {
                etSwissError = true;
                savedInstanceState.putBoolean("swiss", etSwissError);
            }

            // Changes the China error boolean to true and pass it to savedInstanceState bundle
            // if etChina has a border error drawable.
            if (etChina.getBackground().getConstantState().equals(ContextCompat.getDrawable(this,
                    R.drawable.border_error).getConstantState())) {
                etChinaError = true;
                savedInstanceState.putBoolean("china", etChinaError);
            }
        }

        // etc.

        super.onSaveInstanceState(savedInstanceState);
    }

    // onRestoreInstanceState
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);

        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.

        reset = savedInstanceState.getBoolean("answers");
        action = savedInstanceState.getInt("rotate");

        // An if statement to run the checkAnswers or seeSolution method on screen rotation
        // according to the variable int action.
        if (reset) {
            if (action == 2) {
                checkAnswers();
            } else if (action == 3) {
                seeSolution();

                // Restore booleans state from the savedInstanceState if the see solution
                // method activated.
                etBudaError = savedInstanceState.getBoolean("buda");
                etSwissError = savedInstanceState.getBoolean("swiss");
                etChinaError = savedInstanceState.getBoolean("china");

                // Changes Budapest edit text background to a border error drawable if it had one
                // when the onSaveInstanceState method activated.
                if (etBudaError) {
                    etBudapest.setBackgroundResource(R.drawable.border_error);
                }
                // Changes Switzerland edit text background to a border error drawable if it had one
                // when the onSaveInstanceState method activated.
                if (etSwissError) {
                    etSwitzerland.setBackgroundResource(R.drawable.border_error);
                }
                // Changes China edit text background to a border error drawable if it had one
                // when the onSaveInstanceState method activated.
                if (etChinaError) {
                    etChina.setBackgroundResource(R.drawable.border_error);
                }
            }
        }
    }

    // A public void method to count the score, correct answers, wrong answers and no answers,
    // called when the Submit Quiz button is clicked.
    public void submitQuiz(View view) {

        // Restart the activity if the boolean reset is true.
        if (reset) {
            restart();
        }

        // Check the quiz and update the score, the amount of correctly answered questions, no answered questions
        // And wrong answers.
        else {

            // Get checked radio button out of australia radio group.
            int selectedAustralia = rgAustralia.getCheckedRadioButtonId();

            // Declare the checked radio button as checkedAustralia variable.
            AppCompatRadioButton checkedAustralia = (AppCompatRadioButton) findViewById(selectedAustralia);

            // Adds 11 point to quizScore and a point to correct answers if the right answer is marked.
            if (checkedAustralia == rbCanberra) {
                quizScore += +11;
                quizCorrectAnswers += +1;

            } else if (selectedAustralia == -1) {
                // Add a point to no answers variable if no answer is marked.
                quizNoAnswer += 1;

            } else {
                // Add a point to wrong answers if both of the previous statements are false.
                quizWrongAnswer += 1;
            }

            // Get checked radio button out of biggest most visited radio group.
            int selectedMostVisited = rgMostVisited.getCheckedRadioButtonId();

            // Declare the checked radio button as checkedMostVisited variable.
            AppCompatRadioButton checkedMostVisited = (AppCompatRadioButton) findViewById(selectedMostVisited);

            // Adds 11 point to quizScore and a point to correct answers if the right answer is marked.
            if (checkedMostVisited == rbBangkok) {
                quizScore += +11;
                quizCorrectAnswers += +1;

            } else if (selectedMostVisited == -1) {
                // Add a point to no answers variable if no answer is marked.
                quizNoAnswer += 1;

            } else {
                // Add a point to wrong answers if both of the previous statements are false.
                quizWrongAnswer += 1;
            }

            // Adds a point to wrong answers if Santa Cruz checkbox is marked and
            // skip the rest of the checks of the Bolivia linear layout answers.
            if (cbSantaCruz.isChecked()) {
                quizWrongAnswer += 1;

            } else if (cbCochabamba.isChecked()) {
                // Adds a point to wrong answers if Cochabamba checkbox is marked and
                // skip the rest of the checks of the Bolivia linear layout answers.
                quizWrongAnswer += 1;

            } else if (cbLaPaz.isChecked()) {
                // Adds points to quizScore and a point to correct answers
                // accordingly to the marked correct answers.
                if (cbSucre.isChecked()) {
                    quizScore += +11;
                    quizCorrectAnswers += 1;
                } else {
                    quizScore += +6;
                }
            } else if (cbSucre.isChecked()) {
                quizScore += +6;

            } else {
                // Add a point to no answers variable if no answer is marked.
                quizNoAnswer += 1;
            }

            // Declare budapestETValue as extracted String from Budapest edit text.
            String budapestETValue = etBudapest.getText().toString();

            // Declare budapestValueUpperCase as the extracted string in all caps.
            String budapestValueUpperCase = budapestETValue.toUpperCase();

            // Add a point to no answer variable if nothing is written.
            if (budapestValueUpperCase.trim().length() == 0) {
                quizNoAnswer += 1;

            } else if (budapestValueUpperCase.contains("BUDA ")) {
                if (budapestValueUpperCase.contains(" PEST")) {
                    // Adds 11 point to quizScore and a point to correct answers if the words
                    // "buda " and " pest" are written.
                    quizScore += +11;
                    quizCorrectAnswers += 1;
                }
            } else {
                // Add a point to wrong answers if the previous statements are false.
                quizWrongAnswer += 1;
            }

            // Get checked radio button out of biggest land size radio group.
            int selectedLandSize = rgBiggestLandSize.getCheckedRadioButtonId();

            // Declare the checked radio button as checkedLandSize variable.
            AppCompatRadioButton checkedLandSize = (AppCompatRadioButton) findViewById(selectedLandSize);

            // Adds 11 point to quizScore and a point to correct answers if the right answer is marked.
            if (checkedLandSize == rbTokyo) {
                quizScore += +11;
                quizCorrectAnswers += 1;

                // Add a point to no answer variable if no answer is marked.
            } else if (selectedLandSize == -1) {
                quizNoAnswer += 1;

                // Add a point to no answer variable if no answer is marked.
            } else {
                quizWrongAnswer += 1;
            }

            // Adds a point to wrong answers if Sao Paulo checkbox is marked and
            // skip the rest of the checks of the Brazil linear layout answers.
            if (cbPaulo.isChecked()) {
                quizWrongAnswer += 1;

                // Adds a point to wrong answers if Recife checkbox is marked and
                // skip the rest of the checks of the Brazil linear layout answers.
            } else if (cbRecife.isChecked()) {
                quizWrongAnswer += 1;

                // Adds points to quizScore and a point to correct answers
                // accordingly to the marked correct answers.
            } else if (cbRio.isChecked()) {
                if (cbBrasilia.isChecked()) {
                    if (cbSalvador.isChecked()) {
                        quizScore += +12;
                        quizCorrectAnswers += 1;
                    } else {
                        quizScore += +8;
                    }
                } else {
                    quizScore += +4;
                }
            } else if (cbBrasilia.isChecked()) {
                if (cbSalvador.isChecked()) {
                    quizScore += +8;
                } else {
                    quizScore += +4;
                }
            } else if (cbSalvador.isChecked()) {
                quizScore += +4;

                // Add a point to no answer variable if no answer is marked.
            } else {
                quizNoAnswer += 1;
            }

            // Declare switzerlandETValue as extracted String from Switzerland edit text.
            String switzerlandETValue = etSwitzerland.getText().toString();

            // Declare switzerlandValueUpperCase as the extracted string in all caps.
            String switzerlandValueUpperCase = switzerlandETValue.toUpperCase();

            // Add a point to no answer variable if nothing is written.
            if (switzerlandValueUpperCase.trim().length() == 0) {
                quizNoAnswer += 1;

                // Adds 11 point to quizScore and a point to correct answers if the words
                // "bern", "capital", "de jure" and "de facto" are written.
            } else if (switzerlandValueUpperCase.contains("BERN")) {
                if (switzerlandValueUpperCase.contains("DE FACTO")) {
                    if (switzerlandValueUpperCase.contains("CAPITAL")) {
                        if (switzerlandValueUpperCase.contains("DE JURE")) {
                            quizScore += +11;
                            quizCorrectAnswers += 1;
                        }
                    }
                } // Add a point to wrong answers if the previous statements are false.
            } else {
                quizWrongAnswer += 1;
            }

            // Get checked radio button out of unification berlin radio group.
            int selectedBerlinUnification = rgBerlinUnification.getCheckedRadioButtonId();

            // Declare the checked radio button as checkedBerlinUnification variable.
            AppCompatRadioButton checkedBerlinUnification = (AppCompatRadioButton) findViewById(selectedBerlinUnification);

            // Adds 11 point to quizScore and a point to correct answers if the right answer is marked.
            if (checkedBerlinUnification == rbCorrectYear) {
                quizScore += +11;
                quizCorrectAnswers += 1;

                // Adds a point to no answers variable if no answer is marked.
            } else if (selectedBerlinUnification == -1) {
                quizNoAnswer += 1;

                // Adds a point to wrong answers if both of the previous statements are false
            } else {
                quizWrongAnswer += 1;
            }

            // Declare chinaETValue as extracted String from China edit text.
            String chinaETValue = etChina.getText().toString();

            // Declare chinaValueUpperCase as the extracted string in all caps.
            String chinaValueUpperCase = chinaETValue.toUpperCase();

            // Add a point to no answer variable if nothing is written.
            if (chinaValueUpperCase.trim().length() == 0) {
                quizNoAnswer += 1;

                // Adds 11 point to quizScore and a point to correct answers if the word
                // "no" is written.
            } else if (chinaValueUpperCase.contains("NO")) {
                quizScore += 11;
                quizCorrectAnswers += 1;

                // Add a point to wrong answers if the previous statements are false.
            } else {
                quizWrongAnswer += 1;
            }

            // Initiate summaryPerfect as a string resource message based on quiz score and quiz correct answers.
            summaryPerfect = String.format(getResources().getString(R.string.summary_perfect), quizScore, quizCorrectAnswers);

            // Initiate summaryMistakes as a string message based on wrong answers amount.
            summaryMistakes = summaryPerfect;
            summaryMistakes += String.format(getResources().getString(R.string.summary_mistakes), quizWrongAnswer);

            // Initiate summaryNoAnswers as a string message based on no answered questions amount.
            summaryNoAnswers = summaryPerfect;
            summaryNoAnswers += String.format(getResources().getString(R.string.summary_no_answers), quizNoAnswer);

            // Extends the summaryMistakes string message to include the no answered questions amount.
            summaryWorse = summaryMistakes;
            summaryWorse += String.format(getResources().getString(R.string.summary_worse), quizNoAnswer);

            // Display a toast message.
            // display the string messages based on the right conditions.
            if (quizWrongAnswer == 0 && quizNoAnswer == 0) {
                //display in long period of time
                Toast.makeText(this, summaryPerfect,
                        Toast.LENGTH_LONG).show();
            } else if (quizNoAnswer == 0) {
                //display in long period of time
                Toast.makeText(this, summaryMistakes,
                        Toast.LENGTH_LONG).show();
            } else if (quizWrongAnswer == 0) {
                //display in long period of time
                Toast.makeText(this, summaryNoAnswers,
                        Toast.LENGTH_LONG).show();
            } else {
                //display in long period of time
                Toast.makeText(this, summaryWorse,
                        Toast.LENGTH_LONG).show();
            }

            // Goes to a new activity using the summaryPage method.
            summaryPage(view);
        }
    }

    // Public void to disable all answers in the page.
    public void disableAll() {
        disableAustralia();
        disableMostVisited();
        disableBolivia();
        disableBudapest();
        disableBiggestLandSize();
        disableBrazil();
        disableSwitzerland();
        disableBerlinUnification();
        disableChina();
    }

    // Public void method to disable Australia radio group.
    public void disableAustralia() {

        // Count the number of radio buttons in the group.
        rbCountAustralia = rgAustralia.getChildCount();

        // Loop through the radio buttons in the group to disable the radio buttons.
        for (int i = 0; i < rbCountAustralia; i++) {
            RadioButton answer = (RadioButton) rgAustralia.getChildAt(i);
            answer.setEnabled(false);
        }
    }

    // Public void method to disable most visited radio group.
    public void disableMostVisited() {

        // Count the number of radio buttons in the group.
        rbCountMostVisited = rgMostVisited.getChildCount();

        // Loop through the radio buttons in the group to disable most visited radio group.
        for (int i = 0; i < rbCountMostVisited; i++) {
            RadioButton answer = (RadioButton) rgMostVisited.getChildAt(i);
            answer.setEnabled(false);
        }
    }

    // Public void method to disable bolivia linear layout checkboxes.
    public void disableBolivia() {

        // Count the number of checkboxes in the linear layout.
        llCountBolivia = llBolivia.getChildCount();

        // Loop through the check boxes in the linear layout to disable bolivia linear layout
        for (int i = 0; i < llCountBolivia; i++) {
            CheckBox checked = (CheckBox) llBolivia.getChildAt(i);
            checked.setEnabled(false);
        }
    }

    // Public void method to disable budapest edit text.
    public void disableBudapest() {
        etBudapest.setEnabled(false);
        etBudapest.setClickable(false);
        etBudapest.setFocusable(false);
    }

    // Public void method to disable biggest land size radio group.
    public void disableBiggestLandSize() {

        // Count the number of radio buttons in the group.
        rbCountBiggestLandSize = rgBiggestLandSize.getChildCount();

        // Loop through the radio buttons in the group to disable biggest land size radio group.
        for (int i = 0; i < rbCountBiggestLandSize; i++) {
            RadioButton answer = (RadioButton) rgBiggestLandSize.getChildAt(i);
            answer.setEnabled(false);
        }
    }

    // Public void method to disable brazil linear layout checkboxes.
    public void disableBrazil() {

        // Count the number of checkboxes in the linear layout.
        llCountBrazil = llBrazil.getChildCount();

        // Loop through the check boxes in the linear layout to disable brazil linear layout.
        for (int i = 0; i < llCountBrazil; i++) {
            CheckBox checked = (CheckBox) llBrazil.getChildAt(i);
            checked.setEnabled(false);
        }
    }

    // Public void method to disable switzerland edit text.
    public void disableSwitzerland() {
        etSwitzerland.setEnabled(false);
        etSwitzerland.setClickable(false);
        etSwitzerland.setFocusable(false);
    }

    // Public void method to disable berlin unification radio group.
    public void disableBerlinUnification() {

        // Count the number of radio buttons in the group.
        rbCountBerlinUnification = rgBerlinUnification.getChildCount();

        // Loop through the radio buttons in the group to disable berlin unification radio group.
        for (int i = 0; i < rbCountBerlinUnification; i++) {
            RadioButton answer = (RadioButton) rgBerlinUnification.getChildAt(i);
            answer.setEnabled(false);
        }
    }

    // Public void method to disable china edit text.
    public void disableChina() {
        etChina.setEnabled(false);
        etChina.setClickable(false);
        etChina.setFocusable(false);
    }

    // Public void to restart the activity using a not healthy way of closing and relaunching the activity.
    public void restart() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    // Public void to check and color the marked answers based on green and red right and wrong
    // definitions, called when the check answers button is clicked.
    public void checkAnswers() {
        // Change the action int value to 2 for future reference.
        action = 2;

        // Call a method to disable all answers in the page.
        disableAll();

        // Changes the main button text and text color.
        mainBtn.setText(getResources().getString(R.string.restart));
        mainBtn.setTextColor(Color.RED);

        // Set the boolean reset to true so the main button function will change to restart.
        reset = true;

        // Get checked radio button out of australia radio group.
        int selectedAustralia = rgAustralia.getCheckedRadioButtonId();

        // Declare the checked radio button as checkedAustralia variable.
        AppCompatRadioButton checkedAustralia = (AppCompatRadioButton) findViewById(selectedAustralia);

        // Change the right answer text color and button tint to green if the right answer is marked.
        if (checkedAustralia == rbCanberra) {
            rbCanberra.setTextColor(Color.GREEN);
            rbCanberra.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
            rbCanberra.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

        } else if (selectedAustralia == -1) {
            // Change the radio group background drawable to an error red background drawable if no answer is marked.
            rgAustralia.setBackgroundResource(R.drawable.border_error);

        } else {
            // Change the marked answer text color and button tint to red.
            checkedAustralia.setTextColor(Color.RED);
            checkedAustralia.setButtonTintList(ColorStateList.valueOf(Color.RED));
            checkedAustralia.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }

        // Get checked radio button out of biggest most visited radio group.
        int selectedMostVisited = rgMostVisited.getCheckedRadioButtonId();

        // Declare the checked radio button as checkedMostVisited variable.
        AppCompatRadioButton checkedMostVisited = (AppCompatRadioButton) findViewById(selectedMostVisited);

        // Change the right answer text color and button tint to green if the right answer is marked.
        if (checkedMostVisited == rbBangkok) {
            rbBangkok.setTextColor(Color.GREEN);
            rbBangkok.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
            rbBangkok.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

        } else if (selectedMostVisited == -1) {
            // Change the radio group background drawable to an error red background drawable if no answer is marked.
            rgMostVisited.setBackgroundResource(R.drawable.border_error);

        } else {
            // Change the marked answer text color and button tint to red.
            checkedMostVisited.setTextColor(Color.RED);
            checkedMostVisited.setButtonTintList(ColorStateList.valueOf(Color.RED));
            checkedMostVisited.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }

        // Declare and initiate a set of booleans for the bolivia linear layout answers group.
        boolean cbsc = false, cbcb = false, cblp = false, cbsr = false;

        // Change the wrong answers text color and button tint to red if they are marked
        // and set the respective booleans to true.
        if (cbSantaCruz.isChecked()) {
            cbSantaCruz.setTextColor(Color.RED);
            cbSantaCruz.setButtonTintList(ColorStateList.valueOf(Color.RED));
            cbSantaCruz.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            cbsc = true;
        }
        if (cbCochabamba.isChecked()) {
            cbCochabamba.setTextColor(Color.RED);
            cbCochabamba.setButtonTintList(ColorStateList.valueOf(Color.RED));
            cbCochabamba.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            cbcb = true;
        }

        // Change the right answer text color and button tint to green if the right answers are marked
        // and set the respective booleans to true.
        if (cbLaPaz.isChecked()) {
            cbLaPaz.setTextColor(Color.GREEN);
            cbLaPaz.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
            cbLaPaz.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            cblp = true;
        }
        if (cbSucre.isChecked()) {
            cbSucre.setTextColor(Color.GREEN);
            cbSucre.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
            cbSucre.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            cbsr = true;
        }

        // Change the linear layout answers group background drawable to an error red background drawable
        // if no answer is marked and the respective booleans are not true.
        if (!cbsr && !cblp && !cbcb && !cbsc) {
            llBolivia.setBackgroundResource(R.drawable.border_error);
        }

        // Declare budapestETValue as extracted String from Budapest edit text.
        String budapestETValue = etBudapest.getText().toString();

        // Declare budapestValueUpperCase as the extracted string in all caps.
        String budapestValueUpperCase = budapestETValue.toUpperCase();

        // Change the edit text background drawable to a correct green background drawable if the words
        // "buda " and " pest" are written.
        if (budapestValueUpperCase.contains("BUDA ")) {
            if (budapestValueUpperCase.contains(" PEST")) {
                etBudapest.setBackgroundResource(R.drawable.border_correct);
            }

        } else {
            // Change the edit text background drawable to an error red background drawable if the
            // written answer is wrong.
            etBudapest.setBackgroundResource(R.drawable.border_error);
        }

        // Get checked radio button out of biggest land size radio group.
        int selectedLandSize = rgBiggestLandSize.getCheckedRadioButtonId();

        // Declare the checked radio button as checkedLandSize variable.
        AppCompatRadioButton checkedLandSize = (AppCompatRadioButton) findViewById(selectedLandSize);

        // Change the right answer text color and button tint to green if the right answer is marked.
        if (checkedLandSize == rbTokyo) {
            rbTokyo.setTextColor(Color.GREEN);
            rbTokyo.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
            rbTokyo.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

        } else if (selectedLandSize == -1) {
            // Change the radio group drawable to an error red background drawable if no answer is marked.
            rgBiggestLandSize.setBackgroundResource(R.drawable.border_error);

        } else {
            // Change the marked answer text color and button tint to red.
            checkedLandSize.setTextColor(Color.RED);
            checkedLandSize.setButtonTintList(ColorStateList.valueOf(Color.RED));
            checkedLandSize.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }

        // Declare and initiate a set of booleans for the Brazil linear layout answers group.
        boolean cbpo = false, cbrc = false, cbrj = false, cbbs = false, cbsv = false;

        // Change the wrong answers text color and button tint to red if they are marked
        // and set the respective booleans to true.
        if (cbPaulo.isChecked()) {
            cbPaulo.setTextColor(Color.RED);
            cbPaulo.setButtonTintList(ColorStateList.valueOf(Color.RED));
            cbPaulo.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            cbpo = true;
        }
        if (cbRecife.isChecked()) {
            cbRecife.setTextColor(Color.RED);
            cbRecife.setButtonTintList(ColorStateList.valueOf(Color.RED));
            cbRecife.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
            cbrc = true;
        }

        // Change the right answer text color and button tint to green if the right answers are marked
        // and set the respective booleans to true.
        if (cbRio.isChecked()) {
            cbRio.setTextColor(Color.GREEN);
            cbRio.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
            cbRio.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            cbrj = true;
        }
        if (cbBrasilia.isChecked()) {
            cbBrasilia.setTextColor(Color.GREEN);
            cbBrasilia.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
            cbBrasilia.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            cbbs = true;
        }
        if (cbSalvador.isChecked()) {
            cbSalvador.setTextColor(Color.GREEN);
            cbSalvador.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
            cbSalvador.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            cbsv = true;
        }

        // Change the linear layout answers group background drawable to an error red background drawable
        // if no answer is marked and the respective booleans are not true.
        if (!cbpo && !cbrc && !cbrj && !cbbs && !cbsv) {
            llBrazil.setBackgroundResource(R.drawable.border_error);
        }

        // Declare switzerlandETValue as extracted String from Switzerland edit text.
        String switzerlandETValue = etSwitzerland.getText().toString();

        // Declare switzerlandValueUpperCase as the extracted string in all caps.
        String switzerlandValueUpperCase = switzerlandETValue.toUpperCase();

        // Change the edit text background drawable to a correct green background drawable if the words
        // "bern", "capital", "de jure" and "de facto" are written.
        if (switzerlandValueUpperCase.contains("BERN")) {
            if (switzerlandValueUpperCase.contains("DE FACTO")) {
                if (switzerlandValueUpperCase.contains("CAPITAL")) {
                    if (switzerlandValueUpperCase.contains("DE JURE")) {
                        etSwitzerland.setBackgroundResource(R.drawable.border_correct);
                    }
                }
            }
        } else {
            // Change the edit text background drawable to an error red background drawable if the
            // written answer is wrong.
            etSwitzerland.setBackgroundResource(R.drawable.border_error);
        }

        // Get checked radio button out of unification berlin radio group.
        int selectedBerlinUnification = rgBerlinUnification.getCheckedRadioButtonId();

        // Declare the checked radio button as checkedBerlinUnification variable.
        AppCompatRadioButton checkedBerlinUnification = (AppCompatRadioButton) findViewById(selectedBerlinUnification);

        // Change the right answer text color and button tint to green if the right answer is marked.
        if (checkedBerlinUnification == rbCorrectYear) {
            rbCorrectYear.setTextColor(Color.GREEN);
            rbCorrectYear.setButtonTintList(ColorStateList.valueOf(Color.GREEN));
            rbCorrectYear.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));

        } else if (selectedBerlinUnification == -1) {
            // Change the radio group background drawable to an error red background drawable if no answer is marked.
            rgBerlinUnification.setBackgroundResource(R.drawable.border_error);

        } else {
            // Change the marked answer text color and button tint to red.
            checkedBerlinUnification.setTextColor(Color.RED);
            checkedBerlinUnification.setButtonTintList(ColorStateList.valueOf(Color.RED));
            checkedBerlinUnification.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }

        // Declare chinaETValue as extracted String from China edit text.
        String chinaETValue = etChina.getText().toString();

        // Declare chinaValueUpperCase as the extracted string in all caps.
        String chinaValueUpperCase = chinaETValue.toUpperCase();

        // Change the edit text background drawable to a correct green background drawable if the word
        // "no" is written.
        if (chinaValueUpperCase.contains("NO")) {
            etChina.setBackgroundResource(R.drawable.border_correct);
        } else {
            // Change the edit text background drawable to an error red background drawable if the
            // written answer is wrong.
            etChina.setBackgroundResource(R.drawable.border_error);
        }
    }

    // Public void to check and color the marked answers based on green and red right and wrong
    // definitions and to show the right solution in green color, called when the see solution
    // button is clicked.
    public void seeSolution() {
        checkAnswers();
        rbCanberra.setTextColor(Color.GREEN);
        rbBangkok.setTextColor(Color.GREEN);
        cbLaPaz.setTextColor(Color.GREEN);
        cbSucre.setTextColor(Color.GREEN);
        etBudapest.setText(getResources().getString(R.string.budapest_answer));
        etBudapest.setTextColor(Color.GREEN);
        rbTokyo.setTextColor(Color.GREEN);
        cbRio.setTextColor(Color.GREEN);
        cbBrasilia.setTextColor(Color.GREEN);
        cbSalvador.setTextColor(Color.GREEN);
        etSwitzerland.setText(getResources().getString(R.string.switzerland_answer));
        etSwitzerland.setTextColor(Color.GREEN);
        rbCorrectYear.setTextColor(Color.GREEN);
        etChina.setText(getResources().getString(R.string.china_answer));
        etChina.setTextColor(Color.GREEN);

        // Change the action int value to 3 for future reference.
        action = 3;
    }

    // Public void to launch a new activity and wait for a result to pass back.
    public void summaryPage(View view) {
        Intent myIntent = new Intent(this, SummaryMenu.class);
        myIntent.putExtra("wrongAnswers", quizWrongAnswer);
        myIntent.putExtra("noAnswers", quizNoAnswer);
        myIntent.putExtra("stringPerfect", summaryPerfect);
        myIntent.putExtra("stringMistakes", summaryMistakes);
        myIntent.putExtra("stringNoAnswers", summaryNoAnswers);
        myIntent.putExtra("stringWorse", summaryWorse);
        startActivityForResult(myIntent, 0);
    }

    // Override and run the appropriate methods on activity launch according to the result code.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            recreate();
        } else if (resultCode == 1) {
            restart();
        } else if (resultCode == 2) {
            checkAnswers();
        } else if (resultCode == 3) {
            seeSolution();
        }
    }
}
