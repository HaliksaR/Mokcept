package ru.haliksar.mokcept.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import ru.haliksar.mokcept.app.api.SimpleSet
import ru.haliksar.mokcept.app.api.TestApi

class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.Main.immediate + Job())

    private val api: TestApi by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        scope.launch {
            withContext(Dispatchers.IO) {
                try {
                    Log.d("FakeRetrofit", api.simple().toString())
                    delay(1000)
                    Log.d("FakeRetrofit", "1 " + api.simpleQuery(1).toString())
                    delay(1000)
                    Log.d("FakeRetrofit", "2 " + api.simpleQuery(2).toString())
                    delay(1000)
                    Log.d("FakeRetrofit", "3 " + api.simpleQuery(3).toString())
                    delay(1000)
                    Log.d("FakeRetrofit", api.simplePost(SimpleSet("hjhgjghj", "jhjghjgh")).toString())
                } catch (throwable: Throwable) {
                    Log.d("FakeRetrofit", throwable.message.toString())
                }
            }
        }
    }
}