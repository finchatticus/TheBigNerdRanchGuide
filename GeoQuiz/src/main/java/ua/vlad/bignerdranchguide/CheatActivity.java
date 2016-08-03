package ua.vlad.bignerdranchguide;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;


public class CheatActivity extends AppCompatActivity {

    private static final String TAG = CheatActivity.class.getSimpleName();
    private static final String EXTRA_ANSWER_IS_TRUE = CheatActivity.class.getCanonicalName() + "." + "answer_is_true";
    private static final String EXTRA_ANSWER_SHOWN = CheatActivity.class.getCanonicalName() + "." + "answer_shown";
    private static final String EXTRA_CURRENT_INDEX = CheatActivity.class.getCanonicalName() + "." + "current_index";
    private static final String KEY_IS_ANSWER_SHOWN = "is_answer_shown";
    private static final String KEY_CURRENT_INDEX = "current_index";

    private boolean answerIsTrue;
    private boolean isAnswerShown[] = new boolean[5];
    private int currentIndex;

    private TextView textViewAnswer;
    private Button buttonShowAnswer;
    private TextView textViewManufacturer;
    private TextView textViewModel;
    private TextView textViewVersion;
    private TextView textViewVersionRelease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");

        setContentView(R.layout.activity_cheat);

        if(savedInstanceState != null) {
            isAnswerShown = savedInstanceState.getBooleanArray(KEY_IS_ANSWER_SHOWN);
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);

            setAnswerShownResult(isAnswerShown);
        }

        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
        currentIndex = getIntent().getIntExtra(EXTRA_CURRENT_INDEX, 0);
        isAnswerShown = getIntent().getBooleanArrayExtra(EXTRA_ANSWER_SHOWN);

        textViewAnswer = (TextView) findViewById(R.id.text_view_answer);

        buttonShowAnswer = (Button) findViewById(R.id.button_show_answer);
        buttonShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(answerIsTrue) {
                    textViewAnswer.setText(R.string.button_true);
                } else {
                    textViewAnswer.setText(R.string.button_false);
                }

                isAnswerShown[currentIndex] = true;
                setAnswerShownResult(isAnswerShown);


                //add animation working in API Level 21(Lollipop)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = buttonShowAnswer.getWidth() / 2;
                    int cy = buttonShowAnswer.getHeight() / 2;
                    float radius = buttonShowAnswer.getWidth();
                    Animator animator = ViewAnimationUtils.createCircularReveal(buttonShowAnswer, cx, cy, radius, 0);
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            textViewAnswer.setVisibility(View.VISIBLE);
                            buttonShowAnswer.setVisibility(View.INVISIBLE);
                        }
                    });
                    animator.start();
                } else {
                    textViewAnswer.setVisibility(View.VISIBLE);
                    buttonShowAnswer.setVisibility(View.INVISIBLE);
                }
            }
        });

        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        int version = Build.VERSION.SDK_INT;
        String versionRelease = Build.VERSION.RELEASE;

        textViewManufacturer = (TextView) findViewById(R.id.text_view_manufacturer);
        textViewManufacturer.setText(getString(R.string.text_view_manufacturer) + manufacturer);

        textViewModel = (TextView) findViewById(R.id.text_view_model);
        textViewModel.setText(getString(R.string.text_view_model) + model);

        textViewVersion = (TextView) findViewById(R.id.text_view_version);
        textViewVersion.setText(getText(R.string.text_view_version) + Integer.toString(version));

        textViewVersionRelease = (TextView) findViewById(R.id.text_view_version_release);
        textViewVersionRelease.setText(getString(R.string.text_view_version_release) + versionRelease);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstance called");
        outState.putBooleanArray(KEY_IS_ANSWER_SHOWN, isAnswerShown);
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue, int currentIndex, boolean[] isAnswerShown) {
        Intent i = new Intent(packageContext, CheatActivity.class);
        i.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        i.putExtra(EXTRA_CURRENT_INDEX, currentIndex);
        i.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        return i;
    }

    public static boolean[] wasAnswerShown(Intent result) {
        return result.getBooleanArrayExtra(EXTRA_ANSWER_SHOWN);
    }

    private void setAnswerShownResult(boolean[] isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
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
}
