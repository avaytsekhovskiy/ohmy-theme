@file:Suppress("ConstantConditionIf")

package ru.ohmy.theme.core.ext

import ru.ohmy.theme.BuildConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.math.BigInteger
import java.util.*

private const val BITS_NUMBER = 32
private val random = Random()

val Any.debugName get() = "${javaClass.simpleName}@${System.identityHashCode(this)}"

fun Logger.warnOrThrow(msg: String) =
        if (BuildConfig.DEBUG) throw Exception(msg)
        else warn(msg)

fun randomString(instanceClass: Class<*>? = null, bits: Int = BITS_NUMBER) =
        "${instanceClass?.simpleName?.plus(":") ?: ""}${BigInteger(bits, random)}"

/**
 * Slf4j Logger delegate.
 *
 * `val log by logger()` -->
 * `val log by lazy { LoggerFactory.getLogger(javaClass.simpleName) }`
 *
 * `val log by logger("tag")` -->
 * `val log by lazy { LoggerFactory.getLogger(tag) }`
 */
fun Any.logger(tag: String? = null) = lazy<Logger> {
    LoggerFactory.getLogger(tag ?: javaClass.simpleName)
}
