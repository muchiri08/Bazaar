package muchiri.app.bazaar.user.model;

import jakarta.validation.constraints.NotBlank;

public final class Seller extends User {
    @NotBlank(message = "address is required")
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
