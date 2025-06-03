package com.mariomanhique.webfluxtest

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.util.*

@Repository
interface NoteRepository: ReactiveCrudRepository<Note, Long> {
    fun findNoteById(id: Long): Flux<Note>
}