package ru.vikbrovkin.android.geoquiz

import androidx.annotation.StringRes

data class Question (@StringRes val textResId: Int, val answer: Boolean, var enabled: Boolean = true)