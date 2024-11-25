package com.example.practcaltest.response

data class QuestionAnsResponse(
    val response_code: Int,
    val results: List<QuestionAnsData>
)

data class QuestionAnsData(
    val category: String,
    val correct_answer: String,
    val difficulty: String,
    val incorrect_answers: List<String>,
    val question: String,
    val type: String
)