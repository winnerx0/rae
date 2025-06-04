"use client";

import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { ChevronRight } from "lucide-react";
import { useRouter } from "next/navigation";
import { v4 } from "uuid";

const Home = () => {
  
  const router = useRouter()
  
  return (
    <section className="flex items-center justify-center h-full">
      <Card className="w-[400px] h-16 py-4" onClick={() => router.push("/s/" + v4())}>
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
