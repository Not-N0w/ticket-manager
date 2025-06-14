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
import { useState } from "react"
import { Separator } from "@radix-ui/react-menubar"

interface Props {
  open: boolean
  onOpenChange: (open: boolean) => void
  onSubmit: (ticket: any) => void
}

export function TicketFormDialog({ open, onOpenChange, onSubmit }: Props) {
  const [form, setForm] = useState<any>({
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

  const handleChange = (path: string, value: any) => {
    const keys = path.split(".")
    setForm((prev: any) => {
      const updated = { ...prev }
      let obj = updated
      for (let i = 0; i < keys.length - 1; i++) {
        obj = obj[keys[i]]
      }
      obj[keys[keys.length - 1]] = value
      return updated
    })
  }

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

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[600px]">
        <form onSubmit={handleSubmit}>
          <DialogHeader>
            <DialogTitle>Create a Ticket</DialogTitle>
            <Separator />
          </DialogHeader>

          <div className="grid gap-4">
            <div className="grid gap-3">
              <Label>Name</Label>
              <Input
                value={form.name}
                onChange={(e) => handleChange("name", e.target.value)}
              />
            </div>

            <div className="grid gap-3">
              <Label>Price</Label>
              <Input
                type="number"
                value={form.price}
                onChange={(e) =>
                  handleChange("price", parseFloat(e.target.value))
                }
              />
            </div>

            <div className="grid gap-3">
              <Label>Ticket Type</Label>
              <Select
                value={form.ticketType}
                onValueChange={(val) => handleChange("ticketType", val)}
              >
                <SelectTrigger>
                  <SelectValue />
                </SelectTrigger>
                <SelectContent>
                  <SelectItem value="VIP">VIP</SelectItem>
                  <SelectItem value="USUAL">Usual</SelectItem>
                  <SelectItem value="BUDGETARY">Budgetary</SelectItem>
                  <SelectItem value="CHEAP">Cheap</SelectItem>
                </SelectContent>
              </Select>
            </div>

            <div className="flex items-center space-x-2">
              <Checkbox
                checked={form.refundable}
                onCheckedChange={(val) =>
                  handleChange("refundable", val === true)
                }
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
                  onChange={(e) =>
                    handleChange("coordinates.x", parseFloat(e.target.value))
                  }
                />
                <Input
                  type="number"
                  placeholder="Y"
                  value={form.coordinates.y}
                  onChange={(e) =>
                    handleChange("coordinates.y", parseFloat(e.target.value))
                  }
                />
              </div>
            </div>

            <div className="grid gap-3">
              <Label>Person</Label>
              <Input
                type="date"
                value={form.person.birthday}
                onChange={(e) =>
                  handleChange("person.birthday", e.target.value)
                }
              />
              <Input
                type="number"
                placeholder="Weight"
                value={form.person.weight}
                onChange={(e) =>
                  handleChange("person.weight", parseFloat(e.target.value))
                }
              />
              <Input
                placeholder="Passport ID"
                value={form.person.passportId}
                onChange={(e) =>
                  handleChange("person.passportId", e.target.value)
                }
              />
            </div>

            <div className="grid gap-3">
              <Label>Person Location</Label>
              <div className="grid grid-cols-3 gap-2">
                <Input
                  type="number"
                  placeholder="X"
                  value={form.person.location.x}
                  onChange={(e) =>
                    handleChange(
                      "person.location.x",
                      parseFloat(e.target.value)
                    )
                  }
                />
                <Input
                  type="number"
                  placeholder="Y"
                  value={form.person.location.y}
                  onChange={(e) =>
                    handleChange(
                      "person.location.y",
                      parseFloat(e.target.value)
                    )
                  }
                />
                <Input
                  type="number"
                  placeholder="Z"
                  value={form.person.location.z}
                  onChange={(e) =>
                    handleChange(
                      "person.location.z",
                      parseFloat(e.target.value)
                    )
                  }
                />
              </div>
            </div>
          </div>

          {error && (
            <div className="text-red-500 text-sm pt-2">{error}</div>
          )}

          <DialogFooter className="pt-4">
            <DialogClose asChild>
              <Button type="button" variant="outline">
                Cancel
              </Button>
            </DialogClose>
            <Button type="submit">Create</Button>
          </DialogFooter>
        </form>
      </DialogContent>
    </Dialog>
  )
}
