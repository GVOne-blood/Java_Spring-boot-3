package com.example.GVOne_blood.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResponseSuccess extends ResponseEntity<ResponseSuccess.PayLoad> {
    //use for PUT, PATCH, DELETE
    public ResponseSuccess(HttpStatusCode status, String message) {

        super(new PayLoad(message, status.value()), HttpStatus.OK);
    }
    // use for GET, POST vì có data trả về
    public ResponseSuccess(HttpStatusCode status, String message, Object data) {

        super(new PayLoad(message, status.value(), data), HttpStatus.OK);
    }

    public static class PayLoad{
    private    final  String message;
    private    final  int status;
    private    Object data;

        public PayLoad(String message, int status, Object data) {
            this.message = message;
            this.status = status;
            this.data = data;
        }
        public PayLoad(String message, int status) {
            this.message = message;
            this.status = status;

        }

        public String getMessage() {
            return message;
        }

        public int getStatus() {
            return status;
        }

        public Object getData() {
            return data;
        }
    }


}
