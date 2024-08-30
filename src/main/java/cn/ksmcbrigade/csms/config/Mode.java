package cn.ksmcbrigade.csms.config;

public enum Mode {
    VANILLA(0),
    CUSTOM(1),
    NO_BACKGROUND(2),
    NONE(3);

    public final int index;

    Mode(int index){
        this.index = index;
    }

    public static Mode valueOf(int index){
        for (Mode value : Mode.values()) {
            if(value.index==index) return value;
        }
        return VANILLA;
    }
}
