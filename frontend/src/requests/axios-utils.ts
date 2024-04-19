import axios, { AxiosInstance } from "axios";

export const JWT_TOKEN_KEY = 'jwtToken';

export const AXIOS_INSTANCE: AxiosInstance = axios.create({
    baseURL: "http://127.0.0.1:8080/api/v1",
    headers: {
        'Content-Type': 'application/json',
        Authorization: 'Bearer ' + getJwtToken(),
    },
});


export function getJwtToken(): string | null {
    return localStorage.getItem(JWT_TOKEN_KEY);
}

export function setJwtToken(jwtToken: string): void {
    localStorage.setItem(JWT_TOKEN_KEY, jwtToken);
    AXIOS_INSTANCE.defaults.headers.common['Authorization'] = 'Bearer ' + jwtToken;
}