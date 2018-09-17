package ru.ohmy.theme.presentation.di.provider

import com.google.gson.*
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class GsonProvider @Inject constructor() : Provider<Gson> {
    override fun get(): Gson =
            GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(DateTime::class.java, JodaDateTimeSerializer(DATE_FORMAT))
                    .create()

    private class JodaDateTimeSerializer(pattern: String) : JsonDeserializer<DateTime>, JsonSerializer<DateTime> {

        private val formatter: DateTimeFormatter = DateTimeFormat.forPattern(pattern).withLocale(Locale.ENGLISH)

        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement?, typeOfT: Type, context: JsonDeserializationContext): DateTime? =
                json?.asString?.takeIf { it.isEmpty() }?.let(formatter::parseDateTime)

        override fun serialize(src: DateTime?, typeOfSrc: Type, context: JsonSerializationContext) =
                JsonPrimitive(src?.let(formatter::print) ?: "")
    }

    companion object {
        private const val DATE_FORMAT = "dd MMM yyyy HH:mm:ss Z"
    }
}
