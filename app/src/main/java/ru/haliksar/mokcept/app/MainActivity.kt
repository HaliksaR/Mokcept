package ru.haliksar.mokcept.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import ru.haliksar.mokcept.app.api.DataSource
import ru.haliksar.mokcept.app.api.SimpleSet
import ru.haliksar.mokcept.app.di.BASE
import ru.haliksar.mokcept.app.di.MULTI_MODULE


class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.Main.immediate + Job())

    private val base: DataSource by inject(named(BASE))
    private val multiModule: DataSource by inject(named(MULTI_MODULE))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        scope.launch {
            try {
                launchDataSource("BASE", base)
                launchDataSource("MULTI_MODULE", multiModule)
            } catch (throwable: Throwable) {
                Log.d("mokcept throwable", throwable.message.toString())
            }
        }
    }

    private suspend fun launchDataSource(name: String, dataSource: DataSource) {
        Log.d("mokcept $name", dataSource.simple().toString())
        delay(1000)
        Log.d("mokcept $name", "1 " + dataSource.simpleQuery(1).toString())
        delay(1000)
        Log.d("mokcept $name", "2 " + dataSource.simpleQuery(2).toString())
        delay(1000)
        Log.d("mokcept $name", "3 " + dataSource.simpleQuery(3).toString())
        delay(1000)
        Log.d("mokcept $name", dataSource.simplePost(SimpleSet("hjhgjghj", "jhjghjgh")).toString())
    }
}