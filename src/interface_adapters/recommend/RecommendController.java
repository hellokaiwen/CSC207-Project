package interface_adapters.recommend;

import use_cases.recommend.RecommendInputBoundary;
import use_cases.recommend.RecommendInputData;

import java.util.List;

public class RecommendController {

    final RecommendInputBoundary recommendUseCaseInteractor;

    public RecommendController(RecommendInputBoundary recommendUseCaseInteractor) {
        this.recommendUseCaseInteractor = recommendUseCaseInteractor;
    }

    public void execute(String username, List<List<String>> preferredCategories,
                        boolean prioritizeSubcategorySearch, boolean prioritizeUpvotePercentageSearch,
                        boolean wantAutoRecommend) {
        RecommendInputData recommendInputData = new RecommendInputData(
                username, preferredCategories, prioritizeSubcategorySearch,
                prioritizeUpvotePercentageSearch, wantAutoRecommend
        );
        this.recommendUseCaseInteractor.execute(recommendInputData);
    }

}