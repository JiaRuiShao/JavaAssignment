package rest.demo.studentslibrary.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {
    private int errorCode;
    private String message;
    private String request;
    private String requestType;
}
