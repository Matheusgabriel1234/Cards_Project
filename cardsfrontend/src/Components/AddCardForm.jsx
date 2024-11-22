import React, { useState } from 'react';
import '../Styles/AddCardForm.css';

function AddCardForm({ addCard, closeModal }) {
    const [formData, setFormData] = useState({
        name: '',
        maskedCardNumber: '',
        creditLimit: '',
        availableLimit: '',
        emissorBank: '',
        billExpireDate: '',
        cardType: 'DEBIT',
    });

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const dataToSubmit = {
            ...formData,
            creditLimit: parseFloat(formData.creditLimit),
            availableLimit: parseFloat(formData.availableLimit),
            maskedCardNumber: formData.maskedCardNumber,
        };
        try {
            await addCard(dataToSubmit);
            setFormData({
                name: '',
                maskedCardNumber: '',
                creditLimit: '',
                availableLimit: '',
                emissorBank: '',
                billExpireDate: '',
                cardType: 'DEBIT',
            });
            closeModal();
        } catch (error) {
            console.error('Erro ao adicionar cartão:', error);
            // Opcional: Exibir mensagem de erro ao usuário
        }
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <h2>Adicionar Novo Cartão</h2>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <label htmlFor="name">Nome do Titular</label>
                        <input
                            type="text"
                            id="name"
                            name="name"
                            value={formData.name}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="maskedCardNumber">Últimos 4 dígitos</label>
                        <input
                            type="text"
                            id="maskedCardNumber"
                            name="maskedCardNumber"
                            value={formData.maskedCardNumber}
                            onChange={handleChange}
                            required
                            maxLength="4"
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="creditLimit">Limite de Crédito</label>
                        <input
                            type="number"
                            id="creditLimit"
                            name="creditLimit"
                            value={formData.creditLimit}
                            onChange={handleChange}
                            required
                            min="0"
                            step="0.01"
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="availableLimit">Limite Disponível</label>
                        <input
                            type="number"
                            id="availableLimit"
                            name="availableLimit"
                            value={formData.availableLimit}
                            onChange={handleChange}
                            required
                            min="0"
                            step="0.01"
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="emissorBank">Banco Emissor</label>
                        <select
                            id="emissorBank"
                            name="emissorBank"
                            value={formData.emissorBank}
                            onChange={handleChange}
                            required
                        >
                            <option value="">Selecione um banco</option>
                            <option value="BRADESCO">Bradesco</option>
                            <option value="BANCO_DO_BRASIL">Banco do Brasil</option>
                            <option value="ITAU">Itaú</option>
                            <option value="SANTANDER">Santander</option>
                            <option value="CAIXA">Caixa</option>
                            <option value="BANCO_INTER">Banco Inter</option>
                            <option value="NU_BANK">Nubank</option>
                        </select>
                    </div>
                    <div className="form-group">
                        <label htmlFor="billExpireDate">Data de Expiração</label>
                        <input
                            type="date"
                            id="billExpireDate"
                            name="billExpireDate"
                            value={formData.billExpireDate}
                            onChange={handleChange}
                            required
                        />
                    </div>
                    <div className="form-group">
                        <label htmlFor="cardType">Tipo de Cartão</label>
                        <select
                            id="cardType"
                            name="cardType"
                            value={formData.cardType}
                            onChange={handleChange}
                            required
                        >
                            <option value="DEBIT">Débito</option>
                            <option value="CREDIT">Crédito</option>
                        </select>
                    </div>
                    <div className="modal-buttons">
                        <button type="submit" className="btn-primary">Adicionar</button>
                        <button type="button" onClick={closeModal} className="btn-secondary">Cancelar</button>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default AddCardForm;
