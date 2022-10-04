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
