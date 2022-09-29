import { BrowserRouter as Router, Link, Route, Switch } from 'react-router-dom';
import Home from './components/Home';
import Brewery from './components/Brewery';
import BrewerySearch from './components/BrewerySearch';
import NotFound from './components/NotFound';
import Profile from './components/Profile';
import ServerError from './components/ServerError';
import logo from './db_logo.PNG';

import './index.css';


function App() {
  return (
    <div className="App">
      <Router>
        <nav className="navbar navbar-dark navbar-expand-md bg-info">
          <Link className='navbar-brand' to="/"><img src={logo} width="250" height="70"alt="Deja-Brew logo"/></Link>
          <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarText">
            <ul className="navbar-nav mr-auto">
              <li className="nav-item">
                <Link to ="/" className='nav-link'>Home</Link>
              </li>
              <li className="nav-item"><Link to="/BrewerySearch" className='nav-link'>Brewery Search</Link></li>
              <li className="nav-item">
                <Link to="/Profile" className='nav-link'>Profile</Link>
              </li>
            </ul>
          </div>
        </nav>
        <div className='container'>
          <Switch>
            <Route exact path="/"><Home/></Route>
            <Route path="/BrewerySearch"><BrewerySearch/></Route>
            <Route exact path="/error"><ServerError/></Route>
            <Route path="/Profile"><Profile/></Route>
        
          </Switch>
        </div>


        <footer className="footer text-center text-lg-start bg-info">
          <div className='text-center p-3'>© 2020 Copyright: Deja-Brew</div></footer>
        </Router>
    </div>
  );
}

export default App;
