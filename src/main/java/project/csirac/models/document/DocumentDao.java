package project.csirac.models.document;

import project.csirac.models.document.entities.Document;

import java.util.Date;

/**
 * Created by Dy.Zhao on 2016/1/3 0003.
 */
public class DocumentDao
{
    /**
     * Return the document entity find by the document's name.
     *
     * @param name The document's name.
     *
     * @return The document entity.
     */
    public Document findByName(String name)
    {
        return new Document(name, "First you set up a basic build script. You can use any build system you like when building apps with Spring, but the code you need to work with Maven is included here. If you’re not familiar with Maven, refer to Building Java Projects with Maven.", new Date());
    }
}
