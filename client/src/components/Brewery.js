 import { useHistory } from "react-router-dom";
 import { useEffect, useState } from 'react';
 import BrewerySearch from "./BrewerySearch";



 function Brewery({brewery}){
    
    const history = useHistory();
    
    const handleOnClick = () => {
        const init = {
          method: 'GET'
        };
    
        fetch(`https://api.openbrewerydb.org/breweries/${brewery.breweryId}`, init)
        .then( resp => {
          switch(resp.status) {
            case 204:
              return resp.json();
            case 404:
              history.push('/not-found', { id: brewery.breweryId })
              break;
            default:
              return Promise.reject('Oops... something went wrong');
          }
        })
        .then(resp => {
          if (!resp) {
            //success
    
            //setBrewery(resp);
          } else {
            console.log(resp);
          }
        })
        .catch(err => history.push('/error', {errorMessage: err}));
      
    }
    


    return(
    <>
            <div>
                <div><h2>{brewery.breweryName}</h2>
                <h3>{brewery.breweryCity}, {brewery.breweryState}, {brewery.breweryCountry}
                <br/>{brewery.breweryAddress}
                <br/>{brewery.breweryWebsite}</h3>
                </div> 
            </div>





    </>


    )
 }





 export default Brewery;