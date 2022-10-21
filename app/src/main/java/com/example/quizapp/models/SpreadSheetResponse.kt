package com.example.quizapp.models

data class SpreadSheetResponse(
    val majorDimension: String,
    val range: String,
    val values: List<List<String>>
):java.io.Serializable