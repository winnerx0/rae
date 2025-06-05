"use client";

import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { getUser } from "@/lib/server-actions";
import { ChevronRight } from "lucide-react";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { v4 } from "uuid";

const Home = () => {
  const [user, setUser] = useState<User | null>(null);
  const router = useRouter();

  useEffect(() => {
    async function fetchUser() {
      const user = await getUser();
      setUser(user);
    }
    fetchUser();
  }, []);

  return (
    <section className="flex flex-col gap-4 items-center justify-center h-full">
      <h1 className="text-xl font-semibold">
        Hey, Welcome{" "}
        <span className="first-letter:uppercase">{user?.name}</span>
      </h1>
      <Card
        className="w-[400px] h-16 py-4"
        onClick={() => router.push("/s/" + v4())}
      >
        <CardHeader className="py-0">
          <CardTitle className="py-0 flex items-center justify-between">
            <p>Start Session</p>
            <ChevronRight className="flex justify-end" />
          </CardTitle>
        </CardHeader>
        <CardContent></CardContent>
        <CardFooter></CardFooter>
      </Card>
    </section>
  );
};

export default Home;
