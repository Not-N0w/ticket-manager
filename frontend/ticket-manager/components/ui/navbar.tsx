'use client'

import {
  Menubar,
  MenubarContent,
  MenubarItem,
  MenubarMenu,
  MenubarTrigger,
} from "@/components/ui/menubar";
import { IconTicket, IconUserFilled } from "@tabler/icons-react";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Button } from "./button";


export function NavBar() {
  const router = useRouter();

  const [isAuthenticated, setIsAuthenticated] = useState(false)
  const [roles, setRoles] = useState<string[]>([])

  useEffect(() => {
    const token = localStorage.getItem('token')
    setIsAuthenticated(!!token)
    if (token) {
      const rolesString = localStorage.getItem('roles')
      let storedRoles: string[] = []

      if (rolesString) {
        try {
          storedRoles = JSON.parse(rolesString)
        } catch (e) {
          console.error('Roles parse error:', e)
        }
      }

      setRoles(storedRoles)
    }
  }, [])

  const isAdmin = roles.includes("ROLE_ADMIN") || roles.includes("SUPER_ADMIN") // Проверка ролей

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

          {isAdmin && (
            <MenubarMenu>
              <MenubarTrigger className="cursor-pointer transition-colors">
                Admin Panel
              </MenubarTrigger>
              <MenubarContent>
                <MenubarItem className="cursor-pointer" onClick={() => router.push('/admin/users')}>All users</MenubarItem>
                <MenubarItem className="cursor-pointer" onClick={() => router.push('/admin/admins')}>Admins</MenubarItem>
              </MenubarContent>
            </MenubarMenu>
          )}
        </div>

        <div className="flex items-center gap-4 pr-2">
          {isAuthenticated ? (
            <Button variant="ghost" onClick={() => router.push("/me")}>
              <IconUserFilled className="size-5"/>
            </Button>
          ) : (
            <>
              <button
                className="text-sm hover:text-muted-foreground text-primary transition-colors cursor-pointer"
                onClick={() => router.push("/sign-in")}
              >
                Sign In
              </button>
              <button
                className="text-sm font-medium text-white bg-primary px-3 py-1 rounded-md hover:bg-primary/90 transition-colors cursor-pointer"
                onClick={() => router.push("/sign-up")}
              >
                Sign Up
              </button>
            </>
          )}
        </div>
      </Menubar>
    </div>
  );
}
