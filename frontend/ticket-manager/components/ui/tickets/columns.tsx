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
                        <DropdownMenuItem
                            onClick={async () => {
                                if (!confirm(`Are you sure you want to delete ticket with id = "${ticket.id}"?`)) return;

                                try {
                                    const res = await fetch(`http://localhost:1805/api/v1/tickets/delete/${ticket.id}`, {
                                        method: 'DELETE',
                                        headers: {
                                            'Content-Type': 'application/json',
                                            'Authorization': `Bearer ${localStorage.getItem('token')}`,
                                        },
                                    });

                                    if (res.ok) {
                                        alert('Ticket deleted successfully');
                                        window.location.reload();
                                    } else {
                                        let errorMessage = 'Failed to delete ticket';

                                        const contentType = res.headers.get("content-type");
                                        if (contentType?.includes("application/json")) {
                                            const json = await res.json();
                                            errorMessage = json.message || errorMessage;
                                        } else {
                                            const text = await res.text();
                                            if (text) errorMessage = text;
                                        }

                                        alert(`Error: ${errorMessage}`);
                                    }
                                } catch (err) {
                                    console.error("Network error:", err);
                                    alert('Network error. Could not delete ticket.');
                                }
                            }}

                        >Delete</DropdownMenuItem>
                    </DropdownMenuContent>
                </DropdownMenu>
            )
        },
    },
]


