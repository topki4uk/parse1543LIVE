package com.example.parse1543live

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class AllClassesActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_all_classes)
        val allClassesListView: ListView = findViewById(R.id.allClassesList)
        val classes = intent.getStringArrayListExtra("classesList")

        if (classes != null) {
            val adapter: ArrayAdapter<String> = ArrayAdapter(this, android.R.layout.simple_list_item_1, classes)
            allClassesListView.adapter = adapter

            allClassesListView.setOnItemClickListener { parent, view, position, id ->
                val intent = Intent()
                intent.putExtra("selectedOption", classes[position])
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}