package zipgo.petfood.stub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import zipgo.petfood.domain.Keyword;
import zipgo.petfood.domain.repository.KeywordRepository;

public class FakeKeywordRepository implements KeywordRepository {

    private final Map<Long, Keyword> keywords = new HashMap<>();

    public Long id = 1L;

    @Override
    public Keyword save(Keyword keyword) {
        this.keywords.put(id, keyword);
        Keyword response = generateKeyword(id, keyword);
        this.id++;
        return response;
    }

    @Override
    public List<Keyword> findAll() {
        return keywords.keySet().stream()
                .map(id -> generateKeyword(id, keywords.get(id)))
                .toList();
    }

    @Override
    public Optional<Keyword> findByName(String name) {
        return keywords.keySet().stream()
                .filter(id -> keywords.get(id).getName().equals(name))
                .map(id -> generateKeyword(id, keywords.get(id)))
                .findFirst();
    }

    public Keyword generateKeyword(Long id, Keyword keyword) {
        return new Keyword(id, keyword.getName());
    }

}
