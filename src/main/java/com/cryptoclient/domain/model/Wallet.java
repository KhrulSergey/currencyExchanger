package com.cryptoclient.domain.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @OneToMany(targetEntity = WalletItem.class, fetch = FetchType.EAGER, mappedBy = "wallet", orphanRemoval = true,
            cascade = CascadeType.ALL)
    private List<WalletItem> walletItemList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Wallet wallet = (Wallet) o;

        if (!id.equals(wallet.id)) return false;
        if (!name.equals(wallet.name)) return false;
        return Objects.equals(walletItemList, wallet.walletItemList);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 + name.hashCode();
        result = 31 * result + (walletItemList != null ? walletItemList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Wallet id " + id + ", owner's name= " + '\'' + name + '\'';
    }
}
