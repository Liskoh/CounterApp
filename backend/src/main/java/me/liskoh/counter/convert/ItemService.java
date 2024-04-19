package me.liskoh.counter.convert;

import lombok.extern.slf4j.Slf4j;
import me.liskoh.counter.exceptions.impl.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public ItemDto getItemWithPriceUpdates(String name) {
        return itemRepository.findByNameWithPriceUpdates(name)
                .map(this::convertToDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public ItemDto getItemWithPriceUpdates(Long itemId) {
        return itemRepository.findByIdWithPriceUpdates(itemId)
                .map(this::convertToDto)
                .orElseThrow(UserNotFoundException::new);
    }

    public ItemDto convertToDto(Item item) {
        ItemDto dto = new ItemDto();
        dto.setName(item.getName());
        dto.setPriceUpdates(item.getPriceUpdates().stream()
                .map(this::convertPriceUpdateToDto)
                .collect(Collectors.toList()));

        log.info("Item converted to DTO: {}", dto.getName());
        return dto;
    }

    private PriceUpdateDto convertPriceUpdateToDto(PriceUpdate update) {
        PriceUpdateDto dto = new PriceUpdateDto();
        dto.setLot1(update.getLot1());
        dto.setLot10(update.getLot10());
        dto.setLot100(update.getLot100());
        dto.setCraftCost(update.getCraftCost());
        dto.setGain(update.getGain());
//        dto.setTimestamp(update.getTimestamp());
        return dto;
    }
}
