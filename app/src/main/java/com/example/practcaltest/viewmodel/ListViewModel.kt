package com.example.practcaltest.viewmodel

import ApiClient
import android.util.Log
import com.example.practcaltest.response.QuestionAnsData

class ListViewModel: BaseViewModel() {
    fun callListApi( param: HashMap<String, Any>,response: (MutableList<QuestionAnsData>) -> Unit) {
        callApiAndShowDialog(call = {
            ApiClient.getClient().ListData(param)
        }, handleSuccess = { data ->
            data.results.let {
                Log.e("datata2>>>>>",it.toString())
                response.invoke(it)
            }
        }, handleGeneric = {

        }, showDialg = true)
    }



}