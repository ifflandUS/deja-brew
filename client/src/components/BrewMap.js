import  React, { useRef, useEffect, useState }  from 'react';
import  mapboxgl  from 'mapbox-gl';
import './BrewMap.css';
import { useContext } from "react";
import AuthContext from "./AuthContext";
import { useHistory } from 'react-router-dom';
import BeerForm from './BeerForm';

mapboxgl.accessToken = 'pk.eyJ1IjoiaWZmbGFuZCIsImEiOiJjbDhvcWxneXUweWY4M3pvOHc0dXppNmprIn0.sOSxXUtdokO2Ubi_ViG1GQ';
const API_KEY = "AIzaSyBMJLC9_22UyU322aYc95X4ZGayEqyR7Go";
export default function BrewMap({breweryVisits}){
    const mapContainer = useRef(null);
    const map = useRef(null);
    const auth = useContext(AuthContext);
    const history = useHistory();
    const [lng, setLng] = useState(-94.7071);
    const [lat, setLat] = useState(32.5625);
    const [zoom, setZoom] = useState(12);
    

    fetch("https://maps.googleapis.com/maps/api/geocode/json?address="+auth.user.zipCode+'&key='+API_KEY)
              .then(response => response.json())
              .then(data => {
                setLat(data.results.at(0).geometry.location.lat);
                setLng(data.results.at(0).geometry.location.lng);
            }).then(
                ()=>{
                    if (map.current){
                        map.current.setCenter([lng, lat])
                    }
                }
            )
            .catch()

    const handleClick = (id) => {
        history.push({ 
            pathname: `/Brewery/${id}` ,
            state: {breweryId: id}
           });
    }

    useEffect(() => {
    
        if (map.current) return; 
        map.current = new mapboxgl.Map({
        container: mapContainer.current,
        style: 'mapbox://styles/mapbox/streets-v11',
        center: [lng, lat],
        zoom: zoom
        }); 
        });

        if (map.current){
            fetch(`https://api.openbrewerydb.org/breweries?by_dist=${lat},${lng}`)
            .then(response => response.json())
            .then(data => {
    
              for(let i = 0; i < data.length;i++){
                if (breweryVisits.includes(data.at(i).id)){
                    const marker = new mapboxgl.Marker({
                        color: "#28a745",
                        draggable: false
                        })
                    .setLngLat([data.at(i).longitude, data.at(i).latitude])
                    .setPopup(new mapboxgl.Popup()
                                .setHTML(`<h2 >${data.at(i).name}</h2>
                                        <a href=${data.at(i).website_url}>Visit Web Page</a>`)
                                        )
                    .addTo(map.current);




                } else{
                    const marker = new mapboxgl.Marker({
                        color: "#daa520",
                        draggable: false
                        })
                    .setLngLat([data.at(i).longitude, data.at(i).latitude])
                    .setPopup(new mapboxgl.Popup()
                               .setHTML(`<h2 ">${data.at(i).name}</h2>
                                        <a href=${data.at(i).website_url}>Visit Web Page</a>`)
                                        )
                                        
                    .addTo(map.current);

                    
                }
              }
          }) 
        }

        
    return(
      <>
      <div ref={mapContainer} className="map-container" />
      <br></br>
      </>
    )
}