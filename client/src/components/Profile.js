import { MapContainer, TileLayer, useMap, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';


//visit beer bridge table but can still work on map


function Profile({visit, beer, handleDelete}){

   


return(
<>
    <h2>Profile</h2>
    <MapContainer center={[51.505, -0.09]} zoom={13} scrollWheelZoom={false} height="500" width="500">
    <TileLayer
    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"/>
  
    </MapContainer>
<div></div>

</>
)
}

export default Profile;