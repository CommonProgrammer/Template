package pl.kohutmariusz.authservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "token_black_list")
@Data
public class TokenBlackList {

    @Id
    private String jti;
    private String userId;
    private Long expires;
    private Boolean isBlackListed;

    public TokenBlackList() {
    }

    public TokenBlackList(String userId, String jti, Long expires) {
        this.jti = jti;
        this.userId = userId;
        this.expires = expires;
    }

}
