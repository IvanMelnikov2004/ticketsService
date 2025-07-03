import {Button, Label, TextInput} from "flowbite-react";
import {FormEvent, useCallback} from "react";
import {useLocalStorage} from "@uidotdev/usehooks";
import {callBackendNoToken} from "./ptmplace.tsx";
import {Token} from "./user.tsx";
import {useNavigate} from "react-router";

export function SignIn() {
    const [_, setAccessToken] = useLocalStorage('access_token', {} as Token)
    const navigate = useNavigate()

    return (
        <>
            <div className="flex min-h-screen min-w-screen items-center justify-center flex-col">
                <form className="flex w-7xl flex-col gap-4"
                      onSubmit={useCallback((event: FormEvent<HTMLFormElement>) => {
                          event.preventDefault()
                          const elements = event.currentTarget.elements as any
                          const email = elements.email.value
                          const password = elements.password.value

                          callBackendNoToken('authentication/authenticate', {
                              method: 'POST',
                              body: JSON.stringify({
                                  email, password
                              }),
                              headers: {
                                  'Content-Type': 'application/json'
                              }
                          })
                              .then(res => {
                                  if (!res.ok) {
                                      alert("Произошла ошибка авторизации!")
                                      throw "error"
                                  }

                                  return res.json()
                              })
                              .then(res => {
                                  setAccessToken({token: res.access_token})
                                  navigate('/')
                              })
                      }, [setAccessToken, navigate])}>
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
                    <Button type="submit">Войти</Button>
                    <Button onClick={() => navigate("/register")}>Регистрация</Button>
                </form>
            </div>
        </>
    )
}