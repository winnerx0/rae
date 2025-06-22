"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import Link from "next/link";
import { useRouter } from "next/navigation";
import React, { useState } from "react";
import { FcGoogle } from "react-icons/fc";

const Register = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);
  const [isLoading, setIsLoading] = useState(false);

  const router = useRouter();

  const handleSubmit = async () => {
    setIsLoading(true);
    try {
      const response = await fetch("/api/register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: username,
          email,
          password,
        }),
      });

      if (!response.ok) {
        throw new Error(
          (await response.json()).message || "Registration failed",
        );
      }

      setSuccess("Registration successful!");
      router.push("/home");
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
    <form className="w-full max-w-[500px] px-4 py-6 gap-8 flex flex-col justify-center">
      {" "}
      <h2 className="text-3xl font-bold mb-8">Register</h2>
      {success && <p className="text-green-500">{success}</p>}
      <div className="space-y-2 ">
        <Label>Username</Label>
        <Input
          name="username"
          placeholder="Mark"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="py-5"
        />
      </div>
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
      <div className="space-y-2 ">
        <Label>Password</Label>
        <Input
          name="password"
          placeholder="mark1234"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          className="py-5"
        />
      </div>
      {error && <p className="text-red-500 text-center text-sm">{error}</p>}
      <Button
        onClick={handleSubmit}
        disabled={isLoading}
        className="flex items-center gap-2 py-5 rounded-full"
      >
        <p>Register</p>

        {isLoading && <p className="loader"></p>}
      </Button>
      <Button
        className="mt-4 py-5 rounded-full"
        onClick={() =>
          router.push("http://localhost:8080/oauth2/authorization/google")
        }
      >
        <FcGoogle /> Continue With Google
      </Button>
      <p className="text-center">
        Have an account ?{" "}
        <Link className="text-blue-500" href={"/login"}>
          Login
        </Link>
      </p>
    </form>
  );
};

export default Register;
