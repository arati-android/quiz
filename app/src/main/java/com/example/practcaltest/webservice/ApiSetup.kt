package com.example.practcaltest.webservice


import android.util.Log
import com.example.practcaltest.response.BaseModel
import com.google.gson.Gson
import com.intuit.sdp.BuildConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class NoContent(val error: String? = null) : ResultWrapper<Nothing>()
    data class GenericError(val error: String? = null) : ResultWrapper<Nothing>()
    data class ServerError(val error: String? = null) : ResultWrapper<Nothing>()
    data class NetworkError(val error: String?) : ResultWrapper<Nothing>()
    data class SessionExpiredError(val error: String?) : ResultWrapper<Nothing>()
}

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> Response<T>
): ResultWrapper<Response<T>> {
    return withContext(dispatcher) {
        try {
            val result = apiCall.invoke()
            if (BuildConfig.DEBUG) {
                Log.e("ResultWrapper", Gson().toJson(result.body()))
            }
            when (result.code()) {
                200, 201, 204 -> {
                    Log.e("response>>>",result.message())
                    ResultWrapper.Success(result)
                    val base = Gson().fromJson(
                        Gson().toJson(result.body()),
                        BaseModel::class.java
                    )
                    when (result.code()) {
                        200, 201, 204 -> {
                            // success
                            Log.e("response>>>",result.message())
                            ResultWrapper.Success(result)
                        }
                        else -> {
                            ResultWrapper.GenericError(base.message)
                        }
                    }
                }
                /*   201 -> {
                       // No Content
                       ResultWrapper.NoContent(null)
                   }
                   204 -> {
                       // Success but empty data (This can handle under 200 as well)
                       ResultWrapper.Success(result)
                   }*/
                400 -> {
                    val errorString = result.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }
                    val base = Gson().fromJson(
                        errorString,
                        BaseModel::class.java
                    )
                    // Bad Request / Error(The request was invalid.)
                    ResultWrapper.GenericError(base.message)
//                    ResultWrapper.GenericError(errorBody.message)
                }
                401 -> {
                    val errorString = result.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }
                    val base = Gson().fromJson(
                        errorString,
                        BaseModel::class.java
                    )
                    //Unauthorized / Authentication Error
                    ResultWrapper.SessionExpiredError(base.message)
                }
                403 -> {
//                    Forbidden
                    ResultWrapper.GenericError("")
                }
                404 -> {
                    // Not Found
/*                    val errorBody = Gson().fromJson(
                        result.errorBody()?.string(),
                        BaseResponse::class.java
                    )*/
                    ResultWrapper.GenericError("")
                }
                405 -> {
//                    Method Not Allowed / Wrong Request
                    /* val errorBody = Gson().fromJson(
                         result.errorBody()?.string(),
                         BaseResponse::class.java
                     )*/
                    ResultWrapper.GenericError("")
                }
                500, 501, 502, 503, 504 -> {
                    // internal server error
                    ResultWrapper.ServerError(result.message())
                }
                else -> {
/*                    val errorBody = Gson().fromJson(
                        result.errorBody()?.string(),
                        BaseResponse::class.java
                    )*/
                    ResultWrapper.GenericError(result.errorBody()?.string())
                }
            }

        } catch (throwable: Throwable) {
            Log.e("Rk", throwable.message.toString())
            ResultWrapper.NetworkError(throwable.message)
        }
    }
}

