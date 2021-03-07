package com.cryptoclient.controller;

import com.cryptoclient.domain.dto.WalletItemDto;
import com.cryptoclient.mapper.WalletItemMapper;
import com.cryptoclient.service.interfaces.WalletItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/walletItem")
@RequiredArgsConstructor
public class WalletItemController {

    private final WalletItemMapper walletItemMapper;
    private final WalletItemService walletItemService;

    @GetMapping
    public List<WalletItemDto> getAllWalletItem() {
        return walletItemMapper.toDto(walletItemService.getWalletItems());
    }

    @GetMapping("{walletItemId}")
    public WalletItemDto getWalletItemById(@PathVariable Long walletItemId) {
        return walletItemMapper.toDto(walletItemService.findWalletItemById(walletItemId));
    }

    @PostMapping
    public WalletItemDto postWalletItem(@RequestBody WalletItemDto walletItemDto) {
        return walletItemMapper.toDto(walletItemService.postWalletItem
                (walletItemMapper.fromDto(walletItemDto)));
    }

    @PutMapping
    public WalletItemDto updateWalletPosition(@RequestBody WalletItemDto walletItemDto) {
        return walletItemMapper.toDto(walletItemService.updateWalletItem
                (walletItemMapper.fromDto(walletItemDto)));
    }

    @DeleteMapping("{walletItemId}")
    public void deleteWalletItem(@PathVariable Long walletItemId) {
        walletItemService.deleteWalletItem(walletItemId);
    }
}
