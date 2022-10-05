import  React, { useRef, useEffect, useState }  from 'react';
import  mapboxgl  from 'mapbox-gl';
import './BrewMap.css';
import { useContext } from "react";
import AuthContext from "./AuthContext";
import { useHistory } from 'react-router-dom';

mapboxgl.accessToken = 'pk.eyJ1IjoiaWZmbGFuZCIsImEiOiJjbDhvcWxneXUweWY4M3pvOHc0dXppNmprIn0.sOSxXUtdokO2Ubi_ViG1GQ';
const API_KEY = "AIzaSyBMJLC9_22UyU322aYc95X4ZGayEqyR7Go";
export default function BreweryMap({brewery}){
    const mapContainer = useRef(null);
    const map = useRef(null);
    const auth = useContext(AuthContext);
    const history = useHistory();
    

    

    useEffect(() => {
        console.group(brewery)
    
        if (map.current) return; 
        map.current = new mapboxgl.Map({
        container: mapContainer.current,
        style: 'mapbox://styles/mapbox/streets-v11',
        center: [parseFloat(brewery.longitude), parseFloat(brewery.latitude)],
        zoom: 12
        }); 

        if (map.current){
            
                    const marker = new mapboxgl.Marker({
                        color: "#daa520",
                        draggable: false
                        })
                    .setLngLat([brewery.longitude, brewery.latitude])
                    .addTo(map.current);
        }
          
    },[]);

        
    return(
      <>
      <div ref={mapContainer} className="map-container" />
      <br></br>
      </>
    )
}