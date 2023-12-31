package app;

import entities.Author;
import entities.Category;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResearchPaperTransport {
    private final String id;  // string of digits (possibly with punctuations)
    private final String title;  // assume it is in lower case
    private final List<Category> categories = new ArrayList<>();
    private final List<Author> authors;
    //    private final Map<String, Author> authors = new HashMap<>();
    private final LocalDate publishDate;
    private final String paperAbstract;
    private final String journalReference;  // assume it is in lower case
    private final String url;
    private final long upvoteCount;
    private final long downvoteCount;

    public ResearchPaperTransport(String id, String title, List<Category> categories, List<Author> authors,
                         LocalDate publishDate, String paperAbstract, String journalReference,
                         String url, long upvoteCount, long downvoteCount) {
        this.id = id;
        this.title = title;
        this.categories.addAll(categories);
        this.authors = authors;
        this.publishDate = publishDate;
        this.paperAbstract = paperAbstract;
        this.journalReference = journalReference;
        this.url = url;
        this.upvoteCount = upvoteCount;
        this.downvoteCount = downvoteCount;
    }

    public String getId() { return this.id; }

    public String getTitle() { return this.title; }

    public boolean hasAuthor(String authorName) {
        for (Author author: authors) {
            if (author.getName().equals(authorName)) { return true;}
        }
        return false;
    }

    public List<Category> getCategories() {
        return this.categories;
    }

    public String getPaperAbstract() { return this.paperAbstract; }

    public String getUrl() { return this.url; }

    public long getUpvoteCount() { return this.upvoteCount; }


    public long getDownvoteCount() { return this.downvoteCount; }

    public List<Object> toList() {
        List<Object> paperMetadata = new ArrayList<>();
        paperMetadata.add(this.id);
        paperMetadata.add(this.title);
        paperMetadata.add(this.categories);
        paperMetadata.add(this.publishDate);
        paperMetadata.add(this.paperAbstract);
        paperMetadata.add(this.journalReference);
        paperMetadata.add(this.url);
        paperMetadata.add(this.upvoteCount);
        paperMetadata.add(this.downvoteCount);;
        paperMetadata.add(this.authors);

        return paperMetadata;
    }

    public List<Author> getAuthors() {
        return this.authors;
    }
    public LocalDate getPublishDate() { return this.publishDate;}

    public String getJournalReference() { return this.journalReference;}
}
