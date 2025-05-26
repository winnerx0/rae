'use client'

import { Button } from "./ui/button"
import { Input } from "./ui/input"

const Header = () => {
  return (
    <nav className="z-50 sticky top-0 flex items-center justify-between max-w-7xl w-full h-14 border-b bg-background gap-4">
      <h1 className="text-2xl font-bold text-start">Rae</h1>
  
      <div className="w-full max-w-2xl">
        <Input placeholder="Search by title..." className="w-full"/>
      </div>
      <Button>Search</Button>
    </nav>
  )
}

export default Header