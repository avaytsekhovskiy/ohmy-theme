package ru.ohmy.theme.domain.common


@Suppress("unused", "MemberVisibilityCanPrivate")
class Converter<B, D>(
        private val fromBusinessConverter: (B) -> D = unsupported("BO -> DTO is unsupported"),
        private val fromDtoConverter: (D) -> B = unsupported("DTO -> BO is unsupported")
) {

    fun fromBusiness(business: B?) = business?.let(fromBusinessConverter)

    fun fromDto(dto: D?) = dto?.let(fromDtoConverter)

    private companion object {
        fun unsupported(message: String): (Any?) -> Nothing = { throw UnsupportedOperationException(message) }
    }

}
