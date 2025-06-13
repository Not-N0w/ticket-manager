export interface Coordinates {
  x: number
  y: number
}

export interface Location {
  x: number
  y: number
  z: number
}

export interface Person {
  birthday: string
  weight: number
  passportId: string
  location: Location
}

export interface Ticket {
  id: number
  ticketType: "VIP" | "USUAL" | "BUDGETARY" | "CHEAP"
  name: string
  price: number
  refundable: boolean
  creationDate: string
  coordinates: Coordinates
  person: Person
}


export interface User {
  id: number
  username: string
  firstName: string
  lastName: string
  roles: Role[]
  status: Status
}
export type Role = "ROLE_SIMPLE" | "ROLE_ADMIN" | "ROLE_SUPER_ADMIN"
export type Status = "ACTIVE" | "BLOCKED"

