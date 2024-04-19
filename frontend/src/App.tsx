import React, {useEffect, useState} from "react";
import {LoginInputDto} from "./dto/input/login-input-dto.ts";
import {AXIOS_INSTANCE, setJwtToken} from "./requests/axios-utils.ts";
import {RequestPath} from "./requests/request-path.ts";
import {LoginResponseDto} from "./dto/response/login-response-dto.ts";
import {ErrorResponseDto} from "./dto/response/error-response-dto.ts";


function TimerComponent() {
    const [elapsed, setElapsed] = useState<string>("0");
    const now: number = Date.now();
    useEffect(() => {
            const interval: number = setInterval(() => {
                //HH:MM:SS
                const formattedTime: string = new Date(Date.now() - now).toISOString().substr(11, 8);
                setElapsed(formattedTime)
            }, 500);
            return () => clearInterval(interval);
        }
        , []);

    return (
        <div>
            <h1>Timer</h1>
            <h2>{elapsed}</h2>
        </div>
    );
}

function App() {

    const handleRegister = (
        event: React.FormEvent<HTMLFormElement>
    ): void => {
        handleAuth(event, RequestPath.REGISTER, (successDto: LoginResponseDto): void => {
            console.log(successDto);
        }, (errorDto: ErrorResponseDto): void => {
            console.log(errorDto);
        });
    }

    const handleLogin = (
        event: React.FormEvent<HTMLFormElement>
    ): void => {
        handleAuth(event, RequestPath.LOGIN, (successDto: LoginResponseDto): void => {
            console.log(successDto);
        }, (errorDto: ErrorResponseDto): void => {
            console.log(errorDto);
        });
    }

    const handleAuth = (
        event: React.FormEvent<HTMLFormElement>,
        route: string,
        onCompleted: (responseDto: LoginResponseDto) => void,
        onError: (error: ErrorResponseDto) => void
    ): void => {
        event.preventDefault();

        const dto: LoginInputDto = {
            username: event.currentTarget.username.value,
            password: event.currentTarget.password.value
        };

        AXIOS_INSTANCE.post(route, dto).then(response => {
            console.log(response);
            const responseDto: LoginResponseDto = response.data;
            setJwtToken(responseDto.token);

            onCompleted(responseDto);
        }).catch(e => {
            const errorDto: ErrorResponseDto = e.response.data;
            onError(e.response.data);
            const errors: string[] = [];

            console.log(errorDto);
            errors.push(errorDto.message);
            Object.entries(errorDto.errors).forEach(([key, value]) => {
                errors.push(`${key}: ${value}`);
            });

            setError(errors);
        });
    };

    const [error, setError] = React.useState<string[]>([]);

    return (
        <>
            <form onSubmit={handleRegister}>
                <input type="text" name="username" placeholder="Username" required/>
                <input type="password" name="password" placeholder="Password" required/>
                <button type="submit">REGISTER</button>
            </form>
            <form onSubmit={handleLogin}>
                <input type="text" name="username" placeholder="Username" required/>
                <input type="password" name="password" placeholder="Password" required/>
                <button type="submit">LOGIN</button>
            </form>
            <div>
                <ul>
                    {error.map((error, index) => (
                        <li key={index}>{error}</li>
                    ))}
                </ul>
            </div>
            <TimerComponent/>
        </>
    );
}

export default App
