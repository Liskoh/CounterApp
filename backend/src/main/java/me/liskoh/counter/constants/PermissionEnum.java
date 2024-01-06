package me.liskoh.counter.constants;

import lombok.Getter;
import me.liskoh.counter.exceptions.permission.UnknownPermissionIdException;

import java.util.Arrays;

public enum PermissionEnum {

    ADD_SELF_COUNTER,
    ADD_OTHER_COUNTER,
    VIEW_SELF_COUNTER,
    VIEW_OTHER_COUNTER,

    ADD_PERMISSION,
    REMOVE_PERMISSION,
    ;
}
