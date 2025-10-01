/**
 * In-memory store mapping conversation IDs to their latest response IDs.
 * 
 * Key: conversationId (string, UUID for each conversation)
 * Value: responseId (string, returned by the LLM for the last message)
 */
const conversations = new Map<string, string[]>();


/**
 * Repository for managing conversation state.
 * Provides methods to:
 *  - Retrieve the last responseId for a given conversationId.
 *  - Update the stored responseId when a new message is received.
 */
export const conversationRepository = {
    getLastResponseId(conversationId:string){
        const arr = conversations.get(conversationId);
        return arr && arr.length > 0 ? arr[arr.length - 1] : undefined;
    },
    setLastResponseId(conversationId:string,responseId:string){
        if(!conversations.has(conversationId)){
            conversations.set(conversationId, []);
        }
        conversations.get(conversationId)!.push(responseId);
    }

}
