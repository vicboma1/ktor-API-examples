package com.example.endpoint.utils

import java.io.File

object ResourceUtils {
    fun getResourceAsFile(path: String) = File(System.getProperty("user.dir")+path)
}
