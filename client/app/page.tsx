import { buttonVariants } from "@/components/ui/button"
import { Card, CardContent } from "@/components/ui/card"
import { AlignRight, ArrowRight, Brain, CheckCircle, Heart, Shield, Star, Users } from "lucide-react"
import Link from "next/link"

const page = () => {
  return (
    <div className="flex flex-col gap-16 w-full">
      <div className="flex items-center justify-center border-b">
        <nav className="z-50 sticky top-0 flex items-center justify-between max-w-5xl w-full h-14 bg-background gap-4 px-4">
          <h1 className="text-2xl font-bold">Simon</h1>
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

            <Link href={"/register"} className={buttonVariants({ className: "rounded-[9999px]" })}>
              <ArrowRight />
              Get Started
            </Link>
          </div>
        </nav>
      </div>

      {/* Hero Section */}
      <section className="w-full max-w-5xl self-center px-4">
        <div className="self-start mt-16 lg:mt-36 lg:w-2/3">
    
          <h1 className="text-3xl md:text-4xl xl:text-6xl font-bold leading-tight">
            Feel Calm And Prioritize Your Mental Health {" "}
            <span className="text-blue-500">With Ease</span>
          </h1>
          <p className="text-lg md:text-2xl text-muted-foreground mt-6 mb-8">
            Better Understanding Of Yourself Through Personalized Mental Health Tools
          </p>
          <div className="flex flex-col sm:flex-row gap-4">
            <Link
              href="/register"
              className={buttonVariants({
                className: "rounded-full text-lg px-12 py-6",
              })}
            >
              Start Your Journey
              <ArrowRight className="ml-2" />
            </Link>
            {/*<Link*/}
            {/*  href={"/learn-more"}*/}
            {/*  className={buttonVariants({*/}
            {/*    variant: "outline",*/}
            {/*    className: "rounded-full text-lg px-8 py-6",*/}
            {/*  })}*/}
            {/*>*/}
            {/*  Learn More*/}
            {/*</Link>*/}
          </div>
        </div>
      </section>

      {/* Hero Image/Video Placeholder */}
      <section className="w-full max-w-5xl self-center px-4">
        <div className="relative border rounded-2xl h-64 md:h-96 flex items-center justify-center">
          <div className="text-center">
            <Brain className="w-16 h-16 text-blue-500 mx-auto mb-4" />
            <p className="text-lg text-muted-foreground">Interactive Mental Health Dashboard</p>
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="w-full max-w-5xl self-center px-4">
        <div className="text-center mb-12">
          <h2 className="text-3xl md:text-4xl font-bold mb-4">Everything You Need for Mental Wellness</h2>
          <p className="text-lg text-muted-foreground max-w-2xl mx-auto">
            Comprehensive tools designed to support your mental health journey with personalized insights and
            professional guidance.
          </p>
        </div>

        <div className="grid md:grid-cols-2 lg:grid-cols-2 gap-6">



          <Card className="p-6 hover:shadow-lg transition-shadow">
            <CardContent className="p-0">
              <Shield className="w-12 h-12 text-blue-500 mb-4" />
              <h3 className="text-xl font-semibold mb-2">Privacy First</h3>
              <p className="text-muted-foreground">
                Your mental health data is encrypted and never shared without your consent.
              </p>
            </CardContent>
          </Card>

          <Card className="p-6 hover:shadow-lg transition-shadow">
            <CardContent className="p-0">
              <Star className="w-12 h-12 text-blue-500 mb-4" />
              <h3 className="text-xl font-semibold mb-2">AI Help</h3>
              <p className="text-muted-foreground">AI therapy that actually listens.</p>
            </CardContent>
          </Card>
        </div>
      </section>

      {/* Stats Section */}
      {/* <section className="w-full max-w-5xl self-center px-4">
        <div className="bg-blue-50 rounded-2xl p-8 md:p-12">
          <div className="grid md:grid-cols-3 gap-8 text-center">
            <div>
              <h3 className="text-3xl md:text-4xl font-bold text-blue-600 mb-2">10K+</h3>
              <p className="text-muted-foreground">Active Users</p>
            </div>
            <div>
              <h3 className="text-3xl md:text-4xl font-bold text-blue-600 mb-2">95%</h3>
              <p className="text-muted-foreground">Satisfaction Rate</p>
            </div>
            <div>
              <h3 className="text-3xl md:text-4xl font-bold text-blue-600 mb-2">24/7</h3>
              <p className="text-muted-foreground">Support Available</p>
            </div>
          </div>
        </div>
      </section> */}

      {/* Final CTA Section */}
      <section className="w-full max-w-5xl self-center px-4">
        <div className="text-center bg-gradient-to-r from-blue-600 to-indigo-600 text-white rounded-2xl p-8 md:p-12">
          <h2 className="text-3xl md:text-4xl font-bold mb-4">Ready to Start Your Mental Health Journey?</h2>
          <p className="text-lg mb-8 opacity-90 max-w-2xl mx-auto">
            Join the community of people who have already improved their mental wellness with Simon. Start your free trial
            today.
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Link
              href={"/register"}
              className={buttonVariants({
                className: "rounded-full text-lg px-8 py-6 bg-white text-black",
              })}
            >
              Get Started Free
              <ArrowRight className="ml-2 rounded-full" />
            </Link>
          
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="w-full max-w-5xl flex self-center items-center justify-center px-4 py-8 border-t">
        <div className="flex items-center gap-4">
          <h3 className="text-xl font-bold">Simon</h3>
          <p className="text-muted-foreground">© 2025 All rights reserved</p>
        </div>
      </footer>
    </div>
  )
}

export default page
