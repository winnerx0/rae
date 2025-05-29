"use client";

import api from "@/lib/api";
import { useEffect, useState } from "react";
import Loading from "./Loading";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"

interface Book {
  id: string;
  title: string;
  description: string;
  stars: number;
  image: string;
  author: {
    name: string;
  };
}

const Home = () => {
  const [books, setBooks] = useState<Book[]>([]);
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    const fetchBooks = async () => {
      setIsLoading(true);
      try {
        const response = await api.get("/book/");
        setBooks(response.data);
      } catch (error) {
        console.error("Failed to fetch books:", error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchBooks();
  }, []);

  const handleDelete = async (id: string) => {
    try {
      await api.delete(`/book/delete/${id}`);
      setBooks((prevBooks) => prevBooks.filter((book) => book.id !== id));
    } catch (error) {
      console.error("Failed to delete book:", error);
    }
  };

  if (isLoading) return <Loading />;

  if (books.length === 0)
    return <p className="text-center mt-10">No books available.</p>;

  return (
    <section className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 w-full max-w-4xl mx-auto">
      {books.map(({ id, image, title, stars, author, description }) => (
        
        <Card key={id}>
          <CardHeader>
            <CardTitle>{ title }</CardTitle>
          </CardHeader>
          <CardContent>
             <img src={image} alt={title} className="rounded-md object-cover" />

          </CardContent>
          <CardFooter className="flex-col items-start">
            <p>( {stars} by {  author.name } )</p>
            <span>{ description }</span>
          </CardFooter>
        </Card>

      
      ))}
    </section>
  );
};

export default Home;