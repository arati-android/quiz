package com.example.practcaltest.webservice

import com.example.practcaltest.response.ObjectBaseModel
import com.example.practcaltest.response.QuestionAnsData
import com.example.practcaltest.response.QuestionAnsResponse
import com.example.practcaltest.viewmodel.ListBaseModel
import retrofit2.Response
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ApiService {
   @GET("api.php")
    suspend fun ListData(
        @QueryMap params: HashMap<String, Any>?
    ): Response<ListBaseModel<QuestionAnsData>>
}
