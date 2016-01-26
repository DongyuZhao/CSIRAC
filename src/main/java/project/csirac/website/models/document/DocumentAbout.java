package project.csirac.website.models.document;

import project.csirac.website.models.document.entities.Document;

import java.util.Date;

/**
 * Created by chunyiguan on 16/1/26.
 */
public class DocumentAbout {
    /**
     * Return the document entity find by the document's name.
     *
     * @param name The document's name.
     * @return The document entity.
     */
    public Document findByName(String name) {
        return new Document(name, "About us", new Date());
    }
}
