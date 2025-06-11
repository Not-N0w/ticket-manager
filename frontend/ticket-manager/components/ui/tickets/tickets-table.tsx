import { Ticket } from "@/app/models"
import { DataTable } from "./data-table"
import { columns } from "./columns"

interface TicketsProps {
    tickets: Ticket[]
}

export function TicketsTable(props: TicketsProps) {
    return (
        <div className="container mx-auto py-10">
            <DataTable columns={columns} data={props.tickets} />
        </div>
    )
}