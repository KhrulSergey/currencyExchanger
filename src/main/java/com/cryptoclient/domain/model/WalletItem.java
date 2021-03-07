package com.cryptoclient.domain.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

import static java.util.Objects.hash;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Wallet_item")
public class WalletItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @Column(name = "currency")
    @Enumerated
    private Currency currency;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "status")
    @Enumerated
    private WalletItemStatus status = WalletItemStatus.FREE;

    @Column(name = "is_fraud")
    private boolean isFraud;

    @Setter(AccessLevel.PRIVATE)
    @Column(name = "check_sum")
    private Long checkSum;

    @PreUpdate
    @PrePersist
    private void fillCheckSum () {
        checkSum = createCheckSum();
    }

    private long createCheckSum (){
        return hash(id, currency, quantity);
    }

    public boolean isValidCheckSum (){
        return createCheckSum() == checkSum;
    }

    public WalletItem(@NotNull Wallet wallet, Currency currency, Double quantity) {
        this.wallet = wallet;
        this.currency = currency;
        this.quantity = quantity;
    }

    public WalletItem(Long id, @NotNull Wallet wallet, Currency currency, Double quantity) {
        this.id = id;
        this.wallet = wallet;
        this.currency = currency;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WalletItem that = (WalletItem) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!wallet.equals(that.wallet)) return false;
        if (currency != that.currency) return false;
        return quantity.equals(that.quantity);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + wallet.hashCode();
        result = 31 * result + currency.hashCode();
        result = 31 * result + quantity.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "WalletItem{" +
                "id=" + id +
                ", wallet=" + wallet +
                ", currency=" + currency +
                ", quantity=" + quantity +
                '}';
    }
}
