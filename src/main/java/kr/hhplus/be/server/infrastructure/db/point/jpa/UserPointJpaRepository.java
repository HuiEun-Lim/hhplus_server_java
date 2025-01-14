package kr.hhplus.be.server.infrastructure.db.point.jpa;

import jakarta.persistence.LockModeType;
import kr.hhplus.be.server.domain.point.entity.UserPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserPointJpaRepository extends JpaRepository<UserPoint, Long>  {

    UserPoint findByUserId(Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT u FROM UserPoint u WHERE u.userId = :userId")
    UserPoint findByUserIdWithLock(@Param("userId") Long userId);
}
