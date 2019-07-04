package com.sample.com.exam.Presenter.Base.Interface

import com.sample.com.exam.View.Base.Interface.IView

interface IPresenter {

    fun bindView(view: IView)

    fun unBindView()

}