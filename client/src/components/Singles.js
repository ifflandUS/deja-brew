
import Review from "./Review";
import { useHistory, useLocation } from "react-router-dom";
import { useState, useEffect, useContext } from "react";
import './BrewPage.css';
import AuthContext from "./AuthContext";
import BreweryMap from "./BreweryMap";




function Singles() {
    const auth = useContext(AuthContext);
    const history = useHistory();
    const location = useLocation();
    const [brewery, setBrewery] = useState({});
    const [reviews, setReviews] = useState([]);
    const randNum = Math.floor((Math.random(5)*5)+1);
    const imgUrl = `/img/brew${randNum}.jpg`;



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
        
            })
            .catch(err => console.log("Error:", err));
    },[]);
    useEffect(( )=>{
    
        fetch(`http://localhost:8080/review/${location.state.breweryId}`)
        .then(resp =>{
            if (resp.status === 200){
                return resp.json();
            }
            return Promise.reject('Oops...Something Happened.');
        })
        .then(data =>{
            setReviews(data)
    
    
        })
        .catch(err => history.push('/error', {errorMessage: err}));
    
        },[])

    
    const deleteReview = (reviewId) => {
            const filteredReviews = reviews.filter(review => review.reviewId !== reviewId);
            setReviews(filteredReviews);
          }


   const handleAdd = ()=> history.push({
            pathname: '/review/add' ,
            state: {brewery: brewery}});

   

    return(
        <>

        
        <br></br>
        <div className="container">
            <div className="row align-items-start">   
            
                <div className="col">
                    <img src={process.env.PUBLIC_URL + imgUrl} ></img>
                </div>
                <div className="col info">
                    <h1>{brewery.name}</h1>
                    <br/>
                    <a href = {brewery.website_url} target="_blank" rel="noreferrer">{brewery.website_url}</a>
                    {auth.user && <button type="button" className="btn btn-sm btn-warning mr-3 float-right" onClick={handleLog}>Log Visit</button>}
                    {auth.user && <button className="btn btn-sm btn-success float-right" onClick={handleAdd}>Add a review</button>  }
                    <p>Phone: {brewery.phone}</p>
                    <span>
                        <p className="address">{brewery.street}</p>
                        <h6 className="address">{brewery.city}, {brewery.state}</h6>
                        <p className="address">{brewery.postal_code}</p>
                    </span>
                    <h3>{reviews.rating}
                    <br/></h3>  
                    
                </div>  
                <div className="col">
                    {brewery.longitude && <BreweryMap brewery={brewery}/>}
                </div>
            </div>
            

        </div>
        
       

       
    
        
       
        <div >   
        <h3>{brewery.name} Reviews</h3>
        
        <br/>
        </div>

        <div className="reviewTable">
                <table className="table  table-bordered table-striped">
                  <thead><tr>
                  <th scope="col">Rating</th>

              <th scope="col">Review</th>

              
                  </tr></thead>

              <tbody>
           {reviews.map(review => <Review key={review.id} review={review} handleDelete={deleteReview} />)}
           </tbody> 
                </table>
              </div>
              
              <button type="button" className="btn btn-danger m-3 mx-auto d-flex " onClick={handleBack}>back to search</button>
        </>
        
        
    )
}

export default Singles;