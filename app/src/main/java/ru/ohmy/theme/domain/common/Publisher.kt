package ru.ohmy.theme.domain.common

import ru.ohmy.theme.core.ext.debugName
import ru.ohmy.theme.core.ext.logger
import ru.ohmy.theme.core.rx.RxHelper

open class Publisher {
    val rxHelper by lazy { RxHelper() }
    val log by logger()

    init {
        log.info("$debugName created")
    }

    fun stop() {
        log.info("$debugName stopped")
        rxHelper.unsubscribeAll()
    }
}
