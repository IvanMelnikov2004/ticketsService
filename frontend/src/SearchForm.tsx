import {Button, Datepicker, Label, Select, TextInput} from "flowbite-react";
import {callBackend} from "./ticketService.tsx";
import {useLocalStorage} from "@uidotdev/usehooks";
import {Token} from "./user.tsx";
import {useNavigate} from "react-router";

export default function SearchForm() {
    const [accessToken, _] = useLocalStorage('access_token', {} as Token)
    const navigate = useNavigate()

    return (<div className="flex flex-col min-h-screen min-w-screen gap-4 items-center justify-center">
        <h1 className="dark:text-white text-center text-3xl">Поиск маршрута</h1>
        <form className="flex flex-col w-full items-center gap-4" onSubmit={event => {
            event.preventDefault()

            // @ts-ignore
            const from = event.currentTarget.elements.namedItem("from")?.value
            // @ts-ignore
            const to = event.currentTarget.elements.namedItem("to")?.value
            // @ts-ignore
            const mode = event.currentTarget.elements.namedItem("mode")?.value
            // @ts-ignore
            const departure = new Date(event.currentTarget.elements.namedItem("departure")?.value).getTime() / 1000

            let fromId = -1
            let toId = -1

            Promise.all([
                callBackend(`city/${from}`, accessToken.token)
                    .then(res => {
                        if (res.status == 404) {
                            alert("Город отправления не найден!")
                            throw 'City not found'
                        } else {
                            return res.json()
                        }
                    })
                    .then(res => fromId = res.id),
                callBackend(`city/${to}`, accessToken.token)
                    .then(res => {
                        if (res.status == 404) {
                            alert("Город прибытия не найден!")
                            throw 'City not found'
                        } else {
                            return res.json()
                        }
                    })
                    .then(res => toId = res.id)
            ]).then(() => {
                if (fromId != -1 && toId != -1) {
                    navigate(`/route/${fromId}/${toId}/${mode}/${departure}`)
                }
            })
        }}>
            <div className="w-1/2">
                <div className="mb-2 block">
                    <Label htmlFor="from" value="Город отправления" />
                </div>
                <TextInput id="from" name="from" type="text" />
            </div>
            <div className="w-1/2">
                <div className="mb-2 block">
                    <Label htmlFor="to" value="Город прибытия"/>
                </div>
                <TextInput id="to" name="to" type="text"/>
            </div>
            <div className="w-1/2">
                <div className="mb-2 block">
                    <Label htmlFor="departure" value="Дата отправления"/>
                </div>
                <Datepicker id="departure" name="departure" minDate={new Date()}/>
            </div>
            <div className="w-1/2">
                <div className="mb-2 block">
                    <Label htmlFor="mode" value="Вид транспорта"/>
                </div>
                <Select id="mode" name="mode" required>
                    <option value="mix">Не важно</option>
                    <option value="PLANE">Самолет</option>
                    <option value="RAILWAY">Поезд</option>
                    <option value="BUS">Автобус</option>
                </Select>
            </div>
            <Button className="w-1/2" type="submit">Найти</Button>
        </form>
    </div>)
}