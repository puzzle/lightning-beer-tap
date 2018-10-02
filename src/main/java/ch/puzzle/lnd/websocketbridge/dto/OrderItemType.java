package ch.puzzle.lnd.websocketbridge.dto;

public enum OrderItemType {
    PRODUCT_1(5.0d),
    PRODUCT_2(5.0d),
    PRODUCT_3(5.0d),
    PRODUCT_4(5.0d);

    private Double price;

    OrderItemType(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
}
