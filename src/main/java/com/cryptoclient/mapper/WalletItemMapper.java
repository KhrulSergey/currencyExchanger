package com.cryptoclient.mapper;

import com.cryptoclient.domain.model.WalletItem;
import com.cryptoclient.domain.dto.WalletItemDto;
import com.cryptoclient.service.interfaces.WalletService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class WalletItemMapper {

    @Autowired
    protected WalletService walletService;

    public WalletItem fromDto(WalletItemDto walletItemDto) {
        if (walletItemDto.getId() == null) {
            return new WalletItem(walletService.findWalletById(walletItemDto.getWalletId()),
                    walletItemDto.getCurrency(), walletItemDto.getQuantity());
        } else {
            return new WalletItem(walletItemDto.getId(),
                    walletService.findWalletById(walletItemDto.getWalletId()),
                    walletItemDto.getCurrency(),
                    walletItemDto.getQuantity());
        }
    }

    @Mapping(target = "walletId", source = "walletItem.wallet.id")
    public abstract WalletItemDto toDto(WalletItem walletItem);

    public abstract List<WalletItem> fromDto(List<WalletItemDto> walletItemDtoList);

    public abstract List<WalletItemDto> toDto(List<WalletItem> walletItemList);
}
