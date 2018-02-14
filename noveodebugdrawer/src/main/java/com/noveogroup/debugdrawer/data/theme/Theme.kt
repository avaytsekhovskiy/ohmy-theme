package com.noveogroup.debugdrawer.data.theme

enum class Theme {
    BASE_LIGHT,
    BASE_DARK,
    GREEN_LIGHT,
    GREEN_DARK;

    companion object {
        fun byId(name: String): Theme {
            return values().find { name.contentEquals(it.toString()) } ?: BASE_LIGHT
        }
    }
}