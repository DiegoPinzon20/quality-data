package co.com.quality.data.utilities;

import lombok.Getter;

@Getter
public enum Separator {

    COLON(':'),
    HYPHEN('-'),
    COMMA(','),
    SEMICOLON(';'),
    SWUNG_DASH('~'),
    SPACE(' '),
    HASH('#'),
    SLASH('/'),
    VERTICAL_BAR('|'),
    UNDERSCORE('_'),
    LESS_THAN('<'),
    MORE_THAN('>'),
    PERIOD('.');

    private final char value;

    Separator(char value) {
        this.value = value;
    }

}