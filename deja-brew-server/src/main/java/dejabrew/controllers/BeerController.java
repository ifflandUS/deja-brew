package dejabrew.controllers;

import dejabrew.domain.BeerService;
import dejabrew.domain.Result;
import dejabrew.models.Beer;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/beer")
public class BeerController {

    private final BeerService service;

    public BeerController(BeerService service){this.service = service;}

   @GetMapping("/{breweryId}")
    public List<Beer> findBeerByBrewery(@PathVariable String breweryId) throws DataAccessException {
        return service.findBeerByBrewery(breweryId);
   }

   @PostMapping
    public ResponseEntity<?> addBeer(@RequestBody Beer beer){
       Result<Beer> result = service.addBeer(beer);
       if(result.isSuccess()){
           return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
       }else{
           return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
       }
   }

   @PutMapping("/{beerId}")
    public ResponseEntity<?> updateBeer(@PathVariable int beerId, @RequestBody Beer beer){
       if(beerId != beer.getBeerId()){
           return new ResponseEntity<>("Ids do not match",HttpStatus.CONFLICT);
       }
       Result<Beer> result = service.updateBeer(beer);
       if(result.isSuccess()){
           return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
       }else if (result.getMessages().get(0).contains("No Beer found")){
           return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
       } else{
           return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
       }
   }

    @DeleteMapping("/{beerId}")
    public ResponseEntity<?> deleteBeer(@PathVariable int beerId){
        Result<Beer> result = service.deleteBeer(beerId);
        if(result.isSuccess()){
            return new ResponseEntity<>("Beer id #" + beerId + " deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result.getMessages(),HttpStatus.NOT_FOUND);
        }
    }

}
