"use client";

import {
  HistoryIcon,
  HomeIcon,
  LogOut,
  NotebookTabsIcon,
  Settings,
} from "lucide-react";

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
import { useRouter } from "next/navigation";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useEffect, useState } from "react";
import api from "@/lib/api";
import { AxiosError } from "axios";
import { getUser, logout } from "@/actions/server-actions";

export function AppSidebar() {
  const [user, setUser] = useState<User | null>(null);
  const router = useRouter();

  const items = [
    {
      name: "Home",
      Icon: HomeIcon,
    },
    {
      name: "Sessions",
      Icon: HistoryIcon,
    },
    {
      name: "Feedback",
      Icon: NotebookTabsIcon,
    },
  ];

  useEffect(() => {
    async function fetchUser() {
      const user = await getUser();
      setUser(user);
    }
    fetchUser();
  }, []);

  console.log(user);
  return (
    <Sidebar>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel className="text-2xl font-bold mb-4">
            Rae
          </SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu className="flex flex-col gap-2">
              {items.map(({ name, Icon }, index) => (
                <SidebarMenuItem
                  key={index}
                  onClick={() => router.push(`/${name.toLowerCase()}`)}
                >
                  <div className="hover:bg-zinc-800/90 flex items-center w-full hover:[&_.delete-icon]:opacity-100 h-10 rounded-lg">
                    <SidebarMenuButton
                      asChild
                      className="h-10 flex-1 cursor-pointer"
                    >
                      <div>
                        <Icon />
                        {name}
                      </div>
                    </SidebarMenuButton>
                  </div>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>

      <DropdownMenu>
        <DropdownMenuTrigger>
          {" "}
          <div className="border h-14 rounded-md p-2 mb-8 mx-2">
            <p className="font-bold">{user?.name}</p>
            <p className="text-sm">{user?.username}</p>
          </div>
        </DropdownMenuTrigger>
        <DropdownMenuContent className="w-60">
          <DropdownMenuItem
            onClick={async () => {
              await logout();
              router.push("/login");
            }}
          >
            <LogOut /> Logout
          </DropdownMenuItem>
          <DropdownMenuItem>
            <Settings /> Settings
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
    </Sidebar>
  );
}
