'use client'

import { useEffect, useState } from "react"
import { useParams, useRouter } from "next/navigation"
import { User } from "@/app/models"
import { Button } from "@/components/ui/button"
import { IconArrowLeftDashed, IconLogout } from "@tabler/icons-react"

export default function UserDetails() {
    const router = useRouter()
    const { id } = useParams<{ id: string }>()
    const [user, setUser] = useState<User | null>(null)
    const [loading, setLoading] = useState(true)
    const [accessDenied, setAccessDenied] = useState(false)

    const logout = () => {
        localStorage.removeItem("token")
        localStorage.removeItem("roles")
        window.location.href = "/sign-in"
    }

    const isViewingSelf = id === "me"
    const token = typeof window !== "undefined" ? localStorage.getItem("token") : null
    const rolesRaw = typeof window !== "undefined" ? localStorage.getItem("roles") : null
    const userRoles: string[] = rolesRaw ? safelyParseArray(rolesRaw) : []

    useEffect(() => {
        if (!token) {
            logout()
            return
        }

        const isAdmin =
            userRoles.includes("ROLE_ADMIN") || userRoles.includes("ROLE_SUPER_ADMIN")

        if (!isViewingSelf && !isAdmin) {
            setAccessDenied(true)
            setLoading(false)
            return
        }

        const fetchUser = async () => {
            const endpoint = isViewingSelf
                ? "http://localhost:1805/api/v1/users/me"
                : `http://localhost:1805/api/v1/users/${id}`

            try {
                const res = await fetch(endpoint, {
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": `Bearer ${token}`,
                    },
                })

                if (res.ok) {
                    const data: User = await res.json()
                    setUser(data)
                } else {
                    setUser(null)
                    logout()
                }
            } catch (err) {
                console.error("Error fetching user:", err)
                setUser(null)
            } finally {
                setLoading(false)
            }
        }

        fetchUser()
    }, [id])

    if (loading) return <div className="text-center mt-10">Loading...</div>
    if (accessDenied) return <div className="text-center mt-10 text-red-500">Access Denied</div>
    if (!user) return <div className="text-center mt-10 text-red-500">User not found</div>

    return (
        <div className="w-full max-w-4xl mx-auto pt-10 px-4">
            <div className="bg-white drop-shadow-xl rounded-2xl p-6 space-y-6">
                <h2 className="text-2xl font-bold text-gray-800">
                    👤 User: {user.username}
                </h2>

                <div className="grid grid-cols-2 sm:grid-cols-3 gap-4 text-sm">
                    <Info label="ID" value={user.id} />
                    <Info label="First Name" value={user.firstName} />
                    <Info label="Last Name" value={user.lastName} />
                    <Info label="Status" value={user.status} />
                    <Info label="Roles" value={user.roles?.join(", ") || "No roles"} />
                </div>
            </div>

            <div className="flex justify-between mt-10">
                <Button
                    variant="ghost"
                    className="text-gray-500 text-sm flex items-center"
                    onClick={() => router.back()}
                >
                    <IconArrowLeftDashed className="mr-2" />
                    Back
                </Button>

                {isViewingSelf ? (
                    <Button
                        variant="ghost"
                        className="text-gray-500 text-sm flex items-center"
                        onClick={logout}
                    >
                        Logout
                        <IconLogout className="ml-2" />
                    </Button>
                ) : (
                    <div className="flex gap-2">
                        <Button variant="outline" className="text-sm">
                            Block
                        </Button>
                        <Button variant="outline" className="text-sm">
                            Assign as Admin
                        </Button>
                    </div>
                )}
            </div>
        </div>
    )
}

function Info({ label, value }: { label: string; value: React.ReactNode }) {
    return (
        <div>
            <p className="text-gray-500 font-medium">{label}</p>
            <p className="text-gray-900">{value}</p>
        </div>
    )
}

function safelyParseArray(raw: string): string[] {
    try {
        const parsed = JSON.parse(raw)
        return Array.isArray(parsed) ? parsed : []
    } catch {
        return []
    }
}
