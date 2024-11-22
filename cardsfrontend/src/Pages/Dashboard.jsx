import React, { useEffect, useState } from 'react';
import axios from '../Auth/AxiosConfig';
import CardList from '../Components/CardList';
import AddCardForm from '../Components/AddCardForm';
import EditCardForm from '../Components/EditCardForm';
import Notification from '../Components/Notification';
import '../Styles/Dashboard.css';
import { useNavigate } from 'react-router-dom';
import { FiPlus, FiLogOut, FiCreditCard, FiSend, FiX } from 'react-icons/fi';

function Dashboard() {
    const [cards, setCards] = useState([]);
    const [notification, setNotification] = useState({ message: '', type: 'info', errors: [] });
    const [editingCard, setEditingCard] = useState(null);
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [activeSection, setActiveSection] = useState('cards'); 
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
            setIsAddModalOpen(false);
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

    useEffect(() => {
       
        if (isAddModalOpen || editingCard) {
            document.body.style.overflow = 'hidden';
        } else {
            document.body.style.overflow = 'auto';
        }
    }, [isAddModalOpen, editingCard]);

    return (
        <div className="dashboard-container">
            {notification.message && (
                <Notification message={notification.message} type={notification.type} errors={notification.errors} />
            )}
            <div className="dashboard-header">
                <h1>Meu Dashboard</h1>
                <div className="header-buttons">
                    <button onClick={() => setIsAddModalOpen(true)} className="icon-button icon-button-primary" aria-label="Adicionar Cartão">
                        <FiPlus size={24} />
                    </button>
                    <button onClick={handleLogout} className="icon-button icon-button-secondary" aria-label="Logout">
                        <FiLogOut size={24} />
                    </button>
                </div>
            </div>

         
            <div className="dashboard-icons">
                <button
                    onClick={() => setActiveSection('pix')}
                    className={`icon-button icon-button-pix ${activeSection === 'pix' ? 'active' : ''}`}
                    aria-label="Pix"
                >
                    <FiSend size={24} />
                </button>
                <button
                    onClick={() => setActiveSection('cards')}
                    className={`icon-button icon-button-cards ${activeSection === 'cards' ? 'active' : ''}`}
                    aria-label="Cartões"
                >
                    <FiCreditCard size={24} />
                </button>
            </div>

            {/* Renderização Condicional das Seções */}
            {activeSection === 'cards' && (
                <div className="card-section">
                    {isAddModalOpen && (
                        <div className="modal-overlay" onClick={(e) => {
                            if (e.target.classList.contains('modal-overlay')) {
                                setIsAddModalOpen(false);
                            }
                        }}>
                            <div className="modal-content">
                                <button className="close-button" onClick={() => setIsAddModalOpen(false)} aria-label="Fechar Modal">
                                    <FiX size={24} />
                                </button>
                                <AddCardForm
                                    addCard={addCard}
                                    closeModal={() => setIsAddModalOpen(false)}
                                />
                            </div>
                        </div>
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
            )}
            {activeSection === 'pix' && (
                <div className="pix-section">
                    <h2>Gerenciar Pix</h2>
                    {/* Aqui você pode adicionar componentes ou funcionalidades relacionadas ao Pix */}
                    <p>Conteúdo relacionado ao Pix será exibido aqui.</p>
                </div>
            )}
        </div>
    );

}

export default Dashboard;
