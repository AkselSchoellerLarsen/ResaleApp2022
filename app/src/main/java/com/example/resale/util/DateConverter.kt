package com.example.resale.util

import java.util.*

class DateConverter {
    companion object {
        fun unixDateToJavaDate(unix: Int) : Date {
            return Date(unix.toLong() * 1000)
        }
        fun javaDateToUnixDate(javaDate: Date) : Int {
            return (javaDate.time / 1000).toInt()
        }
    }
}