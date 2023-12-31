package view;

import interface_adapters.RecommendHome.RecommendHomeController;
import interface_adapters.RecommendHome.RecommendHomeViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.library.LibraryController;
import interface_adapters.library.LibraryPresenter;
import interface_adapters.library.LibraryViewModel;
import use_cases.library.LibraryInputBoundary;
import use_cases.library.LibraryOutputData;
import use_cases.recommend.RecommendInputBoundary;
import use_cases.showpdf.ShowPdfInputBoundary;
import use_cases.showpdf.ShowPdfInputData;
import use_cases.showpdf.ShowPdfInteractor;
import view.LibraryView;
import view.RecommendHomeView;

import javax.swing.*;
import java.util.ArrayList;

public class LibraryViewTest {
    @org.junit.Test
    public void TestLibraryViewTest(){
        // create the UI; note, we don't make a real InputBoundary,
        // since we don't need it for this test.
        ShowPdfInputBoundary sib = new ShowPdfInteractor(null);
        LibraryController controller = new LibraryController(sib, null);
        LibraryViewModel viewModel = new LibraryViewModel();
        LibraryPresenter presenter = new LibraryPresenter(new ViewManagerModel(),viewModel);
        LibraryView libView = new LibraryView(viewModel,controller);
        JFrame jframe = new JFrame();
        jframe.setContentPane(libView);
        jframe.pack();
        jframe.setVisible(true);
        LibraryOutputData libraryOutputData = new LibraryOutputData();
        libraryOutputData.addPaperPDF("www");
        libraryOutputData.addID("1");
        libraryOutputData.addPaperName("e");
        presenter.prepareLibrary(libraryOutputData);
    }
}
