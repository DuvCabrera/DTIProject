package com.duv.frontdti.domain

abstract class ReminderExceptions : Exception()

class ReminderNotFoundedException(message: String?) : ReminderExceptions()