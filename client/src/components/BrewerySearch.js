import React, {useEffect, useState} from 'react';
import { useHistory } from 'react-router-dom';
import { Form } from 'react-bootstrap';
import Brewery from './Brewery';

 const brewery = {id:'',name:'',street:'', city:'', state:'',county:'',longitude:0.0, latitude:0.0, website_url:'' }
const breweries = [];
function BrewerySearch(){
  
  const history = useHistory();
  const [breweries, setBreweries] = useState([]);

  const handleSearch = (e) =>{
      const searchString = e.target.value;
      const search = searchString.replace(/ /g,"_");

      const init = {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        
        
    };

      fetch('https://api.openbrewerydb.org/breweries/search?query={search}&per_page=15', init)
      .then( resp =>{
      switch(resp.status){
        case 204:
          return resp.json();
      } return Promise.reject('Oops... something went wrong.');

    })
    .then(data => {
      setBreweries(data)
      breweries.push(data);
    })
    .catch(err => history.push('/error', {errorMessage: err}));
  

    // const handeMoreClick = () =>{
    //   //set brewery to that and then go to specific brewery page??
    //   history.pushState('/brewery/${brewery.id}');


    // }
  }
    return(
        <>
            <h2>Brewery Search</h2><p>Find Specific Breweries by Name, City, or State.</p>
           
            <div><form className="SearchBar">
                <input className="search_input"
                input="text" id="search" />
                
                <button className="search_button" onClick={handleSearch}>Search</button>
                </form>
                </div>

              <div className="resultTable">
                <table className="table table-striped">
                  <thead><tr>
                  <th scope="col">Brewery</th>
              <th scope="col">Country</th>
              <th scope="col">State</th>
              <th scope="col">City</th>
                  </tr></thead>

              <tbody>
           {breweries.map(brewery => <Brewery key={brewery.breweryId} brewery={brewery} />)}
           </tbody> 
                </table>
              </div>
            



        </>
    )
}
export default BrewerySearch;

