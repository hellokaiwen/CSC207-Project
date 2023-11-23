package use_cases.showAuthor;

import app.ResearchPaperTransport;
import entities.Author;
import entities.ResearchPaper;

import java.util.ArrayList;
import java.util.List;

public class showAuthorInteractor implements showAuthorInputBoundary{

    final showAuthorDataAccessInterface dao;
    final showAuthorOutputBoundary showAuthorPresenter;

    public showAuthorInteractor(showAuthorOutputBoundary showAuthorPresenter, showAuthorDataAccessInterface dai) {
        this.showAuthorPresenter = showAuthorPresenter;
        this.dao = dai;
    }

    public void execute(showAuthorInputData inputData) {
        Author author = inputData.getAuthor();
        List<ResearchPaper> papers = dao.getPapersbyAuthor(author);
        if (papers.isEmpty()) {
            showAuthorPresenter.prepareFailurView("Author has no papers");
        }
        List<ResearchPaperTransport> transportPapers = new ArrayList<>();
        for (ResearchPaper paper: papers){
            transportPapers.add(
                    new ResearchPaperTransport(paper.getId(), paper.getTitle(), paper.getCategories(), paper.getAuthors(), paper.getPublishDate(), paper.getPaperAbstract(), paper.getJournalReference(), paper.getUrl(), paper.getUpvoteCount(), paper.getDownvoteCount() ));
        }
        showAuthorOutputData output = new showAuthorOutputData(author, transportPapers, false);
        showAuthorPresenter.prepareSuccessView(output);

    }
}