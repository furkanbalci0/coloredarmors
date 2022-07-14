package com.furkan.coloredarmors.data

import java.util.*

class DataManager {

    companion object {
        var players: MutableSet<UUID> = TODO()
            get() {
                return field.toMutableSet()
            }

        fun initialize() {
            players = mutableSetOf()
        }
    }
}