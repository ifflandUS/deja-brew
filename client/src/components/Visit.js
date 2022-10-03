import { useState, useEffect } from "react";

export default function Visit({visit}) {
    const [brewery, setBrewery] = useState({});

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

    return(
        <li className="list-group-item">
            <b>Date: </b>{visit.date} <b>Brewery: </b> {brewery.name} <b>Beers: </b>
            {}
                 
        </li>
    )
}