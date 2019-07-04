package com.sample.com.exam.Shared

import org.modelmapper.ModelMapper

class MapperHelper {

    companion object {
        fun <T> ConvertModelTo(model: Any, clazz: Class<T>) : T {
            return ModelMapper().map(model, clazz)
        }
    }
}