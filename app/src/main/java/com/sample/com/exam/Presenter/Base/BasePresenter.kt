package com.sample.com.exam.Presenter.Base

import com.sample.com.exam.Presenter.Base.Interface.IPresenter
import com.sample.com.exam.Shared.ApiHelper.ApiRequestBuilder
import com.sample.com.exam.View.Base.Interface.IView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.ref.WeakReference

abstract class BasePresenter<T>: IPresenter {

    private var mView: WeakReference<IView>? = null
    private var mCompositeDisposable = CompositeDisposable()

    override fun bindView(view: IView) {
        mView = WeakReference(view)
    }

    override fun unBindView() {
        disposeAll()
    }

    protected fun getView() : T? {
        return if(mView?.get() is IView) mView?.get() as T
        else null
    }

    protected fun getBaseView() : IView? {
        return mView?.get()
    }

    private val onErrorDefault = fun(e: Throwable) {
        getBaseView()?.showErrorDialog(null)
    }

    private val onTerminateDefault = fun() {
        getBaseView()?.dismissProgressDialog()
    }

    private val onSubscribeDefault = fun() {
        getBaseView()?.showProgressDialog()
    }

    private val onRollbackDefault = fun() {
        getBaseView()?.dismissProgressDialog()
    }

    protected fun <G> buildAPI(observable: Observable<G>, onSuccess: (t: G)-> Unit,
                               onError: (e: Throwable)-> Unit = onErrorDefault,
                               onRollback: ()-> Unit = onRollbackDefault,
                               onTerminate: ()-> Unit = onTerminateDefault,
                               onSubscribe: ()-> Unit = onSubscribeDefault) {
        val disposable = ApiRequestBuilder.build(
                observable,
                onTerminate = {
                    onTerminate()
                },
                onSubscribe = {
                    onSubscribe()
                },
                onSuccess = { it ->
                    onSuccess(it)
                },
                onError = {
                    onRollback()
                    onError(it)
                    getBaseView()?.dismissProgressDialog()
                },
                onExceptionResumeNext = {

                }
        )

        addDisposables(disposable)
    }

    private fun addDisposables(disposable: Disposable) {

        if(mCompositeDisposable.isDisposed) mCompositeDisposable = CompositeDisposable()

        mCompositeDisposable.add(disposable)
    }

    private fun disposeAll() {
        mCompositeDisposable.dispose()
        mView = null
    }


}