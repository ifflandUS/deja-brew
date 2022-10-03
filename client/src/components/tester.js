import { useEffect, useState } from 'react';
import {useHistory } from 'react-router-dom';
import Brewery from './Brewery';



const BREWERY_DEFAULT = {breweryId: '', breweryName:'',breweryAddress:'', breweryCity:'', breweryState:'', breweryCountry:'', breweryLongitude:0.0, breweryLatitude:0.0, breweryWebsite:''  }

function BrewerySearch(){
    const [breweries, setBreweries] = useState(BREWERY_DEFAULT);
    const history = useHistory();

    useEffect(() => {
        fetch('https://api.openbrewerydb.org/breweries/search?query=${searchInput}')
        .then(resp => {
          if (resp.status === 200) {
            return resp.json();
          }
          return Promise.reject('Oops...Something went wrong.');
        })
        .then(data => {
          setBreweries(data);
        })
        .catch(err => history.push('/error', {errorMessage: err}));
      },[])


    const useSearchBar = () => {
        
        const [searchInput, setSearchInput] = useState("");
    
        
        const handleChange = (e) => {
            e.preventDefault();
            setSearchInput(e.target.value);

            if (searchInput.length > 0) {
                return searchInput;
            ;}

          }
          
          

 
    return(
        <>
            
            <h2>Brewery Search</h2>
            <p>Find Specific Breweries by Name, City, or State.</p>
            <input type="text" class=""placeholder="Search here" onChange={handleChange} value={searchInput} />
            <button type="button" className="btn btn-primary mb-3" onClick={handleChange}>Search</button>
            <table className="table table-striped">
                <thead>
                <tr>
              
              <th scope="col">Brewery</th>
              <th scope="col">Country</th>
              <th scope="col">State</th>
              <th scope="col">City</th>
             
          
              <th></th>
            </tr>
          </thead>
          <tbody>
            {breweries.map(brewery => <Brewery key={brewery.breweryId} brewery={brewery} />)}
          </tbody>
        </table>



        </>
    )
}}

export default BrewerySearch;