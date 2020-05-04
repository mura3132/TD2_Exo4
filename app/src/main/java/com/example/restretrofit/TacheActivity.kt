package com.example.restretrofit

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_tache.*
import java.util.*

class TacheActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tache)

        val tache = intent.getSerializableExtra("tache") as Tache

        tacheName.text = tache.title
        tacheDescription.text = tache.body
        tacheUser.text = tache.userId


    }
}
