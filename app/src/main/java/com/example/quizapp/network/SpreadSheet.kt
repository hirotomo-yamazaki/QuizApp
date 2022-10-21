package com.example.quizapp.network

import com.example.quizapp.Constants
import com.example.quizapp.models.SpreadSheetResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpreadSheet {
    @GET("{sheet_id}/values/{sheet_name}")
    fun getQuiz(
        @Path("sheet_id") sheet_id: String = Constants.SPREADSHEET_ID,
        @Path("sheet_name") sheet_name: String = Constants.SHEET_NAME,
        @Query("key") api_key: String = Constants.API_KEY
    ): Call<SpreadSheetResponse>
}