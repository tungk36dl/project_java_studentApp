package beans;

public enum Role {
    STUDENT("STUDENT", "Sinh viên"),
    TEACHER("TEACHER", "Giảng viên"),
    PDT("PDT", "Phòng đào tạo");

    private final String code;
    private final String description;

    Role(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Role fromCode(String code) {
        for (Role role : values()) {
            if (role.code.equalsIgnoreCase(code)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role code: " + code);
    }
}
