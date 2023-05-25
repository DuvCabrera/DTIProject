package com.duv.frontdti.domain.model

import java.util.Date

data class Reminder(
    val id: Int?,
    val date: Date,
    val description: String
)
