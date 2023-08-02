package zipgo.petfood.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.repository.KeywordRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KeywordQueryService {

    private final KeywordRepository keywordRepository;

    public List<Keyword> getAll() {
        return keywordRepository.findAll();
    }

}
