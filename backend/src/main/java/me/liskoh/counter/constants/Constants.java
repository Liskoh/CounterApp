package me.liskoh.counter.constants;

import java.util.HashSet;

public class Constants {

    public static final HashSet<PermissionEnum> DEFAULT_PERMISSIONS = new HashSet<>() {{
        add(PermissionEnum.ADD_SELF_COUNTER);
        add(PermissionEnum.VIEW_SELF_COUNTER);
    }};
}
