import Sidebar from "@/components/Sidebar"
import Home from "../../../components/Home"

const page = () => {
  

  return (
    <div className="flex justify-between h-full w-full">
      <Sidebar/>
      <Home/>
    </div>
  )
}

export default page
b