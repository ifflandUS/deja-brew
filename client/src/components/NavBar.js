import { Navbar } from "react-bootstrap";
import { Link, useHistory } from 'react-router-dom';
import logo from '../db_logo.PNG';
import { useContext } from "react";
import AuthContext from "./AuthContext";

export default function NavBar() {

    const auth = useContext(AuthContext);
    const history = useHistory();

    const handleClick = () => {
        auth.logout();
        history.push("/");
    }

    return(
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
                {auth.user && 
                    <li className="nav-item">
                        <Link to="/Profile" className='nav-link'>Profile</Link>
                    </li>
                }   
            </ul>
            { auth.user ? 
                <form className="container-fluid justify-content-end">
                    <button className="btn btn-sm btn-light btn-outline-secondary" 
                            type="button"
                            onClick={handleClick}>
                        Log Out
                    </button>  
                </form>
                :
                <form className="container-fluid justify-content-end">
                    <Link to ="/log-in" className='nav-link'>
                        <button className="btn btn-sm btn-light btn-outline-secondary" type="button">Log In</button>
                    </Link>
                </form>
            }
            
          </div>
        </nav>
    );
}