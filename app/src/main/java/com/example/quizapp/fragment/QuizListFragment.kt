package com.example.quizapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.quizapp.Constants
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizListBinding
import com.example.quizapp.models.SpreadSheetResponse
import com.example.quizapp.network.SpreadSheet
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class QuizListFragment : Fragment() {

    private var _binding: FragmentQuizListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuizListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getSpreadsheetDetail()

        binding.makeQuizButton.setOnClickListener {
            findNavController().navigate(R.id.action_quizListFragment_to_makeQuizFragment)
        }
    }

    //Retrofitを利用し、SpreadSheetからデータを取得
    private fun getSpreadsheetDetail() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: SpreadSheet = retrofit.create(SpreadSheet::class.java)

        val listCall: Call<SpreadSheetResponse> = service.getQuiz()

        listCall.enqueue(object : Callback<SpreadSheetResponse> {
            override fun onFailure(call: Call<SpreadSheetResponse>, t: Throwable) {
                Log.e("Errorrrrr", t.message.toString())
            }

            override fun onResponse(
                call: Call<SpreadSheetResponse>,
                response: Response<SpreadSheetResponse>
            ) {
                if (response.isSuccessful) {
                    val quizList: SpreadSheetResponse = response.body()!!

                    setupUI(quizList)

                    Log.i("Response", "$quizList")
                } else {
                    Log.e("Error", "${response.code()}")
                }
            }
        })
    }

    private fun setupUI(quizList: SpreadSheetResponse) {
        val quiz = mutableListOf<String>()
        for (i in quizList.values.indices){
            if(i > 0){
                quiz += listOf(quizList.values[i][0])
            }
        }
        binding.lvQuiz.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            quiz
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}