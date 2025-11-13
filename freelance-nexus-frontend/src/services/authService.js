import axios from 'axios';

// Set the base URL for your backend API
const API_URL = process.env.REACT_APP_API_URL || 'http://localhost:8080';

// Create axios instance with base configuration
const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

// Add token to requests if it exists
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

const authService = {
  // Register a new user
  register: async (data) => {
    const response = await api.post('/api/users/register', data);
    return response.data;
  },

  // Login user
  login: async (email, password) => {
    const response = await api.post('/api/users/login', { email, password });
    
    if (response.data.token) {
      localStorage.setItem('token', response.data.token);
      localStorage.setItem('user', JSON.stringify(response.data.user));
    }
    
    return response.data;
  },

  // Logout user
  logout: () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  },

  // Get current user from localStorage
  getCurrentUser: () => {
    const userStr = localStorage.getItem('user');
    return userStr ? JSON.parse(userStr) : null;
  },

  // Get token from localStorage
  getToken: () => {
    return localStorage.getItem('token');
  },

  // Get current user profile from backend
  getCurrentProfile: async () => {
    const response = await api.get('/api/users/profile');
    return response.data;
  },

  // Update current user profile
  updateProfile: async (data) => {
    const response = await api.put('/api/users/profile', data);
    
    // Update localStorage with new user data
    if (response.data) {
      localStorage.setItem('user', JSON.stringify(response.data));
    }
    
    return response.data;
  }
};

export default authService;
```

## **Key Points:**

1. **API Endpoints** (lines 30-32 and 36-37):
   - `POST /api/users/register` - Backend registration endpoint
   - `POST /api/users/login` - Backend login endpoint

2. **Axios Instance**: Creates a configured axios instance with your backend URL

3. **Token Interceptor**: Automatically adds JWT token to all requests

4. **Local Storage**: Manages token and user data persistence

## **Environment Variable:**

Create a `.env` file in your React project root:
```
REACT_APP_API_URL=http://localhost:8080
```

Or for production:
```
REACT_APP_API_URL=https://your-backend-domain.com