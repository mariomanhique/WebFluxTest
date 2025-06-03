package com.mariomanhique.webfluxtest

import lombok.RequiredArgsConstructor
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/notes")
class NoteController @Autowired constructor(private val noteService: NoteService) {

    private val logger = LoggerFactory.getLogger(NoteController::class.java)

    @PostMapping("/save")
    fun saveNote(@RequestBody note: Note): Mono<Note> {
        logger.info("Saving note $note")
        return noteService.save(note)
    }

    @GetMapping("/stream", produces = ["text/event-stream"])
    fun stream(): Flux<Note> {
        return noteService.findAll()
    }

}