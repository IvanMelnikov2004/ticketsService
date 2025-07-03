import AppNavbar from "./AppNavbar.tsx";
import {Pagination, Table} from "flowbite-react";
import {useLocalStorage} from "@uidotdev/usehooks";
import {Token} from "./user.tsx";
import {useCallback, useEffect, useState} from "react";
import {callBackend} from "./ticketService.tsx";

export default function Tickets() {
    const [accessToken, _] = useLocalStorage('access_token', {} as Token)
    const [currentPage, setCurrentPage] = useState(1)
    const [totalPages, setTotalPages] = useState(1)
    const [isFetchedPages, setIsFetchedPages] = useState(false)
    const [pageContent, setPageContent] = useState(null as any)

    const mapping: any = {
        PLANE: "Самолёт",
        RAILWAY: "Поезд",
        BUS: "Автобус"
    }

    const onPageChange = useCallback((page: number) => {
        setCurrentPage(page)

        callBackend(`ticket?page=${page != 0 ? page - 1 : page}`, accessToken.token)
            .then(res => res.json())
            .then(res => {
                const content = []
                let num = 0

                for (let city of res.content) {
                    content.push(
                        <Table.Row key={num} className="bg-white dark:border-gray-700 dark:bg-gray-800">
                            <Table.Cell>{city.from}</Table.Cell>
                            <Table.Cell>{city.routes[0].to}</Table.Cell>
                            <Table.Cell>{new Date(city.routes[0].departure).toLocaleString()}</Table.Cell>
                            <Table.Cell>{new Date(city.routes[0].arrival).toLocaleString()}</Table.Cell>
                            <Table.Cell>{mapping[city.routes[0].transportMode]}</Table.Cell>
                            <Table.Cell>
                                <a href="#" className="font-medium text-cyan-600 hover:underline dark:text-cyan-500">
                                    Отменить
                                </a>
                            </Table.Cell>
                        </Table.Row>
                    )

                    num++

                    console.log(city)
                }

                setPageContent(content)
                setTotalPages(res.page.totalPages)
            })
    }, [setPageContent, setCurrentPage])

    const cancel = useCallback((id: any) => {
        callBackend('ticket', accessToken.token, {
            method: "DELETE",
            body: JSON.stringify({
                route_id: id
            }),
            headers: {
                Authorization: `Bearer ${accessToken.token}`,
                'Content-Type': 'application/json'
            }
        }).then(_ => onPageChange(currentPage))
    }, [accessToken, currentPage])

    useEffect(() => {
        if (!isFetchedPages) {
            callBackend("ticket", accessToken.token)
                .then(res => res.json())
                .then(res => {
                    const content = []
                    let num = 0

                    for (let city of res.content) {
                        content.push(
                            <Table.Row key={num} className="bg-white dark:border-gray-700 dark:bg-gray-800">
                                <Table.Cell>{city.from}</Table.Cell>
                                <Table.Cell>{city.routes[0].to}</Table.Cell>
                                <Table.Cell>{new Date(city.routes[0].departure).toLocaleString()}</Table.Cell>
                                <Table.Cell>{new Date(city.routes[0].arrival).toLocaleString()}</Table.Cell>
                                <Table.Cell>{mapping[city.routes[0].transportMode]}</Table.Cell>
                                <Table.Cell>
                                    <a href="#" className="font-medium text-cyan-600 hover:underline dark:text-cyan-500"
                                        onClick={event => {
                                            event.preventDefault()
                                            cancel(city.key.routeId)
                                        }}>
                                        Отменить
                                    </a>
                                </Table.Cell>
                            </Table.Row>
                        )

                        num++

                        console.log(city)
                    }

                    setPageContent(content)
                    setTotalPages(res.page.totalPages ?? 1)
                })

            setIsFetchedPages(true)
        }
    })

    return (<>
        <AppNavbar/>
        <div className="overflow-x-auto">
            <Table>
                <Table.Head>
                    <Table.HeadCell>Откуда</Table.HeadCell>
                    <Table.HeadCell>Куда</Table.HeadCell>
                    <Table.HeadCell>Отправление</Table.HeadCell>
                    <Table.HeadCell>Прибытие</Table.HeadCell>
                    <Table.HeadCell>Тип</Table.HeadCell>
                    <Table.HeadCell>
                        <span className="sr-only">Отменить</span>
                    </Table.HeadCell>
                </Table.Head>
                <Table.Body className="divide-y">
                    {pageContent}
                </Table.Body>
            </Table>
        </div>
        <div className="flex overflow-x-auto sm:justify-center">
            <Pagination currentPage={currentPage} totalPages={totalPages} onPageChange={onPageChange} showIcons
                        nextLabel="Далее" previousLabel="Назад"/>
        </div>
    </>)
}