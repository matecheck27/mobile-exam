package com.sample.com.exam.Model.Repository.Local.Base

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject

abstract class BaseRealm {

    private var mRealm: Realm

    constructor(realm: Realm) {
        mRealm = realm
    }

    protected fun Insert(it: RealmModel) {
        mRealm.executeTransaction({ realm ->
            realm.insert(it)
        })
    }

    protected fun Replace(it: RealmModel, clazz: Class<out RealmModel>) {
        mRealm.executeTransaction({ realm ->
            //delete
            val query = realm.where(clazz).findAll()
            query.deleteAllFromRealm()

            //insert
            realm.insert(it)
        })
    }

    fun Delete(clazz: Class<out RealmModel>, columnName: String, value: String) {
        mRealm.executeTransaction({ realm ->
            val query = realm.where(clazz).equalTo(columnName, value).findAll()
            query.deleteAllFromRealm()
        })
    }

    fun Delete(model: RealmObject) {
        mRealm.executeTransaction({ realm ->
            model.deleteFromRealm()
            realm.commitTransaction()
        })
    }

    protected fun InsertAll(it: Collection<RealmModel>, clazz: Class<out RealmModel>) {
        mRealm.executeTransaction({ realm ->
            //realm.delete(clazz)
            realm.insert(it)
        })
    }

    protected fun <T> GetAll(clazz: Class<out RealmModel>): List<T>? {
        val items = mRealm.where(clazz).findAll()
        if(!items.isEmpty()) {
            return mRealm.copyFromRealm(items) as List<T>
        }
        return null
    }

    fun <T> GetFirst(clazz: Class<out RealmModel>): T? {
        val user = mRealm.where(clazz).findFirst()

        if(user != null) {
            var result = mRealm.copyFromRealm(user)
            return (result as T?)
        }

        return null
    }

    protected fun DeleteAll(clazz: Class<out RealmModel>) {
        mRealm.executeTransaction({
            realm -> realm.delete(clazz)
        })
    }

    protected fun CreateObject(clazz: Class<out RealmModel>): RealmModel {
        return mRealm.createObject(clazz)
    }

    protected fun <T> Query(clazz: Class<out RealmModel>, fieldName: String, value: String) : List<T> {
        val items = mRealm.where(clazz).equalTo(fieldName, value).findAll()
        if(items != null) {
            return mRealm.copyFromRealm(items) as List<T>
        }
        return items as List<T>
    }

    protected fun <T> QueryFirst(clazz: Class<out RealmModel>, fieldName: String, value: String) : T {
        val items = mRealm.where(clazz).equalTo(fieldName, value).findFirst()
        return mRealm.copyFromRealm(items) as T
    }
}