"use client";

import { HistoryIcon, HomeIcon, NotebookTabsIcon } from "lucide-react";

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

export function AppSidebar() {
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
    </Sidebar>
  );
}
