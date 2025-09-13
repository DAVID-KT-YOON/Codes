/**
 * In-memory store mapping conversation IDs to their latest response IDs.
 * 
 * Key: conversationId (string, UUID for each conversation)
 * Value: responseId (string, returned by the LLM for the last message)
 */
const conversations = new Map<string, string>();


/**
 * Repository for managing conversation state.
 * Provides methods to:
 *  - Retrieve the last responseId for a given conversationId.
 *  - Update the stored responseId when a new message is received.
 */
export const conversationRepository = {
    getLastResponseId(conversationId:string){
        return conversations.get(conversationId);
    },
    setLastResponseId(conversationId:string,responseId:string){
        conversations.set(conversationId,responseId);
    }
}
