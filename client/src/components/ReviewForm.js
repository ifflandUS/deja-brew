import { useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import Error from './Error';

const REVIEW_DEFAULT = {reviewId:0, appUserId:0, breweryId:'', rating:0, review:''}
function ReviewForm(){
    const [review, setReview] = useState(REVIEW_DEFAULT);
    const [errors, setErrors] = useState([]);
    const history = useHistory();

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
        const init = {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json'
            },
            body: JSON.stringify({...review})
          };
        fetch('http://localhost:3000/review',init)
        .then(resp => {if(resp.status===201 || resp.status===400){
            return resp.json;
        }
        return Promise.reject('Yikes, something went wrong.');
        })
        .then(body=>{
            if(body.id){
                history.goBack();
            }
            else{
                setErrors(body);
            }}).catch(err=>history.push('/error',{errorMessage:err}));

        history.goBack();    
    }
        return(<>
        <h2>Write A Review</h2>
        {errors.length > 0 ? <Error errors={errors} /> : null}
        <form onSubmit={onSubmit}>
            <div className='form-group'>
                <label htmlFor='rating'>Rate This Brewery 1-5</label>
                <input name='rating' type='number' className='form-control' id='rating' value={review.rating} onChange={handleChange}/></div>
            <div className='form-group'>
                <label htmlFor='review'>Enter Your Review</label>
                <input name='review' type='text' className='form-control' id='review' value={review.review} onChange={handleChange}/></div>
                <div className='form-group'>
                    <button type='Submit' className='btn btn-success mr-3'>Submit</button>
                    <button type='Cancel' className='btn btn-danger'>Cancel</button>
                </div></form></>)
}
export default ReviewForm;