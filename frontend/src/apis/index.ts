import axios from 'axios';

export const { BASE_URL } = process.env;

export const clientBasic = axios.create({
  baseURL: BASE_URL,
});

export const client = axios.create({
  baseURL: BASE_URL,
  headers: {
    Authorization: `Bearer ${localStorage.getItem('auth')}`,
  },
});
