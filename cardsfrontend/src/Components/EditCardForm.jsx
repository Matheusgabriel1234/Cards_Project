import React, { useState } from 'react';

const bankOptions = [
    { value: 'BRADESCO', label: 'Bradesco' },
    { value: 'BANCO_DO_BRASIL', label: 'Banco do Brasil' },
    { value: 'ITAU', label: 'Itaú' },
    { value: 'SANTANDER', label: 'Santander' },
    { value: 'CAIXA', label: 'Caixa Econômica' },
    { value: 'BANCO_INTER', label: 'Banco Inter' },
    { value: 'NUBANK', label: 'NuBank' },
    
];

const cardTypeOptions = [
    { value: 'DEBIT', label: 'Débito' },
    { value: 'CREDIT', label: 'Crédito' },
];

function EditCardForm({card,updateCard,cancelEdit}){
const [name,setName] = useState(card.name)
const [maskedcardNumber,setMaskedcardNumber] = useState(card.maskedCardNumber)
const [creditLimit,setCreditLimit] = useState(card.creditLimit)
const [availiableLimit,setAvailiableLimit] = useState(card.avaliableLimit)
const [emissorBank,setEmissorBank] = useState(card.emissorBank)
const [billExpireDate,setBillExpireDate] = useState(card.billExpireDate)
const [cardType,setCardType] = useState(card.cardType)

const handleSubmit = (e) =>{
    e.preventDefaut()
    if(!name || !maskedcardNumber || !emissorBank || !billExpireDate || !cardType){
        alert("Nenhum campo deve ficar vazio")
    }

    const updatedCard = {
        name,
        maskedcardNumber,
        creditLimit:parseFloat(creditLimit) || 0,
        availiableLimit: parseFloat(availiableLimit) || 0,
        emissorBank,
        billExpireDate,
        cardType
    }
   updateCard(card.id,updateCard)

}

return (
    <form onSubmit={handleSubmit} className="edit-card-form">
            <h2>Editar Cartão</h2>
            <label>Nome:</label>
            <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="Nome do titular"
                required
            />
            <label>Número do Cartão (Últimos 4 dígitos):</label>
            <input
                type="text"
                value={maskedcardNumber}
                onChange={(e) => setMaskedcardNumber(e.target.value)}
                placeholder="****"
                maxLength="4"
                pattern="\d{4}"
                title="Digite os últimos 4 números do cartão"
                required
            />
            <label>Limite de Crédito:</label>
            <input
                type="number"
                value={creditLimit}
                onChange={(e) => setCreditLimit(e.target.value)}
                placeholder="R$ 0,00"
                min="0"
            />
            <label>Limite Disponível:</label>
            <input
                type="number"
                value={availiableLimit}
                onChange={(e) => setAvailiableLimit(e.target.value)}
                placeholder="R$ 0,00"
                min="0"
            />
            <label>Emissor do Banco:</label>
            <select
                value={emissorBank}
                onChange={(e) => setEmissorBank(e.target.value)}
                required
            >
                <option value="">Selecione o banco emissor</option>
                {bankOptions.map((bank) => (
                    <option key={bank.value} value={bank.value}>
                        {bank.label}
                    </option>
                ))}
            </select>
            <label>Data de Vencimento:</label>
            <input
                type="month"
                value={billExpireDate}
                onChange={(e) => setBillExpireDate(e.target.value)}
                required
            />
            <label>Tipo de Cartão:</label>
            <select
                value={cardType}
                onChange={(e) => setCardType(e.target.value)}
                required
            >
                <option value="">Selecione o tipo de cartão</option>
                {cardTypeOptions.map((type) => (
                    <option key={type.value} value={type.value}>
                        {type.label}
                    </option>
                ))}
            </select>
            <div className="form-actions">
                <button type="submit" className="btn-primary">Atualizar</button>
                <button type="button" onClick={cancelEdit} className="btn-secondary">Cancelar</button>
            </div>
        </form>
)
}

export default EditCardForm