import { useHistory } from "react-router-dom";
//import {PBR} from '/test_images/images.jpg';

function ServerError() {

  const history = useHistory();
  
  const errorMessage = history.location.state ? history.location.state.errorMessage : null;

  return(<>
    <h2>Our bad... PBR on us</h2>
    <img src='' alt="Pabst Blue Ribbon "/>
    {errorMessage && (<div className="alert alert-danger">{`${errorMessage}`}</div>)}
  </>)
}

export default ServerError;