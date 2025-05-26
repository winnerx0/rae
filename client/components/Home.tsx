"use client";

import api from "@/lib/api";
import { Delete, Heading4, LucideDelete, Trash } from "lucide-react";
import { useEffect, useState } from "react";
import Loading from "./Loading";
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
import { AxiosError } from "axios";
import { useRouter } from "next/navigation";

const Home = () => {
  const [data, setData] = useState<Book[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [isDeleting, setIsDeleting] = useState(false);
  const [isOpen, setIsOpen] = useState(false);

  const router = useRouter();

  useEffect(() => {
    async function fetchUsers() {
      setIsLoading(true);
      try {
        const res = await api.get("/book/");
        const ans = res.data;
        setData(ans);
      } catch (e: any) {
        if (e instanceof AxiosError) {
          console.log(e.message);
        }
      } finally {
        setIsLoading(false);
      }
    }
    fetchUsers();
  }, []);

  const handleDelete = async (id: string) => {
    setIsDeleting(true);
    const res = await api.delete(`/book/delete/${id}`);

    setIsDeleting(false);
    setIsOpen(false);
    router.refresh();
  };

  if (isLoading) return <Loading />;

  return (
    <div>
      <section className="w-full max-w-4xl grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 place-items-center gap-4">
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
                  <span>Author: {book.author.name}</span>
                </section>
                <AlertDialog open={isOpen} onOpenChange={setIsOpen}>
                  <AlertDialogTrigger>
                    <Trash className="cursor-pointer size-[25px]" />
                  </AlertDialogTrigger>
                  <AlertDialogContent>
                    <AlertDialogHeader>
                      <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                      <AlertDialogDescription>
                        This action cannot be undone.
                      </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                      <AlertDialogCancel>Cancel</AlertDialogCancel>
                      <AlertDialogAction
                        disabled={isDeleting}
                        onClick={() => handleDelete(book.id)}
                      >
                        <span>Delete</span>
                        {isDeleting && <span className="loader"></span>}
                      </AlertDialogAction>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </AlertDialog>
              </div>
            </div>
          ))}
      </section>
    </div>
  );
};

export default Home;
