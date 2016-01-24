export class Settings
{
    sessionId = "";
    frequency = 100;

    constructor(sessionId:string, frequency:number)
    {
        this.sessionId = sessionId;
        this.frequency = frequency;
    }
}