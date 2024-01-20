import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {AXIOS_INSTANCE} from "../requests/axios-utils.ts";

function CounterView() {


    const id: string = useParams<{ id: string }>().id;
    const [loading, setLoading] = useState<boolean>(true);

    useEffect(() => {
        setLoading(true);
        AXIOS_INSTANCE.get(`/counter/${id}`).then(response => {
            console.log(response);
        }).catch(e => {
            console.log(e);
        });
        console.log(id);
        setLoading(false);
    }, [id]);


    return (
        <div>
            <h1>Counter View</h1>
        </div>
    );
}

export default CounterView;