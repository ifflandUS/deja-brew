import './Error.css';

export default function Error({errors =[]}){
    console.log(errors);
    return (
    <div className="alert alert-danger alert-trim" role="alert">    
        {errors.map(error => <p key={error}>{error}</p>)}    
    </div>)
}
