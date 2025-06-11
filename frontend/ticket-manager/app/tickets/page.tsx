import { Button } from "@/components/ui/button";
import { TicketsTable } from "@/components/ui/tickets/tickets-table";
import { tickets } from "@/data/tickets"
import { IconFileArrowLeft, IconFileArrowRight, IconSquareRoundedPlus } from "@tabler/icons-react";
import { Dice1 } from "lucide-react";


export default function TicketsPage() {
    return (
        <div className="w-1/2 m-auto pt-10">
            <div className="flex gap-4">
                <Button variant="outline" size="sm" className="transition hover:text-lime-500 cursor-pointer">
                    <IconSquareRoundedPlus /> Add
                </Button>

                <Button variant="outline" size="sm" className="transition hover:text-lime-500 cursor-pointer">
                    <IconFileArrowRight /> Export to .csv
                </Button>

                <Button variant="outline" size="sm" className="transition hover:text-lime-500 cursor-pointer">
                    <IconFileArrowLeft /> Import from .csv
                </Button>
            </div>
            <TicketsTable tickets={tickets} />
        </div>
    );
}