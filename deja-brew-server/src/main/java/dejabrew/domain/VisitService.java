package dejabrew.domain;

import dejabrew.data.VisitBeerRepository;
import dejabrew.data.VisitRepository;
import dejabrew.models.Beer;
import dejabrew.models.Visit;
import dejabrew.models.VisitBeer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VisitService {
    private final VisitRepository repository;
    private final VisitBeerRepository visitBeerRepository;

    public VisitService(VisitRepository repository, VisitBeerRepository visitBeerRepository) {
        this.repository = repository;
        this.visitBeerRepository = visitBeerRepository;
    }

    public Visit findById(int visitId) {
        return repository.findById(visitId);
    }

    public Visit findByBrewery(String brewery) {
        return repository.findByBrewery(brewery);
    }

    public Result<Visit> add(Visit visit) {
        Result<Visit> result = validate(visit);
        if (!result.isSuccess()) {
            return result;
        }

        if (visit.getVisitId() != 0) {
            result.addMessage("visitId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        visit = repository.add(visit);
        result.setPayload(visit);
        return result;
    }

    public Result<Visit> update(Visit visit) {
        Result<Visit> result = validate(visit);
        if (!result.isSuccess()) {
            return result;
        }

        if (visit.getVisitId() <= 0) {
            result.addMessage("reviewId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(visit)) {
            String msg = String.format("reviewId: %s, not found", visit.getVisitId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int visitId) {
        return repository.deleteById(visitId);
    }

    public Result<List<Beer>> addBeer(VisitBeer visitBeer) {
        Result<List<Beer>> beerListResult = validate(visitBeer);
        if (!beerListResult.isSuccess()) {
            return beerListResult;
        }
        if (!visitBeerRepository.add(visitBeer)) {
            beerListResult.addMessage("beer not added", ResultType.INVALID);
        }
        visitBeerRepository.add(visitBeer);
        beerListResult.setPayload(new ArrayList<>());
        return beerListResult;
    }

//    public Result<Void> updateVisit(VisitBeer visitBeer) {
//        Result<Void> result = validate(visitBeer);
//        if(!result.isSuccess()) {
//            return  result;
//        }
//        if(!visitBeerRepository.update(visitBeer)) {
//            String msg = String.format("update failed for visit id %s, beer id %s: not found", visitBeer.getVisit_id(), visitBeer.getBeer_id());
//            result.addMessage(msg, ResultType.NOT_FOUND);
//        }
//        return result;
//    }

    public boolean deleteBeerById(int visitBeerId) {
        return visitBeerRepository.deleteById(visitBeerId);
    }

    private Result<Visit> validate(Visit visit) {
        Result<Visit> result = new Result<>();
        if (visit == null) {
            result.addMessage("visit cannot be null", ResultType.INVALID);
            return result;
        }
        if (visit.getBreweryId().isBlank() || visit.getBreweryId().isEmpty()) {
            result.addMessage("brewery is required", ResultType.INVALID);
        }
        if (visit.getUserId() == 0) {
            result.addMessage("user is required", ResultType.INVALID);
        }
        return result;
    }

    private Result<List<Beer>> validate(VisitBeer visitBeer) {
        Result<List<Beer>> visitBeerResult = new Result<>();
        if(visitBeer == null) {
            visitBeerResult.addMessage("visitBeer cannot be null", ResultType.INVALID);
            return visitBeerResult;
        }
        if(visitBeer.getBeer_id() < 0) {
            visitBeerResult.addMessage("beer must exist", ResultType.INVALID);
        }
        if (visitBeer.getVisit_id() < 0) {
            visitBeerResult.addMessage("visit must exist", ResultType.INVALID);
        }
        return visitBeerResult;

    }
}
