package com.example.mixed.qt

import com.example.mixed.RealTimeInfo
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.nio.charset.Charset
import java.text.SimpleDateFormat

class QtConverterFactory: Converter.Factory() {

    override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit): Converter<ResponseBody, *> {
        return when(type) {
            RealTimeInfo::class.java -> QtConverter()
            else -> { throw RuntimeException(type.toString()) }
        }
    }
}

class QtConverter : Converter<ResponseBody, RealTimeInfo> {
    val formatA = SimpleDateFormat("yyyyMMddHHmmss")
    val formatB = SimpleDateFormat("MM-dd HH:mm")
    override fun convert(response: ResponseBody): RealTimeInfo {
        val arr = String(response.bytes(), Charset.forName("GBK")).split("~")
        val timeA = formatA.parse(arr[30])
        val timeB = formatB.format(timeA)
        return RealTimeInfo(code = arr[2], name = arr[1], curPrice = arr[3].toDouble(),
                yesterdayPrice = arr[4].toDouble(), time = timeB, percentage = arr[32], pe = arr[39])
    }
}