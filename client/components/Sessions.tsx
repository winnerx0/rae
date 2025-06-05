"use client";

import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import api from "@/lib/api";
import { AxiosError } from "axios";
import { ChevronRight, TrashIcon } from "lucide-react";
import { useRouter } from "next/navigation";
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
import { deleteSession, getSessions } from "@/actions/server-actions";

const Sessions = () => {
  const [sessions, setSessions] = useState<Session[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  const router = useRouter();

  useEffect(() => {
    async function fetchSessions() {
      const res = await getSessions();

      setSessions(res);
      setIsLoading(false);
    }
    fetchSessions();
  }, []);

  const handleDeleteSession = async (
    sessionId: string,
    e: React.MouseEvent,
  ) => {
    e.stopPropagation();
    setSessions((prev) => prev.filter((s) => s.id !== sessionId));
    deleteSession(sessionId);
    router.refresh();
  };

  if (isLoading) {
    return <Loading />;
  }

  return (
    <section className="w-[400px] h-[600px] flex flex-col gap-2 items-center justify-center max-w-4xl self-center">
      <h2 className="text-start text-2xl font-bold w-full">
        Previous Sessions
      </h2>
      {sessions.length === 0 ? (
        <p>No Sessions Created</p>
      ) : (
        sessions.map((session) => (
          <Card
            className="w-[400px] h-12 p-0 flex justify-center"
            key={session.id}
            onClick={() => router.push("/s/" + session.id)}
          >
            <CardHeader className="py-0">
              <CardTitle className="py-0 m-0 flex items-center justify-between">
                <p>{session.name}</p>
                <div className="flex gap-2">
                  <ChevronRight className="flex justify-end" />
                  <AlertDialog>
                    <AlertDialogTrigger
                      onClick={(e) => e.stopPropagation()}
                      className="cursor-pointer"
                    >
                      <TrashIcon />
                    </AlertDialogTrigger>
                    <AlertDialogContent>
                      <AlertDialogHeader>
                        <AlertDialogTitle>Are you sure?</AlertDialogTitle>
                        <AlertDialogDescription>
                          This action cannot be undone. This will permanently
                          delete this session and remove its data from our
                          servers.
                        </AlertDialogDescription>
                      </AlertDialogHeader>
                      <AlertDialogFooter>
                        <AlertDialogCancel>Cancel</AlertDialogCancel>
                        <AlertDialogAction
                          onClick={(e) => handleDeleteSession(session.id, e)}
                          className="text-white"
                        >
                          Delete
                        </AlertDialogAction>
                      </AlertDialogFooter>
                    </AlertDialogContent>
                  </AlertDialog>
                </div>
              </CardTitle>
            </CardHeader>
          </Card>
        ))
      )}
    </section>
  );
};

export default Sessions;
