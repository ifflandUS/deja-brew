import Carousel from 'react-bootstrap/Carousel';



function Home() {
    return (
    <><h1 className="py-4">Welcome to Deja-Brew!</h1>

    <p>The #1 site to see what other Beer Lovers are drinking and where.</p>
    <div className="text-center"><video className="video" width="750" height="500" controls loop autoPlay={true} muted>
      <source src="beer_pouring.mp4" type="video/mp4"/>
     </video> </div>

        {/* will need to use serp API here to get 1)photo of beer and 2)photos of breweries for front page */}
      <Carousel>
      <Carousel.Item interval={1000}>
        <img
          className="d-block w-100"
          src='test_images/download-1.jpg'
          alt="Most popular beer on Deja-Brew"
        />
        <Carousel.Caption>
          <h3>Most Popular Beer</h3>
          <p>Nulla vitae elit libero, a pharetra augue mollis interdum.</p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item interval={500}>
        <img
          className="d-block w-100"
          src='test_images/download.jpg'
          alt="Highest Rated Brewery on Deja-Brew"
        />
        <Carousel.Caption>
          <h3>Highest Rated Brewery</h3>
          <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
        </Carousel.Caption>
      </Carousel.Item>
      <Carousel.Item>
        <img
          className="d-block w-100"
          src='test_images/images.jpg'
          alt="featured Brewery on Deja-Brew"
        />
        <Carousel.Caption>
          <h3>Wild Card</h3>
          <p>
            Praesent commodo cursus magna, vel scelerisque nisl consectetur.
          </p>
        </Carousel.Caption>
      </Carousel.Item>
    </Carousel>


    </>
    

    )
  }
  
  export default Home;