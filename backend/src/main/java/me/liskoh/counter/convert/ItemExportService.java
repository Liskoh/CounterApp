package me.liskoh.counter.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemExportService {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private static final ObjectMapper MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Autowired
    public ItemExportService(ItemRepository itemRepository, ItemService itemService) {
        this.itemRepository = itemRepository;
        this.itemService = itemService;
    }

    public List<ItemDataDto> getFromJsonFile() throws IOException {
        return MAPPER.readValue(new File("items.json"), MAPPER.getTypeFactory().constructCollectionType(List.class, ItemDataDto.class));
    }

    public void writeToJsonFile(List<ItemDto> items) throws IOException {
        MAPPER.writeValue(new File("items_updated.json"), items);
    }

    public void saveToDatabase() throws IOException {
        List<ItemDto> datas = MAPPER.readValue(new File("items_updated_last.json"), MAPPER.getTypeFactory().constructCollectionType(List.class, ItemDto.class));

        for (ItemDto data : datas) {
            Item item = new Item();
            item.setName(data.getName());
            item.setPriceUpdates(data.getPriceUpdates().stream()
                    .map(priceUpdateDto -> {
                        PriceUpdate priceUpdate = new PriceUpdate();
                        priceUpdate.setLot1(priceUpdateDto.getLot1());
                        priceUpdate.setLot10(priceUpdateDto.getLot10());
                        priceUpdate.setLot100(priceUpdateDto.getLot100());
                        priceUpdate.setCraftCost(priceUpdateDto.getCraftCost());
                        priceUpdate.setGain(priceUpdateDto.getGain());
                        priceUpdate.setItem(item);
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
// ...
                        priceUpdate.setTimestamp(LocalDateTime.parse(priceUpdateDto.getTimestamp(), formatter));

//                        priceUpdate.setTimestamp(LocalDateTime.parse(priceUpdateDto.getTimestamp()));
                        return priceUpdate;
                    })
                    .collect(Collectors.toList()));

            itemRepository.save(item);
            log.info("Item saved to database: {}", item.getName());
        }
    }

    public void exportItemWithPriceUpdatesToJsonFile(Long itemId, String filePath) throws IOException {
        Optional<Item> itemOptional = itemRepository.findByIdWithPriceUpdates(itemId);

        if (!itemOptional.isPresent()) {
            throw new IllegalStateException("Item not found with id: " + itemId);
        }

        ItemDto itemDto = convertToItemDto(itemOptional.get());
        MAPPER.writeValue(new File(filePath), itemDto);
    }

    public void exportAllToJsonFile(String filePath) throws IOException {
//        List<String> itemNames = itemRepository.findAllNames();
        List<String> itemNames = List.of("Archi-monstre:_Ouature_la_Mobile", "Pailleton");
        log.info("Exporting {} items to file: {}", itemNames.size(), filePath);
        List<ItemDto> dtos = itemNames.stream()
                .map(itemRepository::findByNameWithPriceUpdates)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::convertToItemDto)
                .toList();

        File file = new File(filePath);
        MAPPER.writeValue(file, dtos);
        log.info("Items exported successfully to path: {}", file.getAbsolutePath());
    }

    private ItemDto convertToItemDto(Item item) {
        return itemService.convertToDto(item);
    }
}
