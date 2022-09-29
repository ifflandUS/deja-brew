import React, { useContext, useState } from "react";
import { useHistory } from "react-router-dom";
import AuthContext from "./AuthContext";


export default function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errors, setErrors] = useState([]);

  const auth = useContext(AuthContext);
  const history = useHistory();

  const handleSubmit = async (event) => {
    event.preventDefault();

    const response = await fetch("http://localhost:8080/authenticate", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({username, password,}),
  });

    if (response.status === 200) {
        const { jwt_token } = await response.json();
        auth.login(jwt_token);
        history.push("/");
    } else if (response.status === 403) {
        setErrors(["Login failed."]);
    } else {
        setErrors(["Unknown error."]);
    }
  };

  const handleClick = () => {
    history.push("/create-account");
  }

  return (
    <div>
     <h1 className="text-center">Login</h1>
     <div className="col-3 border m-4 mx-auto d-flex justify-self-center text-center">
      <form  onSubmit={handleSubmit}>
        <div>
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            onChange={(event) => setUsername(event.target.value)}
            id="username"
          />
        </div>
        <div>
          <label htmlFor="password">Password:</label>
          <input
            type="password"
            onChange={(event) => setPassword(event.target.value)}
            id="password"
          />
        </div>
        <div>
          <button className="btn btn-info m-2 mx-auto" type="submit">Login</button>
        </div>
      </form>
     </div>
     <p className="text-center">New to Deja-Brew?</p>
     <div>
          <button className="btn btn-info m-2 mx-auto d-flex justify-self-center"
          onClick={handleClick}>
            Create Account
          </button>
     </div>
    </div>
  );
}
