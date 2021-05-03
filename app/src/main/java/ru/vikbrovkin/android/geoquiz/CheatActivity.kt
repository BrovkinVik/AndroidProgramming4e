package ru.vikbrovkin.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract

const val EXTRA_ANSWER_IS_TRUE =
        "ru.vikbrovkin.android.geoquiz.answer_is_true"
const val EXTRA_ANSWER_SHOWN =
        "ru.vikbrovkin.android.geoquiz.answer_shown"
private const val KEY_TEXT_VIEW =
        "answer_text_view"
private const val KEY_ANSWER_SHOWN =
        "key_answer_shown"

class CheatActivity : AppCompatActivity() {

    private var answerIsTrue = false
    private var answerShown = false
    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        // получает правильный ответ от MainActivity
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)

        answerTextView = findViewById(R.id.answer_text_view)
        answerTextView.text = savedInstanceState?.getString(KEY_TEXT_VIEW, "")
        answerShown = savedInstanceState?.getBoolean(KEY_ANSWER_SHOWN, false) ?: false

        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            val answerText = when {
                answerIsTrue -> R.string.true_button
                else -> R.string.false_button
            }
            answerTextView.setText(answerText)
            answerShown = true

        }
        setAnswerShownResult(answerShown)
    }
    // передает в интент дополнение, что ответ показан
    private fun setAnswerShownResult(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString(KEY_TEXT_VIEW, answerTextView.text as String?)
        savedInstanceState.putBoolean(KEY_ANSWER_SHOWN, answerShown)
    }
}