import { cookies } from "next/headers";
import { NextRequest, NextResponse } from "next/server";

export const middleware = async (req: NextRequest) => {
  const cookieStore = await cookies();
  if (req.nextUrl.pathname.startsWith("/success")) {
    cookieStore.set({
      name: "token",
      value: req.nextUrl.searchParams.values().toArray()[0],
      sameSite: "lax",
      secure: true,
      maxAge: 60 * 60 * 24 * 7,
    });
    return NextResponse.redirect(new URL("/home", req.nextUrl));
  }
  const token = req.cookies.get("token")?.value;

  if (!token) {
    return NextResponse.redirect(new URL("/login", req.nextUrl));
  }

  const res = await fetch(
    process.env.NEXT_PUBLIC_BACKEND_URL + "/user/verify-token",
    {
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      },
    },
  );
  if (res.status !== 200) {
    return NextResponse.redirect(new URL("/login", req.nextUrl));
  }
  return NextResponse.next();
};

export const config = {
  matcher: ["/home", "/s/:path*", "/sessions", "/success/:path*"],
};
