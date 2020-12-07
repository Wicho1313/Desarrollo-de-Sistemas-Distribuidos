public class ResponseModel {
    public ResponseModel(int responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public int getResponseCode() { return this.responseCode; }
    public String getMessage() { return this.message; }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    } 

    public void setMessage(String message) {
        this.message = message;
    }

    private int responseCode;
    private String message;
}
