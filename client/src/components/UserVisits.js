import  React, { useEffect, useState, useContext }  from 'react';
import AuthContext from "./AuthContext";
import Visit from './Visit';

export default function UserVisits({visits}) {
    return (
       
        <div className="overflow-auto mb-5">
              <ul className='list-group '>
                {visits.length > 1 && visits.map(visit => <Visit key={visit.visitId} visit={visit}/> )}
                {visits.length < 1 && <p>Get out there and drink some beer!</p>}
            </ul>    
            <br></br>         
        </div>
        
    )
}