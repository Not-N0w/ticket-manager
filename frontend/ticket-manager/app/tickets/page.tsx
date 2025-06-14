'use client'

import { Button } from "@/components/ui/button";
import { TicketsTable } from "@/components/ui/tickets/tickets-table";
import { IconFileArrowLeft, IconSquareRoundedPlus } from "@tabler/icons-react";
import { useEffect, useState } from "react";
import { Ticket } from "../models";
import { TicketFormDialog } from "@/components/ui/tickets/create-ticket-dialog";


export default function TicketsPage() {
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [loading, setLoading] = useState(true);
  const [dialogOpen, setDialogOpen] = useState(false); 

  const fetchTickets = async () => {
    const token = localStorage.getItem("token");

    try {
      const res = await fetch("http://localhost:1805/api/v1/tickets/all", {
        method: 'GET',
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer " + token
        },
      });

      if (!res.ok) throw new Error("Failed to fetch tickets");

      const data: Ticket[] = await res.json();
      setTickets(data);
    } catch (error) {
      console.error("Error fetching tickets:", error);
    } finally {
      setLoading(false);
    }
  };

  const handleCreateTicket = async (ticketData: Partial<Ticket>) => {
    const token = localStorage.getItem("token");

    try {
      const res = await fetch("http://localhost:1805/api/v1/tickets/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer " + token
        },
        body: JSON.stringify(ticketData)
      });

      if (!res.ok) throw new Error("Failed to create ticket");

      await fetchTickets();
      setDialogOpen(false);
    } catch (err) {
      console.error("Error creating ticket:", err);
    }
  };

  useEffect(() => {
    fetchTickets();
  }, []);

  return (
    <div className="flex items-center justify-center min-h-screen">
      <div className="flex flex-col w-full max-w-2xl px-4 gap-2">
        <div className="flex gap-2 self-start">
          <Button
            variant="outline"
            size="sm"
            className="transition hover:text-lime-500 cursor-pointer"
            onClick={() => setDialogOpen(true)}
          >
            <IconSquareRoundedPlus /> Add
          </Button>

          <Button
            variant="outline"
            size="sm"
            className="transition hover:text-lime-500 cursor-pointer"
          >
            <IconFileArrowLeft /> Import from .csv
          </Button>
        </div>

        {loading ? (
          <p className="text-center">Loading tickets...</p>
        ) : (
          <TicketsTable tickets={tickets} />
        )}

        <TicketFormDialog
          open={dialogOpen}
          onOpenChange={setDialogOpen}
          onSubmit={handleCreateTicket}
        />
      </div>
    </div>
  );
}
