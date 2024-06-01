package com.example.mixed

data class RealTimeInfo(
        val code: String,
        val name: String,
        val curPrice: Double,
        val yesterdayPrice: Double,
        val time: String,
        val percentage: String)