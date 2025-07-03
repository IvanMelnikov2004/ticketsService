import AppNavbar from "./AppNavbar.tsx";
import {useParams} from "react-router";
import {useLocalStorage} from "@uidotdev/usehooks";
import {Token} from "./user.tsx";
import {Button, Card} from "flowbite-react";
import {FaArrowRight, FaBus, FaPlane, FaTrain} from "react-icons/fa";
import {useCallback, useEffect, useState} from "react";
import {callBackend} from "./ticketService.tsx";

export default function RoutePage() {
    const {from, to, mode, departure} = useParams()
    const [accessToken, _] = useLocalStorage('access_token', {} as Token)
    const [route, setRoute] = useState(null as unknown as any[])
    const [routeIds, setRouteIds] = useState([] as number[])
    const [isRouteSet, setIsRouteSet] = useState(false)
    const cb = useCallback(() => {
        callBackend('ticket', accessToken.token, {
            method: 'POST',
            body: JSON.stringify({
                route_ids: routeIds
            }),
            headers: {
                Authorization: `Bearer ${accessToken.token}`,
                'Content-Type': 'application/json'
            }
        })
            .then(res => {
                if (!res.ok) {
                    alert("Произошла ошибка при бронировании билетов!")
                    throw "error"
                }

                alert("Билеты успешно забронированы!")
            })
    }, [routeIds, accessToken])

    useEffect(() => {
        if (!isRouteSet) {
            callBackend(
                mode == 'mix' ?
                    `route/${from}/${to}?mix=true&departure=${departure}` :
                    `route/${from}/${to}?mode=${mode}&departure=${departure}`,
                accessToken.token
            )
                .then(res => res.json())
                .then(res => {
                    const routes = []
                    const routeIds2 = []

                    for (let i = 0; i < res.length; i++) {
                        const arrival = i != res.length - 1 ? new Date(res[i + 1].arrival) : null
                        const departure = i != res.length - 1 ? new Date(res[i + 1].departure) : null
                        const id = i != res.length - 1 ? res[i + 1].id : null

                        routes.push(
                            <div key={i} className="flex flex-row gap-2 items-center">
                                <h5 className="dark:text-white font-bold text-xl">{res[i].city.name}</h5>
                                {res[i].transportMode == "PLANE" ?
                                    <FaPlane className="dark:text-white"/> : (res[i].transportMode == "RAILWAY" ?
                                        <FaTrain className="dark:text-white"/> :
                                        <FaBus className="dark:text-white"/>)}
                            </div>
                        )

                        if (i != res.length - 1) {
                            routes.push(
                                <div key={res.length + i} className="flex flex-col gap-0.5 items-center">
                                    <div>
                                        <FaArrowRight className="dark:text-white"/>
                                    </div>
                                    <div>
                                        <p className="text-gray-500 text-sm">{departure?.toLocaleString()} - {arrival?.toLocaleString()}</p>
                                    </div>
                                </div>
                            )

                            routeIds2.push(id)
                        }
                    }

                    setRoute(routes)
                    setRouteIds(routeIds2)
                })

            setIsRouteSet(true)
        }
    })

    return (<>
        <AppNavbar/>
        <div className="flex flex-col min-h-screen min-w-screen gap-4 items-center justify-center">
            <h1 className="dark:text-white text-center text-3xl">{route && route.length ? 'Лучший подходящий маршрут' : 'Маршрут не найден!'}</h1>
            {
                route && route.length ?
                    <Card className="min-w-max" horizontal>
                        <div className="flex flex-row gap-4 items-center">
                            {route}
                        </div>
                        <Button onClick={cb}>Забронировать билет</Button>
                    </Card> : null
            }
        </div>
    </>)
}