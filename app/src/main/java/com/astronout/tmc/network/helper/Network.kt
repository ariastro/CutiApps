package com.astronout.tmc.network.helper

import com.astronout.tmc.utils.logDebug
import com.astronout.tmc.utils.logError
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response

class Network(private val coroutineScope: CoroutineScope)  {

    fun <T> request(response: suspend() -> Response<T>, onSuccess:(T?) -> Unit, onError:(String) -> Unit, onFinally:(Boolean) -> Unit) {

        coroutineScope.launch {

            val result = response()
            try {
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) {
                        result.body()?.let {
                            onSuccess(result.body())
                        }
                    } else {
                        var errorMessage = ""
                        try {
                            val jObjError = JSONObject(result.errorBody()?.string())
                            var errorModel = Gson().fromJson(jObjError.getString("message"), ErrorModel.Message::class.java)
                            errorMessage = jObjError.getString("status_code")+": "+errorModel.errors.joinToString()
                        } catch (e: Exception) {
                            logError("Network # Get JSON ERROR : message = $errorMessage", e)
                            errorMessage = "Network # Get JSON ERROR : message = ${e.message}"
                        }

                        logError("Network # errorMessage: $errorMessage")
                        onError("UnSuccessFul # code : $errorMessage")
                    }
                }
            } catch (e: HttpException) {
                logError("Network # HttpException = ${e.message}", e)
                onError("Throwable ${e.message})}")
            } catch (e: Throwable) {
                logError("Network # Throwable = ${e.message}", e)
                onError("Throwable ${e.message})}")
            } finally {
                logDebug("Network # finally")
                onFinally(true)
            }
        }
    }
}