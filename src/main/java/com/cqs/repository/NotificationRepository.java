package com.cqs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cqs.entity.Notifications;

public interface NotificationRepository extends JpaRepository<Notifications, String>{
	
	@Query("SELECT n FROM Notifications n WHERE toId= ?1 ORDER BY createdAt DESC ")
	List<Notifications> getNotificationByToId(String toId);
}
