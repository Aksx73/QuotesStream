package com.absut.jetquotes.data.mapper

import com.absut.jetquotes.data.remote.dto.QuoteDto
import com.absut.jetquotes.model.Quote


fun QuoteDto.toQuote(): Quote {
    return Quote(
        author = author,
        content = content,
        id = id,
        length = length
    )
}