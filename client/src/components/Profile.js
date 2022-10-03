import { useContext } from "react";
import AuthContext from "./AuthContext";
import BrewMap from "./BrewMap"
import UserVisits from "./UserVisits";

function Profile(){
    const auth = useContext(AuthContext);


    

    return(
        <>
            <h1>Cheers, {auth.user.username}!</h1>
            <BrewMap/>
            <h2>Recent Visits:</h2>
            <UserVisits/>
        </>
        
    )



}

export default Profile;