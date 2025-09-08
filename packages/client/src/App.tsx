import { useEffect, useState } from 'react';
import Chatbot from './components/chat/Chatbot';

function App() {
   const [message, setMessage] = useState('');
   useEffect(() => {
      fetch('/api/hello')
         .then((res) => res.json())
         .then((data) => setMessage(data.message));
   }, []);

   return (
      <div className="flex justify-center">
         <div className="p-4 h-screen w-2xl ">
            <Chatbot />
         </div>
      </div>
   );
}

export default App;
