package dejabrew.controllers;

import dejabrew.domain.Result;
import dejabrew.domain.ResultType;
import dejabrew.domain.VisitService;
import dejabrew.models.Visit;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/visit")
public class VisitController {

    private final VisitService service;


    public VisitController(VisitService service) {
        this.service = service;
    }

    @GetMapping("/{visitId}")
    public Visit findById(@PathVariable int visitId) throws DataAccessException {
        return service.findById(visitId);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Visit visit){
        Result<Visit> result = service.add(visit);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{visitId}")
    public ResponseEntity<?> update(@PathVariable int visitId, @RequestBody Visit visit){
        if(visitId != visit.getVisitId()){
            return new ResponseEntity<>("Ids do not match",HttpStatus.CONFLICT);
        }
        Result<Visit> result = service.update(visit);
        if(result.isSuccess()){
            return new ResponseEntity<>(result.getPayload(), HttpStatus.OK);
        }else if (result.getType() == ResultType.NOT_FOUND){
            return new ResponseEntity<>(result.getMessages(), HttpStatus.NOT_FOUND);
        } else{
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{visitId}")
    public ResponseEntity<?> deleteById(@PathVariable int visitId){
        if(service.deleteById(visitId)){
            return new ResponseEntity<>( HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
