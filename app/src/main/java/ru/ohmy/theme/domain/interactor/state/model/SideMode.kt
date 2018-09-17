package ru.ohmy.theme.domain.interactor.state.model

@Suppress("unused")
enum class SideMode(
        val opened: Boolean = false,
        val locked: Boolean = true
) {
    OPENED_LOCKED(true, true),
    OPENED_JUST(true, false),
    DISABLED(false, true),
    CLOSED(false, false);

    fun toggleLock(locked: Boolean = !this.locked) = state(opened, locked)

    fun toggle(opened: Boolean = !this.opened) = state(opened, locked)

    companion object {
        fun state(opened: Boolean, locked: Boolean) = when {
            opened && locked -> OPENED_LOCKED
            opened -> OPENED_JUST
            locked -> DISABLED
            else -> CLOSED
        }
    }
}

