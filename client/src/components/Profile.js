import { useContext } from "react";
import AuthContext from "./AuthContext";
import BrewMap from "./BrewMap"

function Profile(){
    const auth = useContext(AuthContext);


    

    return(
        <>
            <h1>Cheers, {auth.user.username}!</h1>
            <BrewMap/>
            <h2>Recent Visits:</h2>
            <div className="container">
                
            </div>
        </>
        
    )



}

export default Profile;