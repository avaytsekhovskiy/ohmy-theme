package com.noveogroup.template.presentation.di.module.main

import toothpick.config.Module

class MainModule(name: String) : Module() {
    init {
        //Additional parameters wide bindings example
        bind(String::class.java).withName(MainActivityName::class.java).toInstance(name)
    }
}
