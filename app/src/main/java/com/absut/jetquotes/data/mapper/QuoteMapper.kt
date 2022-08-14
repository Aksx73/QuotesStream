package com.absut.jetquotes.data.mapper

import com.absut.jetquotes.data.local.entity.QuoteEntity
import com.absut.jetquotes.data.remote.dto.QuoteDto
import com.absut.jetquotes.domain.model.Quote


fun QuoteDto.toQuote(): Quote {
    return Quote(
        author = author,
        content = content,
        id = id,
        length = length
    )
}

fun Quote.toQuoteEntity():QuoteEntity{
    return QuoteEntity(
        author = author,
        content = content,
        id = id,
        length = length,
        isFavorite = isFavorite
    )
}

fun QuoteEntity.toQuote():Quote{
    return Quote(
        author = author,
        content = content,
        id = id,
        length = length,
        isFavorite = isFavorite
    )
}




