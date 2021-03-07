package com.cryptoclient.mapper;

import com.cryptoclient.domain.model.Wallet;
import com.cryptoclient.domain.dto.WalletDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = WalletItemMapper.class)
public interface WalletMapper {

    Wallet mapToWallet(WalletDto walletDto);

    WalletDto mapToWalletDto(Wallet wallet);

    List<Wallet> mapToWalletList(List<WalletDto> walletDtoList);

    List<WalletDto> mapToWalletDtoList(List<Wallet> walletList);
}

