'use client'

import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { Button } from "@/components/ui/button"
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select"
import { Checkbox } from "@/components/ui/checkbox"
import { Separator } from "@radix-ui/react-menubar"
import { useCallback, useEffect, useState } from "react"
import { Ticket } from "@/app/models"

interface Props {
  open: boolean
  onOpenChange: (open: boolean) => void
  onSubmit: (ticket: TicketFormData) => void
  ticket: Ticket
  isUpdate: boolean
  externalError?: string | null
}

type TicketFormData = {
  name: string
  price: number
  ticketType: "VIP" | "USUAL" | "BUDGETARY" | "CHEAP"
  refundable: boolean
  coordinates: {
    x: number
    y: number
  }
  person: {
    birthday: string
    weight: number
    passportId: string
    location: {
      x: number
      y: number
      z: number
    }
  }
}

export function TicketFormDialog({ open, onOpenChange, onSubmit, ticket, isUpdate, externalError }: Props) {
  const [form, setForm] = useState<TicketFormData>({
    name: "",
    price: 0,
    ticketType: "USUAL",
    refundable: false,
    coordinates: { x: 0, y: 0 },
    person: {
      birthday: "",
      weight: 0,
      passportId: "",
      location: { x: 0, y: 0, z: 0 },
    },
  })

  const [error, setError] = useState<string | null>(null)

  // Без any, безопасно, масштабируемо
  const handleChange = useCallback(
    <K1 extends keyof TicketFormData, V extends TicketFormData[K1]>(
      key: K1,
      value: V
    ) => {
      setForm((prev) => ({ ...prev, [key]: value }))
    },
    []
  )

  const handleNestedChange = useCallback(
    (path: string[], value: unknown) => {
      setForm((prev) => {
        const updated = structuredClone(prev) as any // structuredClone уже создаёт безопасную копию
        let current = updated
        for (let i = 0; i < path.length - 1; i++) {
          current = current[path[i]]
        }
        current[path[path.length - 1]] = value
        return updated
      })
    },
    []
  )

  const validateForm = (): string | null => {
    if (!form.name.trim()) return "Name is required."
    if (form.price <= 0) return "Price must be greater than 0."
    if (!form.person.birthday) return "Birthday is required."
    if (form.person.weight <= 0) return "Weight must be greater than 0."
    if (!form.person.passportId.trim()) return "Passport ID is required."
    return null
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault()
    const validationError = validateForm()
    if (validationError) {
      setError(validationError)
      return
    }
    setError(null)
    onSubmit(form)
  }

  useEffect(() => {
    if (open && ticket) {
      setForm({
        name: ticket.name ?? "",
        price: ticket.price ?? 0,
        ticketType: ticket.ticketType ?? "USUAL",
        refundable: ticket.refundable ?? false,
        coordinates: {
          x: ticket.coordinates?.x ?? 0,
          y: ticket.coordinates?.y ?? 0,
        },
        person: {
          birthday: ticket.person?.birthday ?? "",
          weight: ticket.person?.weight ?? 0,
          passportId: ticket.person?.passportId ?? "",
          location: {
            x: ticket.person?.location?.x ?? 0,
            y: ticket.person?.location?.y ?? 0,
            z: ticket.person?.location?.z ?? 0,
          },
        },
      })
      setError(null)
    }
  }, [open, ticket])

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[600px]">
        <form onSubmit={handleSubmit}>
          <DialogHeader>
            <DialogTitle>{isUpdate ? "Edit Ticket" : "Create Ticket"}</DialogTitle>
            <Separator />
          </DialogHeader>

          <div className="grid gap-4">
            <div className="grid gap-3">
              <Label>Name</Label>
              <Input value={form.name} onChange={(e) => handleChange("name", e.target.value)} />
            </div>

            <div className="grid gap-3">
              <Label>Price</Label>
              <Input
                type="number"
                value={form.price}
                onChange={(e) => handleChange("price", parseFloat(e.target.value))}
              />
            </div>

            <div className="grid gap-3">
              <Label>Ticket Type</Label>
              <Select value={form.ticketType} onValueChange={(val) => handleChange("ticketType", val as TicketFormData["ticketType"])}>
                <SelectTrigger>
                  <SelectValue />
                </SelectTrigger>
                <SelectContent>
                  {["VIP", "USUAL", "BUDGETARY", "CHEAP"].map((type) => (
                    <SelectItem key={type} value={type}>{type}</SelectItem>
                  ))}
                </SelectContent>
              </Select>
            </div>

            <div className="flex items-center space-x-2">
              <Checkbox
                checked={form.refundable}
                onCheckedChange={(checked) => handleChange("refundable", checked === true)}
              />
              <Label>Refundable</Label>
            </div>

            <div className="grid gap-3">
              <Label>Coordinates</Label>
              <div className="grid grid-cols-2 gap-2">
                <Input
                  type="number"
                  placeholder="X"
                  value={form.coordinates.x}
                  onChange={(e) => handleNestedChange(["coordinates", "x"], parseFloat(e.target.value))}
                />
                <Input
                  type="number"
                  placeholder="Y"
                  value={form.coordinates.y}
                  onChange={(e) => handleNestedChange(["coordinates", "y"], parseFloat(e.target.value))}
                />
              </div>
            </div>

            <div className="grid gap-3">
              <Label>Person</Label>
              <Input
                type="date"
                value={form.person.birthday}
                onChange={(e) => handleNestedChange(["person", "birthday"], e.target.value)}
              />
              <Input
                type="number"
                placeholder="Weight"
                value={form.person.weight}
                onChange={(e) => handleNestedChange(["person", "weight"], parseFloat(e.target.value))}
              />
              <Input
                placeholder="Passport ID"
                value={form.person.passportId}
                onChange={(e) => handleNestedChange(["person", "passportId"], e.target.value)}
              />
            </div>

            <div className="grid gap-3">
              <Label>Person Location</Label>
              <div className="grid grid-cols-3 gap-2">
                {(["x", "y", "z"] as const).map((axis) => (
                  <Input
                    key={axis}
                    type="number"
                    placeholder={axis.toUpperCase()}
                    value={form.person.location[axis]}
                    onChange={(e) => handleNestedChange(["person", "location", axis], parseFloat(e.target.value))}
                  />
                ))}
              </div>
            </div>
          </div>

          {(error || externalError) && (
            <div className="text-red-500 text-sm pt-2">{error || externalError}</div>
          )}

          <DialogFooter className="pt-4">
            <DialogClose asChild>
              <Button type="button" variant="outline">Cancel</Button>
            </DialogClose>
            <Button type="submit">{isUpdate ? "Update Ticket" : "Create Ticket"}</Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  )
}
