package com.mariomanhique.webfluxtest

import org.springframework.data.relational.core.mapping.Table

@Table("notes")
data class Note(
    val id: Long,
    val title: String,
    val content: String
)
