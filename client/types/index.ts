declare type Book = {
  id: string
  title: string
  description: string
  image: string
  stars: number
  created_at: Date
  author: Partial<User>
}

declare type User = {
  name: string
}