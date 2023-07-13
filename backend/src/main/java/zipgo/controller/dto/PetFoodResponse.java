package zipgo.controller.dto;

public class PetFoodResponse {
    private final Long id;
    private final String imageUrl;
    private final String name;
    private final String purchaseUrl;

    public PetFoodResponse(Long id, String imageUrl, String name, String purchaseUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.purchaseUrl = purchaseUrl;
    }

    public Long getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPurchaseUrl() {
        return purchaseUrl;
    }
}
