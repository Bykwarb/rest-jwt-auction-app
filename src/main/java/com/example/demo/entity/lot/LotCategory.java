package com.example.demo.entity.lot;

public enum LotCategory {
    CATEGORY_TOYS("TOYS"),
    CATEGORY_REAL_ESTATE("REAL ESTATE"),
    CATEGORY_CARS("CARS"),
    CATEGORY_FURNITURE("FURNITURE"),
    CATEGORY_ELECTRONICS("ELECTRONICS"),
    CATEGORY_FASHION_AND_STYLE("FASHION & STYLE"),
    CATEGORY_HOBBIES_AND_SPORTS("HOBBIES & SPORTS");

    private final String category;

    LotCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public static LotCategory valueOfLabel(String label){
        for (LotCategory e : values()){
            if (e.getCategory().equalsIgnoreCase(label)){
                return e;
            }
        }
        return null;
    }
}
