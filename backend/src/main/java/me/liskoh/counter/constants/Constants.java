package me.liskoh.counter.constants;

import java.util.HashSet;

public class Constants {

    public static final HashSet<Integer> DEFAULT_PERMISSIONS = new HashSet<>() {{
        add(PermissionEnum.ADD_SELF_COUNTER.getId());
        add(PermissionEnum.VIEW_SELF_COUNTER.getId());
    }};
}
