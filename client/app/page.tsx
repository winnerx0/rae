import { buttonVariants } from "@/components/ui/button";
import { AlignRight, ArrowRight } from "lucide-react";
import Link from "next/link";

const page = () => {
  return (
    <div className="flex flex-col gap-16 w-full">
      <div className="flex items-center justify-center border-b">
        <nav className="z-50 sticky top-0 flex items-center justify-between max-w-5xl w-full h-14 bg-background gap-4">
          <h1 className="text-2xl font-bold">Rae</h1>
          <div className="flex gap-4 text-md items-center font-bold">
            <Link href={"/home"} className="underline">
              Home
            </Link>
          </div>
          <AlignRight className="flex sm:hidden" />
          <div className="hidden sm:flex items-center">
            <Link
              href={"/login"}
              className={buttonVariants({
                variant: "ghost",
                className: "rounded-full",
              })}
            >
              Login
            </Link>

            <Link
              href={"/register"}
              className={buttonVariants({ className: "rounded-[9999px]" })}
            >
              <ArrowRight />
              Get Started
            </Link>
          </div>
        </nav>
      </div>

      <section className="w-full max-w-5xl self-center">
        <div className="self-start mt-36 lg:w-2/3">
          <h4 className="text-3xl md:text-4xl xl:text-6xl font-bold">
            Feel Calm And Prioterize Your Mental Health <span className="text-blue-500">Faster</span> And{" "}
            <span className="text-blue-500">Easier</span>
          </h4>
          <p className="text-lg md:text-2xl">
            Better Understanding Of Yourself
          </p>
        </div>
      </section>
      <section className="border w-full max-w-5xl h-48 self-center"></section>
    </div>
  );
};

export default page;
