package com.example.mixed

object PrintUtil {

    @JvmStatic
    fun body(info: RealTimeInfo): String {
        return "%-8s %-8s %-8.2f %-8s %-12s"
                .format(info.code, info.name, info.curPrice, info.percentage, info.time)
    }
}