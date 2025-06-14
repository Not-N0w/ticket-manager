// columns.tsx
"use client"

import { ColumnDef } from "@tanstack/react-table"
import { ArrowUpDown, MoreHorizontal } from "lucide-react"
import { Button } from "../button"
import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuLabel,
    DropdownMenuSeparator,
    DropdownMenuTrigger,
} from "../dropdown-menu"
import { useRouter } from "next/navigation"
import React from "react"

export type Ticket = {
    id: number
    ticketType: "VIP" | "USUAL" | "BUDGETARY" | "CHEAP"
    name: string
    price: number
    refundable: boolean
}

export type SortItem = {
    column: string
    direction: "ASC" | "DESC"
}

export function useTicketColumns(
    sortMap: SortItem[],
    setSortMap: React.Dispatch<React.SetStateAction<SortItem[]>>
) {

    const router = useRouter()


    function updateSortMap(columnKey: string, direction: "ASC" | "DESC", sortMap: SortItem[]): SortItem[] {
        const index = sortMap.findIndex((s) => s.column === columnKey)
        if (index === -1) return [{ column: columnKey, direction }, ...sortMap]

        const updated = [...sortMap]
        const [target] = updated.splice(index, 1)
        updated.unshift({ ...target, direction })
        return updated
    }

    const columns: ColumnDef<Ticket>[] = React.useMemo(() => [
        {
            accessorKey: "id",
            header: ({ column }) => {
                const currentSort = sortMap.find((s) => s.column === "id")
                const newDirection = currentSort?.direction === "ASC" ? "DESC" : "ASC"
                return (
                    <Button
                        variant="ghost"
                        onClick={() => {
                            const newSort = updateSortMap("id", newDirection, sortMap)
                            setSortMap(newSort)
                            column.toggleSorting(newDirection === "ASC")
                        }}
                    >
                        Id
                        <ArrowUpDown className="ml-2 h-4 w-4" />
                    </Button>
                )
            },
        },
        {
            accessorKey: "name",
            header: ({ column }) => {
                const currentSort = sortMap.find((s) => s.column === "name")
                const newDirection = currentSort?.direction === "ASC" ? "DESC" : "ASC"
                return (
                    <Button
                        variant="ghost"
                        onClick={() => {
                            const newSort = updateSortMap("name", newDirection, sortMap)
                            setSortMap(newSort)
                            column.toggleSorting(newDirection === "ASC")
                        }}
                    >
                        Name
                        <ArrowUpDown className="ml-2 h-4 w-4" />
                    </Button>
                )
            },
        },
        {
            accessorKey: "ticketType",
            header: ({ column }) => {
                const currentSort = sortMap.find((s) => s.column === "ticketType")
                const newDirection = currentSort?.direction === "ASC" ? "DESC" : "ASC"
                return (
                    <Button
                        variant="ghost"
                        onClick={() => {
                            const newSort = updateSortMap("ticketType", newDirection, sortMap)
                            setSortMap(newSort)
                            column.toggleSorting(newDirection === "ASC")
                        }}
                    >
                        Ticket type
                        <ArrowUpDown className="ml-2 h-4 w-4" />
                    </Button>
                )
            },
        },
        {
            accessorKey: "price",
            header: ({ column }) => {
                const currentSort = sortMap.find((s) => s.column === "price")
                const newDirection = currentSort?.direction === "ASC" ? "DESC" : "ASC"
                return (
                    <Button
                        variant="ghost"
                        onClick={() => {
                            const newSort = updateSortMap("price", newDirection, sortMap)
                            setSortMap(newSort)
                            column.toggleSorting(newDirection === "ASC")
                        }}
                    >
                        Price
                        <ArrowUpDown className="ml-2 h-4 w-4" />
                    </Button>
                )
            },
        },
        {
            accessorKey: "refundable",
            header: ({ column }) => {
                const currentSort = sortMap.find((s) => s.column === "refundable")
                const newDirection = currentSort?.direction === "ASC" ? "DESC" : "ASC"
                return (
                    <Button
                        variant="ghost"
                        onClick={() => {
                            const newSort = updateSortMap("refundable", newDirection, sortMap)
                            setSortMap(newSort)
                            column.toggleSorting(newDirection === "ASC")
                        }}
                    >
                        Refundable
                        <ArrowUpDown className="ml-2 h-4 w-4" />
                    </Button>
                )
            },
        },
        {
            id: "actions",
            cell: ({ row }) => {
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
                                onClick={() => router.push(`/tickets/${ticket.id}`)}
                            >
                                Details
                            </DropdownMenuItem>
                            <DropdownMenuSeparator />
                            <DropdownMenuItem
                                onClick={async () => {
                                    if (!confirm(`Delete ticket with id = "${ticket.id}"?`)) return

                                    try {
                                        const res = await fetch(`http://localhost:1805/api/v1/tickets/delete/${ticket.id}`, {
                                            method: "DELETE",
                                            headers: {
                                                "Content-Type": "application/json",
                                                Authorization: `Bearer ${localStorage.getItem("token")}`,
                                            },
                                        })

                                        if (res.ok) {
                                            alert("Ticket deleted successfully")
                                            window.location.reload()
                                        } else {
                                            let msg = "Failed to delete ticket"
                                            const type = res.headers.get("content-type")
                                            if (type?.includes("application/json")) {
                                                const json = await res.json()
                                                msg = json.message || msg
                                            } else {
                                                const text = await res.text()
                                                if (text) msg = text
                                            }
                                            alert(`Error: ${msg}`)
                                        }
                                    } catch (err) {
                                        console.error("Network error:", err)
                                        alert("Network error. Could not delete ticket.")
                                    }
                                }}
                            >
                                Delete
                            </DropdownMenuItem>
                        </DropdownMenuContent>
                    </DropdownMenu>
                )
            },
        },
    ], [sortMap, router])

    return columns
}
