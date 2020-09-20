package com.example.mertkesgin.footballapp.base

import androidx.lifecycle.ViewModel
import com.example.mertkesgin.footballapp.utils.Resource
import retrofit2.Response
import timber.log.Timber
import java.lang.Exception

abstract class BaseViewModel : ViewModel() {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.Success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Timber.d(message)
        return Resource.Error("Network call has failed.-> ${message}")
    }
}