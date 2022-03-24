package com.example.rugbyApp

import com.example.rugbyinfo.Platform

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}