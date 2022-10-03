import Brewery from "./Brewery";
import { useHistory, useLocation } from "react-router-dom";
import { useState, useEffect } from "react";
import './BrewPage.css';



function Singles() {

    const history = useHistory();
    const location = useLocation();
    const [brewery, setBrewery] = useState({});
    const [reviews, setReviews] = useState([]);



    const handleBack = ()=>{
        history.push(`/BrewerySearch`);
    }

    const handleLog = ()=>{
        history.push({ 
            pathname: `/visit-form` ,
            state: {brewery: brewery}
           });
    }
    
    useEffect(() => {
        fetch(`https://api.openbrewerydb.org/breweries/${location.state.breweryId}`)
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
        <>
        
        
       

       
       {/*Are we still saving the ones the user has been to */}
        <div>
            <h1>{brewery.name}</h1>
        <button type="button" className="btn btn-success mr-3" onClick={handleLog}>Log Visit</button>
        <button type="button" className="btn btn-danger mr-3" >Never again</button>
        <button type="button" className="btn btn-warning mr-3">Bucketlist</button>
        </div> 
        
        <div className="container">   
            <h3>User reviews: </h3>
            <button className="btn btn-sm btn-success float-right">Add a review</button>
        </div>
        


        <button type="button" className="btn btn-success m-3 mx-auto d-flex" onClick={handleBack}>back to search</button>
        </>
        
        
    )
}

export default Singles;