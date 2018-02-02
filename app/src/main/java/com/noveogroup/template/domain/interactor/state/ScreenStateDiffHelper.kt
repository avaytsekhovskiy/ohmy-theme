package com.noveogroup.template.domain.interactor.state

import com.noveogroup.template.domain.interactor.state.model.*

class ScreenStateDiffHelper(
        private val current: ScreenState,
        private val previous: ScreenState?
) {

    fun ifTitleChanged(listener: (String) -> Unit) = compare({ it.title }, stringsContentEquals, listener)

    fun ifToggleChanged(listener: (Toggle) -> Unit) = compare({ it.toggle }, objectsEquals, listener)

    fun ifToolbarMenuChanged(listener: (ToolbarMenu) -> Unit) = compare({ it.toolbarMenu }, objectsEquals, listener)

    fun ifPageModeChanged(listener: (PageMode) -> Unit) = compare({ it.pageMode }, objectsEquals, listener)

    fun ifSideModeChanged(listener: (SideMode) -> Unit) = compare({ it.sideMode }, objectsEquals, listener)

    private fun <T> compare(
            extractor: (ScreenState) -> T,
            comparator: (T, T) -> Boolean,
            listener: (T) -> Unit
    ) {
        val currentField = extractor(current)
        when {
        //1. if there was no value - set currentActivityScope as initial
            previous == null -> listener(currentField)

        //2. if a previous value isn't the same as currentActivityScope - notify update
            !comparator(currentField, extractor(previous)) -> listener(currentField)
        }
    }

    private companion object {
        val objectsEquals: (Any, Any) -> Boolean = { a, b -> a == b }
        val stringsContentEquals: (String, String) -> Boolean = String::contentEquals
    }
}
