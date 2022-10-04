



function Review({review}){

    return(
    <tr>
      <td>{review.rating}</td>
      <td>{review.review}</td>
    </tr>
    )
}

export default Review;