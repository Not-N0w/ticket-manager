import { Button } from "@/components/ui/button";
import { TicketsTable } from "@/components/ui/tickets/tickets-table";
import { tickets } from "@/data/tickets"
import { IconFileArrowLeft, IconSquareRoundedPlus } from "@tabler/icons-react";

export default function TicketsPage() {
    return (
        <div className="flex flex-col items-center gap-2 text-center w-1/2 m-auto">
            <div className="flex gap-4">
                <Button variant="outline" size="sm" className="transition hover:text-lime-500 cursor-pointer">
                    <IconSquareRoundedPlus /> Add
                </Button>


                <Button variant="outline" size="sm" className="transition hover:text-lime-500 cursor-pointer">
                    <IconFileArrowLeft /> Import from .csv
                </Button>
            </div>
            <TicketsTable tickets={tickets} />
        </div>
    );
}