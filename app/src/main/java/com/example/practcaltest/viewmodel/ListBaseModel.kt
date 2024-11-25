package com.example.practcaltest.viewmodel

import com.example.practcaltest.response.BaseModel
import com.google.gson.annotations.SerializedName

open class ListBaseModel<T>(@SerializedName("results") var results: MutableList<T> = arrayListOf()) :
    BaseModel("Something went wrong") {


}
