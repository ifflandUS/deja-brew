import { useContext } from "react";
import AuthContext from "./AuthContext";
import { useHistory, useLocation } from "react-router-dom";



function Review({review, handleDelete}){
  const history = useHistory();
  const auth = useContext(AuthContext);
    const location = useLocation();



  const handleDeleteClick = () => {

    var deleteReview = prompt('Delete Review?[y/n]');
    
    if(deleteReview==='y'){

    const init = {
      method: 'DELETE',
      "Authorization": `Bearer ${auth.user.token}`
    };

    fetch(`http://localhost:8080/review/${review.reviewId}`, init)
    .then( resp => {
      switch(resp.status) {
        case 204:
          return null;
        case 404:
          history.push('/not-found', { id: review.reviewId })
          break;
        default:
          return Promise.reject('Oops... something went wrong');
      }
    })
    .then(resp => {
      if (!resp) {
        //success

        handleDelete(review.reviewId);
      } else {
        console.log(resp);
      }
    })
    .catch(err => history.push('/error', {errorMessage: err}));
  }

}




  const handleEditClick = () => history.push({
      pathname:`/review/update`,
      state: {review: review}});
  

    return(
    <tr>
      <td>{review.rating}</td>
      <td>{review.review}</td>
      <td>
        <button type="button" className="btn btn-success mr-3" onClick={handleEditClick} >edit </button>
        <button type="button" className="btn btn-danger" onClick={handleDeleteClick}>delete</button>
      </td>
      
    </tr>
    )
}

export default Review;