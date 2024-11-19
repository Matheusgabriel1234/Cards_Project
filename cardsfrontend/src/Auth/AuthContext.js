import React, { createContext, useState, useEffect } from 'react';
import axios from './AxiosConfig';
import {jwtDecode} from 'jwt-decode';


export const AuthContext = createContext()

export const AuthProvider = ({ children }) => {
    const [authState, setAuthState] = useState({
        token: localStorage.getItem("token") || null,
        user: localStorage.getItem("token") ? jwtDecode(localStorage.getItem("token")) : null // Adicionando fallback
    });


useEffect(()=>{
    if(authState.token){
        axios.defaults.headers.common["Authorization"] = `Bearer ${authState.token}`
}else{
    delete axios.defaults.headers.common["Authorization"]
}
},[authState.token])


const loginUser = (token) =>{
    localStorage.setItem("token",token)
    const user = jwtDecode(token)
    setAuthState({token,user})
    }


const logoutUser = (token) =>{
localStorage.removeItem("token")
setAuthState({token:null,user:null})
}

return(
    <AuthContext.Provider value={{authState,loginUser,logoutUser}} >
        {children}

    </AuthContext.Provider>
)
}