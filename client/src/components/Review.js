
function Review({review}){

    return(
    <tr key={review.id}>

      <td>{review.rating}</td>
      <td>{review.review}</td>
    </tr>
    )
}

export default Review;