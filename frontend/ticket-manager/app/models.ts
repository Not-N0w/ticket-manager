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
  birthday: string // ISO 8601 (e.g. "2000-01-01")
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
  creationDate: string // ISO 8601 with time (e.g. "2025-06-11T12:00:00")
  coordinates: Coordinates
  person: Person
}
