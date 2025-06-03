package com.mariomanhique.webfluxtest

import org.springframework.http.ResponseEntity
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.http.HttpResponse

interface NoteService {

    fun save(note: Note): Mono<Note>

    fun findAll(): Flux<Note>
}