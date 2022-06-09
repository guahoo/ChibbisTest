package com.testTask.chibbistest.data.network

import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException

var errorMessage: String = ""

sealed class AppResult<out T> {
    data class Success<out T>(val successData: T) : AppResult<T>()

    open class Error(open var exception: java.lang.Exception, open var message: String? = exception.localizedMessage)
        : AppResult<Nothing>(){
    }

}

class APIError {
    private val statusCode = 0
    val message: String? = null
    fun status(): Int {
        return statusCode
    }

    fun message(): String? {
        return message
    }
}



fun <T : Any> handleApiError(resp: Response<T>): AppResult.Error {
    val error = ApiErrorUtils.parseError(resp)
    println("Эксептион " + Exception(error.message))

    errorMessage = Exception(error.message).toString()

    return AppResult.Error(Exception(error.message))
}


fun <T : Any> handleSuccess(response: Response<T>): AppResult<T> {
    response.body()?.let {
        return AppResult.Success(it)
    } ?: return handleApiError(response)
}


val Any.TAG: String
    get() {
        val tag = javaClass.simpleName
        return if (tag.length <= 23) tag else tag.substring(0, 23)
    }

fun Context.noNetworkConnectivityError(): AppResult.Error {
    return AppResult.Error(Exception(("Ошибка сети")))
}

object ApiErrorUtils {

    fun parseError(response: Response<*>): APIError {

        val gson = GsonBuilder().create()
        val error: APIError

        try {
            error = gson.fromJson(response.errorBody()?.string(), APIError::class.java)
        } catch (e: IOException) {
            e.message?.let { Log.d(ContentValues.TAG, it) }
            return APIError()
        }
        return error
    }


}

