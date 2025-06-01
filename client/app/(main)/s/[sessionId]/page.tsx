import Session from "@/components/Session"

const page = ({ params: { sessionId } }: { params: { sessionId: string } }) => {
  
  return (
    <div className="h-full">
      <Session sessionId={ sessionId } />
    </div>
  )
}

export default page