package ru.vikbrovkin.android.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class CheatActivityContract : ActivityResultContract<Boolean, Boolean?>() {

    override fun createIntent(packageContext: Context, answer_is_true: Boolean): Intent {
        return Intent(packageContext, CheatActivity::class.java)
            .putExtra(EXTRA_ANSWER_IS_TRUE, answer_is_true)
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        return intent?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
    }
}