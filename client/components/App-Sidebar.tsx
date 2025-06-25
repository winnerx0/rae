"use client";

import {
    HistoryIcon,
    HomeIcon,
    LogOut,
    NotebookTabsIcon,
    Settings,
} from "lucide-react";

import {
    DropdownMenu,
    DropdownMenuContent,
    DropdownMenuItem,
    DropdownMenuTrigger
} from "@/components/ui/dropdown-menu";
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
import { getUser, logout } from "@/lib/server-actions";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import Image from "next/image";

export function AppSidebar() {
  const [user, setUser] = useState<User | null>(null);
  const router = useRouter();
console.log(user)
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

  return (
    <Sidebar>
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel className="text-2xl font-bold mb-4">
            Simon
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
        <DropdownMenuTrigger  className="border h-14 rounded-full mb-8 mx-2 outline-none shadow-sm">
     {
       user && (
         <div className="flex items-center gap-2 px-2">
           
                <Image src={user.profilePicture} width={100} height={100} alt={ user.name } className="rounded-full size-[40px]" quality={100} />
              <div className="text-start text-[12px]">
                <p className="font-semibold">{user.name}</p>
                <p className="text-sm">{user.username}</p>
              </div>
         </div>
       )
     }
        </DropdownMenuTrigger>
        <DropdownMenuContent className="w-60">
          <DropdownMenuItem>
            <Settings /> Settings
          </DropdownMenuItem>
          <DropdownMenuItem
            onClick={async () => {
              await logout();
              router.push("/login");
            }}
          >
            <LogOut /> Logout
          </DropdownMenuItem>
        </DropdownMenuContent>
      </DropdownMenu>
    </Sidebar>
  );
}
