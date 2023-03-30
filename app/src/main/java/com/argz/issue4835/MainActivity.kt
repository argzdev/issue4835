package com.argz.issue4835

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


class MainActivity : AppCompatActivity() {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val db = Firebase.firestore
    private lateinit var pb: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pb = findViewById(R.id.pb)
    }

    suspend fun save(test: String) {
        delay(3000)
        db.collection("test")
            .document("random_document")
            .update("test_string", "123456")
            .await()
    }

    fun test(view: View) {
        scope.launch {
            runOnUiThread {
                pb.visibility = View.VISIBLE
            }

            save("test")

            runOnUiThread {
                pb.visibility = View.INVISIBLE
            }
        }
    }
}
