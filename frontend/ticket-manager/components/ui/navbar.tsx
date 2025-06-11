'use client'

import {
  Menubar,
  MenubarContent,
  MenubarItem,
  MenubarMenu,
  MenubarTrigger,
} from "@/components/ui/menubar";
import { IconTicket } from "@tabler/icons-react";
import { useRouter } from "next/navigation";

export function NavBar() {
  const router = useRouter();

  return (
    <div className="absolute top-0 left-0 w-full z-50">
      <Menubar className="w-full flex justify-between px-4 py-5 border-b bg-white shadow-sm">
        <div className="flex items-center gap-4">
          <button
            className="text-lg font-bold text-primary cursor-pointer"
            onClick={() => router.push("/")}
          >
            <IconTicket className="h-10 w-10 text-primary" />
          </button>
          <MenubarMenu>
            <MenubarTrigger className="cursor-pointer transition-colors">
              Admin Panel
            </MenubarTrigger>
            <MenubarContent>
              <MenubarItem className="cursor-pointer">All users</MenubarItem>
              <MenubarItem className="cursor-pointer">Admins</MenubarItem>
            </MenubarContent>
          </MenubarMenu>
        </div>
        <div className="flex items-center gap-4 pr-2">
          <button className="text-sm hover:text-muted-foreground text-primary transition-colors cursor-pointer" onClick={() => router.push("/sign-in")}>
            Sign In
          </button>
          <button
            className="text-sm font-medium text-white bg-primary px-3 py-1 rounded-md hover:bg-primary/90 transition-colors cursor-pointer"
            onClick={() => router.push("/sign-up")}
          >
            Sign Up
          </button>
        </div>
      </Menubar>
    </div>
  );
}
