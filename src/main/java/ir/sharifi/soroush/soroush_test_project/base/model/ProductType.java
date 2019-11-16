package ir.sharifi.soroush.soroush_test_project.base.model;

public enum ProductType {



    DETERGENT(Values.DETERGENT),FOODSTUFF(Values.FOODSTUFF);

    ProductType(String val) {
        if (!this.name().equals(val))
            throw new IllegalArgumentException("There is not proper Value");

    }

    public static class Values {
        public static final String DETERGENT = "D";
        public static final String FOODSTUFF = "F";

    }
}
