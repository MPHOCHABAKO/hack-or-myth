package com.example.hackormyth

import java.io.Serializable

data class Question(
    val text: String,
    val isHack: Boolean,
    val explanation: String
) : Serializable
