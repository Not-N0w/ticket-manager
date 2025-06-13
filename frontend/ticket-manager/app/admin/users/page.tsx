'use client'

import { useEffect, useState } from "react"
import { useRouter } from "next/navigation"
import { User } from "@/app/models"
import { UsersTable } from "@/components/ui/users/users-table"

export default function UsersPage() {
  const router = useRouter()

  const [users, setUsers] = useState<User[]>([])
  const [loading, setLoading] = useState(true)
  const [isAdmin, setIsAdmin] = useState(false)

  useEffect(() => {
    const fetchUsers = async () => {
      const token = localStorage.getItem("token")
      const rolesRaw = localStorage.getItem("roles")

      if (!token) {
        router.push("/sign-in")
        return
      }

      let roles: string[] = []
      try {
        roles = rolesRaw ? JSON.parse(rolesRaw) : []
      } catch (e) {
        console.error("Failed to parse roles:", e)
      }

      const isAdminUser = roles.includes("ROLE_ADMIN") || roles.includes("ROLE_SUPER_ADMIN")
      setIsAdmin(isAdminUser)

      if (!isAdminUser) {
        setLoading(false)
        return
      }

      try {
        const res = await fetch("http://localhost:1805/api/v1/admin/users", {
          headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + token,
          },
        })

        if (!res.ok) throw new Error("Failed to fetch users")

        const data: User[] = await res.json()
        setUsers(data)
      } catch (error) {
        console.error("Error fetching users:", error)
      } finally {
        setLoading(false)
      }
    }


    fetchUsers()
  }, [router])

  if (loading) return <div className="text-center mt-10">Loading...</div>
  if (!isAdmin) return <div className="text-center mt-10 text-red-500">You are not admin</div>
  if (!users.length) return <div className="text-center mt-10 text-red-500">No users found</div>

  return (
    <div className="flex flex-col items-center gap-2 text-center w-1/2 m-auto mt-10">
      <UsersTable users={users} />
    </div>
  )
}
