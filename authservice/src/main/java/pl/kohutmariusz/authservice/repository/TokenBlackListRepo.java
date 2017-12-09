package pl.kohutmariusz.authservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import pl.kohutmariusz.authservice.domain.TokenBlackList;

public interface TokenBlackListRepo extends Repository<TokenBlackList, Long> {

    Optional<TokenBlackList> findByJti(String jti);

    List<TokenBlackList> queryAllByUserIdAndIsBlackListedTrue(String userId);

    void save(TokenBlackList tokenBlackList);

    List<TokenBlackList> deleteAllByUserIdAndExpiresBefore(String userId, Long date);
}
