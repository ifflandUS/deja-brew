import  React, { useEffect, useState }  from 'react';

export default function VisitForm({breweryId}) {
    const [beers, setBeers] = useState([]);



    useEffect(() => {
        fetch("http://localhost:8080/beer/breweryId")
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
    },[])

    
    
}
