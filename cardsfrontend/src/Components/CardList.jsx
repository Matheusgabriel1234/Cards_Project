import React from 'react';
import CardItem from './CardItem';
import '../Styles/CardList.css';

function CardList({cards =[],deleteCard,editCard}){
    if(cards.length === 0){
        return <p>"Voce não possui nenhum cartão"</p>
    }

    return (
        <div className="card-list">
            {cards.map(card => (
                <CardItem
                    key={card.id}
                    card={card}
                    deleteCard={deleteCard}
                    editCard={editCard}
                />
            ))}
        </div>
    );
}

export default CardList