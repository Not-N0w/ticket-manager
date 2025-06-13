"use client"

import { ColumnDef } from "@tanstack/react-table"
import { DropdownMenu, DropdownMenuContent, DropdownMenuItem, DropdownMenuLabel, DropdownMenuSeparator, DropdownMenuTrigger } from "../dropdown-menu"
import { MoreHorizontal } from "lucide-react"
import { Button } from "../button"
import { useRouter } from "next/navigation"
import { Role, User } from "@/app/models"




export const columns: ColumnDef<User>[] = [
    {
        accessorKey: "id",
        header: "id",
    },
    {
        accessorKey: "username",
        header: "username",
    },
    {
        accessorKey: "firstName",
        header: "First name",
    },
    {
        accessorKey: "lastName",
        header: "Last name",
    },
    {
        accessorKey: "roles",
        header: "Roles",
    },
    {
        id: "actions",
        cell: function ActionCell({ row }) {
            const router = useRouter()
            const user = row.original


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
                                router.push(`/user/${user.id}`)
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


