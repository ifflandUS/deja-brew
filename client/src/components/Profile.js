import { useContext } from "react";
import AuthContext from "./AuthContext";

function Profile(){
    const auth = useContext(AuthContext);
    

    return(
        <><h1>Hello, {auth.user.username}!</h1></>
    )



}

export default Profile;