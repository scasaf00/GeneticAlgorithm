package mastermind.utils;

public enum Colors {
    RED("\033[31m"),    // -> 1/7
    YELLOW("\033[33m"), // -> 2/7
    BLUE("\033[34m"),   // -> 3/7
    GREEN("\033[32m"),  // -> 4/7
    PURPLE("\033[35m"), // -> 5/7
    BLACK("\033[30m"),  // -> 6/7
    WHITE("\033[37m"),  // -> 7/7
    EMPTY("\u001B[0m"); // only for the reply initialization

    private final String value;

    Colors(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
