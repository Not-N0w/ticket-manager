'use client'

import { cn } from "@/lib/utils"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { SyntheticEvent, useState } from "react"
import { useRouter } from "next/navigation"

interface AuthResponse {
  token: string
  message: string
  role: string[]
}

export function SignUpForm({
  className,
  ...props
}: React.ComponentProps<"form">) {

  const router = useRouter()

  const [username, setUsername] = useState('')
  const [firstname, setFirstname] = useState('')
  const [lastname, setLastname] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState<string | null>(null)

  const submit = async (e: SyntheticEvent) => {
    e.preventDefault()
    setError(null)

    try {
      const res = await fetch('http://localhost:1805/api/v1/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username,
          firstName: firstname,
          lastName: lastname,
          password
        })
      })

      if (res.ok) {
        const data: AuthResponse = await res.json()
        localStorage.setItem('token', data.token)
        localStorage.setItem('roles', JSON.stringify(data.role))
        window.location.href = "/";


      } else {
        const error: AuthResponse = await res.json()
        setError(error.message || 'Registration failed')
      }
    } catch (err) {
      setError('Network error or server unavailable')
    }
  }

  return (
    <form className={cn("flex flex-col gap-6", className)} {...props} onSubmit={submit}>
      <div className="flex flex-col items-center gap-2 text-center">
        <h1 className="text-2xl font-bold">Create your account</h1>
        <p className="text-muted-foreground text-sm text-balance">
          Enter some info below to сreate your account
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
          <Label htmlFor="firstname">First name</Label>
          <Input id="firstname" placeholder="Ivan" required onChange={e => setFirstname(e.target.value)} />
        </div>
        <div className="grid gap-3">
          <Label htmlFor="lastname">Last name</Label>
          <Input id="lastname" placeholder="Ivanov" required onChange={e => setLastname(e.target.value)} />
        </div>
        <div className="grid gap-3">
          <div className="flex items-center">
            <Label htmlFor="password">Password</Label>
          </div>
          <Input id="password" type="password" required onChange={e => setPassword(e.target.value)} />
        </div>
        <Button type="submit" className="w-full">
          Sign up
        </Button>
      </div>
      <div className="text-center text-sm">
        Already have an account?{" "}
        <a href="/sign-in" className="underline underline-offset-4">
          Sign in
        </a>
      </div>
    </form>
  )
}
