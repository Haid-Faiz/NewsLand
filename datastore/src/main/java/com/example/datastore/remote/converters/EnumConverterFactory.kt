package com.example.datastore.remote.converters

import com.squareup.moshi.Json
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.Exception
import java.lang.reflect.Type

class EnumConverterFactory : Converter.Factory() {

    override fun stringConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
//        return super.stringConverter(type, annotations, retrofit)

        return if (type is Class<*> && type.isEnum) {
            Converter<Enum<*>, String> {
                try {
                    it.javaClass.getField(it.name).getAnnotation(Json::class.java).name
                } catch (e: Exception) {
                    null
                }
            }
        } else null
    }
}
