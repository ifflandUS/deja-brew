package dejabrew.domain;

import dejabrew.data.VisitRepository;
import dejabrew.models.Review;
import dejabrew.models.Visit;

public class VisitService {
    private final VisitRepository repository;

    public VisitService(VisitRepository repository) {
        this.repository = repository;
    }

    public Visit findById(int visitId) {
        return repository.findById(visitId);
    }

    public Visit findByBrewery(Brewery brewery) {
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


}
