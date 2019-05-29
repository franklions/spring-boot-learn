package com.lianwukeji.aiar.example.repository;

import com.lianwukeji.aiar.example.domain.CallbackActionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author flsh
 * @version 1.0
 * @date 2019-05-23
 * @since Jdk 1.8
 */
public interface ActionRepository extends JpaRepository<CallbackActionEntity,Long> {
}
