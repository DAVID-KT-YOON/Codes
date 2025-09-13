import React from 'react';

/**
 * typingindicator that returns 3 dots that animate.
 */
const TypingIndicator = () => {
   return (
      <div className="flex self-start gap-1 px-3 py-3 bg-gray-200 - rounded-xl">
         <Dot></Dot>
         <Dot className="[animation-delay:0.2s]"></Dot>
         <Dot className="[animation-delay:0.4s]"></Dot>
      </div>
   );
};

/**
 * typescript for Dots
 */
type DotProps = {
   className?: string;
};
/**
 * creation of Dots with dynamic implementation of animation delays.
 */
const Dot = (className: DotProps) => (
   <div
      className={`w-2 h-2 rounded-full bg-gray-800 animate-pulse ${className}`}
   ></div>
);
export default TypingIndicator;
