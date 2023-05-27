package com.duv.frontdti.domain

abstract class ReminderExceptions(message: String?) : Exception(message)

class ReminderNotFoundedException(message: String?) : ReminderExceptions(message)

class CreateReminderException(message: String?) : ReminderExceptions(message)

class UpdateReminderException(message: String?) : ReminderExceptions(message)