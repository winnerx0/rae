import { NextRequest, NextResponse } from "next/server";

export const middleware = async (req: NextRequest) => {
  const token = req.cookies.get("token")?.value;

  if (!token) {
    return NextResponse.redirect(new URL("/login", req.nextUrl));
  }

  const res = await fetch(
    process.env.NEXT_PUBLIC_BACKEND_URL + "/auth/verify-token",
    {
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
      },
    },
  );
  if (res.status !== 200) {
    return NextResponse.redirect(new URL("/login", req.nextUrl));
  }
  return NextResponse.next();
};

export const config = {
  matcher: ["/home", "/s/:path*", "/sessions"],
};
