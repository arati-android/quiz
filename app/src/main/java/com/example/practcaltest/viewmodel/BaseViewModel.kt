package com.example.practcaltest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.practcaltest.R
import com.example.practcaltest.comman.Controller
import com.example.practcaltest.comman.Utils
import com.example.practcaltest.webservice.ResultWrapper
import com.example.practcaltest.webservice.safeApiCall
import com.example.practcaltest.dialog.DialogUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val job = Job()

    protected fun <T> callApiAndShowDialog(
        call: suspend () -> Response<T>,
        handleSuccess: (T) -> Unit,
        handleGeneric: (String) -> Unit = {},
        handleNetwork: (String) -> Unit = {},
        handleNoContent: (String) -> Unit = {},
        handleSessionExpiredError: (String) -> Unit = {},
        showDialg: Boolean = true,
        handledGenerinc: Boolean = false
    ) {
        launch {
            if (Utils.hasInternet(Controller.instance.applicationContext)) {
                if (showDialg) Controller.instance.context?.showProgress()
                val result = safeApiCall(Dispatchers.IO) {
                    call.invoke()
                }

                if (showDialg) Controller.instance.context?.hideProgress()

                when (result) {
                    is ResultWrapper.NoContent -> {
                        handleNoContent(result.error ?: "Something went wrong")
                    }
                    is ResultWrapper.NetworkError -> {
                        handleNetwork(result.error ?: "Something went wrong")
                    }
                    is ResultWrapper.GenericError -> {
                        if (handledGenerinc) {
                            handleGeneric(result.error ?: "Something went wrong")
                        } else {
//                            showMessagePopUp(result.error)
                            handleGeneric(result.error ?: "Something went wrong")
                            Controller.instance.context?.let {
                                DialogUtils.showCustomDialogWithButton(
                                    it,
                                    isAlert = true,
                                    message = result.error ?: "Something went wrong"
                                ) {
                                }
                            }
                        }
                    }

                    is ResultWrapper.Success -> {
                        result.value.body()?.let {
                            handleSuccess(it)
                        }
                    }
                    is ResultWrapper.ServerError -> {
                        Controller.instance.context?.let {
                            DialogUtils.showCustomDialogWithButton(
                                it,
                                isAlert = true,
                                message = it.getString(R.string.something_went_wrong)
                                    ?: "Something went wrong"
                            ) {
                                it.onBackPressedDispatcher.onBackPressed()
                            }
                        }
                    }
                    is ResultWrapper.SessionExpiredError -> {
                        Controller.instance.context?.let {
                            DialogUtils.showCustomDialogWithButton(
                                it,
                                isAlert = true,
                                message = result.error ?: "Something went wrong"
                            ) {
//                                AppPreferences.isLogin = false
//                                AppPreferences.clearPref()
                              //  Controller.instance.context?.sessionExpired()
                            }
                        }
//                        sessionExpired()
                    }
                    else -> {
                        handleGeneric("Something went wrong")
                    }
                }
            }
        }
    }
}
