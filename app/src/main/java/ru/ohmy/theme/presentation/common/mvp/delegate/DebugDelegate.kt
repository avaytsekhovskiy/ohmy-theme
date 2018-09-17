package ru.ohmy.theme.presentation.common.mvp.delegate


import ru.ohmy.theme.BuildConfig
import ru.ohmy.theme.presentation.common.mvp.view.message.DebugView
import ru.ohmy.theme.presentation.common.mvp.view.message.ToastView
import org.slf4j.Logger

class DebugDelegate(
        private val toastView: ToastView,
        private val logger: Logger
) : DebugView {

    override fun showDebugMessage(
            comment: CharSequence,
            error: Throwable?
    ) {
        @Suppress("USELESS_ELVIS")
        logger.warn("$comment" + (error?.let { "\n$error" } ?: ""))
        toastView.takeIf { BuildConfig.DEBUG }?.showToast(composeMessage(error, comment))
    }

    private fun composeMessage(error: Throwable?, comment: CharSequence) = when {
        error == null -> comment
        comment.isBlank() -> with(error) { "${javaClass.simpleName}: $message" }
        else -> with(error) { "${javaClass.simpleName}: $comment\n$message" }
    }

}
