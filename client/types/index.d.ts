declare type User = {
  name: string
  username: string
  
}

declare type Message = {
  id: string
  content: string
  role: "user" | "model",
  createdAt: Date
}

declare type Session = {
  id: string
  name: string
}