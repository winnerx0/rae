"use client";

import api from "@/lib/api";
import { Delete, Heading4, LucideDelete, Trash } from "lucide-react";
import { useEffect, useState } from "react";
import Loading from "./Loading";

const Home = () => {
  const [data, setData] = useState<Book[]>([]);
  const [isLoading, setIsLoading] = useState(false);
    
  useEffect(() => {
    async function fetchUsers() {
      setIsLoading(true)
      const res = await api.get("/book/");
      const ans = res.data;
      setData(ans);
      setIsLoading(false)
    }
    fetchUsers();
  }, []);

  const handleDelete = async (id: string) => {
    const res = await api.delete(`/book/delete/${id}`);
  };
  
  if(isLoading) return <Loading/>
  
  return (
    <div>
      <section className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 place-items-center gap-4">
        {data.length !== 0 &&
          data.map((book) => (
            <div
              className="border rounded-md px-4 py-2 flex flex-col gap-4"
              key={book.id}
            >
              <img src={book.image} alt={book.title} className="rounded-md" />
              <div className="flex items-center justify-between">
                <section>
                
                <h4 className="font-semibold">{`Name: ${book.title}`}</h4>
                  <span>Author: { book.author.username }</span>
                </section>
                
                <Trash
                  className="cursor-pointer size-[25px]"
                  onClick={() => handleDelete(book.id)}
                />
              </div>
            </div>
          ))}
      </section>
    </div>
  );
};

export default Home;
