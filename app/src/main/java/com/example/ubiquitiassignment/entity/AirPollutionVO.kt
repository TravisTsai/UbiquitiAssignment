package com.example.ubiquitiassignment.entity

data class AirPollutionVO(
    val siteId: String,
    val siteName: String,
    val pm25: String,
    val county: String,
    val status: StatusEnum
) {
    fun isGoodStatus(): Boolean = status == StatusEnum.GOOD
}

enum class StatusEnum(val rawValue: String) {
    GOOD("良好"),
    NORMAL("普通"),
    UNHEALTHY("對敏感族群不健康"),
    UNKNOWN("");

    companion object {
        fun safeValueOf(rawValue: String?): StatusEnum =
            values().find { it.rawValue == rawValue } ?: UNKNOWN
    }
}
