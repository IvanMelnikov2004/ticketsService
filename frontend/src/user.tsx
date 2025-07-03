type Role = "ADMIN" | "MEMBER"

export type User = {
    id?: number,
    last_name?: string,
    first_name?: string,
    email?: string,
    phone_number?: string,
    roles?: Role[]
}

export type Token = {
    token: string
}