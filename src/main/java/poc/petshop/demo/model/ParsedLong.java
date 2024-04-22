package poc.petshop.demo.model;

public class ParsedLong {

    private Long resultLong;

    private boolean isSuccess;

    private String errorMessage;

    public ParsedLong() {
    }

    public Long getResultLong() {
        return resultLong;
    }

    public void setResultLong(Long resultLong) {
        this.resultLong = resultLong;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
