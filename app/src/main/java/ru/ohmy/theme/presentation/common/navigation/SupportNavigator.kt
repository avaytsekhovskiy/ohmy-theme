package ru.ohmy.theme.presentation.common.navigation

import ru.ohmy.theme.core.ext.logger
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.*

open class SupportNavigator : Navigator {

    val log by logger()

    private val handlers = mapOf<Class<*>, (Command) -> Unit>(
            Forward::class.java to { command -> forward(command as Forward) },
            Replace::class.java to { command -> replace(command as Replace) },
            BackTo::class.java to { command -> backTo(command as BackTo) },
            Back::class.java to { command -> back(command as Back) },
            SystemMessage::class.java to { command -> systemMessage(command as SystemMessage) }
    )

    override fun applyCommand(command: Command) {
        handlers[command.javaClass]
                ?.invoke(command)
                ?: throw UnsupportedOperationException("unknown command")
    }

    open fun forward(forward: Forward) {
        throw UnsupportedOperationException("forward not defined, override forward(Forward) method")
    }

    open fun replace(replace: Replace) {
        throw UnsupportedOperationException("replace not defined, override replace(Replace) method")
    }

    open fun back(back: Back) {
        throw UnsupportedOperationException("back not defined, override back(Back) method")
    }

    open fun backTo(backTo: BackTo) {
        throw UnsupportedOperationException("backTo not defined, override backTo(BackTo) method")
    }

    open fun systemMessage(systemMessage: SystemMessage) {
        throw UnsupportedOperationException("systemMessage not defined, override systemMessage(SystemMessage) method")
    }

}