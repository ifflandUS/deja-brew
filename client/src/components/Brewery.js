  import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import Singles from "./Singles";





 function Brewery({brewery}){
    const [reviews, setReviews] = useState([]);
    const history = useHistory();
    

    useEffect(( )=>{
    
    fetch(`http://localhost:3000/api/review/${brewery.id}`)
    .then(resp =>{
        if (resp.status === 200){
            return resp.json();
        }
        return Promise.reject('Oops...Something Happened.');
    })
    .then(data =>{
        setReviews(data);
        brewery.reviews = reviews;
    })
    .catch(err => history.push('/error', {errorMessage: err}));

    },[])

    
    const handleIndividual = () =>{
      history.push({ 
        pathname: `/Brewery/${brewery.id}` ,
        state: {breweryId: brewery.id}
       });
        
     }
     return(<>
    <tr>
      <td>{brewery.name} <br/>* * * * *</td>
      <td>{brewery.country}</td>
      <td>{brewery.state}</td>
      <td>{brewery.city}</td>
      <td><button type="button" className="btn btn-success mr-3" onClick = {handleIndividual}>More...</button></td>
    

    </tr>
        
    
    
    
    
     </>)



 }

 export default Brewery;