import {DarkThemeToggle, Navbar} from "flowbite-react";
import {useLocation, useNavigate} from "react-router";
import {useLocalStorage} from "@uidotdev/usehooks";
import {Token} from "./user.tsx";
import {useCallback} from "react";

export default function AppNavbar() {
    const location = useLocation()
    const [_, setAccessToken] = useLocalStorage("access_token", {} as Token)
    const navigate = useNavigate()

    return (
        <Navbar>
            <Navbar.Brand href="https://flowbite-react.com">
                <img src="/ticketService.svg" className="mr-3 h-6 sm:h-9" alt="ticketService Logo"/>
                <span
                    className="self-center whitespace-nowrap text-xl font-semibold dark:text-white">ptmplace</span>
            </Navbar.Brand>
            <div className="flex md:order-2">
                <DarkThemeToggle />
                <Navbar.Toggle/>
            </div>
            <Navbar.Collapse>
                <Navbar.Link href="/" active={location.pathname == '/'}>
                    Главная
                </Navbar.Link>
                <Navbar.Link href="/tickets" active={location.pathname == '/tickets'}>
                    Мои билеты
                </Navbar.Link>
                <Navbar.Link href="/cities" active={location.pathname == '/cities'}>
                    Города
                </Navbar.Link>
                <Navbar.Link href="#" onClick={useCallback(() => {
                    setAccessToken({} as Token)
                    navigate("/signin")
                }, [setAccessToken, navigate])}>
                    Выйти
                </Navbar.Link>
            </Navbar.Collapse>
        </Navbar>
    )
}