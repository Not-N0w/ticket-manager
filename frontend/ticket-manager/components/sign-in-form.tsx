'use client'

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { useRouter } from "next/navigation"
import { SyntheticEvent, useState } from "react"
import { User } from "@/app/models"



interface AuthResponse {
  token: string
  message: string
}

export function SignInForm({
  className,
  ...props
}: React.ComponentProps<"form">) {

  const router = useRouter();

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null)

  const submit = async (e: SyntheticEvent) => {
    e.preventDefault()
    setError(null)

    try {
      const res = await fetch('http://localhost:1805/api/v1/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username,
          password
        })
      })

      if (res.ok) {
        const data: AuthResponse = await res.json()

        localStorage.setItem('token', data.token)
        window.location.href = "/";


      } else {
        const error: AuthResponse = await res.json()
        setError(error.message || 'Login failed')
      }
    } catch (err) {
      setError('Network error or server unavailable')
    }

    try {
      const res = await fetch('http://localhost:1805/api/v1/users/me', {
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${localStorage.getItem('token')}`,
        },
      })

      if (res.ok) {
        const data: User = await res.json()
        localStorage.setItem("roles", JSON.stringify(data.roles))
        localStorage.setItem("username", data.username)
        localStorage.setItem("avatar", !!data.avatar ? data.avatar : "default-avatar.png")
        window.location.href = "/";


      } else {

      }
    } catch (err) {
      console.error("Error fetching user:", err)
    }
  }
  return (
    <form className={cn("flex flex-col gap-6", className)} {...props} onSubmit={submit}>
      <div className="flex flex-col items-center gap-2 text-center">
        <h1 className="text-2xl font-bold">Sing in to your account</h1>
        <p className="text-muted-foreground text-sm text-balance">
          Enter your username below to sign in to your account
        </p>
      </div>
      {error && (
        <div className="text-red-500 text-sm text-center">
          {error}
        </div>
      )}
      <div className="grid gap-6">
        <div className="grid gap-3">
          <Label htmlFor="username">Username</Label>
          <Input id="username" placeholder="Some_username" required onChange={e => setUsername(e.target.value)} />
        </div>
        <div className="grid gap-3">
          <div className="flex items-center">
            <Label htmlFor="password">Password</Label>
          </div>
          <Input id="password" type="password" required onChange={e => setPassword(e.target.value)} />
        </div>
        <Button type="submit" className="w-full">
          Sign in
        </Button>

      </div>
      <div className="text-center text-sm">
        Don&apos;t have an account?{" "}
        <a href="/sign-up" className="underline underline-offset-4">
          Sign up
        </a>
      </div>
    </form>
  )
}
