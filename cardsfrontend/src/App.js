import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Register from './Pages/Register';
import Login from "./Pages/Login"
import Dashboard from './Pages/Dashboard';
import PrivateRoute from './Auth/PrivateRoute';

function App() {
  return(
    <Router>
      <Routes>
        <Route path='/register' element={<Register />}></Route>
        <Route path='/login' element={<Login/>}></Route>      

       <Route 
          path="/dashboard" 
          element={
            <PrivateRoute>
              <Dashboard />
            </PrivateRoute>
          }/>       
      
      </Routes>
    </Router>
  )
}

export default App;
