'use client'

import { Ticket } from "@/app/models"
import { Button } from "@/components/ui/button"
import { TicketFormDialog } from "@/components/ui/tickets/ticket-dialog"
import { IconArrowLeftDashed, IconEdit } from "@tabler/icons-react"
import { useParams, useRouter } from "next/navigation"
import { useEffect, useState } from "react"

export default function TicketDetails() {
  const router = useRouter()
  const params = useParams()
  const ticketId = Number(params.id)
  const [ticket, setTicket] = useState<Ticket | null>(null)
  const [dialogOpen, setDialogOpen] = useState(false)
  const [externalError, setExternalError] = useState<string | null>(null)

  useEffect(() => {
    const fetchTicket = async () => {
      const token = localStorage.getItem("token")
      if (!token) return

      try {
        const res = await fetch(`http://localhost:1805/api/v1/tickets/${ticketId}`, {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token,
          },
        })

        if (!res.ok) throw new Error("Failed to fetch ticket")

        const data: Ticket = await res.json()
        setTicket(data)
      } catch (err) {
        console.error("Error fetching ticket:", err)
      }
    }

    fetchTicket()
  }, [ticketId])

  const handleEditTicket = async (ticketData: Partial<Ticket>) => {
    if (!ticket) return

    const token = localStorage.getItem("token")
    if (!token) return

    setExternalError(null)

    try {
      const res = await fetch(`http://localhost:1805/api/v1/tickets/update/${ticket.id}`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer " + token,
        },
        body: JSON.stringify(ticketData),
      })

      if (!res.ok) {
        let errorMessage = "Failed to update ticket"

        try {
          const errorData = await res.json()
          if (errorData.message) errorMessage = errorData.message
        } catch (jsonErr) {
          console.error("Failed to parse error response:", jsonErr)
        }

        setExternalError(errorMessage)
        return
      }

      const updatedTicket: Ticket = await res.json()
      setTicket(updatedTicket)
      setDialogOpen(false)
    } catch (err) {
      console.error("Unexpected fetch error:", err)
      setExternalError("An unexpected error occurred.")
    }

  }

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

      <div className="mt-10 flex justify-between">
        <Button variant="ghost" className="mt-10 text-gray-500 text-mf cursor-pointer" onClick={() => router.back()}>
          <IconArrowLeftDashed /> back
        </Button>

        <Button variant="outline" className="mt-10 text-gray-500 text-mf cursor-pointer" onClick={() => setDialogOpen(true)}>
          <IconEdit /> Edit
        </Button>

        <TicketFormDialog
          open={dialogOpen}
          onOpenChange={setDialogOpen}
          onSubmit={handleEditTicket}
          ticket={ticket}
          isUpdate={true}
          externalError={externalError}
        />
      </div>
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
