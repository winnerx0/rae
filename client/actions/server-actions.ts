"use server";

import { cookies } from "next/headers";

export const getUser = async () => {
  const cookieStore = await cookies();
  const res = await fetch(process.env.NEXT_PUBLIC_BACKEND_URL + "/user/me", {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${cookieStore.get("token")?.value}`,
    },
  });

  const user: User = await res.json();
  return user;
};

export const getMessages = async (sessionId: string) => {
  const cookieStore = await cookies();
  const res = await fetch(
    `${process.env.NEXT_PUBLIC_BACKEND_URL}/ai/${sessionId}`,
    {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${cookieStore.get("token")?.value}`,
      },
    },
  );

  const messages: Message[] = await res.json();
  return messages;
};

export const createMessage = async (sessionId: string, message: string) => {
  const cookieStore = await cookies();
  const res = await fetch(
    `${process.env.NEXT_PUBLIC_BACKEND_URL}/ai/${sessionId}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${cookieStore.get("token")?.value}`,
      },
      body: JSON.stringify({
        message,
      }),
    },
  );

  const { message: response } = await res.json();
  return response as string;
};

export const getSessions = async () => {
  const cookieStore = await cookies();
  const res = await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/session/`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${cookieStore.get("token")?.value}`,
    },
  });

  const sessions = await res.json();
  return sessions as Session[];
};

export const deleteSession = async (sessionId: string) => {
  const cookieStore = await cookies();
  await fetch(`${process.env.NEXT_PUBLIC_BACKEND_URL}/session/${sessionId}`, {
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${cookieStore.get("token")?.value}`,
    },
  });
};

export const logout = async () => {
    const cookieStore = await cookies();
  cookieStore.delete("token")
}