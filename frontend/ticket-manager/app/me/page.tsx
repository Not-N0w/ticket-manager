'use client'

import { User } from "@/app/models"
import { Button } from "@/components/ui/button"
import { IconArrowLeftDashed, IconLogout } from "@tabler/icons-react"
import { useParams, useRouter } from "next/navigation"
import { useEffect, useState } from "react"

export default function UserDetails() {

    const logout = () => {
        localStorage.removeItem('token')
        localStorage.removeItem('roles')
        window.location.href = "/sign-in";
    }

    const router = useRouter()
    const params = useParams()
    const [user, setUser] = useState<User | null>(null)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        const fetchUser = async () => {
            try {
                const res = await fetch(`http://localhost:1805/api/v1/users/me`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + localStorage.getItem('token'),
                    },
                })

                if (res.ok) {
                    const data: User = await res.json()
                    setUser(data)
                } else {
                    setUser(null)
                }
            } catch (err) {
                console.error(err)
                logout()
                setUser(null)
            } finally {
                setLoading(false)
            }
        }

        fetchUser()
    }, [params.id])

    if (loading) return <div className="text-center mt-10">Loading...</div>
    if (!user) return <div className="text-center mt-10 text-red-500">User not found</div>

    return (
        <div className="w-1/2 m-auto pt-10">
            <div className="max-w-3xl mx-auto mt-8 p-6 bg-white drop-shadow-xl rounded-2xl space-y-6">
                <h2 className="text-2xl font-bold text-gray-800">👤 User: {user.username}</h2>

                <div className="grid grid-cols-2 gap-4 text-sm">
                    <Info label="ID" value={user.id} />
                    <Info label="First Name" value={user.firstName} />
                    <Info label="Last Name" value={user.lastName} />
                    <Info label="Status" value={user.status} />
                    <Info
                        label="Roles"
                        value={user.roles && user.roles.length > 0 ? user.roles.join(', ') : "No roles"}
                    />
                </div>
            </div>
            <div className="flex justify-between mt-10 w-full">
                <Button
                    variant="ghost"
                    className="text-gray-500 text-sm cursor-pointer flex items-center"
                    onClick={() => router.back()}
                >
                    <IconArrowLeftDashed className="mr-2" />
                    Back
                </Button>

                <Button
                    variant="ghost"
                    className="text-gray-500 text-sm cursor-pointer flex items-center"
                    onClick={logout}
                >
                    Logout
                    <IconLogout className="ml-2" />
                </Button>
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
