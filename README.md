# Simon - Your AI-Powered Therapist

Simon is a modern AI therapist platform built to help users talk about their problems and receive thoughtful, AI-driven responses. The platform combines a sleek frontend with a powerful backend to deliver a seamless mental wellness experience.

---

## Tech Stack

### Frontend

* **Next.js** 
* **Tailwind CSS**
* **shadcn/ui**

### Backend

* **Spring Boot**
* **PostgreSQL**
* **Swagger/OpenAPI**
* **Docker**

---

## Features

* AI-powered therapist chat experience
* Secure user authentication and session management
* Store and retrieve therapy sessions and chat logs
* Clean, accessible, responsive UI
* API documentation via Swagger UI
* Fully containerized setup for easy deployment

---

## Getting Started

### Prerequisites

* Docker
* Java 21+
* Node.js 22+

### 1. Clone the repository

```bash
git clone https://github.com/winnerx0/simon.git
cd simon
```

### 2. Set up environment variables

Create a `.env` file in `simon/` and `client/` directories:

**.env**

```env
SECRET_KEY=

CLOUDINARY_API_KEY=
CLOUDINARY_SECRET_KEY=
CLOUDINARY_CLOUD_NAME=

GOOGLE_API_KEY=

GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=

POSTGRES_USERNAME=
POSTGRES_PASSWORD=
POSTGRES_HOSTNAME=
POSTGRES_DATABASE=
```

**client/.env**

```env
NEXT_PUBLIC_BACKEND_URL=http://localhost:8080/api/v1
```

### 3. Start the application

```bash
docker build -t simon .
```

* Frontend: [http://localhost:3000](http://localhost:3000)
* Backend API: [http://localhost:8080](http://localhost:8080)
* Swagger Docs: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## API Documentation

The API is documented using Swagger.

* Visit: `http://localhost:8080/swagger-ui.html`
* Explore available endpoints and models

---

## Deployment

To deploy Simon to production:

1. Use Docker
2. Point environment variables to production database and frontend URLs
3. Use HTTPS and secure secrets with vaults or environment management

---

## Contributing

We welcome contributions! To contribute:

1. Fork the repo
2. Create a feature branch
3. Make your changes
4. Submit a pull request

---

## License

MIT License. See `LICENSE` file for details.

---