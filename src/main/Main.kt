package main

import engine.StartupUtilities.start

object Main {
    @JvmField
    var debug = false

    @JvmStatic
    fun main(args: Array<String>) {
        start()
    }
}