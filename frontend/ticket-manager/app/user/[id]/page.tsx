'use client'

import { useEffect, useState } from "react"
import { useParams, useRouter } from "next/navigation"
import { User } from "@/app/models"
import { Button } from "@/components/ui/button"
import { IconArrowLeftDashed, IconLogout } from "@tabler/icons-react"

export default function UserDetails() {
    const minioBaseUrl = "http://localhost:9000/images/"

    const router = useRouter()
    const { id } = useParams<{ id: string }>()
    const [user, setUser] = useState<User | null>(null)
    const [loading, setLoading] = useState(true)

    const [accessDenied, setAccessDenied] = useState(false)
    const [canAssignAsAdmin, setcanAssignAsAdmin] = useState(false)
    const [canBlock, setCanBlock] = useState(false)

    const [needUnblock, setNeedUnblock] = useState(false)
    const [needRemoveAdminRole, setNeedRemoveAdminRole] = useState(false)



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
                logout()

            } finally {
                setLoading(false)
            }
        }

        fetchUser()
    }, [id])


    const blockUser = async () => {
        try {
            const res = await fetch(`http://localhost:1805/api/v1/admin/users/${id}/block`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`,
                },
            })
            if (res.ok) {
                alert("User blocked successfully")
                const data: User = await res.json()
                setUser(data)
                router.refresh()
            } else {
                alert("Failed to block user")
            }
        } catch (err) {
            console.error(err)
            alert("Error while blocking user")
        }
    }

    const assignUserAsAdmin = async () => {
        try {
            const res = await fetch(`http://localhost:1805/api/v1/admin/users/${id}/assign-admin-role`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`,
                },
            })
            if (res.ok) {
                alert("User assigned as admin")
                const data: User = await res.json()
                setUser(data)
                router.refresh()
            } else {
                alert("Failed to assign role")
            }
        } catch (err) {
            console.error(err)
            alert("Error while assigning role")
        }
    }


    const unblockUser = async () => {
        try {
            const res = await fetch(`http://localhost:1805/api/v1/admin/users/${id}/unblock`, {
                method: "PATCH",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`,
                },
            })
            if (res.ok) {
                alert("User unblocked successfully")
                const data: User = await res.json()
                setUser(data)
                router.refresh()
            } else {
                alert("Failed to unblock user")
            }
        } catch (err) {
            console.error(err)
            alert("Error while unblocking user")
        }
    }

    const removeAdminRole = async () => {
        try {
            const res = await fetch(`http://localhost:1805/api/v1/admin/users/${id}/remove-admin-role`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${token}`,
                },
            })
            if (res.ok) {
                alert("User assigned as simple")
                const data: User = await res.json()
                setUser(data)
                router.refresh()
            } else {
                alert("Failed to assign role")
            }
        } catch (err) {
            console.error(err)
            alert("Error while assigning role")
        }
    }


    useEffect(() => {
        if (!user) return

        const canBlockCheck =
            (userRoles.includes("ROLE_SUPER_ADMIN") && !(user.roles.includes("ROLE_SUPER_ADMIN"))) ||
            (userRoles.includes("ROLE_ADMIN") &&
                !user.roles.includes("ROLE_ADMIN") &&
                !user.roles.includes("ROLE_SUPER_ADMIN"))

        const canAssignCheck =
            userRoles.includes("ROLE_SUPER_ADMIN") && !user.roles.includes("ROLE_SUPER_ADMIN")

        setCanBlock(canBlockCheck)
        setcanAssignAsAdmin(canAssignCheck)

        setNeedRemoveAdminRole(
            user.roles.includes("ROLE_ADMIN") && !user.roles.includes("ROLE_SUPER_ADMIN")
        )
        setNeedUnblock(
            user.status === "BLOCKED"
        )


    }, [user, userRoles])


    const handleAvatarUpload = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0]
        if (!file) return

        const formData = new FormData()
        formData.append("file", file)

        try {
            const res = await fetch("http://localhost:1805/api/v1/users/me/image", {
                method: "POST",
                headers: {
                    "Authorization": `Bearer ${token}`,
                },
                body: formData,
            })

            if (res.ok) {
                const updatedUser: User = await res.json()
                setUser(updatedUser)
                localStorage.setItem("avatar", updatedUser.avatar || "default-avatar.png")
                window.location.href = "/user/me";
                alert("Avatar updated successfully")
            } else {
                alert("Failed to upload avatar")
            }
        } catch (err) {
            console.error("Avatar upload error:", err)
            alert("Error uploading avatar")
        }
    }

    if (loading) return <div className="text-center mt-10">Loading...</div>
    if (accessDenied) return <div className="text-center mt-10 text-red-500">Access Denied</div>
    if (!user) return <div className="text-center mt-10 text-red-500">User not found</div>
    return (
        <div className="min-h-screen flex items-center justify-center px-4">
            <div className="w-full max-w-4xl">
                <div className="bg-white drop-shadow-xl rounded-2xl p-6 space-y-6">
                    <h2 className="text-2xl font-bold text-gray-800">
                        👤 User: {user.username}
                    </h2>
                    <div className="flex items-center gap-4">
                        <img
                            src={user.avatar ? `${minioBaseUrl}${user.avatar}` : "/default-avatar.png"}
                            alt="User Avatar"
                            className="w-24 h-24 rounded-full object-cover border"
                        />

                        {isViewingSelf && (
                            <div>
                                <input
                                    type="file"
                                    accept="image/*"
                                    onChange={handleAvatarUpload}
                                    className="block text-sm text-gray-600 file:mr-4 file:py-2 file:px-4 file:rounded-md file:border-0 file:text-sm file:font-semibold file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100 cursor-pointer"
                                />
                            </div>
                        )}
                    </div>


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
                            {canBlock && (
                                needUnblock ? (
                                    <Button variant="outline" className="text-sm" onClick={unblockUser}>
                                        Unblock
                                    </Button>
                                ) : (
                                    <Button variant="outline" className="text-sm" onClick={blockUser}>
                                        Block
                                    </Button>
                                )
                            )}

                            {canAssignAsAdmin && (
                                needRemoveAdminRole ? (
                                    < Button variant="outline" className="text-sm" onClick={removeAdminRole}>
                                        Remove admin role
                                    </Button>
                                ) : (
                                    < Button variant="outline" className="text-sm" onClick={assignUserAsAdmin}>
                                        Assign as Admin
                                    </Button>
                                )
                            )}
                        </div>
                    )}
                </div>
            </div>
        </div >
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
