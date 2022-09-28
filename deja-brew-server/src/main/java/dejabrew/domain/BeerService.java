package dejabrew.domain;

import dejabrew.data.BeerRepository;
import dejabrew.models.Beer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeerService {
    private BeerRepository repository;

    public BeerService(BeerRepository repository){
        this.repository = repository;
    }

    public List<Beer> findBeerByBrewery(String breweryId){
        return repository.findBeersByBrewery(breweryId);
    }

    public Result<Beer> addBeer(Beer beer){
        Result<Beer> result = validate(beer);
        if(result.isSuccess()){
            for (Beer prevBeer : repository.findBeersByBrewery(beer.getBreweryId())){
                if(prevBeer.equalsMinusId(beer)){
                    result.addMessage("Beer already exists for this brewery", ResultType.INVALID);
                    return result;
                }
            }
            if (beer.getBeerId() > 0){
                result.addMessage("Beer must not have an id.", ResultType.INVALID);
            } else{
                result.setPayload(repository.addBeer(beer));
            }
        }

        return result;
    }

    public Result<Beer> updateBeer(Beer beer){
        Result<Beer> result = validateUpdate(beer);
        if(result.isSuccess()){
            repository.updateBeer(beer);
            result.setPayload(beer);
        }
        return result;
    }

    public Result<Beer> deleteBeer(int beerId){
        Result<Beer> result = new Result<>();
        if (!repository.deleteBeer(beerId)){result.addMessage("Beer could not be found under id #" +beerId, ResultType.NOT_FOUND);}
        return result;
    }


    private Result<Beer> validate(Beer beer){
        Result<Beer> result = new Result<>();

        if (beer == null){
            result.addMessage("Beer must be a valid beer.", ResultType.INVALID);
            return result;
        } if (  beer.getBeerName() == null || beer.getBeerName().isBlank()){
            result.addMessage("Beer must have a valid name.", ResultType.INVALID);
        } if (beer.getAbv() <0.0 || beer.getAbv() > 30.0){
            result.addMessage("Beer must have a valid avb between 0 - 30.", ResultType.INVALID);
        } if (beer.getType() == null || beer.getType().isBlank()) {
            result.addMessage("Beer must have a valid type.", ResultType.INVALID);
        } if (beer.getBreweryId() == null || beer.getBreweryId().isBlank()) {
            result.addMessage("Beer must have a valid Brewery.", ResultType.INVALID);
        }

        return result;

    }

    private Result<Beer> validateUpdate(Beer beer){
        Result<Beer> result = validate(beer);
        if (result.isSuccess()){
            Beer currBeer = repository.findBeersById(beer.getBeerId());
            if(currBeer == null){
                result.addMessage("No Beer to update under that id.", ResultType.NOT_FOUND);
            } else {
                if (currBeer == beer){
                    result.addMessage("No changes are being made.", ResultType.INVALID);
                }
            }
        }
        return result;
    }
}
