package com.noveogroup.template.domain.common

import com.noveogroup.template.core.ext.debugName
import com.noveogroup.template.core.ext.logger
import com.noveogroup.template.core.rx.RxHelper

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
