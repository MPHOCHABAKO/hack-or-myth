package com.example.hackormyth

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_review)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val questions = IntentCompat.getSerializableExtra(intent, "questions", Array<Question>::class.java)
        val userAnswers = intent.getBooleanArrayExtra("userAnswers")
        val container = findViewById<LinearLayout>(R.id.reviewContainer)

        questions?.forEachIndexed { index, question ->
            val userAnswer = userAnswers?.getOrNull(index) ?: false
            val isCorrect = userAnswer == question.isHack

            val itemView = layoutInflater.inflate(android.R.layout.simple_list_item_2, container, false)
            val text1 = itemView.findViewById<TextView>(android.R.id.text1)
            val text2 = itemView.findViewById<TextView>(android.R.id.text2)

            text1.text = "Q${index + 1}: ${question.text}"
            text1.setTextColor(resources.getColor(android.R.color.white, null))

            val resultText = if (isCorrect) "Correct" else "Incorrect"
            text2.text = "$resultText - ${question.explanation}"
            text2.setTextColor(if (isCorrect) resources.getColor(android.R.color.holo_green_light, null) else resources.getColor(android.R.color.holo_red_light, null))

            container.addView(itemView)
        }

        findViewById<Button>(R.id.backButton).setOnClickListener {
            finish()
        }
    }
}
