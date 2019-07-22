package be.xplore.recordreplay.service;

import be.xplore.fakes.model.Response;
import be.xplore.fakes.model.Stub;
import be.xplore.fakes.service.MemoryRepository;
import be.xplore.usecases.RecordUseCase;

import java.util.Optional;

public class RecordHttpServlet extends AbstractHttpServlet {

    private static final long serialVersionUID = 5440889442582869400L;
    private final RecordUseCase useCase;

    public RecordHttpServlet(){
        super();
        this.useCase = new RecordUseCase(new MemoryRepository());
    }

    @Override
    protected Optional<Response> executeUseCase(Stub stub) {
        return useCase.execute(stub);
    }
}
