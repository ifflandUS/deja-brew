// import { useHistory } from "react-router-dom";


// function Brewery(){

//     const [brewery, setBrewery] = useState([]);
  
//     const history = useHistory();
  
//     useEffect(() => {
//       fetch('http://localhost:8080/api/agent')
//       .then(resp => {
//         if (resp.status === 200) {
//           return resp.json();
//         }
//         return Promise.reject('Oops...Something went wrong.');
//       })
//       .then(data => {
//         setBrewery(data);
//       })
//       .catch(err => history.push('/error', {errorMessage: err}));
//     },[])

//     return(<>
    
    
    
    
    
    
//     </>)



// }

// export default Brewery;