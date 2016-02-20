package osykov.ShopsProject;

public enum Status {
	AVAILABLE(1), ABSENT(2), EXPECTED(3);

	private int code;
	 
    private Status(int code) {
        this.code = code;
    }
 
    public int getCode() {
        return code;
    }

}
