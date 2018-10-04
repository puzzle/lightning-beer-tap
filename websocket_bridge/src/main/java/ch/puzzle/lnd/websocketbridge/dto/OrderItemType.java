package ch.puzzle.lnd.websocketbridge.dto;

public enum OrderItemType {
	LARGE_BEER(2.0d),
	SMALL_BEER(1.0d);

    private Double price;

    OrderItemType(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
}
