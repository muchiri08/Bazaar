package muchiri.app.bazaar.product.model;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class Product {
    private long id;
    @NotNull(message = "seller id is required")
    private Long sellerId;
    @NotBlank(message = "product name is required")
    private String name;
    private String description;
    @NotBlank(message = "product type is required")
    private String type;
    @NotBlank(message = "product url is required")
    private String url;
    @NotNull(message = "starting bid is required")
    private BigDecimal startingBid;
    @NotNull(message = "auction start time is required")
    private Instant auctionStartTime;
    @NotNull(message = "auction end time is required")
    private Instant auctionEndTime;
    private Status status;
    @NotNull(message = "isListed is required")
    @JsonProperty("isListed")
    private Boolean isListed;
    @NotBlank(message = "pickup location is required")
    private String pickupLocation;
    @JsonInclude(value = Include.NON_NULL)
    private Instant createdAt;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getStartingBid() {
        return startingBid;
    }

    public void setStartingBid(BigDecimal startingBid) {
        this.startingBid = startingBid;
    }

    public Instant getAuctionStartTime() {
        return auctionStartTime;
    }

    public void setAuctionStartTime(Instant auctionStartTime) {
        this.auctionStartTime = auctionStartTime;
    }

    public Instant getAuctionEndTime() {
        return auctionEndTime;
    }

    public void setAuctionEndTime(Instant auctionEndTime) {
        this.auctionEndTime = auctionEndTime;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Boolean getIsListed() {
        return isListed;
    }

    public void setIsListed(Boolean isListed) {
        this.isListed = isListed;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", sellerId=" + sellerId + ", name=" + name + ", description=" + description
                + ", type=" + type + ", url=" + url + ", startingBid=" + startingBid + ", auctionStartTime="
                + auctionStartTime + ", auctionEndTime=" + auctionEndTime + ", status=" + status + ", isListed="
                + isListed + ", pickupLocation=" + pickupLocation + ", createdAt=" + createdAt + "]";
    }

}
