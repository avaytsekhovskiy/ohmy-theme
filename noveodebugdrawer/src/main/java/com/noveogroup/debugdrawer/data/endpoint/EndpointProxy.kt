package com.noveogroup.debugdrawer.data.endpoint

import com.noveogroup.debugdrawer.Config
import com.noveogroup.debugdrawer.SelectorProvider

class EndpointProxy(private val provider: SelectorProvider) {

    fun readEndpoint(): String = provider.read(Config.SELECTOR_ENDPOINT)

}
