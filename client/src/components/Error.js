<<<<<<< HEAD
function Error({ errors }) {
    return (<div className="alert alert-danger" role="alert">
             <ul>
               {errors.map(error => <li key={error}>{error}</li>)}
             </ul>
           </div>)
   }
   export default Error
=======
export default function Error({errors =[]}){
    console.log(errors);
    return (
    <div className="alert alert-danger" role="alert">
        <h3>Errors:</h3>
          <ul>
            {errors.map(error => <li key={error}>{error}</li>)}
          </ul>
        </div>)
}
>>>>>>> 21961eb46b271a725a697e722ac27916b185945e
