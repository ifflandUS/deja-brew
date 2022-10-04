import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import './Visit.css';

export default function Visit({visit}) {
    const [brewery, setBrewery] = useState({});
    const history = useHistory();
   

    useEffect(() => {
        fetch(`https://api.openbrewerydb.org/breweries/${visit.breweryId}`)
            .then(resp => {
                
                    return resp.json();
                
            })
            .then(data => {
                setBrewery(data);
                console.log(brewery)
            })
            .catch(err => console.log("Error:", err));
    },[]);

    const handleClick = () =>{
        history.push({ 
            pathname: `/Brewery/${brewery.id}` ,
            state: {breweryId: brewery.id}
           });
    }


    return(
        <li className="list-group-item list-group-item-hover" onClick={handleClick} >
            <b>Date: </b>{visit.date} <b>Brewery: </b> {brewery.name} <b>Beers: </b>
            {visit.beerList.map(beer => <span>{beer.beerName} </span>)}
                 
        </li>
    )
}