import Carousel from 'react-bootstrap/Carousel';



function Home() {
    return (
    <><h1 className="py-4">Welcome to Deja-Brew!</h1>

    <p>The #1 site to see what other Beer Lovers are drinking and where.</p>
    <div className="text-center"><video className="video" width="750" height="500" controls loop autoPlay={true} muted>
      <source src="beer_pouring.mp4" type="video/mp4"/>
     </video> </div>
      


    </>
    

    )
  }
  
  export default Home;