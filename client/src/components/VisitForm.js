import  React, { useEffect, useState }  from 'react';
import { useHistory, useLocation } from "react-router-dom";
import { useContext } from "react";
import AuthContext from "./AuthContext";

const date = new Date()
const maxDate = `${date.getFullYear()}-${(date.getMonth()+1).toString().padStart(2,0)}-${date.getDate().toString().padStart(2,0)}`;

export default function VisitForm() {
    const [beers, setBeers] = useState([{}]);
    const [visit, setVisit] = useState();
    const auth = useContext(AuthContext);
    const history = useHistory();
    const location = useLocation();

    useEffect(() => {
        fetch(`http://localhost:8080/beer/${location.state.brewery.id}`)
            .then(resp => {
                if (resp.status !== 200){
                    return Promise.reject("[REDACTED] went wrong!");
                }else {
                    return resp.json();
                }
            })
            .then(data => {
                setBeers(data);
            })
            .catch(err => console.log("Error:", err));

    },[]);
    
    const handleSubmit = (evt) => {
        evt.preventDefault();
        const selected = document.querySelectorAll('#beer option:checked');
        const values = Array.from(selected).map(el => el.value);
        console.log(values);
        const selectedBeers = beers.filter(beer => 
            values.includes(beer.beerName));
        console.log(selectedBeers)
        const newVisit = {...visit};
        newVisit["breweryId"] = location.state.brewery.id;
        newVisit["userId"] = auth.user.userId;
        newVisit["beerList"] = selectedBeers;
        console.log(newVisit);

        const init = {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "Authorization": `Bearer ${auth.user.token}`
            },
            body: JSON.stringify(newVisit)
        };
        fetch(`http://localhost:8080/visit`, init)
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
                    console.log(location.state.brewery)
                  history.push({ 
                    pathname: `/Brewery/${location.state.brewery.id}` ,
                    state: {breweryId: location.state.brewery.id}
                   });
                } else {
                    console.log(body)
                }
            })
            .catch((err) =>{
                console.log(err)
                history.push("/error")
            })

        
    }
    const handleChange = (evt) => {
        const property = evt.target.name;
        const value =  evt.target.value;
    
        const newVisit = {...visit};
        newVisit[property] = value;
        setVisit(newVisit);
    }

    const handleCancel = (evt) => {
        evt.preventDefault();
        history.push("/")
    }

    const handleAddBeer = () =>{
        history.push({ 
            pathname: `/beer-form` ,
            state: {brewery: location.state.brewery}
           });
    }

    return(
        <>
        <h2>{auth.user.username}'s Visit to {location.state.brewery.name}</h2>
        <form onSubmit={handleSubmit}>
            <div>
                <label htmlFor="date">Date Visited:</label>
                <input
                    type="date"
                    onChange={handleChange}
                    id="date"
                    name="date"
                    max={maxDate}
                    />
            </div>
            <div>
                <h4>Beers:</h4>
                <select id="beer" multiple>
                    {beers.map(beer => <option key={beer.id} value={beer.id}>{beer.beerName}</option>)}
                </select>
                <br></br>

                <span className='mr-5'>Don't see a Beer?</span>
                <button className="btn btn-sm btn-success  mr 3" onClick={handleAddBeer}>Add a Beer</button>

                <div className="form-group">
                    <button type="submit" className="btn btn-success mr-2">Submit</button>
                    <button type="submit" className="btn btn-danger mr-2" onClick={handleCancel}>Cancel</button>         
                </div>

            </div>
        </form>
        </>
    )
       

    
}
