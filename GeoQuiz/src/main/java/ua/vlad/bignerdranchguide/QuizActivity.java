package ua.vlad.bignerdranchguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = QuizActivity.class.getSimpleName();
    private static final String KEY_INDEX = "index";

    private Button buttonTrue;
    private Button buttonFalse;
    private Button buttonPrev;
    private Button buttonNext;
    private TextView textViewQuestion;

    private Question[] questionBank = new Question[] {
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");

        setContentView(R.layout.activity_quiz);

        if(savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(KEY_INDEX);
        }

        textViewQuestion = (TextView) findViewById(R.id.text_view_question);
        textViewQuestion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });
        updateQuestion();

        buttonTrue = (Button) findViewById(R.id.button_true);
        buttonTrue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        buttonFalse = (Button) findViewById(R.id.button_false);
        buttonFalse.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        buttonPrev = (Button) findViewById(R.id.button_prev);
        buttonPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentIndex == 0) {
                    currentIndex = questionBank.length - 1;
                } else {
                    currentIndex = currentIndex - 1;
                }
                updateQuestion();
            }
        });

        buttonNext = (Button) findViewById(R.id.button_next);
        buttonNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });
    }

    private void updateQuestion() {
        int question = questionBank[currentIndex].getTextResId();
        textViewQuestion.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = questionBank[currentIndex].isAnswerTrue();

        int messageResId = 0;

        if(userPressedTrue == answerIsTrue) {
            messageResId = R.string.toast_correct;
        } else {
            messageResId = R.string.toast_incorrect;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState called");
        outState.putInt(KEY_INDEX, currentIndex);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy called");
    }

    /*
    *
    *  Buttons in Android
    * | Back button | Home button | Recent apps button |
    *
    *
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d(TAG, "Back button click");
        }

        int clickedButtonKey = event.getKeyCode();
        Log.d(TAG, "Button click code " + clickedButtonKey + " " + KeyEvent.keyCodeToString(clickedButtonKey));

        return super.onKeyDown(keyCode, event);
    }
}
