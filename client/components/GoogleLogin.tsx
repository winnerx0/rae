import { useRouter } from "next/navigation";
import { Button } from "./ui/button";
import { FcGoogle } from "react-icons/fc";

const GoogleLogin = () => {
  const router = useRouter();
  return (
    <Button
      className="mt-4 rounded-full py-5"
      onClick={() =>
        router.push(
          `https://simon-i3w9.onrender.com/oauth2/authorization/google`,
        )
      }
    >
      <FcGoogle /> Continue With Google
    </Button>
  );
};

export default GoogleLogin;
