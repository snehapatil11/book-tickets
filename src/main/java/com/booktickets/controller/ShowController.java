package com.booktickets.controller;

import com.booktickets.exception.ResourceNotFoundException;
import com.booktickets.model.Show;
import com.booktickets.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */
@RestController
@RequestMapping("/api")
public class ShowController {

    @Autowired
    ShowRepository showRepository;

    @GetMapping("/shows")
    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    @PostMapping("/shows")
    public Show createShow(@Valid @RequestBody Show show) {
        return showRepository.save(show);
    }

    @GetMapping("/shows/{id}")
    public Show getShowById(@PathVariable(value = "id") Long showId) {
        return showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show", "id", showId));
    }

    @PutMapping("/shows/{id}")
    public Show updateShow(@PathVariable(value = "id") Long showId,
                           @Valid @RequestBody Show showDetails) {

        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show", "id", showId));

        show.setTitle(showDetails.getTitle());
        show.setDescription(showDetails.getDescription());

        Show updatedShow = showRepository.save(show);
        return updatedShow;
    }

    @DeleteMapping("/shows/{id}")
    public ResponseEntity<?> deleteShow(@PathVariable(value = "id") Long showId) {
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new ResourceNotFoundException("Show", "id", showId));

        showRepository.delete(show);

        return ResponseEntity.ok().build();
    }
}
