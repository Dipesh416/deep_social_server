package com.deep.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deep.models.Reels;

public interface ReelsRepository extends JpaRepository<Reels, Integer>{

	public List<Reels> findByUserId(Integer userId);
}
