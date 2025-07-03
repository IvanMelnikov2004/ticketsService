import {BACKEND_URL} from "./consts.tsx";

export function callBackendNoToken(method: string, props?: RequestInit) {
    return fetch(`${BACKEND_URL}/${method}`, {
        ...props
    })
}

export function callBackend(method: string, token: string, props?: RequestInit) {
    return callBackendNoToken(method, {
        headers: {
            Authorization: `Bearer ${token}`
        },
        ...props
    })
}