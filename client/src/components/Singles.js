import Brewery from "./Brewery";
import {useHistory} from "react-router-dom";
import { useState } from "react";



function Singles({brewery, reviews}) {

    const history = useHistory();
    
    const handleBack = ()=>{
        history.push(`/BrewerySearch`);
    }

   

    return(
        <>
        <button type="button" className="btn btn-success mr-3" onClick={handleBack}>back to search</button>
        
        <div>   
            
        </div>

       
       {/*Are we still saving the ones the user has been to */}
        <div>
        <button type="button" className="btn btn-success mr-3">Been there done that</button>
        <button type="button" className="btn btn-danger mr-3" >Never again</button>
        <button type="button" className="btn btn-warning mr-3">Bucketlist</button>
        </div> 

        <h3>Reviews of </h3>

        </>
        
        
    )
}

export default Singles;