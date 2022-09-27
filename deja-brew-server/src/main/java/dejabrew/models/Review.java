package dejabrew.models;

public class Review {


    private int reviewId;
    private int userId;

    private int breweryId;

    private int rating;

    private String review;

    //private Brewery brewery;

    //private User user;
    public Review() {

    }
    public Review(int reviewId, int userId, int breweryId, int rating, String review) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.breweryId = breweryId;
        this.rating = rating;
        this.review = review;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBreweryId() {
        return breweryId;
    }

    public void setBreweryId(int breweryId) {
        this.breweryId = breweryId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

}