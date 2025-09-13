import type { Request, Response } from "express"
import { chatService } from "../services/chat.service";
import z from 'zod';

/**
 * Zod schema for validating incoming chat requests.
 * Ensures that:
 *  - `prompt` is a trimmed, non-empty string up to 1000 characters.
 *  - `conversationId` is a valid UUID.
 */
const chatSchema = z.object({
    prompt:z.string()
    .trim()
    .min(1,'prompt is required')
    .max(1000, "prompt is too long max 1000 characters"),
    conversationId:z.string().uuid()
});

/**
 * Chat controller that handles requests from the client.
 *
 * - Validates the request body against `chatSchema`.
 * - If validation fails, responds with a 400 status and validation errors.
 * - If validation succeeds, passes the prompt and conversationId to `chatService`.
 * - Returns the chatbot's response message as JSON.
 * - On unexpected errors, responds with a 500 status and an error message.
 */
export const chatController={
    async sendMessage(req:Request, res:Response){
        const parseResult = chatSchema.safeParse(req.body);
    
    if(!parseResult.success){
        return res.status(400).json(parseResult.error.format());
    }
    
    try {
        const {prompt, conversationId} = req.body;
        const response = await chatService.sendMessage(prompt,conversationId)
        res.json({message: response.message})
        
    } catch (error) {
        res.status(500).json({error:'failed to generate a response.'})
    }
    }
}