package com.booktickets.controller;

import com.booktickets.exception.ResourceNotFoundException;
import com.booktickets.model.Actor;
import com.booktickets.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ActorController {

    @Autowired
    ActorRepository actorRepository;

    @GetMapping("/actors")
    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    @PostMapping("/actors")
    public Actor createActor(@Valid @RequestBody Actor actor) {
        return actorRepository.save(actor);
    }

    @GetMapping("/actor/{id}")
    public Actor getActorById(@PathVariable(value = "id") Long actorId) {
        return actorRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId));
    }

    @PutMapping("/actor/{id}")
    public Actor updateActor(@PathVariable(value = "id") Long actorId,
                                         @Valid @RequestBody Actor details) {

        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId));

        actor.setName(details.getName());

        Actor updatedActor = actorRepository.save(actor);
        return updatedActor;
    }

    @DeleteMapping("/actor/{id}")
    public ResponseEntity<?> deleteActor(@PathVariable(value = "id") Long actorId) {
        Actor actor = actorRepository.findById(actorId)
                .orElseThrow(() -> new ResourceNotFoundException("Actor", "id", actorId));

        actorRepository.delete(actor);

        return ResponseEntity.ok().build();
    }
}
