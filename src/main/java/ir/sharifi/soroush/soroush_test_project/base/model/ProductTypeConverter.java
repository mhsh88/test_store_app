package ir.sharifi.soroush.soroush_test_project.base.model;

import javax.persistence.AttributeConverter;

public class ProductTypeConverter implements AttributeConverter<ProductType, Character> {
    @Override
    public Character convertToDatabaseColumn(ProductType productType) {
        if (productType != null) {
            return productType.getValue().charAt(0);
        }
        return null;
    }

    @Override
    public ProductType convertToEntityAttribute(Character s) {
        if (s != null) {
            return ProductType.fromValue(s.toString());
        }
        return null;
    }
}
