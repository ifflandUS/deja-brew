import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";

export default function CreateAccount(){
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [zipcode, setZipcode] = useState("");
    const [errors, setErrors] = useState({
        "username": "",
        "password": "",
        "confirmPassword": ""
    })

    const history = useHistory();

    const handleSubmit = async (evt) => {
        evt.preventDefault();

        const response = await fetch("http://localhost:8080/create_account", {
                                method: "POST",
                                headers: {
                                    "Content-Type": "application/json",
                                },
                                body: JSON.stringify({username, password, zipcode}),
        }).then(resp => {
            console.log(resp.status)
            switch(resp.status){
                case 201:
                    return null;
                case 400:
                    return resp.json();
                default:
                    return Promise.reject('Something went wrong on our end :/')
            }
        })
        .then(body => {
            console.log(body)
            if(!body){
                history.push("/log-in");
            } else if(body.at(0).includes("password")){
                const stateObj = { ...errors};
                stateObj["password"] = body;
                setErrors(stateObj)
            } else {
                const stateObj = { ...errors};
                stateObj["username"] = body;
                setErrors(stateObj)
            }
        })
        .catch((err) =>{
            console.log(err)
            history.push("/error")
        })
    }

    const handleErrors = (evt) => {
        const {name, value} = evt.target;

        setErrors(prev => {
            const stateObj = { ...prev, [name]: "" };
            
            switch(name){
                case "username":
                    if (!value) {
                        stateObj[name] = "Please enter Username.";
                      }
                    break;
                case "password":
                    if (!value){
                        stateObj[name] = "Please enter Password."
                    } else if(value !== confirmPassword){
                        stateObj["confirmPassword"] = "Passwords do not match."
                    } else {
                        stateObj["confirmPassword"] = confirmPassword ? "" : errors.confirmPassword;
                    }
                    break;
                case "confirmPassword":
                    if (!value){
                        stateObj[name] = "Please enter Password."
                    } else if(value !== password){
                        stateObj["confirmPassword"] = "Passwords do not match."
                    } else {
                        stateObj["confirmPassword"] = confirmPassword ? "" : errors.confirmPassword;
                    }
                    break;
                default:
                    break;
            }
        return stateObj;
        });
    }

    return (
        <div className="container">
        <h1 className="text-center">Create Account</h1>
        <div className="col-3 border m-4 mx-auto d-flex justify-self-center text-center">
            <form  onSubmit={handleSubmit}>
        <div>
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            onChange={(event) => setUsername(event.target.value)}
            id="username"
            name="username"
            onBlur={handleErrors}
          />
          <br></br>
            {errors.username && <span className='font-weight-light text-danger text-center'><small>{errors.username}</small></span>}
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            onChange={(event) => setPassword(event.target.value)}
            id="password"
            name="password"
            onBlur={handleErrors}
          />
          <br></br>
          {errors.password && <span className='font-weight-light text-danger text-center'><small>{errors.password}</small></span>}
        </div>
        <div>
          <label htmlFor="confirmPassword">Confirm Password:</label>
          <input
            type="password"
            onChange={(event) => setConfirmPassword(event.target.value)}
            id="confirmPassword"
            name="confirmPassword"
            onBlur={handleErrors}
          />
          <br></br>
          {errors.confirmPassword && <span className='font-weight-light text-danger text-center'><small>{errors.confirmPassword}</small></span>}
        </div>
        <div>
          <label htmlFor="zipcode">Zipcode:</label>
          <input
            type="text"
            onChange={(event) => setZipcode(event.target.value)}
            id="zipcode"
            name="zipcode"
            onBlur={handleErrors}
          />
          <br></br>
          {errors.confirmPassword && <span className='font-weight-light text-danger text-center'><small>{errors.confirmPassword}</small></span>}
        </div>
        <div>
          <button className="btn btn-info m-2 mx-auto" 
                  type="submit" 
                  disabled={errors.username||errors.password||errors.confirmPassword}>
                    Create Account
          </button>
        </div>
      </form>
     </div>
     </div>
    );
}