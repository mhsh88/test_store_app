package ir.sharifi.soroush.soroush_test_project.base.model;


public enum ProductType implements BaseEnum<String> {
    DETERGENT("D"), FOODSTUFF("F");

    ProductType(String value) {
        this.value = value;
    }

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ProductType fromValue(String value) {
        for (ProductType productType : ProductType.values()) {
            if (productType.getValue().equals(value)) {
                return productType;
            }
        }
        return null;
    }
}
