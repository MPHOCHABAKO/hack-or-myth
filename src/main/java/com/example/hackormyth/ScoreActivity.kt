package com.example.hackormyth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // UI elements
        val scoreText = findViewById<TextView>(R.id.scoreText)
        val messageText = findViewById<TextView>(R.id.messageText)
        val reviewButton = findViewById<Button>(R.id.reviewButton)
        val restartButton = findViewById<Button>(R.id.restartButton)

        val score = intent.getIntExtra("score", 0)
        val totalQuestions = intent.getIntExtra("totalQuestions", 0)
        val questions = IntentCompat.getSerializableExtra(intent, "questions", Array<Question>::class.java)
        val userAnswers = intent.getBooleanArrayExtra("userAnswers")
        
        val feedbackText = "You scored $score out of $totalQuestions"
        scoreText.text = feedbackText

        // Personalized feedback
        val feedbackMessage = when {
            score == totalQuestions -> "🏆 Master Hacker!"
            score >= totalQuestions / 2 -> "😎 Sharp Thinker!"
            else -> "⚠️ Stay Safe Online!"
        }
        messageText.text = feedbackMessage

        // Review Answers Button
        reviewButton.setOnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra("questions", questions)
            intent.putExtra("userAnswers", userAnswers)
            startActivity(intent)
        }

        // Restart Quiz Button
        restartButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }
}
