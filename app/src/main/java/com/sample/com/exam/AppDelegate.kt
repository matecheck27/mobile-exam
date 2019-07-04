package com.sample.com.exam

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication
import com.sample.com.exam.Dependency.Component.ApplicationComponent
import com.sample.com.exam.Dependency.Component.DaggerApplicationComponent
import com.sample.com.exam.Dependency.Module.ApplicationModule
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.net.ssl.SSLContext

class AppDelegate : MultiDexApplication() {

    private lateinit var activityComponent: ApplicationComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        //for below SDK 20
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()

        buildDaggerDI()

        initRealm()

        createSSLEngine()
    }

    fun getComponent() : ApplicationComponent = activityComponent

    private fun buildDaggerDI() {
        activityComponent = DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()

    }

    private fun initRealm() {
        Realm.init(this)
        val config = RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build() //.encryptionKey(key)

        Realm.setDefaultConfiguration(config)
    }


    private fun createSSLEngine() {
        val sslContext = SSLContext.getInstance("TLSv1.2")
        sslContext.init(null, null, null)
        val engine = sslContext.createSSLEngine()
        engine.enabledProtocols
    }

}