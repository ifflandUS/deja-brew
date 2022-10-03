import  React, { useEffect, useState }  from 'react';

export default function UserVisits() {
    const [visits, setVisits] = useState([]);



    useEffect(() => {
        fetch("http://localhost:8080/visit")
            .then(resp => {
                if (resp.status !== 200){
                    return Promise.reject("[REDACTED] went wrong!");
                }else {
                    return resp.json();
                }
            })
            .then(data => {
                setVisits(data);
                console.log(visits)
            })
            .catch(err => console.log("Error:", err));
    },[]);

    return (
       
        <div className="container">
                           
        </div>
        
    )
}