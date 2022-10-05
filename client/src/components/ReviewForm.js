import { useEffect, useState } from 'react';
import { useHistory, useParams, useLocation } from 'react-router-dom';
import Error from './Error';
import { useContext } from "react";
import AuthContext from "./AuthContext";

const REVIEW_DEFAULT = {reviewId:0, userId:0, breweryId:'', rating:0, review:''}
function ReviewForm(){
    const [review, setReview] = useState(REVIEW_DEFAULT);
    const [errors, setErrors] = useState([]);
    const history = useHistory();
    const auth = useContext(AuthContext);
    const location = useLocation();

    const handleChange = (e) => {
        const property = e.target.name;
        const valueType = e.target.type === 'checkbox' ? 'checked' : 'value';
        const value = e.target[valueType];
        const newReview = {...review};
        newReview[property] = value;
        setReview(newReview);
    }

    const handleCancel = () => history.goBack();

    const onSubmit = (e) => {
        e.preventDefault();
        const newReview = {...review};
        newReview["breweryId"] = location.state.brewery.id;
        newReview["userId"] = auth.user.userId;
       




        const init = {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
              "Accept": "application/json",
                "Authorization": `Bearer ${auth.user.token}`
            },
            body: JSON.stringify({newReview})
          };
        fetch('http://localhost:8080/review',init)
        .then(resp => {if(resp.status===201 || resp.status===400){
            return resp.json;
        }
        return Promise.reject('Yikes, something went wrong.');
        })
        .then(body=>{
            if(body.id){
                history.push({ 
                    pathname: `/Brewery/${location.state.brewery.id}` ,
                    state: {brewery: location.state.brewery}
                   });
            }
            else{
                setErrors(body);
            }}).catch(err=>history.push('/error',{errorMessage:err}));

        history.push({ 
            pathname: `/Brewery/${location.state.brewery.breweryId}` ,
            state: {brewery: location.state.brewery}
           });    
    }
    const ratingClick = (e) =>(
        e.preventDefault()
    
        // switch(e.target.name){
        //     case rating1:
        //         review.rating = 1
        //         break;
        //     case rating2:
        //         review.rating = 2
        //         break;
        //     case rating3:
        //         review.rating = 3
        //         break;
        //     case rating4:
        //         review.rating = 4
        //         break;
        //     case rating5:
        //         review.rating = 5
        //         break;


        // }

    )
        return(<>
        <h2>Write A Review</h2>
        {errors.length > 0 ? <Error errors={errors} /> : null}
        <form onSubmit={onSubmit}>
           {/* <div className='form-group'>
                <label htmlFor='rating'>Rate This Brewery 1-5</label>
                <input name='rating1' type='radio' className='form-Control' id='rating' value={review.rating} onClick={ratingClick} onChange = {handleChange}>image</input>
                <input name='rating2' type='radio' className='form-Control' id='rating' value={review.rating} onClick={ratingClick} onChange = {handleChange}>image</input>
                <input name='rating3' type='radio' className='form-Control' id='rating' value={review.rating} onClick={ratingClick} onChange = {handleChange}>image</input>
                <input name='rating4' type='radio' className='form-Control' id='rating' value={review.rating} onClick={ratingClick} onChange = {handleChange}>image</input>
                <input name='rating' type='radio' className='form-Control' id='rating' value={review.rating} onClick={ratingClick} onChange = {handleChange}>image</input>
        
        </div>*/}


            <div className='form-group'>
                <label htmlFor='review'>Give us your experience</label>
                <input name='review' type='text' className='form-control' id='review' value={review.review} onChange={handleChange}/></div>

                <div><label htmlFor='rating'>Give this Brewery a rating 1-5</label><input name='rating' type='number' className='form-control' id='rating' value={review.rating} onChange={handleChange}/></div>

                <div className='form-group'>
                    <button type='Submit' className='btn btn-success mr-3'>Submit</button>
                    <button type='Cancel' className='btn btn-danger' onClick={handleCancel}>Cancel</button>
                </div></form></>)
}
export default ReviewForm;