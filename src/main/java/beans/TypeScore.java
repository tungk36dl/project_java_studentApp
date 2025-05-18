package beans;

public enum TypeScore {
    attendance("ATTENDANCE", "Điểm chuyên cần"),
    test("TEST", "Điểm kiểm tra"), 
    final_exam("FINAL_EXAM", "Điểm thi");
	
	private final String code;
	private final String description;
	
	TypeScore(String code, String description){
		this.code = code;
		this.description = description;
	}
    
	
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static TypeScore fromCode(String code) {
        for (TypeScore typeScore : values()) {
            if (typeScore.code.equalsIgnoreCase(code)) {
                return typeScore;
            }
        }
        throw new IllegalArgumentException("Invalid typeScore code: " + code);
    }
    
}
