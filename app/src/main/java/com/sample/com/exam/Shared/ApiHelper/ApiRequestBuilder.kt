package com.sample.com.exam.Shared.ApiHelper

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class ApiRequestBuilder {

    companion object {

        fun <T> build(observable: Observable<T>,
                      onTerminate: ()-> Unit,
                      onSubscribe: ()-> Unit,
                      onSuccess: (t: T)-> Unit,
                      onExceptionResumeNext: (t: Observer<in T>)-> Unit,
                      onError: (e: Throwable)-> Unit) : Disposable  {

            return observable.observeOn(AndroidSchedulers.mainThread())
                    //.retry(1)
                    .doOnSubscribe { onSubscribe() }
                    .doOnTerminate { onTerminate() }
                    //.onExceptionResumeNext { onExceptionResumeNext(it) }
                    .subscribe({
                        onSuccess(it)
                    }, {
                        decodeError(it, onError)
                    })
        }

        private fun decodeError(e: Throwable, onError: (e: Throwable) -> Unit) {

            when (e) {
                is SocketTimeoutException -> {
                    //timeout error
                    onError(Throwable("Socket Timeout"))
                }

                is retrofit2.HttpException -> {
                    //http exception
                    onError(Throwable(e.response().errorBody()?.string()))
                }

                is HttpException -> {
                    //unknown error
                    onError(Throwable("HTTP Exception"))
                }

                is IOException -> {
                    //network error
                    onError(Throwable("IO Exception"))
                }

                else -> {
                    //unknown error
                    onError(Throwable(e.localizedMessage))
                }
            }
        }
    }
}