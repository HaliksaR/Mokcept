package ru.haliksar.mokcept.android.ext

import android.content.Context
import android.content.res.Resources
import androidx.annotation.RawRes

infix fun Context.json(@RawRes res: Int): String =
    resources.json(res)


infix fun Resources.json(@RawRes res: Int): String =
    openRawResource(res)
        .bufferedReader()
        .use { buffer ->
            buffer.readText()
        }
