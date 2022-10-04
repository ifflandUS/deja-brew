
import  React, { useEffect, useState, useContext }  from 'react';
import { useHistory, useLocation } from "react-router-dom";
import AuthContext from "./AuthContext";

export default function BeerForm() {
  const [beer, setBeer] = useState();
  const history = useHistory();
  const location = useLocation();
  const auth = useContext(AuthContext);

  const handleChange = (evt) => {
    const property = evt.target.name;
    const value =  evt.target.value;

    const newBeer = {...beer};
    newBeer[property] = value;
    setBeer(newBeer);
  }

  const onSubmit = (evt) => {
    evt.preventDefault();
    const newBeer = {...beer};
    newBeer["breweryId"] = location.state.brewery.id

    const init = {
      method: "POST",
      headers: {
          "Content-Type": "application/json",
          "Accept": "application/json",
          "Authorization": `Bearer ${auth.user.token}`
      },
      body: JSON.stringify(newBeer)
  };
  fetch(`http://localhost:8080/beer`, init)
      .then(resp => {
          console.log(resp.status)
          switch(resp.status){
              case 201:
                  return null;
              case 400:
                  return resp.json();
              case 404:
                  history.push("/not-found");
                  break;
              default:
                  return Promise.reject('[REDACTED] went wrong!')
          }
      })
      .then(body => {
          if (!body){
            history.push({ 
              pathname: `/visit-form` ,
              state: {brewery: location.state.brewery}
             });
          } 
      })
      .catch((err) =>{
          console.log(err)
          history.push("/error")
      })
  }
  const handleCancel = (evt) => {
    evt.preventDefault();
    history.push("/")
  }


  
  return (
    <>
      <h2>Add a Beer to {location.state.brewery.name}</h2>
      <form onSubmit={onSubmit}>
            <div className="form-group">
                <label htmlFor="beerName">Beer Name: </label> 
                <input className="form-control" name="beerName" id="beerName" type="text" onChange={handleChange} required></input>           
            </div>
  
            <div className="form-group">
                <label htmlFor="abv">ABV%: </label> 
                <input className="form-control" name="abv" id="abv" type="number" step="0.1" max="70.0" min="0.0" onChange={handleChange} required></input>           
            </div>

            <div className="form-group">
                <label htmlFor="type">Type: </label> 
                <input className="form-control" name="type" id="type" type="text" onChange={handleChange} required></input>           
            </div>

            <div className="form-group">
                <button type="submit" className="btn btn-success mr-2">Submit</button>
                <button type="submit" className="btn btn-danger mr-2" onClick={handleCancel}>Cancel</button>         
            </div>
        </form>

    </>
  );
}

 
