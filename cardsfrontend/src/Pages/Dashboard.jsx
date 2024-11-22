import React, { useEffect, useState } from 'react';
import axios from '../Auth/AxiosConfig';
import CardList from '../Components/CardList';
import AddCardForm from '../Components/AddCardForm';
import EditCardForm from '../Components/EditCardForm';
import Notification from '../Components/Notification';
import '../Styles/Dashboard.css';
import { useNavigate } from 'react-router-dom';

function Dashboard() {
    const [cards, setCards] = useState([]);
    const [notification, setNotification] = useState({ message: '', type: 'info', errors: [] });
    const [editingCard, setEditingCard] = useState(null);
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        const token = localStorage.getItem('token');
        if (!token) {
            navigate('/login');
        }
    }, [navigate]);

    const fetchCards = async () => {
        try {
            const response = await axios.get('/api/cards/my-cards');
            setCards(response.data);
        } catch (error) {
            console.error('Erro ao buscar cartões:', error);
            const errorMessage = error.response?.data?.message || 'Erro ao buscar cartões.';
            const errorDetails = error.response?.data?.errors || [];
            setNotification({ message: errorMessage, type: 'error', errors: errorDetails });
        }
    };

    useEffect(() => {
        fetchCards();
    }, []);

    const addCard = async (cardData) => {
        try {
            const response = await axios.post('/api/cards', cardData);
            setCards([...cards, response.data]);
            setNotification({ message: 'Cartão adicionado com sucesso!', type: 'success', errors: [] });
        } catch (error) {
            console.error('Erro ao adicionar cartão:', error);
            const errorMessage = error.response?.data?.message || 'Erro ao adicionar cartão.';
            const errorDetails = error.response?.data?.errors || [];
            setNotification({ message: errorMessage, type: 'error', errors: errorDetails });
        }
    };

    const updateCard = async (updatedCard) => {
        try {
            const response = await axios.put(`/api/cards/${updatedCard.id}`, updatedCard);
            const updatedCards = cards.map(card => card.id === updatedCard.id ? response.data : card);
            setCards(updatedCards);
            setNotification({ message: 'Cartão atualizado com sucesso!', type: 'success', errors: [] });
            setEditingCard(null);
        } catch (error) {
            console.error('Erro ao atualizar cartão:', error);
            const errorMessage = error.response?.data?.message || 'Erro ao atualizar cartão.';
            const errorDetails = error.response?.data?.errors || [];
            setNotification({ message: errorMessage, type: 'error', errors: errorDetails });
        }
    };

    const deleteCard = async (id) => {
        if (!window.confirm('Tem certeza que deseja excluir este cartão?')) {
            return;
        }
        try {
            await axios.delete(`/api/cards/${id}`);
            const updatedCards = cards.filter(card => card.id !== id);
            setCards(updatedCards);
            setNotification({ message: 'Cartão excluído com sucesso!', type: 'success', errors: [] });
        } catch (error) {
            console.error('Erro ao excluir cartão:', error);
            const errorMessage = error.response?.data?.message || 'Erro ao excluir cartão.';
            const errorDetails = error.response?.data?.errors || [];
            setNotification({ message: errorMessage, type: 'error', errors: errorDetails });
        }
    };

    const handleLogout = () => {
        localStorage.removeItem('token');
        navigate('/');
    };

    useEffect(() => {
        if (notification.message) {
            const timer = setTimeout(() => {
                setNotification({ message: '', type: 'info', errors: [] });
            }, 5000);
            return () => clearTimeout(timer);
        }
    }, [notification]);

    return (
        <div className="dashboard-container">
            <Notification message={notification.message} type={notification.type} errors={notification.errors} />
            <div className="dashboard-header">
                <h1>Meu Dashboard</h1>
                <div className="header-buttons">
                    <button onClick={() => setIsAddModalOpen(true)} className="btn-primary">Adicionar Cartão</button>
                    <button onClick={handleLogout} className="btn-secondary">Logout</button>
                </div>
            </div>
            {isAddModalOpen && (
                <AddCardForm
                    addCard={addCard}
                    closeModal={() => setIsAddModalOpen(false)}
                />
            )}
            {editingCard && (
                <EditCardForm
                    card={editingCard}
                    updateCard={updateCard}
                    cancelEdit={() => setEditingCard(null)}
                />
            )}
            <CardList
                cards={cards}
                deleteCard={deleteCard}
                editCard={(card) => setEditingCard(card)}
            />
        </div>
    );
}

export default Dashboard;
