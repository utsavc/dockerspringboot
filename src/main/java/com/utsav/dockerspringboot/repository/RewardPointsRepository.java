package com.utsav.dockerspringboot.repository;

import com.utsav.dockerspringboot.model.RewardPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RewardPointsRepository extends JpaRepository<RewardPoints, Long> {
    @Query("SELECT SUM(rp.pointsEarned), SUM(rp.pointsRedeemed) FROM RewardPoints rp")
    List<Object[]> getTotalPointsEarnedAndRedeemed();
}
