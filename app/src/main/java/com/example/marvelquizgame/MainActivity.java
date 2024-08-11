package com.example.marvelquizgame;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView movieImage;
    private EditText answerInput;
    private TextView resultText, timerText;
    private Button submitButton;

    private String[] movieNames = {"iron man", "thor", "avengers", "black panther", "captain america"};
    private int[] movieImages = {R.drawable.movie1, R.drawable.movie2, R.drawable.movie3, R.drawable.movie4, R.drawable.movie5};
    private int currentQuestionIndex = 0;

    private CountDownTimer countDownTimer;
    private static final long TIME_LIMIT = 15000; // 15 seconds
    private long timeLeftInMillis = TIME_LIMIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieImage = findViewById(R.id.movieImage);
        answerInput = findViewById(R.id.answerInput);
        resultText = findViewById(R.id.resultText);
        timerText = findViewById(R.id.timerText);
        submitButton = findViewById(R.id.submitButton);

        loadNewQuestion();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void loadNewQuestion() {
        movieImage.setImageResource(movieImages[currentQuestionIndex]);
        answerInput.setText("");
        resultText.setText("");
        resetTimer();
        startTimer();
    }

    private void checkAnswer() {
        countDownTimer.cancel(); // Stop timer when user submits an answer
        String userAnswer = answerInput.getText().toString().toLowerCase().trim();
        if (userAnswer.equals(movieNames[currentQuestionIndex])) {
            resultText.setTextColor(getResources().getColor(R.color.correctGreen));
            resultText.setText("Correct!");
            currentQuestionIndex++;
            if (currentQuestionIndex < movieNames.length) {
                loadNewQuestion();
            } else {
                resultText.setText("You've completed the quiz!");
                submitButton.setEnabled(false);
            }
        } else {
            resultText.setTextColor(getResources().getColor(R.color.incorrectRed));
            resultText.setText("Wrong answer. Try again!");
            startTimer();
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimer();
            }

            @Override
            public void onFinish() {
                resultText.setTextColor(getResources().getColor(R.color.incorrectRed));
                resultText.setText("Time's up!");
                currentQuestionIndex++;
                if (currentQuestionIndex < movieNames.length) {
                    loadNewQuestion();
                } else {
                    resultText.setText("Quiz Over!");
                    submitButton.setEnabled(false);
                }
            }
        }.start();
    }

    private void resetTimer() {
        timeLeftInMillis = TIME_LIMIT;
        updateTimer();
    }

    private void updateTimer() {
        int secondsLeft = (int) (timeLeftInMillis / 1000);
        timerText.setText("Time left: " + secondsLeft);
    }
}
