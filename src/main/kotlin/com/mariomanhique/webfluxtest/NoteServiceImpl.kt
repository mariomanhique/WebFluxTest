package com.mariomanhique.webfluxtest

import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.http.HttpResponse

@Service
class NoteServiceImpl @Autowired constructor(
    private val noteRepository: NoteRepository,
    private val eventSink: EventSink
): NoteService {

    override fun save(note: Note): Mono<Note> {
        return noteRepository.save<Note>(note)
            .doOnNext { savedNoted ->
                eventSink.emit(savedNoted)
            }
    }

    override fun findAll(): Flux<Note> {
        return noteRepository.findAll().concatWith(eventSink.flux)
    }

}