import {createBrowserRouter} from "react-router-dom";
import App from "../App.tsx";
import CounterView from "../views/CounterView.tsx";

export const ROUTER = createBrowserRouter([
    {
        path: "/auth",
        element: <App/>
    },
    {
        path: "/counter/:id",
        element: <CounterView/>

    }
]);
