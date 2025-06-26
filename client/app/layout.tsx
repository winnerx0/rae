import type { Metadata } from "next";
import { Roboto, Roboto_Mono } from "next/font/google";

import "./globals.css";
import { Toaster } from "@/components/ui/sonner";

const roboto = Roboto({
  variable: "--font-roboto-sans",
  subsets: ["latin"],
  weight: ["100", "200", "300", "400", "500", "600", "700", "800", "900"]
});

const robotoMono = Roboto_Mono({
  variable: "--font-roboto-mono",
  subsets: ["latin"],
  weight: ["100", "200", "300", "400", "500", "600", "700"]
});

export const metadata: Metadata = {
  title: "Simon",
  description: "AI Therapist",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className="dark">
      <body
        className={`${roboto.variable} ${robotoMono.variable} antialiased`}
      >
        <main className="w-full px-4">{children}</main>
        <Toaster />
      </body>
    </html>
  );
}
