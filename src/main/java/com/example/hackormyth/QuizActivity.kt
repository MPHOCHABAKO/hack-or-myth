package com.example.hackormyth

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class QuizActivity : AppCompatActivity() {

    private val questionBank = arrayOf(
        Question(
            "Drinking water first thing in the morning boosts metabolism.",
            true,
            "Hydration can slightly support metabolism and improve energy balance."
        ),
        Question(
            "Cracking your knuckles can cause arthritis.",
            false,
            "Studies have not shown that cracking knuckles can cause arthritis."
        ),
        Question(
            "Putting a wet phone in rice fully repairs water damage.",
            false,
            "Rice may absorb moisture, but it does not properly repair internal damage."
        ),
        Question(
            "Using strong passwords improves online security.",
            true,
            "Strong passwords make it harder for hackers to access accounts."
        ),
        Question(
            "Chocolate directly causes acne.",
            false,
            "Acne is more influenced by hormones and skin care than chocolate alone."
        )
    )

    private var currentIndex = 0
    private var score = 0
    private val userAnswers = mutableListOf<Boolean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        updateQuestion()

        findViewById<Button>(R.id.trueButton).setOnClickListener {
            checkAnswer(true)
        }
        findViewById<Button>(R.id.falseButton).setOnClickListener {
            checkAnswer(false)
        }

        findViewById<Button>(R.id.nextButton).setOnClickListener {
            currentIndex++
            if (currentIndex < questionBank.size) {
                updateQuestion()
            } else {
                val intent = Intent(this, ScoreActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("totalQuestions", questionBank.size)
                intent.putExtra("questions", questionBank)
                intent.putExtra("userAnswers", userAnswers.toBooleanArray())
                startActivity(intent)
                finish()
            }
        }
    }

    private fun updateQuestion() {
        val questionTextView = findViewById<TextView>(R.id.questionText)
        val feedbackText = findViewById<TextView>(R.id.feedbackText)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val trueButton = findViewById<Button>(R.id.trueButton)
        val falseButton = findViewById<Button>(R.id.falseButton)

        questionTextView.text = questionBank[currentIndex].text
        
        // Reset UI for the new question
        trueButton.isEnabled = true
        falseButton.isEnabled = true
        feedbackText.visibility = View.GONE
        nextButton.visibility = View.GONE
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val currentQuestion = questionBank[currentIndex]
        val correctAnswer = currentQuestion.isHack
        userAnswers.add(userAnswer)

        val feedbackText = findViewById<TextView>(R.id.feedbackText)
        val nextButton = findViewById<Button>(R.id.nextButton)
        val trueButton = findViewById<Button>(R.id.trueButton)
        val falseButton = findViewById<Button>(R.id.falseButton)

        // Disable buttons after selection to prevent multiple answers
        trueButton.isEnabled = false
        falseButton.isEnabled = false

        val isCorrect = userAnswer == correctAnswer
        if (isCorrect) {
            score++
        }

        // Prepare feedback message with emoji and explanation
        val resultEmoji = if (isCorrect) "✅" else "❌"
        val resultTitle = if (isCorrect) "Correct!" else "Incorrect!"
        val factType = if (correctAnswer) "It's a HACK." else "It's a MYTH."
        
        feedbackText.text = "$resultEmoji $resultTitle $factType\n\n${currentQuestion.explanation}"
        feedbackText.setTextColor(if (isCorrect) Color.parseColor("#4CAF50") else Color.parseColor("#F44336"))
        
        // Show explanation and the Next button
        feedbackText.visibility = View.VISIBLE
        nextButton.visibility = View.VISIBLE
    }
}
