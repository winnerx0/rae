"use client";

import {
    Card,
    CardContent,
    CardFooter,
    CardHeader,
    CardTitle
} from "@/components/ui/card";
import api from "@/lib/api";
import { formatDistanceToNow } from "date-fns";
import { ChevronRight } from "lucide-react";
import Link from "next/link";
import { useEffect, useState } from "react";
import Loading from "./Loading";

interface Session {
  id: string;
  name: string;
  createdAt: Date;
  user: {
    name: string;
  };
}

const Home = () => {
  const [sessions, setSessions] = useState<Session[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const fetchBooks = async () => {
      setIsLoading(true);
      try {
        const response = await api.get("/s/");
        if(response.status !== 200){
          throw new Error(response.data)
        }
        setSessions(response.data);
      } catch (error) {
        console.error("Failed to fetch books:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchBooks();
  }, []);

  if (isLoading) return <Loading />;

  if (sessions.length === 0)
    return <p className="text-center mt-10">No sessions available.</p>;

  return (
    <section className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 w-full max-w-7xl mx-auto">
      {sessions.map(({ id, name, createdAt }) => (
        <Link href={`/session/${id}`} key={id}>
        
        
        <Card >
          <CardHeader>
            <CardTitle>{ name }</CardTitle>
          </CardHeader>
          <CardContent>
             {/* <img src={image} alt={title} className="rounded-md object-cover" /> */}

          </CardContent>
          <CardFooter className="flex justify-between items-start">
            <ChevronRight/>
              <p>{ formatDistanceToNow(createdAt) }</p>
          </CardFooter>
        </Card>
      </Link>

      
      ))}
    </section>
  );
};

export default Home;