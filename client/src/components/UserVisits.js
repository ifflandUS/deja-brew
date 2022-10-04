import  React, { useEffect, useState, useContext }  from 'react';
import AuthContext from "./AuthContext";
import Visit from './Visit';

export default function UserVisits() {
    const [visits, setVisits] = useState([]);
    const auth = useContext(AuthContext);



    useEffect(() => {

        const init = {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${auth.user.token}`
            }};

        fetch("http://localhost:8080/visit", init)
            .then(resp => {
                if (resp.status !== 200){
                    return Promise.reject("[REDACTED] went wrong!");
                }else {
                    return resp.json();
                }
            })
            .then(data => {
                setVisits(data);
            })
            .catch(err => console.log("Error:", err));
    },[]);


    return (
       
        <div className="container">
              <ul className='list-group'>
                {visits.length > 1 && visits.map(visit => <Visit key={visit.visitId} visit={visit}/> )}
                {visits.length <1 && <p>Get out there and drink some beer!</p>}
            </ul>             
        </div>
        
    )
}