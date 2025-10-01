import {pool} from "./connectToDB";

export async function logConversationId(conversationId:string){
    return pool.query(
            "INSERT INTO conversations (id) VALUES (?) ON DUPLICATE KEY UPDATE id = id",
            [conversationId]
            );
}

export async function logUserMessages(conversationId:string, prompt:string){
    return pool.query(
            "INSERT INTO messages (conversation_id, sender, content) VALUES (?, ?, ?)",
            [conversationId, "user", prompt]
        );
}
export async function logBotMessages(conversationId:string, text:string, responseId:string){
    return pool.query(
                "INSERT INTO messages (conversation_id, sender, content, response_id) VALUES (?, ?, ?, ?)",
                [conversationId, "bot", text, responseId]
            );
}