  import { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";





 function Brewery({brewery}){
    const [reviews, setReviews] = useState([]);
    const history = useHistory();
    
    
    const handleIndividual = () =>{
      history.push({ 
        pathname: `/Brewery/${brewery.id}` ,
        state: {breweryId: brewery.id}
       });
        
    }
     return(<>
    <tr>
      <td>{brewery.name} <br/>*****</td>
      <td>{brewery.country}</td>
      <td>{brewery.state}</td>
      <td>{brewery.city}</td>
      <td><button type="button" className="btn btn-success mr-3" onClick = {handleIndividual}>More...</button></td>
    

    </tr>
        
    
    
    
    
     </>)



 }

 export default Brewery;