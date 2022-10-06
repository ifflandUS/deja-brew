import React, {useEffect, useState} from 'react';
import { useHistory } from 'react-router-dom';
import Brewery from './Brewery';




function BrewerySearch(){
  
  const history = useHistory();
  const [breweries, setBreweries] = useState([]);
  const [search, setSearch] = useState("");

  const onSubmit = (e) =>{
     e.preventDefault();

      const init = {
        method: "GET",
        headers: {
            "Content-Type": "application/json"
            //"Accept": "application/json"
        },
        
        
    };

      fetch(`https://api.openbrewerydb.org/breweries/search?query=${search}&per_page=15`, init)
      .then( resp =>{
      switch(resp.status){
        case 200:
          return resp.json();
      } return Promise.reject('Oops... something went wrong.');

    })
    .then(data => {
      setBreweries(data);
    })
    .catch(err => history.push('/error', {errorMessage: err}));
  

  }
  const handleChange = (e) => {
    e.preventDefault();

    setSearch(e.target.value);

    if (search.length > 0) {
        return search;
    ;}

  }
    return(
        <>
            <h2>Brewery Search</h2><p>Find Specific Breweries by Name, City, or State.</p>
           
            <div><form className="SearchBar" onSubmit={onSubmit}>
                <input className="search_input"
                input="text" id="search" placeholder='search for breweries' onChange={handleChange}/>
                
                <button className="search_button">search</button>
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
           {breweries.map(brewery => <Brewery key={brewery.id} brewery={brewery} />)}
           </tbody> 
                </table>
              </div>
            



        </>
    )
}
export default BrewerySearch;
