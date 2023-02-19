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
    SENSITIVE_UNHEALTHY("對敏感族群不健康"),
    UNHEALTHY("對所有族群不健康"),
    HIGHLY_UNHEALTHY("非常不健康"),
    HARM("危害"),
    UNKNOWN("");

    companion object {
        fun safeValueOf(rawValue: String?): StatusEnum =
            values().find { it.rawValue == rawValue } ?: UNKNOWN
    }
}
