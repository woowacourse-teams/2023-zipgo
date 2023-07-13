package zipgo.domain;

public class PetFood {
    private Long id;
    private String name;
    private String link;
    private String image;
    private Keyword keyword;

    public PetFood(String name, String link, String image) {
        this.name = name;
        this.link = link;
        this.image = image;
    }

    public PetFood(final String name, final String link, final String image, final Keyword keyword) {
        this.name = name;
        this.link = link;
        this.image = image;
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public Keyword getKeyword() {
        return keyword;
    }
}
