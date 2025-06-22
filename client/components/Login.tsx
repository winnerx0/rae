"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import Link from "next/link";
import { useRouter } from "next/navigation";
import React, { useState } from "react";
import { FcGoogle } from "react-icons/fc";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const router = useRouter();

  const handleSubmit = async () => {
    setIsLoading(true);
    try {
      const response = await fetch("/api/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email,
          password,
        }),
      });

      const { message } = await response.json();
      if (!response.ok) {
        throw new Error(message || "Login failed");
      }
      router.push("/home");
      setSuccess("Login successful!");
      setError(null);
    } catch (error) {
      if (error instanceof Error) {
        setError(error.message);
      }
      setSuccess(null);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <form className="w-full max-w-[500px] px-4 py-6 gap-4 flex flex-col justify-center">
      <h2 className="text-3xl font-bold mb-8">Login</h2>
      {success && <p className="text-green-500">{success}</p>}
      <div className="space-y-2 ">
        <Label>Email</Label>
        <Input
          name="email"
          placeholder="mark10@gmail.com"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="py-5"
        />
      </div>
      <div className="space-y-2">
        <Label>Password</Label>
        <Input
          name="password"
          placeholder="mark1234"
          type="password"
          value={password}
          className="py-5"
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>

      {error && <p className="text-red-500 text-center text-sm">{error}</p>}
      <Button
        onClick={handleSubmit}
        disabled={isLoading}
        className="flex items-center gap-2 py-5 rounded-full"
      >
        <p>Login</p>

        {isLoading && <p className="loader"></p>}
      </Button>
      <Button
        className="mt-4 rounded-full py-5"
        onClick={() =>
          router.push("http://localhost:8080/oauth2/authorization/google")
        }
      >
        <FcGoogle /> Continue With Google
      </Button>
      <p className="text-center">
        Dont&apos;t have an account ?{" "}
        <Link className="text-blue-500" href={"/register"}>
          Register
        </Link>
      </p>
    </form>
  );
};

export default Login;
