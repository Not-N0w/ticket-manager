import { Ticket } from "@/app/models"
import { DataTable } from "./data-table"
import { SortItem, useTicketColumns } from "./columns"
import { useEffect, useState } from "react"

interface TicketsProps {
    tickets: Ticket[]
}

export function TicketsTable(props: TicketsProps) {
    const [data, setData] = useState<Ticket[]>(props.tickets)
    const [sortMap, setSortMap] = useState<SortItem[]>([
        { column: "id", direction: "ASC" },
        { column: "name", direction: "ASC" },
        { column: "price", direction: "ASC" },
        { column: "ticketType", direction: "ASC" },
        { column: "refundable", direction: "ASC" },
    ])

    const columns = useTicketColumns(sortMap, setSortMap)
    const sortItems = Object.values(sortMap);
    const sortString = JSON.stringify(sortItems);
    useEffect(() => {

        fetch(`http://localhost:1805/api/v1/tickets/all-sorted`, {
            method: "POST",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                'Content-Type': 'application/json'

            },
            body: JSON.stringify(sortItems)
        })
            .then((res) => res.json())
            .then((data) => {
                setData(data)

            })
            .catch((err) => {
                console.error("Error fetching sorted data:", err);
            });
    }, [sortString])

    useEffect(() => {
        console.log("Data updated:", data);
    }, [data]);
    return (
        <div className="container mx-auto py-10">
            <DataTable columns={columns} data={data} key={JSON.stringify(data)} />
        </div>
    )
}