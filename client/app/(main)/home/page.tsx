import { Metadata } from "next"
import Home from "../../../components/Home"
import Sidebar from "@/components/Sidebar"

export const metadata: Metadata = {
    title: "Rae | Home"
}
  
const page = () => {
  
  return (
    <div className="flex justify-between h-full">
      <Sidebar/>
      <Home/>
    </div>
  )
}

export default page
