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

    useEffect(() => {
        console.log(visits);
      }, [visits]);

    return (
       
        <div className="container">
              <ul className='list-group'>
                {visits.map(visit => <Visit key={visit.visitId} visit={visit}/> )}
            </ul>             
        </div>
        
    )
}