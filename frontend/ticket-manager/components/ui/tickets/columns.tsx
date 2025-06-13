"use client"

import { ColumnDef } from "@tanstack/react-table"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "../dropdown-menu"
import { MoreHorizontal } from "lucide-react"
import { Button } from "../button"
import { useRouter } from "next/navigation"

// This type is used to define the shape of our data.
// You can use a Zod schema here if you want.
export type Ticket = {
    id: number
    ticketType: "VIP" | "USUAL" | "BUDGETARY" | "CHEAP"
    name: string
    price: number
    refundable: boolean
}

export const columns: ColumnDef<Ticket>[] = [
    {
        accessorKey: "id",
        header: "id",
    },
    {
        accessorKey: "name",
        header: "Name",
    },
    {
        accessorKey: "ticketType",
        header: "Ticket type",
    },
    {
        accessorKey: "price",
        header: "Price",
    },
    {
        accessorKey: "refundable",
        header: "Refundable",
    },
    {
        id: "actions",
        cell: function ActionCell({ row }) {
            const router = useRouter()
            const ticket = row.original


            return (
                <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                        <Button variant="ghost" className="h-8 w-8 p-0">
                            <span className="sr-only">Open menu</span>
                            <MoreHorizontal className="h-4 w-4" />
                        </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent align="end">
                        <DropdownMenuLabel>Actions</DropdownMenuLabel>
                        <DropdownMenuItem
                            onClick={() => {
                                router.push(`/tickets/${ticket.id}`)
                            }}
                        >
                            Details
                        </DropdownMenuItem>
                        <DropdownMenuSeparator />
                        <DropdownMenuItem>Delete</DropdownMenuItem>
                    </DropdownMenuContent>
                </DropdownMenu>
            )
        },
    },
]


