package com.example.practcaltest.response

import com.google.gson.annotations.SerializedName

open class ObjectBaseModel<T>(@SerializedName("data") var data: T?) :
    BaseModel( "Something went wrong")

open class BaseModel(
    @SerializedName("message") var message: String = ""
)

