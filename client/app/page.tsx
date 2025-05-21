import { Button } from "@/components/ui/button";
import { ArrowRight } from "lucide-react";

const page = () => {
  return (
    <div className="flex flex-col gap-16 w-full">
      <div className="flex items-center justify-center border-b py-2">
        <nav className="flex items-center justify-between max-w-5xl w-full ">
          <h1 className="text-2xl font-bold">Rae</h1>
          <div className="flex gap-4 text-xl items-center font-bold">
            <span>Home</span>
            <span>Feed</span>
          </div>
          <div className="flex items-center">
            <Button className="rounded-full" variant={"ghost"}>Login</Button>
            <Button className="rounded-full">
              <ArrowRight />
              Get Started
            </Button>
          </div>
        </nav>
      </div>

<section className="w-full max-w-5xl self-center">

<div className="self-start mt-36 lg:w-2/3">
  <h4 className="text-2xl md:text-4xl xl:text-6xl font-bold">Track Your Books, <span className="text-blue-500">Faster</span> And <span className="text-blue-500">Easier</span></h4>
</div>
</section>
      <section className="border w-full max-w-5xl h-48 self-center">

      </section>
    </div>
  );
};

export default page;
