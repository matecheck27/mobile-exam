package com.sample.com.exam.View.Base

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.sample.com.exam.AppDelegate
import com.sample.com.exam.Dependency.Component.ApplicationComponent
import com.sample.com.exam.Presenter.Base.Interface.IPresenter
import com.sample.com.exam.R
import com.sample.com.exam.View.Base.Interface.IView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.lang.Exception
import java.util.concurrent.TimeUnit

abstract class BaseView: AppCompatActivity(), IView {

    private var presenter: IPresenter? = null
    private var progressDialog: Dialog? = null
    private var mCompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        //inject presenter
        inject((application as AppDelegate).getComponent())

        //get injected presenter from child class
        presenter = getPresenter()

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        hideKeyBoard()
        return super.onTouchEvent(event)
    }

    override fun onResume() {
        super.onResume()

        initProgressDialog()

        //bind to presenter
        presenter?.bindView(this)

        //View did load after binding
        onLoad()

    }

    override fun onPause() {
        super.onPause()

        //close progress
        dismissProgressDialog()

        //unbind
        presenter?.unBindView()

        //dispose
        mCompositeDisposable.clear()

    }

    override fun showErrorDialog(message: String?) {
        showErrorToast(message ?: "")
    }

    private fun addDisposables(disposable: Disposable) {

        if(mCompositeDisposable.isDisposed) mCompositeDisposable = CompositeDisposable()

        mCompositeDisposable.add(disposable)
    }

    private fun hideKeyBoard() {
        try {
            val inputMethodManager: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            var view = currentFocus
            if (view == null) view = View(this)

            if (inputMethodManager.isActive) {
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
                overridePendingTransition(0, 0)
            }
        }catch (e: Exception){}
    }

    private fun initProgressDialog() {
        progressDialog = Dialog(this)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setTitle("Loading")
        progressDialog!!.setContentView(R.layout.layout_progress)
        progressDialog!!.setCancelable(false)
        progressDialog!!.window.setDimAmount(0.2f)
    }

    override fun showProgressDialog() {
        if(progressDialog?.isShowing != true) progressDialog!!.show()
    }

    override fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showErrorToast(message: String) {
        showToast(message)
    }

    fun runWithDelay(delayInSec: Long, action: () -> Unit) {
        addDisposables(Observable.timer(delayInSec, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
            action()
        })
    }

    override fun goBack() {
        finish()
    }

    abstract fun onLoad()

    abstract fun getPresenter() : IPresenter?

    abstract fun inject(applicationComponent: ApplicationComponent)

}