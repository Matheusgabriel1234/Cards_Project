import React from 'react';
import '../Styles/CardItem.css';
import bradescoLogo from '../Assets/logos/bradesco.svg';
import bancoDoBrasilLogo from '../Assets/logos/banco-do-brasil.svg';
import itauLogo from '../Assets/logos/itau.svg';
import santanderLogo from '../Assets/logos/santander.svg';
import caixaLogo from '../Assets/logos/caixa.svg';
import bancoInterLogo from '../Assets/logos/inter.svg';
import nuBankLogo from '../Assets/logos/nubank.svg';
import debitIcon from '../Assets/Icons/debit.svg';
import creditIcon from '../Assets/Icons/credit.svg';

const bankLogos = {
    BRADESCO: bradescoLogo,
    BANCO_DO_BRASIL: bancoDoBrasilLogo,
    ITAU: itauLogo,
    SANTANDER: santanderLogo,
    CAIXA: caixaLogo,
    BANCO_INTER: bancoInterLogo,
    NU_BANK: nuBankLogo,
};

const bankColors = {
    BRADESCO: '#FF0000',     
    BANCO_DO_BRASIL: '#F7DF0A', 
    ITAU: '#FF6900',          
    SANTANDER: '#EC1D25',        
     CAIXA: '#005CA9',          
    BANCO_INTER: '#FF7F00',   
    NU_BANK: '#8A05BE',       
};

const cardTypeStyles = {
    DEBIT: {
        icon: debitIcon,
        color: '#000000',
    },
    CREDIT: {
        icon: creditIcon,
        color: '#000000',
    },
};

function CardItem({ card, deleteCard, editCard }) {
    console.log('Card in CardItem:', card);
    console.log('Card ID:', card.id);

    const bankLogo = bankLogos[card.emissorBank] || null;
    const bankColor = bankColors[card.emissorBank] || '#555555';
    const cardType = card.cardType;
    const typeStyle = cardTypeStyles[cardType] || {};

    const formatDate = (date) => {
        const d = new Date(date);
        const month = ('0' + (d.getMonth() + 1)).slice(-2);
        const year = d.getFullYear().toString().slice(-2);
        return `${month}/${year}`;
    };

    return (
        <div
            className={`card-item card-${card.emissorBank} card-${cardType}`}
            style={{
                borderColor: bankColor,
                background: `linear-gradient(135deg, ${bankColor}, ${typeStyle.color || '#777777'})`,
            }}
        >
            <div className="card-top">
                {bankLogo && (
                    <img src={bankLogo} alt={`${card.emissorBank} Logo`} className="bank-logo" />
                )}
            </div>
            <div className="card-number">
                 {card.maskedCardNumber}
            </div>
            <div className="card-details">
                <div className="card-holder">
                    <span>Titular:</span> {card.name}
                </div>
                <div className="card-validity">
                    <span>Validade:</span> {formatDate(card.billExpireDate)}
                </div>
            </div>
            <div className="card-bottom">
                <div className="card-limits">
                    <span>Limite:</span> R$ {card.creditLimit}
                </div>
                <div className="card-available">
                    <span>Disponível:</span> R$ {card.availableLimit}
                </div>
            </div>
            {/* Novo contêiner para o tipo de cartão e botões */}
            <div className="card-footer">
                <div className="card-type">
                    {typeStyle.icon && (
                        <img src={typeStyle.icon} alt={`${cardType} Icon`} className="card-type-icon" />
                    )}
                    <span>{cardType === 'DEBIT' ? 'Débito' : 'Crédito'}</span>
                </div>
                <div className="card-actions">
                    <button onClick={() => editCard(card)} className="btn-edit">
                        Editar
                    </button>
                    <button onClick={() => deleteCard(card.id)} className="btn-delete">
                        Excluir
                    </button>
                </div>
            </div>
        </div>
    );
}

export default CardItem;
