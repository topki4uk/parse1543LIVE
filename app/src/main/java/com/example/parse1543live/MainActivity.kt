package com.example.parse1543live

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.PackageManagerCompat.LOG_TAG
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import serealize.LessonsPerDay
import webUtils.HtmlParser
import webUtils.WebThread
import java.net.URI

class MainActivity : AppCompatActivity() {
    private val uri = URI("https://live.1543.msk.ru/tt/school/")
    private val webThread = WebThread(uri)
    private var parser: HtmlParser? = null
    private var allLessonsMap: Map<String, Map<String, LessonsPerDay>>? = null
    private var allClasses: ArrayList<String>? = null
    private var selectedOption: String = ""

    private val startForResult = registerForActivityResult(StartActivityForResult()) { result: androidx.activity.result.ActivityResult? ->
        if (result != null) {
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                selectedOption = intent?.getStringExtra("selectedOption") as String
            }
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonClass: Button = findViewById<Button>(R.id.buttonChangeClass)

        if (savedInstanceState != null) {
            parser = savedInstanceState.getSerializable("parser", HtmlParser::class.java)
        }

        if (parser == null) {
            webThread.start()
            webThread.join()

            if (webThread.hasExceptions) {
                buttonClass.isEnabled = false
                return
            }

            parser = HtmlParser(webThread.data)
        }

        allLessonsMap = parser!!.allLessons
        allClasses = parser!!.classes as ArrayList<String>
    }

    @SuppressLint("RestrictedApi")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("option", selectedOption)
        outState.putSerializable("parser", parser)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        parser = savedInstanceState.getSerializable("parser", HtmlParser::class.java)
        selectedOption = savedInstanceState.getString("option") as String
    }

    override fun onResume() {
        super.onResume()

        setContentView(R.layout.activity_main)

        val buttonClass: Button = findViewById<Button>(R.id.buttonChangeClass)
        val root = findViewById<LinearLayout>(R.id.root_layout)

        if (selectedOption != "") {
            val allLessons4class = allLessonsMap?.get(selectedOption)

            if (allLessons4class != null) {

                for (day in HtmlParser.days) {
                    val card = CardView(this)
                    val title = TextView(card.context)
                    title.text = day

                    val listView = RecyclerView(card.context)
                    val adapter = UserAdapter(allLessons4class[day]?.lessons!!)

                    val manager = object : LinearLayoutManager(card.context) {
                        override fun canScrollVertically(): Boolean {
                            return false
                        }
                    }

                    listView.adapter = adapter
                    listView.layoutManager = manager

                    card.addView(title)
                    card.addView(listView)
                    root.addView(card)
                }
            }
        }

        buttonClass.setOnClickListener {
            val intent = Intent(this@MainActivity, AllClassesActivity::class.java)
            intent.putExtra("classesList", allClasses)
            startForResult.launch(intent)
        }
    }
}
