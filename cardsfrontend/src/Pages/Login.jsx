import React, { useState, useEffect, useContext } from 'react';
import axios from '../Auth/AxiosConfig'; 
import '../Styles/Login.css'; 
import { AuthContext } from '../Auth/AuthContext';
import { useNavigate, Link } from 'react-router-dom';
import Notification from '../Components/Notification';

function Login(){
const navigate = useNavigate()
const {loginUser} = useContext(AuthContext)
const [formData,setFormData] = useState({
    email:"",
    password:""
    })

const [notification,setNotification] = useState({
    message:"",
    type:""
})

const {email,password} = formData


const handleChange = (e) => {
    setFormData({
        ...formData,
        [e.target.name]: e.target.value
    })
}

const handleSubmit = async (e) => {
e.preventDefault()
setNotification({message:"",type:""})
try {
const response = await axios.post("/api/auth/login",formData)
const token = response.data.token
loginUser(token)
setNotification({message:"Login realizado com sucesso!",type:"success"})
setTimeout(()=>{
    setNotification({message:"",type:""})
    navigate("/dashboard")
},3000)
} catch (error) {
if(error.response && error.response.data){
    setNotification({message:error.response.data,type:"error"})
}
else{
    setNotification({message:"Erro ao realizar o Login",type:"error"})

}
}
}

useEffect(()=>{
    if(notification.message){
        const timer = setTimeout(()=>{
            setNotification({message:"",type:""})
        },3000)
        return ()=> clearTimeout(timer)
    }
},[notification])

return (
    <div className="login-container">
        {/* Logotipo "Lume" */}
        <div className="logo">
            <Link to="/" className="logo-link">Lume</Link>
        </div>

        {/* Notificação de Erro/Sucesso */}
        {notification.message && (
            <Notification 
                message={notification.message} 
                type={notification.type} 
            />
        )}

        <form className="login-form" onSubmit={handleSubmit}>
            <h2>Login</h2>

            <label htmlFor="email">Email</label>
            <input 
                type="email" 
                id="email"
                name="email" 
                placeholder="Digite seu email" 
                value={email} 
                onChange={handleChange} 
                required 
            />

            <label htmlFor="password">Senha</label>
            <input 
                type="password" 
                id="password"
                name="password" 
                placeholder="Digite sua senha" 
                value={password} 
                onChange={handleChange} 
                required 
            />

            <button type="submit" className="btn-primary">Entrar</button>
            <p>Não possui uma conta? <Link to="/register">Registrar-se</Link></p>
        </form>
    </div>
);
}

export default Login;

