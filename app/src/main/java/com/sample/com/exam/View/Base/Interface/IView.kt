package com.sample.com.exam.View.Base.Interface

interface IView {

    fun showProgressDialog()

    fun dismissProgressDialog()

    fun showToast(message: String)

    fun showErrorToast(message: String)

    fun showErrorDialog(message: String?)

    fun goBack()

}