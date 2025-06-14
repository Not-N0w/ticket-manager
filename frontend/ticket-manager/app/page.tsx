'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { Button } from '@/components/ui/button';
import { IconTicket } from '@tabler/icons-react';

export default function HomePage() {
  const router = useRouter();
  const [isAuthenticated, setIsAuthenticated] = useState<boolean | null>(null);

  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsAuthenticated(!!token);
  }, []);

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-50">
      <div className="bg-white p-10 rounded-2xl shadow-lg max-w-xl w-full text-center">
        <div className="flex items-center justify-center gap-2 mb-6">
          <IconTicket className="h-10 w-10 text-primary" />
          <h1 className="text-4xl font-extrabold tracking-tight text-gray-900">
            Ticket Manager
          </h1>
        </div>

        {isAuthenticated === null ? (
          <div className="text-gray-500">Loading...</div>
        ) : isAuthenticated ? (
          <Button
            variant="default"
            className="text-lg px-6 py-3"
            onClick={() => router.push('/tickets')}
          >
            Go to Tickets
          </Button>
        ) : (
          <div className="flex flex-col gap-4 items-center">
            <Button
              variant="default"
              className="text-lg px-6 py-3 w-48"
              onClick={() => router.push('/sign-in')}
            >
              Sign In
            </Button>
            <Button
              variant="outline"
              className="text-lg px-6 py-3 w-48"
              onClick={() => router.push('/sign-up')}
            >
              Sign Up
            </Button>
          </div>
        )}
      </div>
    </div>
  );
}
