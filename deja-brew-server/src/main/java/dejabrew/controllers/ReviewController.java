package dejabrew.controllers;

import dejabrew.domain.Result;
import dejabrew.domain.ReviewService;
import dejabrew.models.AppUser;
import dejabrew.models.Review;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }

    @GetMapping("/{breweryId}")
    public ResponseEntity<List<Review>> findByBrewery(@PathVariable String breweryId) {
        List<Review> reviews= service.findByBrewery(breweryId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/app-user/{appUserId}")
    public ResponseEntity<?> findByUser(@RequestHeader(name = "authorization")AppUser appUser){
        List<Review> reviews = service.findByUser(appUser);
        if (reviews == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody Review review) {
        Result<Review> result = service.add(review);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return ErrorResponse.build(result);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Object> update(@PathVariable int reviewId, @RequestBody Review review) {
        if (reviewId != review.getReviewId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Review> result = service.update(review);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }

        return ErrorResponse.build(result);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteById(@PathVariable int reviewId) {
        if (service.deleteById(reviewId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
