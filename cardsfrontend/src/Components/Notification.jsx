import React from 'react';
import '../Styles/Register.css';

function Notification({ message = "", type, errors =[] }) {
    return (
        <div className={`error-notification ${type}`}>
            <i className={`fa ${type === 'error' ? 'fa-times-circle' : 'fa-check-circle'} icon`}></i>
            <div>
                <p>{message}</p>
                {type === 'error' && errors.length > 0 && (
                    <ul>
                        {errors.map((err, index) => (
                            <li key={index}>{err.fieldName}: {err.message}</li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}

export default Notification;
