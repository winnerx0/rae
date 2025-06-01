"use client";

import { XIcon } from "lucide-react";

import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
    AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
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
import { AxiosError } from "axios";
import { useEffect, useState } from "react";
import { Button } from "./ui/button";

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
    fetchSessions();
  }, []);
  
  const handleDeleteSession = async (sessionId: string) => {
    await api.delete("/s/" + sessionId)
    setSessions((prev) => prev.filter(s => s.id !== sessionId))
  }

  return (
    <Sidebar>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel className="text-2xl font-bold mb-4">
            Rae
          </SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu className="flex flex-col gap-2">
              <Button>New Session</Button>
              {sessions.map((session) => (
                <SidebarMenuItem key={session.id}>
                  <div className="hover:bg-zinc-800/90 flex items-center w-full hover:[&_.delete-icon]:opacity-100 h-10 rounded-lg">
                    <SidebarMenuButton asChild className="h-10 flex-1 ">
                      <div>
                        <a
                          href={`/session/${session.id}`}
                          className="flex items-center w-full px-3"
                        >
                          <span className="text-left truncate flex-1">
                            {session.name}
                          </span>
                        </a>
                        <AlertDialog>
                          <AlertDialogTrigger asChild>
                            <button
                              className="delete-icon h-8 w-8 opacity-0 transition-opacity cursor-pointer hover:bg-destructive/10 rounded flex items-center justify-center mr-2"
                              type="button"
                              
                            >
                              <XIcon className="h-4 w-4" />
                            </button>
                          </AlertDialogTrigger>
                          <AlertDialogContent>
                            <AlertDialogHeader>
                              <AlertDialogTitle>
                                Delete Session
                              </AlertDialogTitle>
                              <AlertDialogDescription>
                                Are you sure you want to delete "{session.name}
                                "? This action cannot be undone.
                              </AlertDialogDescription>
                            </AlertDialogHeader>
                            <AlertDialogFooter>
                              <AlertDialogCancel>Cancel</AlertDialogCancel>
                              <AlertDialogAction className="bg-red-500 hover:bg-red-500/90 text-foreground" onClick={() => handleDeleteSession(session.id)}>
                                Delete
                              </AlertDialogAction>
                            </AlertDialogFooter>
                          </AlertDialogContent>
                        </AlertDialog>
                      </div>
                    </SidebarMenuButton>
                  </div>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  );
}
