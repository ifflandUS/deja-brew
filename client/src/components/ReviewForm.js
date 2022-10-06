import { useEffect, useState } from 'react';
import { useHistory, useParams, useLocation } from 'react-router-dom';
import Error from './Error';
import { useContext } from "react";
import AuthContext from "./AuthContext";


const REVIEW_DEFAULT = { reviewId: 0, userId: 0, breweryId: '', rating: 0, review: '' }

function ReviewForm() {



    const [review, setReview] = useState(REVIEW_DEFAULT);
    const [errors, setErrors] = useState([]);

    const { reviewId } = useParams();
    const history = useHistory();
    const auth = useContext(AuthContext);
    const location = useLocation();


    useEffect(() => {
        if (reviewId) {
            fetch(`http://localhost:8080/review/${reviewId}`)
                .then(resp => {
                    switch (resp.status) {
                        case 200:
                            return resp.json();
                        case 404:
                            break;
                        default:
                            return Promise.reject('Yikes...Something went wrong.');
                    }
                })
                .then(body => {
                    if (body) {
                        setReview(body);
                    }
                })
                .catch(err => history.push('/error', { errorMessage: err }));
        }

    }, [])

    const saveReview = () => {
        const newReview = { ...review};
        newReview["breweryId"] = location.state.brewery.id;
        newReview["userId"] = auth.user.userId;
        
        const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Accept": "application/json",
            "Authorization": `Bearer ${auth.user.token}`
        },
        body: JSON.stringify(newReview)
    };


    fetch('http://localhost:8080/review', init)
        .then(resp => {
            if (resp.status === 201) {
                history.push({
                    pathname: `/Brewery/${location.state.brewery.id}`,
                    state: { breweryId: location.state.brewery.id }
                });
                return {};
            }
            else if (resp.status === 400) {
                return resp.json;
            }
            return Promise.reject('Yikes, something went wrong.');
        })
        .then(body => {
            if (body.id) {
                history.push({
                    pathname: `/Brewery/${location.state.brewery.id}`,
                    state: { breweryId: location.state.brewery.id }
                });
            }
            else {
                setErrors(body);
            }
        }).catch(err => history.push('/error', { errorMessage: err }));
     }


    const updateReview = () => {
        const updateReview = { ...review };

        const init = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                "Accept": "application/json",
                "Authorization": `Bearer ${auth.user.token}`
            },
            body: JSON.stringify(updateReview)
        }; 
        fetch(`http://localhost:8080/review/${reviewId}`, init)
            .then(resp => {
                switch (resp.status) {
                    case 204:
                        return null;
                    case 400:
                        return resp.json();
                    case 404:
                        history.push('/not-found', { id: reviewId });
                        break;
                    default:
                        return Promise.reject('Yikes...Something terrible has gone wrong.');

                }
            })
            .then(body => {
                if (!body) {
                    history.push({
                        pathname: `/Brewery/${location.state.brewery.id}`,
                        state: { breweryId: location.state.brewery.id }
                    })
                } else if (body) {
                    setErrors(body)
                    window.alert(body);

                }
            })
            .catch(err => history.push('/error', { errorMessage: err }));
    }


    const handleChange = (e) => {
        const property = e.target.name;
        const valueType = e.target.type === 'checkbox' ? 'checked' : 'value';
        const value = e.target[valueType];
        const newReview = { ...review };
        newReview[property] = value;
        setReview(newReview);
    }

    


    const onSubmit = (e) => {
        e.preventDefault();


        const fetchFunction = reviewId > 0 ? updateReview : saveReview;
        fetchFunction();


        history.push({
                    pathname: `/Brewery/${location.state.brewery.id}`,
                    state: { breweryId: location.state.brewery.id }
                });

    }
    const handleCancel = () => history.push({
        pathname: `/Brewery/${location.state.brewery.id}`,
        state: { breweryId: location.state.brewery.id }
    })

    return (<>
        <h2> Review</h2>
        {errors.length > 0 ? <Error errors={errors} /> : null}
        <form onSubmit={onSubmit}>




            <div className='form-group'>
                <label htmlFor='review'>Give us your experience</label>


                <input name='rating' type='number' className='form-control' id='rating' value={review.rating} onChange={handleChange} /></div>
            <div className='form-group'>
                <label htmlFor='review'>Enter Your Review</label>
                <input name='review' type='text' className='form-control' id='review' value={review.review} onChange={handleChange} /></div>
            <div className='form-group'>
                <button type='Submit' className='btn btn-success mr-3'>Submit</button>
                <button type='Cancel' className='btn btn-danger' onClick={handleCancel}>Cancel</button>
            </div>
            </form></>);

}


export default ReviewForm;