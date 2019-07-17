package be.xplore.fakes.usecase;

import be.xplore.fakes.model.Request;
import be.xplore.fakes.service.*;

import java.util.ArrayList;
import java.util.List;

public class RecordReplayUseCase {

    private final ReplayUseCase replay;
    private final ForwardRequestUseCase forward;
    private final RecordUseCase record;
    private final List<RequestMatcher> matchers;

    public void execute(Request request) {
    }
    // execute replay use case
    // if existing recording is found, return recording
    // if no existing recording is found, execute the forward use case
    // store the response by executing the record use case
    // return the response
 private static class Builder {
        private ReplayUseCase replay;
        private RecordUseCase record;
        private ForwardRequestUseCase forward;
        private List<RequestMatcher> matchers = new ArrayList<>();

        private Builder() {
            Repository memRepo = new MemoryRepository();
            initDefaultMatchers();
            replay = new ReplayUseCase(memRepo, matchers);
            record = new RecordUseCase(memRepo);
            forward = new ForwardRequestUseCase(new DefaultHttpClient());
        }

        public Builder repository(Repository repository){
            replay = new ReplayUseCase(repository);
            record = new RecordUseCase(repository);
            return this;
        }

        public Builder httpClient(HttpClient httpClient){
            forward = new ForwardRequestUseCase(httpClient);
            return this;
        }

        public Builder matchers(List<RequestMatcher> matchers) {
            this.matchers = matchers;
            return this;
        }

        private void initDefaultMatchers(){
            matchers.add(new RequestHeaderMatcher());
            matchers.add(new RequestParamMatcher());
            matchers.add(new RequestBodyMatcher());
            matchers.add(new RequestMethodMatcher());
            matchers.add(new RequestPathMatcher());
        }

    }

}
