"use client";

import { Home, Pencil, Settings } from "lucide-react";
import { Button } from "./ui/button";
import { useRouter } from "next/navigation";

const Sidebar = () => {
  
  const router = useRouter()
  return (
    <div className="w-[250px] h-min rounded-lg border flex flex-col py-8 px-4 bg-card">
      <Button onClick={() => router.push('/home')} className="flex justify-start font-bold" variant={"ghost"}>
        <Home />
        Home
      </Button>
      <Button onClick={() => router.push('/create')} className="flex justify-start font-bold" variant={"ghost"}>
        <Pencil />
        Create
      </Button>
      <Button onClick={() => router.push('/settings')} className="flex justify-start font-bold" variant={"ghost"}>
        <Settings />
        Settings
      </Button>
    </div>
  );
};

export default Sidebar;
