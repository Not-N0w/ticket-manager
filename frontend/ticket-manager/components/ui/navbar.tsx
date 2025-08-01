'use client'

import {
  Menubar
} from "@/components/ui/menubar";
import { IconDirectionArrowsFilled, IconTicket } from "@tabler/icons-react";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { Button } from "./button";


export function NavBar() {
  const minioBaseUrl = "http://localhost:9000/images/"

  const router = useRouter();

  const [isAuthenticated, setIsAuthenticated] = useState(false)
  const [roles, setRoles] = useState<string[]>([])
  const [avatar, setAvatar] = useState("default-avatar.png")
  const [username, setUsername] = useState("User")

  useEffect(() => {
    const token = localStorage.getItem('token')
    setIsAuthenticated(!!token)
    if (token) {
      const rolesString = localStorage.getItem('roles')
      setAvatar(localStorage.getItem("avatar") || "default-avatar.png");
      setUsername(localStorage.getItem("username") || "User")

      let storedRoles: string[] = []

      if (rolesString) {
        console.log(token)
        try {
          storedRoles = JSON.parse(rolesString)
          console.log(storedRoles)
        } catch (e) {
          console.error('Roles parse error:', e)
        }
      }
      setRoles(storedRoles)
    }
  }, [])

  const isAdmin = roles.includes("ROLE_ADMIN") || roles.includes("ROLE_SUPER_ADMIN")
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
            <Button variant="ghost" className="cursor-pointer transition-colors"
            onClick={() => router.push("/admin/users")}
            >
              <IconDirectionArrowsFilled />
              Admin Panel
            </Button>

          )}
        </div>

        <div className="flex items-center gap-4 pr-2">
          {isAuthenticated ? (
            <Button variant="ghost" onClick={() => router.push("/user/me")}>
              <img
                src={`${minioBaseUrl}${avatar}`}
                alt="User Avatar"
                className="w-8 h-8 rounded-full object-cover border"
              />
              {username}
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
