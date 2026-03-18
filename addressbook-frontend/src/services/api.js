import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/contacts';

const api = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json'
    }
});

// Get all contacts
export const getAllContacts = async () => {
    const response = await api.get('');
    return response.data.data;
};

// Get contact by ID
export const getContactById = async (id) => {
    const response = await api.get(`/${id}`);
    return response.data.data;
};

// Add new contact
export const addContact = async (contact) => {
    const response = await api.post('', contact);
    return response.data.data;
};

// Update contact
export const updateContact = async (id, contact) => {
    const response = await api.put(`/${id}`, contact);
    return response.data.data;
};

// Delete contact
export const deleteContact = async (id) => {
    const response = await api.delete(`/${id}`);
    return response.data;
};

// Search by city
export const searchByCity = async (city) => {
    const response = await api.get(`/search/city/${city}`);
    return response.data.data;
};

// Search by state
export const searchByState = async (state) => {
    const response = await api.get(`/search/state/${state}`);
    return response.data.data;
};

// Sort by name
export const sortByName = async () => {
    const response = await api.get('/sort/name');
    return response.data.data;
};

// Sort by city
export const sortByCity = async () => {
    const response = await api.get('/sort/city');
    return response.data.data;
};

// Sort by state
export const sortByState = async () => {
    const response = await api.get('/sort/state');
    return response.data.data;
};

// Sort by zip
export const sortByZip = async () => {
    const response = await api.get('/sort/zip');
    return response.data.data;
};

// Group by city
export const groupByCity = async () => {
    const response = await api.get('/group/city');
    return response.data.data;
};

// Group by state
export const groupByState = async () => {
    const response = await api.get('/group/state');
    return response.data.data;
};

// Count by city
export const countByCity = async () => {
    const response = await api.get('/count/city');
    return response.data.data;
};

// Count by state
export const countByState = async () => {
    const response = await api.get('/count/state');
    return response.data.data;
};

export default api;
