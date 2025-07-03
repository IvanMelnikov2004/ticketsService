import AppNavbar from "./AppNavbar.tsx";
import {useEffect, useState} from "react";
import {Pagination, Table} from "flowbite-react";
import {callBackend} from "./ticketService.tsx";
import {useLocalStorage} from "@uidotdev/usehooks";
import {Token} from "./user.tsx";

export default function Cities() {
    const [currentPage, setCurrentPage] = useState(1);
    const [totalPages, setTotalPages] = useState(1);
    const [isFetchedPages, setIsFetchedPages] = useState(false);
    const [accessToken, _] = useLocalStorage('access_token', {} as Token)
    const [pageContent, setPageContent] = useState(null as any)

    const onPageChange = (page: number) => setCurrentPage(page);

    useEffect(() => {
        if (!isFetchedPages) {
            callBackend("city", accessToken.token)
                .then(res => res.json())
                .then(res => {
                    const content = []

                    for (let city of res.content) {
                        content.push(
                            <Table.Row key={city.id} className="bg-white dark:border-gray-700 dark:bg-gray-800">
                                <Table.Cell className="whitespace-nowrap font-medium text-gray-900 dark:text-white">
                                    {city.id + 1}
                                </Table.Cell>
                                <Table.Cell>{city.name}</Table.Cell>
                            </Table.Row>
                        )
                    }

                    setPageContent(content)
                    setTotalPages(res.page.totalPages)
                })

            setIsFetchedPages(true)
        }
    });

    return (<>
        <AppNavbar/>
        <div className="overflow-x-auto">
            <Table>
                <Table.Head>
                    <Table.HeadCell>#</Table.HeadCell>
                    <Table.HeadCell>Название</Table.HeadCell>
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