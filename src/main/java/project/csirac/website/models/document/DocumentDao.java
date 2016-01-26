package project.csirac.website.models.document;

import project.csirac.website.models.document.entities.Document;

import java.util.Date;

/**
 * Created by Dy.Zhao on 2016/1/3 0003.
 */
public class DocumentDao {
    /**
     * Return the document entity find by the document's name.
     *
     * @param name The document's name.
     * @return The document entity.
     */
    public Document findByName(String name) {
        return new Document(name, "How to use CSIRAC emulator in this web application. Step 1:According the guide from <a href=\"/Use_Guide/user_guide.pdf\">User Guide</a>, write down the instructions in the first blank and submit the program. Step 2: Input the data and submit. Step 3: When the monitor status shows 'Ready', Submit the frequency and Start the submitted program. If you want to pause at the process of the running program, press 'Pause' and revise the PCRegister if you want. Step 4: Continue the progress. Read the program memory history, register history and data memory history from the web.", new Date());
    }
}
