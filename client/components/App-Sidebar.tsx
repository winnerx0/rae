"use client";

import { Calendar, Home, Inbox, Search, Settings } from "lucide-react";

import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import api from "@/lib/api";
import { useEffect, useState } from "react";
import { AxiosError } from "axios";
import { Button } from "./ui/button";

// Menu items.
const items = [
  {
    title: "Home",
    url: "#",
    icon: Home,
  },
  {
    title: "Inbox",
    url: "#",
    icon: Inbox,
  },
  {
    title: "Calendar",
    url: "#",
    icon: Calendar,
  },
  {
    title: "Search",
    url: "#",
    icon: Search,
  },
  {
    title: "Settings",
    url: "#",
    icon: Settings,
  },
];

export function AppSidebar() {
  const [sessions, setSessions] = useState<Session[]>([]);

  useEffect(() => {
    async function fetchSessions() {
      try {
        const res = await api.get("/session/");
        if (res.status !== 200) {
          throw new Error(res.data);
        }
        const ans: Session[] = res.data;
        setSessions(ans);
      } catch (e: any) {
        if (e instanceof AxiosError) {
        }
      }
    }
    fetchSessions()
  }, []);
  return (
    <Sidebar>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel className="text-2xl font-bold mb-4">Rae</SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu className="flex flex-col gap-4">
              <Button>New Session</Button>
              {sessions.map((session) => (
                <SidebarMenuItem key={session.id}>
                  <SidebarMenuButton asChild className="h-10">
                    <a href={`/session/${session.id}`}>
                      <span className="text-center w-full">{session.name}</span>
                    </a>
                  </SidebarMenuButton>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  );
}
