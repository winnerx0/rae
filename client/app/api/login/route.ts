import { NextRequest, NextResponse } from "next/server";

export const POST = async (req: NextRequest) => {
  try {
    const { email, password } = await req.json();

    console.log(email)
    const res = await fetch(
      process.env.NEXT_PUBLIC_BACKEND_URL + "/auth/login",
      {
        method: "POST",
        body: JSON.stringify({ email, password }),
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    if (res.status !== 200) {
      const error = await res.json();
      return NextResponse.json({ message: error.message }, { status: res.status });
    }
    const { token } = await res.json();

    const response = NextResponse.json({ message: "Login successful" });

    response.cookies.set({
      name: "token",
      value: token,
      httpOnly: true,
      secure: true,
      sameSite: "lax",
      maxAge: 60 * 60 * 24 * 7,
    });

    return response;
  } catch (e) {
    return NextResponse.json(
      { message: "Internal Server Error" },
      { status: 500 }
    );
  }
};
