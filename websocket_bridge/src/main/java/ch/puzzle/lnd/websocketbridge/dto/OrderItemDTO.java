package ch.puzzle.lnd.websocketbridge.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderItemDTO {

    private Long id;
    private String productKey;
    private Integer count;
    private List<String> options = new ArrayList<>();
    private Double total;

    public Long getId() {
        return id;
    }

    public String getProductKey() {
		return productKey;
	}

	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}

	public void setId(Long id) {
        this.id = id;
    }

    

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OrderItemDTO itemDTO = (OrderItemDTO) o;
        if (itemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
            "id=" + id +
            ", productKey=" + productKey +
            ", count=" + count +
            ", options=" + options +
            ", total=" + total +
            '}';
    }
}

