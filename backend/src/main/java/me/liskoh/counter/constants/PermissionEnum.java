package me.liskoh.counter.constants;

import lombok.Getter;
import me.liskoh.counter.exceptions.permission.UnknownPermissionIdException;

import java.util.Arrays;

@Getter
public enum PermissionEnum {

    ADD_SELF_COUNTER(1),
    ADD_OTHER_COUNTER(2),

    VIEW_SELF_COUNTER(3),
    VIEW_OTHER_COUNTER(4),

    ADD_PERMISSION(5),
    REMOVE_PERMISSION(6),
    ;

    private final int id;

    PermissionEnum(int id) {
        this.id = id;
    }

    public static PermissionEnum fromId(int id) {
        return Arrays.stream(PermissionEnum.values())
                .filter(permissionEnum -> permissionEnum.getId() == id)
                .findFirst()
                .orElseThrow(() -> new UnknownPermissionIdException(id));
    }
}
