@file:Suppress("unused")

import android.app.Application
import com.noveogroup.template.core.ext.logger
import io.reactivex.exceptions.CompositeException
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.exceptions.UndeliverableException
import io.reactivex.plugins.RxJavaPlugins
import org.slf4j.Logger
import java.io.IOException

private const val RX = "[RxJava]"

fun Application.runErrorHandler(
        log: Logger = ErrorHandler.log,
        shouldCrash: (Throwable) -> Boolean
) = Thread.getDefaultUncaughtExceptionHandler().let { handler ->
    Thread.setDefaultUncaughtExceptionHandler { thread, error ->
        log.error("FATAL EXCEPTION:", error)
        if (shouldCrash(error)) handler.uncaughtException(thread, error)
    }
}

fun Application.runRxJavaErrorHandler(
        log: Logger = ErrorHandler.log,
        shouldCrash: (Throwable) -> Boolean
) {
    RxJavaPlugins.setErrorHandler { error ->
        val cause = when (error) {
            is OnErrorNotImplementedException -> run { error.cause ?: error }
                    .also { log.error("$RX shouldCrash() not implemented") }
            is CompositeException -> error
                    .apply {
                        log.error(
                                "$RX error during Exception handling\n" +
                                        "$RX Contains ${exceptions.size} exceptions")
                        exceptions.forEach { log.error("         - ", it) }
                    }
            is UndeliverableException -> run { error.cause ?: error }
                    .also { log.error("$RX Schedulers delivery issue\n", it) }
            is IOException -> error
                    .also { log.error("$RX Network problem or cancellation throws") }
            else -> error
        }

        Thread.currentThread().let {
            log.error("$RX Unhandled Rx Chain Exception ${error.javaClass.simpleName}")
            if (shouldCrash(cause)) it.uncaughtExceptionHandler.uncaughtException(it, cause)
        }
    }
}

internal object ErrorHandler {
    val log by logger("ErrorHandler")
}
