import { BrowserRouter as Router, Link, Route, Switch } from 'react-router-dom';
import Home from './components/Home';
import Brewery from './components/Brewery';
import BrewerySearch from './components/BrewerySearch';
import NotFound from './components/NotFound';
import Profile from './components/Profile';
import ServerError from './components/ServerError';
import { useState, useEffect } from "react";
import AuthContext from "./components/AuthContext";
import NavBar from './components/NavBar';
import jwtDecode from 'jwt-decode';

import './index.css';
import Login from './components/Login';
import CreateAccount from './components/CreateAccount';

const LOCAL_STORAGE_TOKEN_KEY = "dejaBrewToken";

function App() {

  const [user, setUser] = useState(null);
  const [restoreLoginAttemptCompleted, setRestoreLoginAttemptCompleted] = useState(false);

  useEffect(() => {
    const token = localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY);
    if (token) {
      login(token);
    }
    setRestoreLoginAttemptCompleted(true);
  }, []);

  const login = (token) => {
    localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, token);

    console.log(jwtDecode(token));

    const { sub: username, authorities: authoritiesString, zipCode: zipCode} = jwtDecode(token);

    const roles = authoritiesString.split(',');

    const user = {
      username,
      zipCode,
      roles,
      token,
      hasRole(role) {
        return this.roles.includes(role);
      }
    };

    setUser(user);
    
    return user;
  };
  
  const logout = () => {
    setUser(null);
    localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY)
  };
  
  const auth = {
    user: user ? {...user} : null,
    login,
    logout
  };

  if (!restoreLoginAttemptCompleted) {
    return null;
  }


  return (
    <AuthContext.Provider value={auth}>
    <div className="App">
      <Router>
        <NavBar/>
        <div className='container'>
          <Switch>
            <Route exact path="/"><Home/></Route>
            <Route path="/BrewerySearch"><BrewerySearch/></Route>
            <Route exact path="/error"><ServerError/></Route>
            <Route path="/Profile"><Profile/></Route>
            <Route path="/log-in"><Login/></Route>
            <Route path="/create-account"><CreateAccount/></Route>
          </Switch>
        </div>


        <footer className="footer text-center text-lg-start bg-info">
          <div className='text-center p-3'>© 2020 Copyright: Deja-Brew</div></footer>
        </Router>
    </div>
    </AuthContext.Provider>
  );
}

export default App;
