import { useContext, useEffect, useState} from "react";
import AuthContext from "./AuthContext";
import BrewMap from "./BrewMap"
import UserVisits from "./UserVisits";

function Profile(){
    const auth = useContext(AuthContext);
    const [breweryVisits, setBreweryVisits] = useState([]);

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
                setBreweryVisits(data)
            })
            .catch(err => console.log("Error:", err));
        },[]);
    

    return(
        <>
            <h1>Cheers, {auth.user.username}!</h1>
            <BrewMap breweryVisits={breweryVisits.map(visit => visit.breweryId)}/>
            <h2>Recent Visits:</h2>
            <UserVisits visits={breweryVisits}/>
        </>
        
    )



}

export default Profile;