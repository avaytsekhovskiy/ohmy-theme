package com.noveogroup.debugdrawer

object Config {
    const val ENABLER_STETHO = "Stetho"
    const val ENABLER_LEAK = "LeakCanary"

    const val SELECTOR_ENDPOINT = "Endpoint"

    internal val ENDPOINTS = arrayOf(
            "http://staging.noveogroup.com",
            "http://production.noveogroup.com",
            "http://mock.noveogroup.com")
}
