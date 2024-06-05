package com.example.mixed

data class RealTimeInfo(
        val code: String,
        val name: String,
        val curPrice: Double,
        val yesterdayPrice: Double,
        val time: String,
        val percentage: String) {

    companion object {
        @JvmField
        val defaultValue = RealTimeInfo("unknown", "unknown", -1.0, -1.0, "unknown", "unknown")
    }
}