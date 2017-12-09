package pl.kohutmariusz.authservice.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import pl.kohutmariusz.authservice.domain.TokenBlackList;
import pl.kohutmariusz.authservice.repository.TokenBlackListRepo;

@Service
public class TokenBlackListService {

    @Autowired
    TokenBlackListRepo tokenBlackListRepo;

    public Boolean isBlackListed( String jti ) throws TokenNotFoundException {
        Optional<TokenBlackList> token = tokenBlackListRepo.findByJti(jti);
        if ( token.isPresent() ) {
            return token.get().getIsBlackListed();
        } else {
            throw new TokenNotFoundException(jti);
        }
    }

    @Async
    public void addToEnabledList(String userId, String jti, Long expired ) {
        // clean all black listed tokens for user
        List<TokenBlackList> list = tokenBlackListRepo.queryAllByUserIdAndIsBlackListedTrue(userId);
        if (list != null && !list.isEmpty()) {
            list.forEach(
                    token -> {
                        token.setIsBlackListed(true);
                        tokenBlackListRepo.save(token);
                    }
            );
        }
        // Add new token white listed
        TokenBlackList tokenBlackList = new TokenBlackList(userId, jti, expired);
        tokenBlackList.setIsBlackListed(false);
        tokenBlackListRepo.save(tokenBlackList);
        tokenBlackListRepo.deleteAllByUserIdAndExpiresBefore(userId, new Date().getTime());
    }

    @Async
    public void addToBlackList(String jti ) throws TokenNotFoundException {
        Optional<TokenBlackList> tokenBlackList = tokenBlackListRepo.findByJti(jti);
        if ( tokenBlackList.isPresent() ) {
            tokenBlackList.get().setIsBlackListed(true);
            tokenBlackListRepo.save(tokenBlackList.get());
        } else throw new TokenNotFoundException(jti);
    }

    public static class TokenNotFoundException extends Exception {
        /**
         * 
         */
        private static final long serialVersionUID = -2221379277309824357L;
        public final String jti;
        public final String message;
        public TokenNotFoundException(String jti) {
            super();
            this.jti = jti;
            message = String.format("Token with jti[%s] not found.",jti);
        }
    }

}
