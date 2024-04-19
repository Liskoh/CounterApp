package me.liskoh.counter.runners;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.liskoh.counter.constants.RoleEnum;
import me.liskoh.counter.convert.*;
import me.liskoh.counter.entities.UserEntity;
import me.liskoh.counter.repositories.UserRepository;
import me.liskoh.counter.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AccountGeneratorRunner implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserService userService;
    private final ItemExportService itemExportService;
    private final ItemRepository itemRepository;

    private List<PriceUpdateDto> getPrices(List<String> alreadyUpdatedDtos, List<ItemDataDto> dtos, ItemDataDto item) {
        if (item == null || item.getName() == null) {
            return new ArrayList<>();
        }
        List<PriceUpdateDto> prices = dtos.stream()
                .filter(dto -> dto.getName().equals(item.getName()))
                .map(dto -> {
                    PriceUpdateDto price = new PriceUpdateDto();
                    price.setLot1(dto.getLot_1());
                    price.setLot10(dto.getLot_10());
                    price.setLot100(dto.getLot_100());
                    price.setCraftCost(dto.getCraft_cost());
                    price.setGain(dto.getGain());
                    price.setTimestamp(dto.getCreated_at());
                    return price;
                })
                .toList();
        alreadyUpdatedDtos.add(item.getName());
        return prices;
    }

    @Override
    public void run(String... args) {
        itemRepository.findByNameWithPriceUpdates("Dofus_Vulbis").ifPresent(item -> {
            log.info("Item found: {}", item);
        });
//        try {
//            List<ItemDataDto> items = itemExportService.getFromJsonFile();
//            List<ItemDto> dtos = new ArrayList<>();
//            for (ItemDataDto item : items) {
//                if (alreadyUpdatedDtos.contains(item.getName())) {
//                    continue;
//                }
//
//                ItemDto itemDto = new ItemDto();
//                itemDto.setName(item.getName());
//                itemDto.setPriceUpdates(getPrices(alreadyUpdatedDtos, items, item));
//                dtos.add(itemDto);
////                if (count >= 3) {
////                    break;
////                }
//                log.info("Item added to DTO: {}", item.getName());
////                count++;
//            }
//
//            itemExportService.writeToJsonFile(dtos);
//            log.info("Items exported successfully to items.json file");
//        } catch (Exception e) {
////            log.info("Failed to read items from file", e);
////            log.error("Failed to export items to file", e);
//            e.printStackTrace();
//        }
//
//        log.info("Items exported successfully");
        if (userRepository.count() != 0) {
            return;
        }

        log.info("User repository is empty, generating default accounts");

        List.of(RoleEnum.values()).forEach(role -> {
            final String name = role.name().toLowerCase();
            UserEntity user = new UserEntity(name, name);
            user.setRole(role);

            userService.create(user);
            log.info("Created default user: USERNAME: {}, PASSWORD: {}, AUTHORITIES: {}", user.getUsername(), name, user.getAuthorities());
        });
    }
}
