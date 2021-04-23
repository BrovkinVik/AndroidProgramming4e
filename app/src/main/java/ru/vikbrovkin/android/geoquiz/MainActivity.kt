package ru.vikbrovkin.android.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"

class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView

    private var result = 0.0
    private var countDisabled = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        quizViewModel.currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0

        Log.d(TAG, "Got a QuizViewModel: $quizViewModel")

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
            buttonsEnabled(false)
            quizViewModel.questionIsEnabled(false)
            countDisabled += 1
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
            buttonsEnabled(false)
            quizViewModel.questionIsEnabled(false)
            countDisabled += 1
        }

        prevButton.setOnClickListener {
            quizViewModel.moveToIndexBack()
            updateQuestion()
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToIndexForward()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToIndexForward()
            updateQuestion()
        }

        updateQuestion()
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        if (countDisabled == 6) {
            questionTextView.setText("You scored $result procent")
        } else {
            questionTextView.setText(questionTextResId)
        }
        buttonsEnabled(quizViewModel.returnIsEnabled())
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }

        if (messageResId == R.string.correct_toast) {
            result += (100 / 6.0)
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show()
    }

    private fun buttonsEnabled(flag: Boolean) {
        trueButton.isEnabled = flag
        falseButton.isEnabled = flag
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }
}