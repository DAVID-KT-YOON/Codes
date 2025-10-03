import axios from 'axios';
import { useRef, useState } from 'react';
import TypingIndicator from './TypingIndicator';
import type { Message } from './ChatMessages';
import ChatMessages from './ChatMessages';
import ChatInput, { type ChatFormData } from './ChatInput';
import popSound from '@/assets/sounds/pop.mp3';
import notificationSound from '@/assets/sounds/notification.mp3';

const popAudio = new Audio(popSound);
popAudio.volume = 0.2;
const notificationAudio = new Audio(notificationSound);
notificationAudio.volume = 0.2;

type ChatResponse = {
   message: string;
};

const Chatbot = () => {
   //state hooks. used later as a re-rendering purposes
   const [messages, setMessages] = useState<Message[]>([]);
   const [isBotTyping, setIsBotTyping] = useState(false);
   const [error, setError] = useState('');

   //ref hook. used so conversationId is same throughout re-rendering.
   const conversationId = useRef(crypto.randomUUID());

   /* call back function given to chatInput.tsx onSubmit.
      takes type ChatFormData imported from ChatInput.tsx*/
   const onSubmit = async ({ prompt }: ChatFormData) => {
      try {
         //setting user prompts
         setMessages((prev) => [...prev, { content: prompt, role: 'user' }]);
         setIsBotTyping(true);
         setError('');

         popAudio.play();

         /* front end communication to backend using axios.*/
         const { data } = await axios.post<ChatResponse>('/api/chat', {
            prompt,
            conversationId: conversationId.current,
         });

         //setting bot messages
         setMessages((prev) => [
            ...prev,
            { content: data.message, role: 'bot' },
         ]);

         notificationAudio.play();
      } catch (error) {
         console.log(error);
         setError('something went wrong. try again');
      } finally {
         setIsBotTyping(false);
      }
   };

   return (
      <div className="flex flex-col h-full w-full bg-red-600">
         <div className="flex flex-col flex-1 gap-3 mb-10 overflow-y-auto bg-blue-500">
            <ChatMessages messages={messages}></ChatMessages>
            {isBotTyping && <TypingIndicator></TypingIndicator>}
            {error && <p className="text-red-500">{error}</p>}
         </div>
         <ChatInput onSubmit={onSubmit}></ChatInput>
      </div>
   );
};

export default Chatbot;
