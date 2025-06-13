'use client'

import { Ticket } from "@/app/models"
import { Button } from "@/components/ui/button"
import { tickets } from "@/data/tickets"
import { IconArrowLeftDashed } from "@tabler/icons-react"
import { useParams, useRouter } from "next/navigation"


export default function TicketDetails() {
  const router = useRouter()
  const params = useParams()
  const ticketId = Number(params.id)
  const ticket: Ticket | undefined = tickets.find((item) => item.id === ticketId)

  if (!ticket) return <div>Ticket not found</div>

  return (
    <div className="w-1/2 m-auto pt-10">
      <div className="max-w-3xl mx-auto mt-8 p-6 bg-white drop-shadow-xl rounded-2xl space-y-6">
        <h2 className="text-2xl font-bold text-gray-800">🎫 Ticket: {ticket.name}</h2>

        <div className="grid grid-cols-2 gap-4 text-sm">
          <Info label="ID" value={ticket.id} />
          <Info label="Type" value={ticket.ticketType} />
          <Info label="Price" value={`$${ticket.price.toFixed(2)}`} />
          <Info label="Refundable" value={ticket.refundable ? "Yes" : "No"} />
          <Info label="Created at" value={new Date(ticket.creationDate).toLocaleString()} />
          <Info label="Coordinates" value={`X: ${ticket.coordinates.x}, Y: ${ticket.coordinates.y}`} />
        </div>

        <div className="mt-6 border-t pt-4">
          <h3 className="text-lg font-semibold text-gray-700 mb-2">👤 Person Info</h3>
          <div className="grid grid-cols-2 gap-4 text-sm">
            <Info label="Birthday" value={ticket.person.birthday} />
            <Info label="Weight" value={`${ticket.person.weight} kg`} />
            <Info label="Passport ID" value={ticket.person.passportId} />
            <Info
              label="Location"
              value={`X: ${ticket.person.location.x}, Y: ${ticket.person.location.y}, Z: ${ticket.person.location.z}`}
            />
          </div>
        </div>
      </div>

      <Button
        variant="ghost"
        className="mt-10 text-gray-500 text-mf cursor-pointer"
        onClick={() => router.back()}
      >
        <IconArrowLeftDashed /> back
      </Button>
    </div>
  )
}

function Info({ label, value }: { label: string; value: React.ReactNode }) {
  return (
    <div>
      <p className="text-gray-500 font-medium">{label}</p>
      <p className="text-gray-900">{value}</p>
    </div>
  )
}
