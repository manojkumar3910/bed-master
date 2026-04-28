package com.bedmaster.inventory.repository;

import com.bedmaster.inventory.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    List<Room> findByUnitID(Integer unitID);
}