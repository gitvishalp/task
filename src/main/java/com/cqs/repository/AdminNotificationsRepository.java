package com.cqs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cqs.entity.AdminNotifications;

public interface AdminNotificationsRepository extends JpaRepository<AdminNotifications, String> {


	@Query("SELECT n from AdminNotifications n ORDER BY createdAt DESC ")
	List<AdminNotifications> adminNotification();
	
}
