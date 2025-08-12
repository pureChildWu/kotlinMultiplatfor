package com.ax.tototoproj

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform