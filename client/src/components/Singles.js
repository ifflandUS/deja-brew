import Brewery from "./Brewery";
import {useHistory} from "react-router-dom";
import { useState } from "react";

const REVIEW_DEFAULT = { reviewId: 0, userID: 0 , middleName: '', lastName: '', dob: '', review: ''}

function Singles({brewery, reviews}) {

    const history = useHistory();
    const [errors, setErrors] = useState([]);
    
    const handleBack = ()=>{
        history.push(`/BrewerySearch`);
    }
    const writeReview = (e) =>{
        
    }

   

    return(
        <>
        <button type="button" className="btn btn-success mr-3" onClick={handleBack}>back to search</button>
        
        <div>
        <div>   
          <h2>{brewery.name}</h2>
          <h3>{reviews.rating}
          <br/> {brewery.city}, {brewery.state}
          <br/><a href = {brewery.website_url} target="_blank">{brewery.website_url}</a></h3>  
        </div>
        {/*image (working on the webscraping) */}
        </div>

       
       {/*Are we still saving the ones the user has been to */}
        <div>
        <button type="button" className="btn btn-success mr-3">Been there done that</button>
        <button type="button" className="btn btn-danger mr-3" >Never again</button>
        <button type="button" className="btn btn-warning mr-3">Bucketlist</button>
        </div> 

        <h3>{brewery.name} Reviews</h3>
        
        <button type="button" className = "btn btn-primary mr3" onClick={writeReview}>write review</button>

        </>
        
        
    )
}

export default Singles;