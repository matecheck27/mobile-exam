package com.sample.com.exam.Model.Repository.Local.Base

/**
 * Created by mark on 20/03/2018.
 */
interface IBaseData <T> {

    fun insert(t: T)

    fun update(t: T)

    fun delete(t: T)

    fun deleteAll()

    fun getAll() : List<T>?

    fun getFirst() : T?

    fun queryAll(fieldName: String, value: String) : List<T>

    fun queryFirst(fieldName: String, value: Int) : T
}