import { Ticket } from "@/app/models"
import { columns } from "./columns"
import { DataTable } from "./data-table"

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