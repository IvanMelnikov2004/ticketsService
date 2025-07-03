import {FormEvent, useCallback} from "react";
import {useNavigate} from "react-router";
import {Button, Label, TextInput} from "flowbite-react";
import {callBackendNoToken} from "./ticketService.tsx";

export default function Register() {
    const navigate = useNavigate()

    return (
        <>
            <div className="flex min-h-screen min-w-screen items-center justify-center flex-col">
                <form className="flex w-7xl flex-col gap-4"
                      onSubmit={useCallback((event: FormEvent<HTMLFormElement>) => {
                          event.preventDefault()
                          const elements = event.currentTarget.elements as any
                          const first_name = elements.first_name.value
                          const last_name = elements.last_name.value
                          const phone_number = elements.phone_number.value
                          const email = elements.email.value
                          const password = elements.password.value
                          const password2 = elements.password2.value

                          if (password != password2) {
                              alert("Пароли не совпадают")
                              return
                          }

                          if (password.length < 8) {
                              alert("Длина пароля должна быть хотя бы 8 символов")
                              return
                          }

                          callBackendNoToken('authentication/register', {
                              method: 'POST',
                              body: JSON.stringify({
                                  first_name, last_name, phone_number, email, password
                              }),
                              headers: {
                                  'Content-Type': 'application/json'
                              }
                          })
                              .then(res => {
                                  if (!res.ok) {
                                      alert("Произошла ошибка при регистрации!")
                                      throw "error"
                                  }

                                  return res.json()
                              })
                              .then(_ => {
                                  alert('Успешная регистрация!')
                                  navigate('/signin')
                              })
                      }, [navigate])}>
                    <div>
                        <div className="mb-2 block">
                            <Label htmlFor="first_name" value="Ваше имя"/>
                        </div>
                        <TextInput id="first_name" name="first_name" type="text" placeholder="Иван" required/>
                    </div>
                    <div>
                        <div className="mb-2 block">
                            <Label htmlFor="last_name" value="Ваше имя"/>
                        </div>
                        <TextInput id="last_name" name="last_name" type="text" placeholder="Иванов" required/>
                    </div>
                    <div>
                        <div className="mb-2 block">
                            <Label htmlFor="phone_number" value="Номер телефона"/>
                        </div>
                        <TextInput id="phone_number" name="phone_number" type="tel" placeholder="79998887766" required/>
                    </div>
                    <div>
                        <div className="mb-2 block">
                            <Label htmlFor="email" value="Ваш E-mail"/>
                        </div>
                        <TextInput id="email" name="email" type="email" placeholder="me@example.com" required/>
                    </div>
                    <div>
                        <div className="mb-2 block">
                            <Label htmlFor="password" value="Ваш пароль"/>
                        </div>
                        <TextInput id="password" name="password" type="password" required/>
                    </div>
                    <div>
                        <div className="mb-2 block">
                            <Label htmlFor="password2" value="Повторите пароль"/>
                        </div>
                        <TextInput id="password2" name="password2" type="password" required/>
                    </div>
                    <Button type="submit">Зарегистрироваться</Button>
                    <Button onClick={useCallback(() => navigate("/signin"), [navigate])}>Вход</Button>
                </form>
            </div>
        </>
    )
}