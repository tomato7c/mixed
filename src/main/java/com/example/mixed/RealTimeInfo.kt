package com.example.mixed

data class RealTimeInfo(
        val code: String,
        val name: String,
        val curPrice: Double,
        val yesterdayPrice: Double,
        val time: String,
        val percentage: String,
        val pe: String) {

    companion object {
        @JvmField
        val defaultValue = RealTimeInfo("any", "any", -1.0, -1.0, "any", "any", "any")
    }
}