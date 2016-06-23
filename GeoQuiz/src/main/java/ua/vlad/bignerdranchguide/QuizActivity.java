package ua.vlad.bignerdranchguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button buttonTrue;
    private Button buttonFalse;
    private ImageButton buttonPrev;
    private ImageButton buttonNext;
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
        setContentView(R.layout.activity_quiz);

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

        buttonPrev = (ImageButton) findViewById(R.id.button_prev);
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

        buttonNext = (ImageButton) findViewById(R.id.button_next);
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
}
