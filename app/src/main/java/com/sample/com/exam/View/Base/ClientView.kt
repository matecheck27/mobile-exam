package com.sample.com.exam.View.Base

import android.content.Intent
import android.os.Bundle

abstract class ClientView: BaseView() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // set view
        setContentView(getContentView())
    }

    abstract fun getContentView(): Int

    fun gotoActivity(intent: Intent){
        startActivity(intent)
    }
}