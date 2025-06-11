'use client'

import { Button } from "@/components/ui/button";
import { tickets } from "@/data/tickets"
import { IconFileArrowRight } from "@tabler/icons-react";
import { json2csv } from 'json-2-csv';

export function DownloadButton() {

    async function sendCsvFile() {
        const data = tickets
        const csv = await json2csv(data);

        var blob = new Blob([decodeURIComponent('%ef%bb%bf'), csv], { type: 'text/csv;charset=utf-8' });
        var url = URL.createObjectURL(blob);
        var a = document.createElement('a');
        a.href = url;
        a.download = 'tickets.csv';
        document.body.appendChild(a);
        a.click();
    }
    return (
        <Button variant="outline" size="sm" className="transition hover:text-lime-500 cursor-pointer" onClick={
            sendCsvFile
        } >
            <IconFileArrowRight /> Export to .csv
        </Button>
    )
}