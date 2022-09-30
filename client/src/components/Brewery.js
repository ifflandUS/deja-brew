  import { useHistory } from "react-router-dom";
  import React, {useState} from 'react';

//can add ratings in here
 function Brewery(){

     const [brewery, setBrewery] = useState([]);
  
     const history = useHistory();
  


    //  const handleBack= () =>{
    //     history.push(`/BrewerySearch`);
    //  }
     return(<>
    
        <h5 onClick={handleBack}>&lt;back to search</h5>
        <h2>{brewery.name}</h2>

        <p>
            
            {brewery.breweryCity},{brewery.breweryState}
            <br/>*Ratings*
            <br/>Website:<a href={brewery.breweryWebsite} target="_blank"/>
            
        </p>
    
    
    
    
    
    
     </>)



 }

export default Brewery;