package app;

import data_access.*;
import entities.*;
import interface_adapters.RecommendHome.RecommendHomeController;
import interface_adapters.RecommendHome.RecommendHomePresenter;
import interface_adapters.RecommendHome.RecommendHomeViewModel;
import interface_adapters.ViewManagerModel;
import interface_adapters.library.LibraryController;
import interface_adapters.library.LibraryPresenter;
import interface_adapters.library.LibraryViewModel;
import interface_adapters.localsave.LocalSaveController;
import interface_adapters.login.LoginController;
import interface_adapters.login.LoginViewModel;
import interface_adapters.recommend.RecommendPresenter;
import interface_adapters.recommend.RecommendViewModel;
import interface_adapters.save.SaveController;
import interface_adapters.save.SavePresenter;
import interface_adapters.save.SaveViewModel;
import interface_adapters.signup.SignupViewModel;
import interface_adapters.switchView.SwitchViewPresenter;
import interface_adapters.switchView.SwitchViewViewModel;
import interface_adapters.vote.VoteController;
import interface_adapters.vote.VotePresenter;
import interface_adapters.vote.VoteViewModel;
import org.w3c.dom.css.CSSValue;
import use_cases.library.LibraryInteractor;
import use_cases.localsave.LocalSaveInteractor;
import use_cases.login.LoginInteractor;
import use_cases.recommend.RecommendInteractor;
import use_cases.save.SaveInteractor;
import use_cases.switchView.SwitchViewInteractor;
import use_cases.vote.VoteInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args) throws IOException {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("cs", "cs.AI"));
        categories.add(new Category("cs", "cs.CL"));
        categories.add(new Category("cs", "cs.CC"));
        categories.add(new Category("cs", "cs.CE"));
        categories.add(new Category("cs", "cs.CG"));
        categories.add(new Category("cs", "cs.GT"));
        categories.add(new Category("cs", "cs.CV"));
        categories.add(new Category("cs", "cs.CY"));
        categories.add(new Category("cs", "cs.CR"));
        categories.add(new Category("cs", "cs.DS"));
        categories.add(new Category("cs", "cs.DB"));
        categories.add(new Category("cs", "cs.DL"));
        categories.add(new Category("cs", "cs.DM"));
        categories.add(new Category("cs", "cs.DC"));
        categories.add(new Category("cs", "cs.ET"));
        categories.add(new Category("cs", "cs.FL"));
        categories.add(new Category("cs", "cs.GL"));
        categories.add(new Category("cs", "cs.CR"));
        categories.add(new Category("cs", "cs.AR"));
        categories.add(new Category("cs", "cs.HC"));
        categories.add(new Category("cs", "cs.IR"));
        categories.add(new Category("cs", "cs.IT"));
        categories.add(new Category("cs", "cs.LO"));
        categories.add(new Category("cs", "cs.LG"));
        categories.add(new Category("cs", "cs.MS"));
        categories.add(new Category("cs", "cs.MA"));
        categories.add(new Category("cs", "cs.MM"));
        categories.add(new Category("cs", "cs.NI"));
        categories.add(new Category("cs", "cs.NE"));
        categories.add(new Category("cs", "cs.NA"));
        categories.add(new Category("cs", "cs.OS"));
        categories.add(new Category("cs", "cs.OH"));
        categories.add(new Category("cs", "cs.PF"));
        categories.add(new Category("cs", "cs.PL"));
        categories.add(new Category("cs", "cs.RO"));
        categories.add(new Category("cs", "cs.SI"));
        categories.add(new Category("cs", "cs.SE"));
        categories.add(new Category("cs", "cs.SD"));
        categories.add(new Category("cs", "cs.SC"));
        categories.add(new Category("cs", "cs.SY"));
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        JFrame application = new JFrame("Arxiv Paper Recommender");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.setPreferredSize(new Dimension(1920,1080));
        application.setResizable(false);
        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);
        DataAccessFacade DAO;
        try {
            DAO = new DataAccessFacade(categories);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // The data for the views, such as username and password, are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are observable, and will
        // be observed by the Views.
        HomePageView homePageView = new HomePageView(viewManagerModel);
        LoginViewModel loginViewModel = new LoginViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        views.add(homePageView,homePageView.viewName);
        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, DAO);
        views.add(signupView, signupView.viewName);
        RecommendHomeViewModel recommendHomeViewModel = new RecommendHomeViewModel();
        LibraryViewModel libraryViewModel = new LibraryViewModel();
        LibraryPresenter libraryPresenter = new LibraryPresenter(viewManagerModel,libraryViewModel);
        LibraryInteractor libraryInteractor = new LibraryInteractor(DAO,libraryPresenter);

        ArxivDataAccessObject arxivDataAccessObject = new ArxivDataAccessObject(categories, new AuthorFactory());
        LocalUserDataAccessObject localUserDataAccessObject = new LocalUserDataAccessObject(new CommonUserFactory());
        RecommendDataAccessObject recommendDataAccessObject = new RecommendDataAccessObject(arxivDataAccessObject, localUserDataAccessObject);
        RecommendViewModel recommendViewModel = new RecommendViewModel();
        RecommendPresenter recommendPresenter = new RecommendPresenter(recommendViewModel, viewManagerModel);
        RecommendInteractor recommendInteractor = new RecommendInteractor(recommendDataAccessObject, recommendPresenter, new CategoryFactory(), new PreferenceDataFactory());
        RecommendHomeView recommendHomeView = new RecommendHomeView(recommendHomeViewModel, new RecommendHomeController(recommendInteractor,libraryInteractor));
        RecommendHomePresenter recommendHomePresenter = new RecommendHomePresenter(viewManagerModel,recommendHomeViewModel,loginViewModel);
        LoginView loginView = new LoginView(loginViewModel,new LoginController(new LoginInteractor(DAO,recommendHomePresenter)));
        views.add(loginView, loginView.viewName);
        views.add(recommendHomeView,recommendHomeView.viewName);
        LibraryView libraryView = new LibraryView(libraryViewModel, new LibraryController(null,new SwitchViewInteractor(null,new SwitchViewPresenter(new SwitchViewViewModel(),viewManagerModel))));
        views.add(libraryView, libraryView.viewName);
        SaveViewModel saveViewModel = new SaveViewModel();
        VoteViewModel voteViewModel = new VoteViewModel();

        SavePresenter savePresenter = new SavePresenter(saveViewModel,viewManagerModel);
        SaveController saveController = new SaveController(new SaveInteractor(DAO,savePresenter));
        VotePresenter votePresenter = new VotePresenter(voteViewModel,viewManagerModel);
        VoteController voteController = new VoteController(new VoteInteractor(DAO,votePresenter));
        LocalSaveController localSaveController = new LocalSaveController(new LocalSaveInteractor(null));
        RecommendedPapersView recommendedPapersView = new RecommendedPapersView(saveViewModel,voteViewModel,recommendViewModel,recommendHomeViewModel,saveController,voteController,localSaveController);
        views.add(recommendedPapersView, recommendedPapersView.viewName);


        viewManagerModel.setActiveView(homePageView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}