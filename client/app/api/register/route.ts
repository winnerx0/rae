import { cookies } from "next/headers";
import { NextRequest, NextResponse } from "next/server";

export const POST = async (req: NextRequest) => {
  try {
    const cookieStore = await cookies();
    const { email, password, name } = await req.json();

    const res = await fetch(
      process.env.NEXT_PUBLIC_BACKEND_URL + "/auth/register",
      {
        method: "POST",
        body: JSON.stringify({
          email,
          password,
          name,
        }),
        headers: {
          "Content-Type": "application/json",
        },
      },
    );

    if (res.status !== 200) {
      return NextResponse.json(
        { message: (await res.json()).message },
        {
          status: res.status,
        },
      );
    }

    const { token } = await res.json();
    cookieStore.set({
      name: "token",
      httpOnly: true,
      value: token,
      sameSite: "lax",
      secure: true,
      maxAge: 60 * 60 * 24 * 7,
    });
    return NextResponse.json("Register Successful");
  } catch (e) {
    if (e instanceof Error) {
      return NextResponse.json(
        { message: e.message },
        {
          status: 500,
        },
      );
    }
  }
};
