import { User } from "@/app/models"
import { columns } from "./columns"
import { DataTable } from "./data-table"

interface UserProps {
    users: User[]
}

export function UsersTable(props: UserProps) {
    return (
        <div className="container mx-auto py-10">
            <DataTable columns={columns} data={props.users} />
        </div>
    )
}