package com.priximmo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.priximmo.R


class mutationActivity : AppCompatActivity() {
    val Tag = "mutationActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Tag, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mutation)


    }
}