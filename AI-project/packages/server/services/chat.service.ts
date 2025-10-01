import OpenAI from "openai";
import { conversationRepository } from "../repositories/conversation.repository";
import fs from 'fs';
import path from 'path';
import template from '../prompts/chatbot.txt';
import type { Chat } from "openai/resources";
import { error } from "console";
import mysql from "mysql2/promise";
import type { RowDataPacket } from "mysql2";
import {pool} from "../database/connectToDB"
import { logBotMessages, logConversationId, logUserMessages } from "../database/databaseQueries";
/**
 * Create an OpenAI client instance.
 * Uses the API key stored in the .env file for secure authentication.
 */
const client = new OpenAI({
   apiKey: process.env.OPENAI_API_KEY
});

/**
 * Load external content and prepare instructions.
 * Reads park information from WonderWorld.md and injects it into the chatbot
 * template (chatbot.txt) by replacing the {{parkInfo}} placeholder.
 */
const parkInfo = fs.readFileSync(path.join(__dirname, '..', 'prompts', 'WonderWorld.md'), 'utf-8');
const instructions = template.replace('{{parkInfo}}', parkInfo);

/**
 * Type definition for chatbot responses.
 * Each response contains a unique ID and the generated message text.
 */
type ChatResponse ={
    id: string,
    message:string
}
/**
 * Chat service for sending messages to the LLM.
 *
 * - Accepts a user prompt and conversationId as inputs.
 * - Passes the last stored response ID for the conversationId as
 *   `previous_response_id` to maintain conversational context.
 * - Updates the repository so that the conversationId points to the
 *   latest response ID returned by the LLM.
 * - Returns an object containing the new response ID and the output text.
 */
export const chatService ={
    async sendMessage(prompt:string, conversationId:string):Promise<ChatResponse>{
        const [conversations_result] = await logConversationId(conversationId);
        const [messages_user_result] = await logUserMessages(conversationId,prompt);

        const response = await client.responses.create({
            model: 'gpt-4o-mini',
            instructions,
            input: prompt,
            temperature: 0.2,
            max_output_tokens:200,
            previous_response_id:conversationRepository.getLastResponseId(conversationId)
        });

        const [result] = await logBotMessages(conversationId,response.output_text,response.id);

        conversationRepository.setLastResponseId(conversationId,response.id)

        return {
            id:response.id,
            message:response.output_text
        };
    }
}