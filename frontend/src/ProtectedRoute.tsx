import {useLocalStorage} from "@uidotdev/usehooks";
import {Navigate} from "react-router";
import {Token} from "./user.tsx";

// @ts-ignore
export function ProtectedRoute({children}) {
    const [accessToken, _] = useLocalStorage('access_token', {} as Token)

    if (!accessToken || !accessToken.token) {
        return <Navigate to='/signin' />
    }

    return children
}