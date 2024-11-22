// src/pages/Register.js
import React, { useState, useEffect } from 'react';
import axios from '../Auth/AxiosConfig';
import '../Styles/Register.css';
import { useNavigate, Link } from 'react-router-dom';
import Notification from '../Components/Notification';

function Register() {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        firstName: "",
        lastName: "",
        email: "",
        password: ""
    });
    const [notification, setNotification] = useState({
        message: "",
        type: "", 
        errors: []
    });

    const { firstName, lastName, email, password } = formData;

    const handleChange = (e) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value
        });
    }

    const handleSubmit = async (e) => {
      e.preventDefault();
      setNotification({ message: "", type: "", errors: [] });
      try {
          const response = await axios.post("/api/auth/register", formData);
          setNotification({ message: response.data.message || "Registro realizado com sucesso!", type: "success", errors: [] });
          setTimeout(() => {
              setNotification({ message: "", type: "", errors: [] });
              navigate("/");
          }, 3000);
      } catch (error) {
          if (error.response && error.response.data) {
              const { message, errors } = error.response.data;
              setNotification({ message: message || "Erro ao registrar usuário", type: "error", errors: errors || [] });
          } else {
              setNotification({ message: "Erro ao registrar usuário", type: "error", errors: [] });
          }
      }
  };
    useEffect(() => {
        if (notification.message) {
            const timer = setTimeout(() => {
                setNotification({ message: "", type: "", errors: [] });
            }, 3000);

            return () => clearTimeout(timer);
        }
    }, [notification]);

    return (
        <div className="register-container">
            <div className="logo">
                <div className="logo-link">Lume</div>
            </div>

            {notification.message && (
                <Notification 
                    message={notification.message} 
                    type={notification.type} 
                    errors={notification.errors}
                />
            )}

            <form className="register-form" onSubmit={handleSubmit}>
                <h2>Registrar-se</h2>

                <label htmlFor="firstName">Nome</label>
                <input 
                    type="text" 
                    id="firstName"
                    name="firstName" 
                    placeholder="Nome" 
                    value={firstName} 
                    onChange={handleChange} 
                    required 
                />

                <label htmlFor="lastName">Sobrenome</label>
                <input 
                    type="text" 
                    id="lastName"
                    name="lastName" 
                    placeholder="Sobrenome" 
                    value={lastName} 
                    onChange={handleChange} 
                    required 
                />

                <label htmlFor="email">Email</label>
                <input 
                    type="email" 
                    id="email"
                    name="email" 
                    placeholder="Email" 
                    value={email} 
                    onChange={handleChange} 
                    required 
                />

                <label htmlFor="password">Senha</label>
                <input 
                    type="password" 
                    id="password"
                    name="password" 
                    placeholder="Senha" 
                    value={password} 
                    onChange={handleChange} 
                    required 
                />

                <button type="submit" className="btn-primary">Registrar</button>
                <p>Já possui uma conta? <Link to="/">Login</Link></p>
            </form>
        </div>
    );
}

export default Register;
