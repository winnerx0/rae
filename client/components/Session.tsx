"use client";
import { createMessage, getMessages } from "@/lib/server-actions";
import { SendHorizontal } from "lucide-react";
import { useEffect, useRef, useState } from "react";
import Loading from "./Loading";
import { Textarea } from "./ui/textarea";

const Session = ({ sessionId }: { sessionId: string }) => {
  const [message, setMessage] = useState("");
  const [messages, setMessages] = useState<Message[]>([]);
  const messagesEndRef = useRef<HTMLDivElement>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isThinking, setIsThinking] = useState(false);
  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messages]);

  useEffect(() => {
    async function fetchMessages() {
      const res = await getMessages(sessionId);

      setMessages(res);
      setIsLoading(false);
      setIsThinking(false);
    }
    fetchMessages();
  }, [sessionId]);

  const handleMessage = async () => {
    if (!message.trim()) return;
    setIsThinking(true);
    try {
      const text = message;
      setMessage("");

      const userMessage: Message = {
        id: Date.now().toString(),
        content: text,
        role: "user",
        createdAt: new Date(),
      };
      setMessages((prev) => [...prev, userMessage]);

      const content = await createMessage(sessionId, text);

      const aiMessage: Message = {
        id: (Date.now() + 1).toString(),
        content,
        role: "model",
        createdAt: new Date(),
      };
      setMessages((prev) => [...prev, aiMessage]);
    } catch (e) {
      console.log(e);
    } finally {
      setIsThinking(false);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault();
      handleMessage();
    }
  };

  return (
    <div className="h-dvh flex flex-col">
      <div className="flex-1 ">
        <div className="flex flex-col h-full w-full max-w-3xl mx-auto px-4 py-4 gap-4 pb-28">
          {isLoading ? (
            <Loading />
          ) : messages.length === 0 ? (
            <div className="flex items-center justify-center h-full text-muted-foreground">
              <p className="text-2xl font-bold">Start a conversation</p>
            </div>
          ) : (
            messages.map((m) => {
              const formattedText = m.content
                .replace(/\*(.*?)\*/g, "<strong>$1</strong>")
                .replace(/_(.*?)_/g, "<em>$1</em>");
              return (
                <div
                  key={m.id}
                  className={`bg-card border p-3 h-min w-max rounded-lg max-w-[80%] ${
                    m.role === "user"
                      ? "self-end bg-primary text-primary-foreground"
                      : "self-start"
                  }`}
                >
                  <p
                    dangerouslySetInnerHTML={{ __html: formattedText }}
                    className="text-wrap whitespace-pre-wrap"
                  />
                </div>
              );
            })
          )}
          {isThinking && (
            <div className="bg-card border p-3 h-min w-max rounded-lg max-w-[80%] self-start">
              <span className="animate-pulse">Thinking...</span>
            </div>
          )}
          <div ref={messagesEndRef} />
        </div>
      </div>

      <div className="bg-background bottom-0 w-full max-w-3xl self-center">
        <div className="w-full mx-auto p-4">
          <div className="relative bg-card rounded-xl border">
            <Textarea
              value={message}
              placeholder="Type your message..."
              className="resize-none min-h-[60px] max-h-[200px] pr-12 border-none bg-transparent focus:border-none focus-visible:ring-0 focus-visible:ring-offset-0"
              onChange={(e) => setMessage(e.target.value)}
              onKeyDown={handleKeyPress}
            />
            <button
              onClick={handleMessage}
              disabled={!message.trim()}
              className="absolute right-3 top-1/2 -translate-y-1/2 p-2 rounded-md hover:bg-accent disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
            >
              <SendHorizontal className="h-4 w-4" />
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Session;
