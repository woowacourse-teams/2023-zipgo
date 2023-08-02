import axios from 'axios';

export const { BASE_URL } = process.env;

export const client = axios.create({
  baseURL: BASE_URL,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('auth')}`,
  },
});
