package com.example.mixed

object PrintUtil {

    @JvmStatic
    fun body(info: RealTimeInfo): String {
        return listOf(
                padString(info.code, 8),
                padString(info.name, 12),
                padString(info.curPrice.toString(), 8),
                padString(info.percentage, 8),
                padString(info.time, 14),
                padString(info.pe, 6))
                .joinToString("")
    }

    private fun getDisplayWidth(s: String): Int {
        // 中文字符通常占用两个字符的宽度，非中文字符占用一个字符的宽度
        return s.toCharArray().sumBy { if (it.toInt() > 0xFF) 2 else 1 }
    }

    private fun padString(s: String, width: Int): String {
        val paddingWidth = width - getDisplayWidth(s)
        return if (paddingWidth > 0) s + " ".repeat(paddingWidth) else s
    }
}