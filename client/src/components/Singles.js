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
    const writeReview = (e) =>{
        
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

   const handleAdd = ()=> history.push('/writereview')

   

    return(
        <>
        <button type="button" className="btn btn-success m-3 mx-auto d-flex float-left" onClick={handleBack}>back to search</button>
        <div>
        <div>   
          <h2>{brewery.name}</h2>
          <h3>{reviews.rating}
          <br/> {brewery.city}, {brewery.state}
          <br/><a href = {brewery.website_url} target="_blank" rel="noreferrer">{brewery.website_url}</a></h3>  
        </div>
        {/*image (working on the webscraping) */}
        </div>
        
       

       
       {/*Are we still saving the ones the user has been to */}
       
    
        <button type="button" className="btn btn-success mr-3" onClick={handleLog}>Log Visit</button>
       {/* <button type="button" className="btn btn-danger mr-3" >Never again</button>
    <button type="button" className="btn btn-warning mr-3">Bucketlist</button></>*/}
        
        <div className="container">   
        <h3>{brewery.name} Reviews</h3>
            <button className="btn btn-sm btn-success float-right" onClick={handleAdd}>Add a review</button>  
        </div>
        
        
        </>
        
        
    )
}

export default Singles;