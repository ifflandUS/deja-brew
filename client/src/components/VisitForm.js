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
        console.log(location.state.brewery.id)
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
        console.log(visit)
    }
    const handleChange = (evt) => {
        const property = evt.target.name;
        const value =  evt.target.value;
    
        const newVisit = {...visit};
        newVisit[property] = visit;
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
        <form>
            <div>
                <label htmlFor="date">Date Visited:</label>
                <input
                    type="date"
                    onChange={handleChange}
                    id="date"
                    max={maxDate}
                    />
            </div>
            <div>
                <h4>Beers:</h4>
                <select id="beer" multiple onChange={handleChange}>
                    {beers.map(beer => <option value={beer.id}>{beer.beerName}</option>)}
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
