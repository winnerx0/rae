import Session from "@/components/Session"

type Params = Promise<{ sessionId: string }>

const page = async (props: { params: Params }) => {
  
  const sessionId = (await props.params).sessionId
  
  return (
    <div className="h-full">
      <Session sessionId={ sessionId } />
    </div>
  )
}

export default page