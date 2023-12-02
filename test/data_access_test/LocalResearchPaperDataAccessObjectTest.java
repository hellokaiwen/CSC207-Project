package data_access_test;

import data_access.LocalResearchPaperDataAccessObject;
import entities.*;
import org.junit.Test;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

public class LocalResearchPaperDataAccessObjectTest {
    private static final String filepath = "test/test_files/papers.csv";
    private static final String emptyFilepath = "test/test_files/emptyPapers.csv";
    private final AuthorFactory af = new AuthorFactory();
    private final CategoryFactory cf = new CategoryFactory();
    private final ResearchPaperFactory rpf = new ResearchPaperFactory();

    @Test
    public void testGetPaperByIDThatExists() {
        try {
            LocalResearchPaperDataAccessObject lrpDAO = new LocalResearchPaperDataAccessObject(filepath, af, cf, rpf);
            String expectedPaperID = "0";
            ResearchPaper paper = lrpDAO.getPaperByID(expectedPaperID);
            System.out.println(paper);  // TODO

            String actualPaperID = paper.getID();
            String actualTitle = paper.getTitle();
            List<Category> actualCategories = paper.getCategories();
            List<Author> actualAuthors = paper.getAuthors();
            String actualPublishDate = paper.getPublishDate().toString();
            String actualAbstract = paper.getPaperAbstract();
            String actualJournalReference = paper.getJournalReference();
            String actualURL = paper.getUrl();
            long actualUpvoteCount = paper.getUpvoteCount();
            long actualDownvoteCount = paper.getDownvoteCount();

            StringBuilder actualCategoriesStringRep = new StringBuilder();
            for (Category category : actualCategories) {
                StringBuilder categoryStringRep = new StringBuilder(category.toString());
                categoryStringRep.deleteCharAt(0);
                categoryStringRep.deleteCharAt(categoryStringRep.length() - 1);
                actualCategoriesStringRep.append(categoryStringRep).append("^");
            }
            actualCategoriesStringRep.deleteCharAt(actualCategoriesStringRep.length() - 1);

            StringBuilder actualAuthorsStringRep = new StringBuilder();
            for (Author author : actualAuthors) {
                StringBuilder authorStringRep = new StringBuilder(author.toString());
                authorStringRep.deleteCharAt(0);
                authorStringRep.deleteCharAt(authorStringRep.length() - 1);
                actualAuthorsStringRep.append(authorStringRep).append("^");
            }
            actualAuthorsStringRep.deleteCharAt(actualAuthorsStringRep.length() - 1);
            String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                    actualPaperID, actualTitle, actualCategoriesStringRep.toString(),
                    actualAuthorsStringRep.toString(), actualPublishDate.toString(),
                    actualAbstract, actualJournalReference, actualURL, actualUpvoteCount,
                    actualDownvoteCount);
            BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
            String row;
            while ((row = reader.readLine()) != null) {
                String[] paperAttributes = row.split(",");
                if (paperAttributes[0].equals(expectedPaperID)) {
                    break;
                }
            }

            assert (paper.getID().equals(expectedPaperID) && line.equals(row));
        } catch (IOException ioe) {
            System.out.println("IOException in testGetPaperByIDThatExists().");
        }
    }

    @Test
    public void testGetPapersByIDThatDoesNotExist() {
        try {
            LocalResearchPaperDataAccessObject lrpDAO = new LocalResearchPaperDataAccessObject(filepath, af, cf, rpf);
            String paperID = "2";
            ResearchPaper paper = lrpDAO.getPaperByID(paperID);
            assert (paper == null);
        } catch (IOException ioe) {
            System.out.println("IOException in testGetPaperByIDThatDoesNotExist().");
        }
    }

    @Test
    public void testWriteToDatabaseWithEmptyPaperCollection() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(emptyFilepath)));
            writer.write("");
            writer.close();

            LocalResearchPaperDataAccessObject lrpDAO = new LocalResearchPaperDataAccessObject(emptyFilepath, af, cf, rpf);
            BufferedReader reader = new BufferedReader(new FileReader(new File(emptyFilepath)));
            String inFileHeader = reader.readLine();
            String inObjectHeader = lrpDAO.papersCSVFileHeaderToString();

            int counter = 0;
            while (reader.readLine() != null) { counter++; }

            assert (inObjectHeader.equals(inFileHeader) && counter == 0);
        } catch (IOException ioe) {
            System.out.println("IOException in testWriteToDatabase().");
        }
    }

    @Test
    public void testWriteToDatabaseWithNonEmptyPaperCollection() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(emptyFilepath)));
            writer.write("");
            writer.close();

            LocalResearchPaperDataAccessObject lrpDAO = new LocalResearchPaperDataAccessObject(filepath, af, cf, rpf);
            lrpDAO.writeToDatabase(new File(emptyFilepath));

            BufferedReader reader = new BufferedReader(new FileReader(new File(emptyFilepath)));
            String header = reader.readLine();
            int counter = 1;
            while (reader.readLine() != null) {
                counter++;
            }

            assert (counter == 3 && lrpDAO.papersCSVFileHeaderToString().equals(header));

        } catch (IOException ioe) {
            System.out.println("IOException in testWriteToDatabaseWithNonEmptyPaperCollection().");
        }
    }

}