import {Guid} from "../utils/guid";
export interface SessionEnabledClient
{
    _sessionId:string;
}

export class SessionServices
{
    public static ensureSessionId(client: SessionEnabledClient)
    {
        var node = document.getElementById("session_id");
        if (node == null)
        {
            client._sessionId = Guid.newGuid();
        }
        else
        {
            client._sessionId = node.innerText;
            if (node.innerText == null || node.innerText == "")
            {
                client._sessionId = Guid.newGuid();
                node.innerText = client._sessionId;
            }
        }
    }
}